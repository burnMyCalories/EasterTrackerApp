<template>
  <div class="body">
    <h1>Welcome</h1>
    <div id="nav">
      <form action="">
        <input type="text" name="" id="" placeholder="Username/Email" v-model="username">
        <input type="password" name="" id="" placeholder="Password" v-model="password">
      </form>
      <button @click="login">Login</button>
        <div class="btn-grp">
        <p class="or">—————————— OR ——————————</p>
        <router-link to="/about"><button>Register</button></router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  },
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login: function () {
      console.log(`${this.username} log in with password ${this.password}`)
      // if (this.username === 'admin' && this.password === '12345') {
      //   this.$router.push('/home')
      // } else {
      //   alert('Error')
      // }
      this.$router.push('/home')
    }
  }
}
</script>

<style scoped>
.body {
    width: 100%;
    height: 100%;
    background: rebeccapurple;
}
.or {
    text-align: center;
    color: #fff;
}
</style>
