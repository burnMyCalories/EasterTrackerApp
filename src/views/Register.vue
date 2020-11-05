<template>
  <div id="register" class="my-container">
    <h1 class="title">Register</h1>
    <form action="">
      <div class="form-group">
        <label for="ipt_reg_name">User Name</label>
        <small class="tips">Only 4 to 32 letters, numbers and underscores are allowed</small>
        <input type="text" class="form-control" id="ipt_reg_name" name="user" minlength="4" maxlength="32" v-model="username" @blur="verifyName()">
      </div>
      <div class="form-group">
        <label for="ipt_reg_pwd">Password</label>
        <small class="tips">Your password should be no less than 6 and no more than 32 characters</small>
        <input type="password" class="form-control" id="ipt_reg_pwd" name="pwd" autocomplete="new-password" v-model="password" @blur="verifyPwd()">
      </div>
      <div class="form-group">
        <label for="ipt_reg_cpwd">Confirm Password</label>
        <small class="tips">Two inputed password should be the same</small>
        <input type="password" class="form-control" id="ipt_reg_cpwd" name="cpwd" autocomplete="new-password" v-model="cpassword" @blur="verifyCPwd()">
      </div>
      <!-- <div class="form-group">
        <label for="">Contact Infomation</label>
        <small class="d-block text-black-50">Contact information is only used for password retrieval</small>
        <div class="d-flex justify-content-between">
          <div class="form-check form-check form-control">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="email">
            <label class="form-check-label" for="inlineRadio1">Email</label>
          </div>
          <div style="width:1rem;"></div>
          <div class="form-check form-check form-control">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="phone">
            <label class="form-check-label" for="inlineRadio1">Phone</label>
          </div>
        </div>
      </div> -->
      <div class="form-group">
        <label for="ipt_reg_email" v-if="contactIsEmail">Email</label>
        <small class="tips">Your email will only be used for password retrieval</small>
        <!-- <label for="ipt_reg_tel" v-else>Phone</label> -->
        <div class="d-flex">
          <input type="email" class="form-control" id="ipt_reg_email" v-if="contactIsEmail" v-model="email" @blur="verifyEmail()" @change="emailChange()">
          <!-- <input type="tel" class="form-control" id="ipt_reg_tel" v-else> -->
          <button class="btn btn-link" type="button" id="verifyButton" @click="getVCode()">{{ verifyLabel }}</button>
        </div>
      </div>
      <div class="form-group">
        <label for="ipt_reg_vcode">Validation Code</label>
        <input type="text" class="form-control" id="ipt_reg_vcode" v-model="validation" @blur="verifyCode()">
      </div>
      <div class="btngrp">
        <router-link to="/"><button class="btn btn-link">Cancel</button></router-link>
        <button class="btn btn-primary" type="button" @click="register()">Submit</button>
      </div>
    </form>

  </div>
</template>

<script>
export default {
  data () {
    return {
      username: '',
      password: '',
      cpassword: '',
      gender: null,
      nickname: null,
      email: '',
      contactIsEmail: true,
      verifyLabel: 'Verify',
      trueVCode: null,
      validation: '',
      resVCodeEmail: '',
      latitude: 0,
      longitude: 0
    }
  },
  methods: {
    getVCode () {
      const _this = this
      const button = document.querySelector('#verifyButton')
      button.blur()
      button.setAttribute('disabled', 'disabled')
      let secs = 60
      const time = setInterval(() => {
        if (secs > 0) {
          _this.verifyLabel = `${secs--} s`
        } else {
          clearInterval(time)
          _this.verifyLabel = 'Verify'
          button.removeAttribute('disabled')
        }
      }, 1000)
      this.axios.post('/confirm', null, { params: { email: _this.email } })
        .then(res => {
          _this.trueVCode = res.data.code
          _this.resVCodeEmail = _this.email
        })
    },
    updateInput (inputID, isValid) {
      const inputDOM = document.querySelector(inputID)
      const classlist = inputDOM.classList
      classlist.remove(!isValid ? 'is-valid' : 'is-invalid')
      classlist.add(isValid ? 'is-valid' : 'is-invalid')
      return isValid
    },
    verifyName () {
      const regex = /^[\w]{4,32}$/
      return this.updateInput('#ipt_reg_name', regex.test(this.username))
    },
    verifyPwd () {
      const regex = /^.{6,32}$/
      return this.updateInput('#ipt_reg_pwd', regex.test(this.password))
    },
    verifyCPwd () {
      const regex = /^.{6,32}$/
      return this.updateInput('#ipt_reg_cpwd', regex.test(this.cpassword) && this.password === this.cpassword)
    },
    emailChange () {
      if (this.resVCodeEmail !== '') {
        this.verifyCode()
      }
    },
    verifyEmail () {
      const regex = /^[A-Za-z0-9]+([_.][A-Za-z0-9]+)*@([A-Za-z0-9-]+\.)+[A-Za-z]{2,6}$/
      return this.updateInput('#ipt_reg_email', regex.test(this.email))
    },
    verifyCode () {
      return this.updateInput('#ipt_reg_vcode', this.trueVCode === this.validation && this.email === this.resVCodeEmail)
    },
    verifyAll () {
      const errorItems = []
      this.verifyName() === false && errorItems.push('user name')
      this.verifyPwd() === false && errorItems.push('password')
      this.verifyCPwd() === false && errorItems.push('confirm password')
      this.verifyEmail() === false && errorItems.push('email')
      this.verifyCode() === false && errorItems.push('validation code')
      if (errorItems.length !== 0) {
        const msg = `Please check your ${errorItems.join(', ')}!`
        this.$store.commit('updateAlert', { msg: msg, type: 'danger', sync: false })
        return false
      }
      return true
    },
    register () {
      if (this.verifyAll() === false) {
        return
      }
      const _this = this
      this.$store.commit('updateAlert', { msg: 'Registering, please for a moment...', type: 'primary', sync: true })
      this.axios.post('/register', null, {
        params: {
          username: _this.username,
          password: _this.password,
          contact: _this.email,
          gender: '',
          nickname: '',
          latitude: _this.latitude,
          longitude: _this.longitude
        }
      }).then(res => {
        const userdata = res.data.result.data[0]
        userdata.password = this.password
        this.$store.commit('login', userdata)
        _this.axios.put('/login', null, {
          params: userdata
        }).then(res => {
          _this.$store.commit('updateAlert', {
            msg: 'Successfully registered!',
            type: 'success',
            sync: false
          })
          this.$router.push('/addProfile')
        })
      }).catch(err => {
        if (err.response.status === 500) {
          this.updateInput('#ipt_reg_name', false)
          _this.$store.commit('updateAlert', { msg: `Repeated user name '${_this.username}' founded! Please try a new one!`, type: 'danger' })
        }
      })
    }
  }
}
</script>
<style scoped>
.btngrp {
  display: flex;
  justify-content: space-between;
}
#verifyButton {
  min-width: 5rem;
}
</style>
