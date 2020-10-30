import { createStore } from 'vuex'

export default createStore({
  state: {
    mapLoaded: false
  },
  mutations: {
    loadMap (state) {
      state.mapLoaded = true
    },
    logout (state) {
      state.mapLoaded = false
    }
  },
  actions: {
  },
  modules: {
  }
})
