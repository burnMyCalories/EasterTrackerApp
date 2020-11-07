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
                  <label :for="'eggRadio' + egg.type" class="egg-btn" v-for="egg in eggs" :key="egg.type" :class="{ active: egg.type === chooseType}" @click="resetFile()">
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
                <label for="ipt_add_content">Upload & Preview</label>
                <div class="media-box form-control">
                  <label for="ipt_add_content" v-if="!fileLoaded">
                    <span v-if="chooseType === 3">Select or Capture a Photo</span>
                    <span v-if="chooseType === 2">Select or Record an Audio</span>
                    <span v-if="chooseType === 4">Select or Capture a Video</span>
                  </label>

                  <div class="preview" v-if="fileLoaded">
                    <img id="previewMedia" src="" alt="" v-if="chooseType === 3">
                    <audio id="previewMedia" controls src="" v-if="chooseType === 2"></audio>
                    <video id="previewMedia" controls src="" v-if="chooseType === 4"></video>
                  </div>
                </div>

                <input type="file" class="form-control" id="ipt_add_content" @change="preview($event)" accept="image/jpg" v-if="chooseType===3">
                <input type="file" class="form-control" id="ipt_add_content" @change="preview($event)" accept="audio/mp3" v-if="chooseType===2">
                <input type="file" class="form-control" id="ipt_add_content" @change="preview($event)" accept="video/mp4" v-if="chooseType===4">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-link" data-dismiss="modal" @click="close()">Close</button>
            <button type="button" class="btn btn-link" @click="submit()" :disabled="invalidSave">Save</button>
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
      filename: '',
      loading: false,
      fileLoaded: false
    }
  },
  computed: {
    invalidSave () {
      return !this.chooseType || this.loading
    }
  },
  beforeMount () {
    this.user = this.$store.state.currentUser
    const today = new Date()
    const tomorrow = today.setDate(today.getDate() + 1)
    this.tomorrow = new Date(tomorrow).toISOString().substring(0, 10)
    this.$store.commit('resetFiredEgg')
  },
  methods: {
    resetFile () {
      this.fileLoaded = false
      document.getElementById('ipt_add_content') && (document.getElementById('ipt_add_content').value = '')
    },
    verifyInput () {
      if (this.eggname === '') {
        this.$store.commit('updateAlert', {
          msg: 'It seems you forgot to name the egg! 😱',
          type: 'warning'
        })
        return false
      }

      if (this.chooseType === 1 && this.description === '') {
        this.$store.commit('updateAlert', {
          msg: 'Please enter the description!',
          type: 'warning'
        })
        return false
      }

      return true
    },
    save () {
      const _this = this

      console.log('save')

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
          _this.loading = false
        })
      }).catch(err => {
        console.log(err)
        if (err.response.status === 500) {
          _this.$store.commit('updateAlert', {
            msg: 'Oops! Somebody used the same name with your egg, please change a new one.',
            type: 'warning'
          })
        }
        _this.loading = false
      }).finally(() => {
      })
    },
    close () {
      this.$parent.hideEggWindow = false
    },
    preview (e) {
      this.fileLoaded = true
      const file = e.target.files[0]

      const fileReader = new FileReader()
      fileReader.onload = () => {
        document.getElementById('previewMedia').src = fileReader.result
      }
      fileReader.readAsDataURL(file)
    },
    submit () {
      const _this = this
      const inputDOM = document.getElementById('ipt_add_content')
      if (this.verifyInput() === false && inputDOM) {
        return
      }
      if (this.verifyInput() && this.chooseType === 1) {
        this.save()
        return
      }

      const file = inputDOM.files[0]

      console.log('upload', inputDOM.files)

      const format = file.name.split('.').pop()
      const filename = `${this.eggname}_content.${format}`
      this.filename = filename

      console.log(filename)

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
        _this.save()
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
      })
    }
  }
}
</script>

<style scoped>
.media-box {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 7rem;
}
.media-box label {
  margin: 0;
  position: relative;
  z-index: 500;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.media-box::after {
  content: 'PREVIEW';
  position: absolute;
  font-size: 4rem;
  color: #e0e0e02e;
  z-index: 490;
}
.media-box .preview {
  width: 100%;
  height: 100%;
  position: relative;
  z-index: 520;
  display: flex;
  align-items: center;
}
.media-box .preview > * {
  width: 100%;
  object-fit: contain;
}
.media-box .preview > img, .media-box .preview > video {
  height: 100%;
}
#ipt_add_content {
  display: none;
}
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
