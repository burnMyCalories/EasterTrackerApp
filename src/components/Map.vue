<template>
  <div class="map">
    <div id="map"></div>
    <div class="tool">
      <div class="layers">
        <button @click="myEggsLayerToggle()">My Eggs</button>
        <button @click="otherEggsLayerToggle()">Nearby Eggs</button>
      </div>

      <button @click="refresh()" id="btn_map_refresh"><i class="fas fa-sync-alt"></i></button>
      <button @click="hideEggWindow = !hideEggWindow" id="btn_map_hide">Hide An Egg</button>
      <button @click="myLocation()" id="btn_map_location"><i class="fas fa-crosshairs"></i></button>
    </div>
    <HideEgg class="egg-box" v-if="hideEggWindow"/>
  </div>
</template>

<script>
import HideEgg from '@/components/HideEgg.vue'
export default {
  setup () {
    return {}
  },
  components: {
    HideEgg
  },
  data () {
    return {
      hideEggWindow: false
    }
  },
  mounted () {
    if (this.$store.state.mapLoaded === false) {
      this.loadMap()
    }
  },
  methods: {
    loadMap () {
      /* eslint-disable */
      // Create the script tag, set the appropriate attributes
      const _this = this
      // if (!window.google) {
      var script = document.createElement('script')
      const url = `https://maps.googleapis.com/maps/api/js?key=${process.env.VUE_APP_GOOGLE_MAP_KEY}&callback=initMap`
      // console.log(process.env, url)
      script.src = url
      script.defer = true
      // }

      

      // Attach your callback function to the `window` object
      window.initMap = function () {
        // JS API is loaded and available
        console.log('map loaded')
        window.google = google
        window.myMap = new google.maps.Map(document.getElementById('map'), {
          center: { lat: -34.397, lng: 150.644 },
          zoom: 15,
          disableDefaultUI: true
        })
        let map = window.myMap
        navigator.geolocation.getCurrentPosition(function(position) {
          console.log(position.coords.latitude, position.coords.longitude);
          _this.$store.commit('updateLocation', position)
          const pos = {lat: position.coords.latitude, lng: position.coords.longitude}
          const myLayers = new google.maps.MVCObject()
          window.myLayers = myLayers
          myLayers.setValues({ myeggs: map, foundeggs: map, myloc: map })
          const myLocationMarker = new google.maps.Marker({ position: pos })
          window.myLocationMarker = myLocationMarker
          myLocationMarker.bindTo('map', myLayers, 'myloc')
          map.setCenter(pos)
        })
        
      }

      // Append the 'script' element to 'head'
      document.head.appendChild(script)
      this.$store.commit('loadMap')
      /* eslint-disable */
    },
    findMyEggs () {
      // this.$store.di
    },
    refresh () {
      this.$store.dispatch('getMyEggs')
    },
    myLocation () {
      if (!this.$store.state.myLatitude || !window.myMap) {
        this.$store.commit('updateAlert', { msg: 'Map is loading! Please wait...' })
        return
      }
      let lat = this.$store.state.myLatitude
      let lng = this.$store.state.myLongitude
      const pos = { lat, lng }
      window.myMap.panTo(pos)
      // window.myMap.setZoom(16)
    },
    myEggsLayerToggle () {

    },
    otherEggsLayerToggle () {}
  }
}
</script>

<style scoped>
  .map, #map{
    width: 100%;
    height: 100%;
  }
  .tool {
  }
  .egg-box{
    position: absolute;
    top: 3rem;
  }
  .tool button {
    position: absolute;
    width: 2rem;
    height: 2rem;
    border: 0.2rem solid #03A9F4;
    border-radius: 30%;
    background: #fff;
    color: #03A9F4;
  }
  #btn_map_refresh {
    left: 1rem;
    bottom: 7.5rem;
  }
  #btn_map_location {
    left: 1rem;
    bottom: 5rem;
  }
  #btn_map_hide {
    right: 1rem;
    bottom: 5rem;
  }
</style>
