import { createStore } from 'vuex'

export default createStore({
  state: {
    mapLoaded: false,
    currentUser: localStorage.getItem('currentUser') ? JSON.parse(localStorage.getItem('currentUser')) : null
  },
  mutations: {
    login (state, data) {
      state.currentUser = data
    },
    loadMap (state) {
      state.mapLoaded = true
    },
    logout (state) {
      state.currentUser = null
      state.mapLoaded = false
    }
  },
  actions: {
  },
  modules: {
  }
})
