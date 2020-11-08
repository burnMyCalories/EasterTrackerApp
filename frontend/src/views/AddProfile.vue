/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The Profile Edit Page, where users add or update their profiles
 */
<template>
  <div class="my-container" id="addProfile">

    <h1 class="title" v-if="!isEditProfile">Nearly done!</h1>
    <h1 class="title" v-else>Edit Your Profile</h1>

    <form action="">
      <div class="form-group">
        <div class="img-box mb-3">
          <img id="img_add_portrait" class="img img-thumbnail rounded-circle" :src="imgsrc" alt="">
        </div>
        <input type="file" id="ipt_add_portrait" @change="uploadPortrait($event)" accept="image/jpg">
        <label for="ipt_add_portrait" class="form-control">
          <span v-if="!isEditProfile">Select Photo</span>
          <span v-else>Change Your Portrait</span>
        </label>
      </div>

      <div class="form-group">
        <label for="ipt_add_nickname">
          <span v-if="!isEditProfile">Choose a nice nick name</span>
          <span v-else>Change your nick name</span>
        </label>
        <small class="tips">More than 32 characters will be hard to remember :-)</small>
        <input type="text" class="form-control" id="ipt_add_nickname" name="nickname" autocomplete="off" v-model="nickname" maxlength="32">
      </div>

      <div class="form-group gender-radios">
        <label for="genderRadios">
          <span v-if="!isEditProfile">You are ...</span>
          <span v-else>Gender</span>
        </label>
        <div class="radio-group">
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="genderRadios" id="ra_add_male" value="M" v-model="gender">
            <label class="form-check-label male" for="ra_add_male">
              Boy <i class="fas fa-mars"></i>
            </label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="genderRadios" id="ra_add_female" value="F" v-model="gender">
            <label class="form-check-label female" for="ra_add_female">
              Girl <i class="fas fa-venus"></i>
            </label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="genderRadios" id="ra_add_secret" value="S" v-model="gender">
            <label class="form-check-label secret" for="ra_add_secret">
              Secret <i class="fas fa-question"></i>
            </label>
          </div>
        </div>
      </div>

      <div class="form-group mt-5">
        <button type="button" class="btn btn-primary btn-block" @click="updatePorfile()">
          <span v-if="!isEditProfile">Go! Go! Go!!!</span>
          <span v-else>Save Changes</span>
        </button>
      </div>
      <div class="form-group">
        <button type="button" class="btn btn-link btn-block" @click="skip()">
          <span v-if="!isEditProfile">Skip for now</span>
          <span v-else>Cancel</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  },
  data () {
    return {
      nickname: '',
      gender: '',
      userdata: null,
      imgsrc: require('../assets/portraits/default-portrait.svg')
    }
  },
  computed: {
    isEditProfile () {
      return this.$store.state.isEditProfile
    }
  },
  beforeMount () {
    this.userdata = this.$store.state.currentUser
    console.log(this.userdata)
  },
  mounted () {
    if (this.isEditProfile) {
      const _this = this
      this.nickname = this.userdata.nickname
      this.gender = this.userdata.gender
      this.axios.get(`/files/${this.userdata.id}_portrait.jpg`).then(res => {
        _this.imgsrc = `${process.env.VUE_APP_HOST}/files/${this.userdata.id}_portrait.jpg`
      }).catch(err => {
        console.log(err)
      })
    }
  },
  methods: {
    uploadPortrait (e) {
      const _this = this
      const file = e.target.files[0]

      const format = file.name.split('.').pop()
      const filename = `${this.userdata.id}_portrait.${format}`

      const fileReader = new FileReader()
      fileReader.onload = () => {
        document.getElementById('img_add_portrait').src = fileReader.result
      }
      fileReader.readAsDataURL(file)

      const formData = new FormData()
      formData.append('file', file, filename)

      this.$store.commit('updateAlert', {
        msg: 'Uploading portrait, please wait...',
        type: 'primary',
        sync: true
      })
      this.axios.post('/file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        _this.$store.commit('updateAlert', {
          msg: 'Portrait uploaded successfully!',
          type: 'success',
          sync: false
        })
      }).catch(err => {
        let msg = ''
        const code = err.response.status
        if (code === 401) {
          msg = 'Please check your file!'
        } else if (code === 500) {
          msg = 'Please try again later!'
        }
        _this.$store.commit('updateAlert', {
          msg: msg,
          type: 'danger',
          sync: false
        })
      })
    },
    updatePorfile () {
      const _this = this
      console.log(this.gender, this.nickname)
      const user = this.userdata
      const gender = this.gender
      const nickname = this.nickname
      this.$store.commit('updateAlert', {
        msg: 'Uploading your profile...',
        type: 'primary',
        sync: true
      })
      if (gender && nickname) {
        this.axios.put('/user', null, {
          params: {
            uuname: user.username,
            id: user.id,
            gender: gender,
            nickname: nickname
          }
        }).then(res => {
          console.log(res)
          const user = res.data.result.data[0]
          this.$store.commit('updateAlert', {
            msg: 'Your profile uploaded successfully!',
            type: 'success'
          })
          _this.$store.commit('updateProfile', user)
          this.skip()
        }).catch(err => {
          console.log(err)
          this.$store.commit('updateAlert', {
            msg: 'Oops! Something bad happend, please tra again.',
            type: 'danger'
          })
        })
      }
    },
    skip () {
      if (!this.isEditProfile) {
        this.$router.push('/home')
      } else {
        this.$router.push('/home/myProfile')
      }
    }
  }
}
</script>

<style scoped>
.gender-radios .radio-group {
  height: 3rem;
  display: flex;
  justify-content: space-between;
}
.gender-radios .radio-group .form-check-inline {
  width: 32%;
  margin: 0;
  display: flex;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  background: #fff;
}
.gender-radios .radio-group .form-check-inline input[type="radio"] {
  flex: 1
}
.gender-radios .radio-group .form-check-inline label {
  flex: 3
}
.male {
  color: #0086d6;
}
.female {
  color: #E91E63;
}
.secret {
  color: #6D6D6D;
}
#ipt_add_portrait {
  display: none;
}
label[for="ipt_add_portrait"] {
  display: block;
  text-align: center;
}
.img-box {
  height: 9rem;
  display: flex;
  justify-content: center;
}
#img_add_portrait {
  width: 9rem;
  height: 9rem;
  object-fit: contain;
}
</style>
