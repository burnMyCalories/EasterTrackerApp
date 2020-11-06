<template>
  <div class="egg-window">
    <div class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Hide An Egg</h5>
            <button type="button" class="close" data-dismiss="modal" @click="close()" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form action="">
              <div class="form-group">
                <label>Choose an Egg First</label>
                <div class="egg-group">
                  <label :for="'eggRadio' + egg.type" class="egg-btn" v-for="egg in eggs" :key="egg.type" :class="{ active: egg.type === chooseType}">
                    <img :src="egg.url" alt="">
                    <span>{{ egg.name }}</span>
                    <input type="radio" name="eggRadioGroup" :id="'eggRadio' + egg.type" :value="egg.type" v-model="chooseType">
                  </label>
                </div>
              </div>
              <div class="form-group" v-if="chooseType">
                <label for="ipt_add_eggname">Egg Name</label>
                <input type="text" class="form-control" id="ipt_add_eggname" v-model="eggname" placeholder="Pick an interesting name!">
              </div>
              <div class="form-group" v-if="chooseType">
                <label for="ipt_add_expire">Expire Time</label>
                <small class="tips tips-inline">Empty if no expire time needed</small>
                <input type="date" class="form-control" id="ipt_add_expire" v-model="expire" :min="tomorrow" max="2099-12-31">
              </div>
              <div class="form-group" v-if="chooseType === 1">
                <label for="ipt_add_description">Description</label>
                <textarea class="form-control" id="ipt_add_description" v-model="description" placeholder="Something you wanna share"></textarea>
              </div>
              <div class="form-group" v-if="chooseType > 1">
                <label for="ipt_add_content">Content</label>
                <input type="file" class="form-control" id="ipt_add_content" @change="upload($event)" accept="image/jpg" v-if="chooseType===3">
                <input type="file" class="form-control" id="ipt_add_content" @change="upload($event)" accept="audio/mp3" v-if="chooseType===2">
                <input type="file" class="form-control" id="ipt_add_content" @change="upload($event)" accept="video/mp4" v-if="chooseType===4">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-link" data-dismiss="modal" @click="close()">Close</button>
            <button type="button" class="btn btn-link" @click="submit()" :disabled="chooseType===null||loading">Save</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const contentURL = `${process.env.VUE_APP_HOST}/files/`
export default {
  setup () {
    return {}
  },
  data () {
    return {
      eggUrlStr: '../../static/egg&shell/egg/egg',
      eggs: [
        { type: 1, name: 'Text', url: require('../../static/egg&shell/egg/egg1.png') },
        { type: 2, name: 'Audio', url: require('../../static/egg&shell/egg/egg2.png') },
        { type: 3, name: 'Image', url: require('../../static/egg&shell/egg/egg3.png') },
        { type: 4, name: 'Video', url: require('../../static/egg&shell/egg/egg4.png') }
        // { type: 5, name: 'Secret', url: require('../../static/egg&shell/egg/egg5.png') }
      ],
      chooseType: null,
      description: '',
      eggname: '',
      user: null,
      expire: null,
      tomorrow: '',
      fileIsUploaded: false,
      filename: '',
      loading: false
    }
  },
  beforeMount () {
    this.user = this.$store.state.currentUser
    console.log(this.user)
    const today = new Date()
    const tomorrow = today.setDate(today.getDate() + 1)
    this.tomorrow = new Date(tomorrow).toISOString().substring(0, 10)
  },
  methods: {
    verifyInput () {
      if (this.chooseType === 1 && this.description === '') {
        this.$store.commit('updateAlert', {
          msg: 'Please enter the description!',
          type: 'warning'
        })
        return false
      } else if (this.chooseType !== 1 && (this.eggname === '' || this.fileIsUploaded === false)) {
        if (this.eggname === '') {
          this.$store.commit('updateAlert', {
            msg: 'Please fill in egg\'s name before uploading the file!',
            type: 'warning'
          })
        } else if (this.fileIsUploaded === false) {
          this.$store.commit('updateAlert', {
            msg: 'Please upload the file!',
            type: 'warning'
          })
        }
        return false
      }
      return true
    },
    submit () {
      const _this = this
      if (this.verifyInput() === false) {
        return
      }
      console.log('submit')

      const content = this.chooseType === 1 ? this.description : (contentURL + this.filename)
      const params = {
        uuname: this.user.username,
        name: this.eggname,
        color: this.chooseType,
        type: this.chooseType,
        latitude: this.$store.state.myLatitude || 0,
        longitude: this.$store.state.myLongitude || 0,
        content: content,
        expire_time: this.expire || '2099-12-31'
      }

      console.log(params)

      _this.$store.commit('updateAlert', {
        msg: 'Hiding your egg 🐣...',
        sync: true
      })
      this.loading = true
      this.axios.post('/egg', null, {
        params: params
      }).then(res => {
        console.log(res)
        const params1 = {
          uuname: _this.user.username,
          user_id: _this.user.id,
          egg_id: res.data.result.data[0].id,
          action: 1
        }
        _this.axios.post('/action', null, {
          params: params1
        }).then(res => {
          console.log(res)
          _this.$store.commit('updateAlert', {
            msg: 'You successfully hide an egg 🤫...',
            type: 'success'
          })
          _this.close()
        })
      }).catch(err => {
        console.log(err)
        if (err.response.status === 500) {
          _this.$store.commit('updateAlert', {
            msg: 'Oops! Somebody used the same name with your egg, please change a new one.',
            type: 'warning'
          })
        }
      }).finally(() => {
        _this.loading = false
      })
    },
    close () {
      this.$parent.hideEggWindow = false
    },
    upload (e) {
      const _this = this
      if (this.verifyInput === false) {
        return
      }
      console.log('welcome')

      const file = e.target.files[0]

      const format = file.name.split('.').pop()
      const filename = `${this.eggname}_content.${format}`
      this.filename = filename

      console.log(filename)

      const fileReader = new FileReader()
      fileReader.onload = () => {
        document.getElementById('ipt_add_content').src = fileReader.result
      }
      fileReader.readAsDataURL(file)

      const formData = new FormData()
      formData.append('file', file, filename)

      this.$store.commit('updateAlert', {
        msg: 'Uploading file, please wait...',
        type: 'primary',
        sync: true
      })
      this.loading = true
      this.axios.post('/file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        _this.$store.commit('updateAlert', {
          msg: 'Your file uploaded successfully!',
          type: 'success',
          sync: false
        })
        _this.fileIsUploaded = true
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
      }).finally(() => {
        _this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.egg-window textarea {
  resize: none;
  height: 7rem;
}
.egg-window .modal {
  display: flex;
  height: auto;
}
.egg-window .modal .modal-dialog {
  width: 100%;
}
.egg-group {
  display: flex;
  justify-content: space-between;
}
.egg-btn {
  width: 23%;
  border: 1px solid #ccc;
  color: #ccc;
  border-radius: 0.2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0.3rem;
}
.egg-btn.active {
  border-color: #037bfe;
  color: #037bfe;
}
.egg-btn input[type="radio"]{
  display: none;
}
.egg-btn img {
  width: 3rem;
}
</style>
