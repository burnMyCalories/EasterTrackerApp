<template>
  <div id="myProfile" class="my-container">
    <h1 class="title">My Profile</h1>
    <button @click="logout" class="btn btn-light">Log out</button>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  },
  methods: {
    logout () {
      // this.$store.commit('logout')
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
