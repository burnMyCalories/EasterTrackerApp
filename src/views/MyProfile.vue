<template>
  <div id="myProfile" class="my-container">
    <div class="info-box">
      <img class="img img-thumbnail rounded-circle" src="../assets/portraits/portrait1.jpg" alt="">
      <!-- <font-awesome-icon icon="user-secret" /> -->
    </div>
    <h1 class="title">My Profile</h1>
    <p v-if="user">{{user}}</p>
    <button @click="logout" class="btn btn-light">Log out</button>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: null
    }
  },
  setup () {
    return {}
  },
  mounted () {
    this.user = this.$store.state.currentUser
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
