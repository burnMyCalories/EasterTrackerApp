/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The Navigition Bar Component of this App
 */

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
.navbar a.router-link-exact-active .btn{
  font-weight: bold;
  color: #007bff;
}
.navbar button:focus, .navbar button:active, .navbar a:active, .navbar a:focus {
  outline: none;
  box-shadow: none;
  text-decoration: none;
}
.navbar button {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 0.9rem;
  color: #9e9e9e;
}
.navbar button .icon {
  width: 2rem;
}
</style>
