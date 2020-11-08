/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The About Page, presents the information of our group
 */
<template>
  <div>
    <h1 class="title lg">About</h1>
    <h3>Intro</h3>
    <p>This is an project of Unimelb COMP90018</p>
    <p>Made by burnMyCalories team with ❤️</p>
    <h3 class="mt-3">Our Team</h3>
    <ul>
      <li>Binbin Tang - 1042031</li>
      <li>Jia Zhu - 890604</li>
      <li>Quan Zhou - 1065302</li>
      <li>Weilun Chen - 960043</li>
      <li>Xinnan Shen - 1051380</li>
      <li>Zongdong Liu - 1037180</li>
    </ul>
    <h3 class="mt-3">Thanks!</h3>
    <p>Hope you enjoy it</p>
    <div class="d-flex">
      <button class="btn btn-link btn-block mt-3" @click="$store.commit('showAbout', false)">Go Back</button>
    </div>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  },
  methods: {
  }
}
</script>

<style scoped>
  h3 {
    color: #9eccfd;
  }
</style>
