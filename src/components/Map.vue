<template>
  <div class="map">
    <div id="map"></div>
    <div class="tool">
      <div class="layers">
        <button @click="myEggsLayerToggle()" id="btn_map_mine" :class="{active: showMyEgg}">
          <img class="icon" src="../../static/icons/myEgg.png" alt="">
        </button>
        <button @click="otherEggsLayerToggle()" id="btn_map_others" :class="{active: showOthers}">
          <img class="icon" src="../../static/icons/nearEggs.png" alt="">
        </button>
      </div>

      <button @click="refresh()" id="btn_map_refresh">
        <!-- <i class="fas fa-sync-alt"></i> -->
        <img class="icon" src="../../static/icons/refresh.png" alt="">
      </button>
      <button @click="hideEggWindow = !hideEggWindow" id="btn_map_hide">
        <img class="icon" src="../../static/icons/hideEgg.png" alt="">
      </button>
      <button @click="myLocation()" id="btn_map_location">
        <!-- <i class="fas fa-crosshairs"></i> -->
        <img class="icon" src="../../static/icons/location.png" alt="">
      </button>
    </div>
    <HideEgg class="egg-box" v-if="hideEggWindow"/>
    <CheckEgg class="egg-box"/>
  </div>
</template>

<script>
import HideEgg from '@/components/HideEgg.vue'
import CheckEgg from '@/components/CheckEgg.vue'
const iconURL = `${process.env.VUE_APP_STATIC}/icon`
export default {
  setup () {
    return {}
  },
  components: {
    HideEgg, CheckEgg
  },
  computed: {
    firedEgg () {
      return this.$store.state.firedEgg
    },
    myEggs () {
      return this.$store.state.myEggs
    },
    othersEggs () {
      return this.$store.state.othersEggs
    }
  },
  data () {
    return {
      hideEggWindow: false,
      showMyEgg: true,
      showOthers: false,
      myMarkers: [],
      otherMarkers: [],
      username: '',
      userid: '',
      user: null
    }
  },
  mounted () {
    if (this.$store.state.mapLoaded === false) {
      this.loadMap()
    }
    this.user = this.$store.state.currentUser
    this.username = this.user.username
    this.userid = this.user.id
  },
  methods: {
    loadMap () {
      /* eslint-disable */
      // Create the script tag, set the appropriate attributes
      const _this = this
      const googleIsLoaded = document.getElementById('google_map_is') !== null
      if (!googleIsLoaded) {
        var script = document.createElement('script')
        script.id = 'google_map_js'
        const url = `https://maps.googleapis.com/maps/api/js?key=${process.env.VUE_APP_GOOGLE_MAP_KEY}&libraries=geometry&callback=initMap`
        // console.log(process.env, url)
        script.src = url
        script.defer = true
      }

      

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
          const myLocationMarker = new google.maps.Marker({ position: pos ,icon: `${iconURL}/mypos.svg` })
          window.myLocationMarker = myLocationMarker
          myLocationMarker.bindTo('map', myLayers, 'myloc')
          map.setCenter(pos)
        })
        _this.refresh()
      }

      // Append the 'script' element to 'head'
      if (!googleIsLoaded && script) {
        document.head.appendChild(script)
      }
      this.$store.commit('loadMap')
      /* eslint-disable */
    },
    findMyEggs () {
      // this.$store.di
    },
    markersFromActions (actions, markerList, shell) {

      let map = window.myMap
      const _this = this

      for (const action of actions) {
        action.egg.user = action.user
        action.egg.action_id = action.id
        action.egg.action = action.action
        action.egg.action_time = action.update_time
        const egg = action.egg

        const icon = {
          url: `${iconURL}/${shell ? 'shell' : 'egg'}${egg.type}.png`,
          scaledSize: new google.maps.Size(20, 20),
        };
        const marker = new google.maps.Marker({
          position: { lat: egg.latitude, lng: egg.longitude },
          map: map,
          icon: icon
        })
        marker.eggData = egg
        marker.addListener('click', function (ev) {
          console.log(ev, this)
          _this.$store.dispatch('checkFiredEgg', this.eggData)
        })
        markerList.push(marker)
        
      }
      console.log(markerList)
    },
    refresh () {
      // this.$store.dispatch('getMyEggs')
      // this.$store.dispatch('getOthersEggs')

      const _this = this

      let map = window.myMap
      let google = window.google

      this.myMarkers = []
      this.otherMarkers = []

      this.axios.get(`/action?uuname=${this.username}&user_id=${this.userid}&action=1`)
        .then(res => {
          const actions = res.data.result.data
          console.log('my action 1 eggs', actions)
          _this.markersFromActions(actions, _this.myMarkers, false)
        })

      this.axios.get(`/othersegg?uuname=${this.username}`)
        .then(res => {
          const actions = res.data.result.data
          console.log('other eggs', actions)
          _this.markersFromActions(actions, _this.otherMarkers, true)
        })
      console.log(this.showMyEgg, this.showOthers)
      this.setMarkersVisible(this.myMarkers, this.showMyEgg)
      this.setMarkersVisible(this.otherMarkers, this.showOthers)
      
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
      this.showMyEgg = !this.showMyEgg
      this.setMarkersVisible(this.myMarkers, this.showMyEgg)
    },
    otherEggsLayerToggle () {
      this.showOthers = !this.showOthers
      this.setMarkersVisible(this.otherMarkers, this.showOthers)
    },
    setMarkersVisible (markers, visible) {
      for (let i = 0; i < markers.length; i++) {
        markers[i].setVisible(visible);
      }
    },
    showEgg () {
      console.log('show egg')
    }
  }
}
</script>

<style scoped>
  .map, #map{
    width: 100%;
    height: 100%;
  }
  
  .egg-box{
    position: absolute;
    top: 3rem;
    z-index: 490;
  }
  .tool button {
    outline: none;
    border: none;
    position: absolute;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 30%;
    background: #fff;
    color: #828282;
    box-shadow: 1px 1px 3px 0px #a5a5a5;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .tool button .icon {
    width: 1.8rem;
  }
  #btn_map_refresh {
    left: 1rem;
    bottom: 8.5rem;
    font-size: 1.3rem;
  }
  #btn_map_location {
    left: 1rem;
    bottom: 5rem;
    font-size: 1.4rem;
  }
  #btn_map_hide {
    right: 1rem;
    bottom: 5rem;

  }
  .layers {
    position: absolute;
    top: 1rem;
    left: 1rem;
  }
  .layers button {
    filter: grayscale(1);
  }
  .layers button.active {
    filter: none;
  }
  #btn_map_others {
    position: relative;
    left: 3.5rem;
  }
</style>
