<template>
  <div id="welcome" class="my-container">
    <h1 class="title lg">Welcome</h1>
    <div id="form-containter">
      <form class="loginForm" action="">
        <div class="form-group">
          <label for="ipt_login_name">Username or Email address</label>
          <input type="text" class="form-control" id="ipt_login_name" v-model="username" autocomplete="username">
        </div>
        <div class="form-group">
          <label for="ipt_login_pwd">Password</label>
          <a href="javascript:" class="ml-3"><small @click="forgot()">Forgot?</small></a>
          <input type="password" class="form-control" id="ipt_login_pwd" v-model="password" autocomplete="current-password">
        </div>
      </form>
      <div class="btn-grp">
        <button @click="login()" class="btn btn-primary btn-lg btn-block">Login</button>
        <hr>
        <button class="btn btn-secondary btn-lg btn-block" @click="register()">Register</button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  components: {},
  setup () {
  },
  data () {
    return {
      username: '',
      password: '',
      logging: false,
      message: ''
    }
  },
  provide () {
  },
  methods: {
    login: function () {
      const _this = this
      if (this.logging) {
        return
      }
      this.logging = true
      this.$store.commit('updateAlert', {
        msg: 'Please wait for a monment...',
        type: 'primary',
        sync: true
      })
      this.axios.put('/login', null, {
        params: { username: this.username, password: this.password }
      })
        .then((res) => {
          const user = res.data.result.data[0]
          console.log(user)
          user.password = _this.password
          _this.$store.commit('login', user)
          _this.$store.commit('updateAlert', {
            msg: `Welcome ${user.nickname ? user.nickname : user.username}!`,
            type: 'success'
          })
          _this.$router.push('/home')
        })
        .catch((err) => {
          console.log(err)
          const alertmsg = err.response.data.status.msg
          this.$store.commit('updateAlert', {
            msg: alertmsg,
            type: 'danger',
            sync: false
          })
        })
        .finally(() => {
          _this.logging = false
        })
    },
    register () {
      if (this.logging) {
        return
      }
      this.$router.push('/register')
    },
    forgot () {
      if (this.logging) {
        return
      }
      console.log('forgot')
    }
  }
}
</script>

<style>
</style>
