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
    myEggs: null
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
      state.alertTimer && clearTimeout(state.alertTimer)
      state.alertTimer = setTimeout(() => {
        _this.commit('resetAlert')
      }, 5000)
    },
    updateLocation (state, pos) {
      state.myLatitude = pos.coords.latitude
      state.myLongitude = pos.coords.longitude
      console.log('Position updated')
    },
    updateMyEggs (state, eggs) {
      state.myEggs.length = 0
      for (let egg of eggs) {
        state.myEggs.push(egg)
      }
      let map = window.myMap
      let google = window.google
      console.log(state.myEggs, eggs, map, google)
      for (let i = 0; i < eggs.length; i++) {
        const marker = new google.maps.Marker({
          position: { lat: eggs[i].latitude, lng: eggs[i].longitude },
          map: map
        })
      }
    }
  },
  actions: {
    loadPortrait (state, data) {
      console.log('loadportrait')
      // console.log(data)
      // axios.get('/file', null, { params: { filename: `${data.id}_portrait.jpg` } })
      //   .then(res => {
      //     console.log(res)
      //   })
    },
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
    }
  },
  modules: {
  }
})
