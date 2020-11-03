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
                <input type="file" class="form-control" id="ipt_add_content">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-link" data-dismiss="modal" @click="close()">Close</button>
            <button type="button" class="btn btn-link" @click="submit()" :disabled="chooseType===null">Save</button>
          </div>
        </div>
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
      tomorrow: ''
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
      }
      return true
    },
    submit () {
      const _this = this
      if (this.verifyInput() === false) {
        return
      }
      console.log('submit')

      const content = this.chooseType === 1 ? this.description : null
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
        })
      }).catch(err => {
        console.log(err)
      })
    },
    close () {
      this.$parent.hideEggWindow = false
    }
  }
}
</script>

<style scoped>
.egg-window textarea {
  resize: none;
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
