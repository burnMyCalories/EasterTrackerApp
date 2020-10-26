<template>
  <div class="map">
    Map page
    <div id="map"></div>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  },
  mounted: function () {
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
        zoom: 8
      })
    }

    // Append the 'script' element to 'head'
    document.head.appendChild(script)
    /* eslint-disable */
  }
}
</script>

<style scoped>
  .map, #map{
    width: 100%;
    height: 100%;
  }
</style>
