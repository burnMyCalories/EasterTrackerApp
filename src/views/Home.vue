<template>
  <div class="home">
    <router-view class="container"/>
    <Map class="map" />
    <Navbar class="navbar"/>
  </div>
</template>

<script>
import Map from '@/components/Map.vue'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'Home',
  components: {
    Map, Navbar
  }
}
</script>

<style scoped>
.home, .container {
  width: 100%;
  height: 100%;
  overflow: hidden;
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
}
</style>
