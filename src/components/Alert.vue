/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The Global Notification Component of this App
 */

<template>
  <div class="my-alert" v-if="alert.msg">
    <div class="alert tip-box" :class="alertClass" role="alert">
      <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" v-if="alert.sync"></span>
      <span v-if="alert.type === 'danger'">Error!</span> {{ alert.msg }}
    </div>
  </div>
</template>

<script>
export default {
  setup () {
  },
  data () {
    return {
    }
  },
  beforeMount () {
    this.alert = this.$store.state.alert
  },
  computed: {
    alertClass () {
      return `alert-${this.alert.type}`
    }
  }
}
</script>

<style scoped>
.my-alert {
  width: 100%;
}
</style>
