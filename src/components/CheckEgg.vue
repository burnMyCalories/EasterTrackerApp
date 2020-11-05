<template>
  <div class="egg-window" v-if="firedEgg">
    <div class="modal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <span v-if="eggIsMine">My Egg</span>
              <span v-else>Check The Egg</span></h5>
            <button type="button" class="close" data-dismiss="modal" @click="close()" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>

          <div class="modal-body" v-if="(eggExpire || eggFaraway) && !eggIsMine">
            <h4>Sorry... 😭 </h4>
            <p v-if="eggExpire">This egg is expired... You should come a little earlier...😔</p>
            <p v-if="eggFaraway">This egg is toooooo far away from your position. Try to get closer! 💪</p>
            <button class="btn btn-primary" @click="close()">Fine</button>
          </div>

          <div class="modal-body" v-if="eggNotChecked && !eggIsMine && !eggExpire && !eggFaraway">
            <h4>Congrats! 🎉 </h4>
            <p>You find a mysterious easter egg hiden by your friend</p>
            <button class="btn btn-primary" @click="check()">Click Here to Check</button> or
            <button class="btn btn-light" @click="close()">Ignore it</button>
          </div>
          <div class="modal-body" v-if="eggIsMine || (!eggIsMine && !eggExpire && !eggNotChecked)">
            <div class="form-group">
              <img class="img img-thumbnail rounded-circle egg-img" :src="imgsrc" alt="">
              <i class="fas fa-plus"></i>
              <img class="img img-thumbnail rounded-circle egg-img" src="../../static/egg&shell/egg/egg2.png" alt="">
              <i class="fas fa-equals"></i>
              <span>
                <span>{{firedEgg.user.nickname || firedEgg.user.username}}'s</span>
                <span><small>{{eggTypeStr[firedEgg.type]}} Egg</small></span>
              </span>
              <i class="fas fa-exclamation"></i>
            </div>
            <div class="form-group">
              <label for="">Egg Title</label>
              <input type="text" :value="firedEgg.name" class="form-control" disabled>
            </div>
            <div class="form-group">
              <label for="">Expire Time</label>
              <input type="text" :value="expireTime" class="form-control" disabled>
            </div>
            <div class="form-group">
              <label for="">Content</label>
              <textarea class="form-control" disabled :value="firedEgg.content" v-if="firedEgg.type===1"></textarea>
              <audio class="form-control" controls :src="firedEgg.content" v-if="firedEgg.type===2"></audio>
              <video class="form-control" controls :src="firedEgg.content" v-if="firedEgg.type===4"></video>
              <img :src="firedEgg.content" alt="" v-if="firedEgg.type===3">
            </div>
          </div>

          <div class="modal-footer" v-if="!eggNotChecked">
            <button type="button" class="btn btn-link" data-dismiss="modal" @click="close()">OK</button>
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
  mounted () {
    const _this = this
    this.show = this.$store.state.firedEgg !== null
    this.myName = this.$store.state.currentUser.username

    if (this.firedEgg) {
      // this.eggIsMine = this.myName === this.firedEgg.user.username
      this.eggExpire = new Date() > new Date(this.firedEgg.expire_time)

      console.log('TEST')
      this.axios.get(`/files/${this.firedEgg.user.id}_portrait.jpg`).then(res => {
        _this.imgsrc = `${process.env.VUE_APP_HOST}/files/${this.firedEgg.user.id}_portrait.jpg`
      }).catch(err => {
        console.log(err)
      })
    }
  },
  computed: {
    firedEgg () {
      return this.$store.state.firedEgg
    },
    eggIsMine () {
      return this.$store.state.firedEgg.user.username === this.$store.state.currentUser.username
    },
    expireTime () {
      const date = this.firedEgg.expire_time.split(' ')
      if (date[0] === '2099-12-31') {
        return 'None'
      } else if (new Date() > new Date(this.firedEgg.expire_time)) {
        return `Expired at ${this.firedEgg.expire_time}`
      }
      return date[0]
    },
    isMyEgg () {
      return this.firedEgg.user.username === this.myName
    },
    mypos () {
      return new window.google.maps.LatLng({ lat: this.$store.state.myLatitude, lng: this.$store.state.myLongitude })
    },
    eggpos () {
      return new window.google.maps.LatLng({ lat: this.firedEgg.latitude, lng: this.firedEgg.longitude })
    },
    dist () {
      return window.google.maps.geometry.spherical.computeDistanceBetween(this.mypos, this.eggpos)
    },
    eggFaraway () {
      return this.dist > 20
    },
    eggNotChecked () {
      return this.firedEgg.eggNotChecked
    }
  },
  data () {
    return {
      eggExpire: false,
      eggType: 1,
      show: false,
      myName: '',
      eggTypeStr: {
        1: 'Text',
        2: 'Audio',
        3: 'Image',
        4: 'Video'
      },
      imgsrc: require('../assets/portraits/default-portrait.svg')
    }
  },
  methods: {
    close () {
      this.$store.commit('resetFiredEgg')
    },
    check () {
      const _this = this
      const params = {
        uuname: this.myName,
        user_id: this.$store.state.currentUser.id,
        egg_id: this.firedEgg.id,
        action: 2
      }
      console.log('check', params)
      this.$store.commit('updateAlert', {
        msg: 'Working on it...',
        sync: true,
        type: 'primary'
      })
      this.axios.post('/action', null, {
        params: params
      }).then(res => {
        console.log(res)
        // this.firedEgg.eggNotChecked = true
        _this.$store.commit('findAnEgg')
        _this.$store.commit('updateAlert', {
          msg: 'WOW! You discovered an new Easter egg!🎉',
          type: 'success'
        })
      }).catch(err => {
        console.log(err)
      })
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
  .egg-img {
    width: 4rem;
    height: 4rem;
    object-fit: contain;
  }
</style>
