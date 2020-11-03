<template>
  <div class="navbar">
    <router-link to="/home/messages"><button class="btn btn-link">Circle</button></router-link>
    <router-link to="/home"><button class="btn btn-link">Map</button></router-link>
    <router-link to="/home/myProfile"><button class="btn btn-link">Setting</button></router-link>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  }
}
</script>

<style scoped>
.navbar {
  background: hsla(0, 0%, 100%, .75);
  backdrop-filter: blur(5px);
}
.navbar button {
  outline: none;
}
</style>
