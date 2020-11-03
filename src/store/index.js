import { createStore } from 'vuex'
// import axios from 'axios'

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
    myLongitude: null
  },
  mutations: {
    login (state, data) {
      this.commit('updateProfile', data)
      this.dispatch('getMyLocation')
      this.dispatch('watchMyLocation')
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
      console.log('Getting my location')
      navigator.geolocation.getCurrentPosition(function (position) {
        console.log('GET', position.coords.latitude, position.coords.longitude)
        _this.commit('updateLocation', position)
      })
    }
  },
  modules: {
  }
})
