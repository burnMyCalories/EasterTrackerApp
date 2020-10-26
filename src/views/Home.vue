<template>
  <div class="home">
    <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
    <Map />
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import Map from '@/views/Map.vue'

export default {
  name: 'Home',
  components: {
    Map
  }
}
</script>

<style>
.home {
  width: 100%;
  height: 100%;
}
</style>
