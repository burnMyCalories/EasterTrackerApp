<template>
  <div class="home">
    <router-view class="container"/>
    <Map class="map" />
    <Navbar class="navbar"/>
    <About class="my-container about" v-if="showAbout"/>
  </div>
</template>

<script>
import Map from '@/components/Map.vue'
import Navbar from '@/components/Navbar.vue'
import About from '@/views/About.vue'

export default {
  name: 'Home',
  components: {
    Map, Navbar, About
  },
  data () {
    return {
      firedEgg: ''
    }
  },
  computed: {
    showAbout () {
      return this.$store.state.showAbout
    }
  },
  mounted () {
    console.log(this.$refs)
  },
  methods: {
  }
}
</script>

<style scoped>
.home, .container {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 500;
  position: absolute;
  top: 0;
}
.navbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 4rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  z-index: 1000;
}
.about.my-container {
  background: hsla(0, 0%, 100%, .75);
  backdrop-filter: blur(5px);
  z-index: 1001;
}
</style>
