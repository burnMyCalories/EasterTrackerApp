<template>
  <div id="welcome" class="my-container">
    <h1 class="title">Welcome</h1>
    <div id="form-containter">
      <form class="loginForm" action="">
        <div class="form-group">
          <label for="ipt_login_name">Username or Email address</label>
          <input type="text" class="form-control" id="ipt_login_name" v-model="username">
        </div>
        <div class="form-group">
          <label for="ipt_login_pwd">Password</label>
          <small class="ml-2"><router-link to="/">Forgot?</router-link></small>
          <input type="password" class="form-control" id="ipt_login_pwd" v-model="password">
        </div>
      </form>
      <button @click="login" class="btn btn-primary btn-lg btn-block">Login</button>
        <div class="btn-grp">
        <hr>
        <router-link to="/register"><button class="btn btn-secondary btn-lg btn-block">Register</button></router-link>
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
      if (this.username === 'admin' && this.password === '12345') {
        this.$router.push('/home')
      } else {
        alert('Error')
      }
      // this.$router.push('/home')
    }
  }
}
</script>

<style>
</style>
