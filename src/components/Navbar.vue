<template>
  <div class="navbar">
    <router-link to="/home/circle"><button class="btn btn-link"><img class="icon" src="../../static/icons/circle.png" alt="">Circle</button></router-link>
    <router-link to="/home"><button class="btn btn-link"><img class="icon" src="../../static/icons/map.png" alt="">Map</button></router-link>
    <router-link to="/home/myProfile"><button class="btn btn-link"><img class="icon" src="../../static/icons/setting.png" alt="">Setting</button></router-link>
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
  padding: 0 2rem;
}
.btn:focus,
.btn:active:focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn.active.focus {
  outline: none；
}
.navbar button:focus, .navbar button:active, .navbar a:active, .navbar a:focus {
  outline: none !important;
}
.navbar button {
  outline: none !important;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 0.9rem;
}
.navbar button .icon {
  width: 2rem;
}
</style>
