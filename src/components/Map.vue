<template>
  <div class="map">
    <div id="map"></div>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
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
        let map = new google.maps.Map(document.getElementById('map'), {
          center: { lat: -34.397, lng: 150.644 },
          zoom: 15,
          disableDefaultUI: true
        })
        navigator.geolocation.getCurrentPosition(function(position) {
          console.log(position.coords.latitude, position.coords.longitude);
          map.setCenter({lat: position.coords.latitude, lng: position.coords.longitude})
        })
      }

      // Append the 'script' element to 'head'
      document.head.appendChild(script)
      this.$store.commit('loadMap')
      /* eslint-disable */
    }
  }
}
</script>

<style scoped>
  .map, #map{
    width: 100%;
    height: 100%;
  }
</style>
