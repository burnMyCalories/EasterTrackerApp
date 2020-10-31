<template>
  <div id="welcome" class="my-container">
    <div class="alert alert-danger tip-box" role="alert" v-if="alertmsg">
      Error! {{ alertmsg }}
    </div>
    <div class="alert alert-info tip-box" role="alert" v-if="logging">
      <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
      Please wait for a moment...
    </div>
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
      password: '',
      alertmsg: null,
      logging: false,
      timeoutID: null
    }
  },
  methods: {
    login: function () {
      const _this = this
      if (this.logging) {
        return
      }
      this.logging = true
      this.axios.put('/login', null, {
        params: { username: this.username, password: this.password }
      })
        .then((res) => {
          const user = res.data.result.data[0]
          _this.$store.commit('login', user)
          localStorage.setItem('currentUser', JSON.stringify(user))
          _this.$router.push('/home')
        })
        .catch((err) => {
          _this.alertmsg = err.response.data.status.msg
          clearTimeout(_this.timeoutID)
          _this.timeoutID = setTimeout(() => {
            _this.alertmsg = null
            _this.timeoutID = null
          }, 5000)
        })
        .finally(() => {
          _this.logging = false
        })
    }

  }
}
</script>

<style>
.tip-box {
  position: absolute;
  width: calc(100% - 4rem);
  transition: 0.2s;
}
</style>
