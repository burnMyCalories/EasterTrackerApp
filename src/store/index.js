import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    mapLoaded: false,
    currentUser: localStorage.getItem('currentUser') ? JSON.parse(localStorage.getItem('currentUser')) : null,
    alert: {
      msg: '',
      type: 'info',
      sync: false
    },
    alertTimer: null,
    userPortrait: null,
    GPSWatchID: null,
    myLatitude: null,
    myLongitude: null,
    myEggs: [],
    othersEggs: [],
    firedEgg: null,
    isEditProfile: false,
    showAbout: false
  },
  mutations: {
    login (state, data) {
      console.log('login', data)
      this.commit('updateProfile', data)
      this.dispatch('getMyLocation')
      this.dispatch('watchMyLocation')
      this.dispatch('getMyEggs', data)
    },
    updateProfile (state, data) {
      state.currentUser = state.currentUser ? Object.assign(state.currentUser, data) : data
      localStorage.setItem('currentUser', JSON.stringify(data))
    },
    loadMap (state) {
      state.mapLoaded = true
    },
    logout (state) {
      state.currentUser = null
      state.mapLoaded = false
      state.isEditProfile = false
    },
    resetAlert (state) {
      state.alert.msg = ''
      state.alert.type = 'info'
      state.alert.sync = false
      state.alertTimer && clearTimeout(state.alertTimer)
    },
    updateAlert (state, alert) {
      const _this = this
      state.alert.msg = alert.msg || ''
      state.alert.type = alert.type || 'info'
      state.alert.sync = alert.sync || false
      state.alert.time = alert.time || 5000
      state.alertTimer && clearTimeout(state.alertTimer)
      state.alertTimer = setTimeout(() => {
        _this.commit('resetAlert')
      }, state.alert.sync ? 10000 : state.alert.time)
    },
    updateLocation (state, pos) {
      state.myLatitude = pos.coords.latitude
      state.myLongitude = pos.coords.longitude
      console.log(pos, 'Position updated', state.myLatitude, state.myLongitude)
      if (window.myLocationMarker) {
        window.myLocationMarker.setPosition({ lat: pos.coords.latitude, lng: pos.coords.longitude })
      }
    },
    updateMyEggs (state, eggs) {
      state.myEggs = eggs
      console.log('My eggs updated', state.myEggs, eggs)
    },
    updateOthers (state, others) {
      state.othersEggs = others
      console.log('Others updated', state.othersEggs, others)
    },
    updateFiredEgg (state, egg) {
      state.firedEgg = egg
      console.log('Update fired egg', state.firedEgg, egg)
    },
    resetFiredEgg (state) {
      state.firedEgg = null
    },
    editProfile (state) {
      state.isEditProfile = true
    },
    addProfile (state) {
      state.isEditProfile = false
    },
    findAnEgg (state) {
      state.firedEgg.eggNotChecked = false
      state.currentUser.get_count++
      this.commit('updateProfile', state.currentUser)
    },
    showAbout (state, show) {
      state.showAbout = show
    },
    hideAnEgg (state) {
      state.currentUser.set_count++
      this.commit('updateProfile', state.currentUser)
    },
    deleteAnEgg (state) {
      state.currentUser.set_count--
      this.commit('updateProfile', state.currentUser)
    }
  },
  actions: {
    watchMyLocation (state) {
      console.log('Watching my location')
      const _this = this
      if (state.GPSWatchID !== null) {
        navigator.geolocation.clearWatch(state.GPSWatchID)
      }
      state.GPSWatchID = navigator.geolocation.watchPosition(function (position) {
        console.log('WATCH', position.coords.latitude, position.coords.longitude)
        _this.commit('updateLocation', position)
      })
    },
    getMyLocation (state) {
      const _this = this
      const options = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
      }
      console.log('Getting my location')
      navigator.geolocation.getCurrentPosition(function (position) {
        console.log('GET', position.coords.latitude, position.coords.longitude)
        _this.commit('updateLocation', position)
      }, null, options)
    },
    getMyEggs (state, data) {
      console.log('get me eggs')
      const _this = this
      if (!(data || state.currentUser)) {
        data = JSON.parse(localStorage.getItem('currentUser'))
      }
      axios.get(`/egg?uuname=${data.username}`)
        .then(res => {
          console.log('eggs', res)
          const myEggs = res.data.result.data
          _this.commit('updateMyEggs', myEggs)
        })
    },
    getOthersEggs (state, data) {
      console.log('get others eggs')
      const _this = this
      if (!(data || state.currentUser)) {
        data = JSON.parse(localStorage.getItem('currentUser'))
      }
      axios.get(`/othersegg?uuname=${data.username}`)
        .then(res => {
          const actions = res.data.result.data
          const othersList = []
          for (const action of actions) {
            action.egg.user = action.user
            action.egg.action_id = action.id
            action.egg.action = action.action
            action.egg.action_time = action.update_time
            othersList.push(action.egg)
          }
          _this.commit('updateOthers', othersList)
        })
    },
    checkFiredEgg (state, egg) {
      const _this = this
      this.commit('resetFiredEgg')
      this.commit('updateAlert', {
        msg: 'Checking this egg...',
        sync: true
      })

      const cu = JSON.parse(localStorage.getItem('currentUser'))
      console.log(egg, cu)
      console.log(egg.user, state.currentUser)

      if (egg.unknownCreater) {
        egg.eggIsMine = false
      } else {
        egg.eggIsMine = egg.user.username === cu.username
      }

      egg.eggExpire = new Date() > new Date(egg.expire_time)

      if (egg.eggIsMine) {
        egg.eggNotChecked = false
        _this.commit('updateFiredEgg', egg)
        _this.commit('resetAlert')
        return
      }

      const myname = cu.username
      const eggid = egg.id
      const eggname = egg.name

      if (egg.unknownCreater) {
        // 别人的蛋 我发现过了（来自Found列表） 但不知道是谁埋的 => 查询谁埋的
        egg.eggNotChecked = false
        axios.get(`/action?uuname=${myname}&egg_id=${eggid}&egg_name=${eggname}&action=1`)
          .then(res => {
            egg.user = res.data.result.data[0].user
          }).catch(_err => {
          }).finally(() => {
            _this.commit('updateFiredEgg', egg)
            _this.commit('resetAlert')
          })
      } else {
        // 别人的蛋 我不知道发没发现过（来自地图和Friends'列表）=> 查询是否发现
        const myid = cu.id
        axios.get(`/action?uuname=${myname}&user_id=${myid}&egg_id=${eggid}&egg_name=${eggname}&action=2`)
          .then(_res => {
            egg.eggNotChecked = false
          }).catch(_err => {
            egg.eggNotChecked = true
          }).finally(() => {
            _this.commit('updateFiredEgg', egg)
            _this.commit('resetAlert')
          })
      }
    }
  },
  modules: {
  }
})
