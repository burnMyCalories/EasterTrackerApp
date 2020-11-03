<template>
  <div class="map">
    <div id="map"></div>
    <div class="tool">
      <button>My Eggs</button>
      <button>Nearby Eggs</button>
      <button @click="refresh()">Refresh Map</button>
      <button @click="hideEggWindow = !hideEggWindow">Hide An Egg</button>
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
      
      var script = document.createElement('script')
      script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyAwb-FjgOhpZrFDOn18WkrJ5IQKhG2zbLo&callback=initMap'
      script.defer = true

      // Attach your callback function to the `window` object
      window.initMap = function () {
        // JS API is loaded and available
        console.log('map loaded')
        window.myMap = new google.maps.Map(document.getElementById('map'), {
          center: { lat: -34.397, lng: 150.644 },
          zoom: 15,
          disableDefaultUI: true
        })
        let map = window.myMap
        navigator.geolocation.getCurrentPosition(function(position) {
          console.log(position.coords.latitude, position.coords.longitude);
          map.setCenter({lat: position.coords.latitude, lng: position.coords.longitude})
        })
      }

      // Append the 'script' element to 'head'
      document.head.appendChild(script)
      this.$store.commit('loadMap')
      /* eslint-disable */
    },
    refresh () {
      this.$store.dispatch('getMyEggs')
    }
  }
}
</script>

<style scoped>
  .map, #map{
    width: 100%;
    height: 100%;
  }
  .tool {
    position: absolute;
    top: 0;
  }
  .egg-box{
    position: absolute;
    top: 3rem;
  }
</style>
