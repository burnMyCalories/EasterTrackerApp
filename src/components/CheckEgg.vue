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

          <div class="modal-body" v-if="(eggExpire || eggFaraway) && !eggIsMine && eggNotChecked">
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
            <div class="form-group egg-info">
              <span>
                <h4>{{firedEgg.user.nickname || firedEgg.user.username}}'s</h4>
                <span class="egg-type">{{eggTypeStr[firedEgg.type]}} Egg</span>
              </span>
              <div class="img-group">
                <img class="img img-thumbnail rounded-circle egg-img" :src="porURL + firedEgg.user.id + '_portrait.jpg'" @error="imgNotFound($event)" alt="">
                <img class="img img-thumbnail rounded-circle egg-img" :src="eggURL + firedEgg.type+'.png'" alt="">
              </div>
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
              <div class="media-box" v-if="firedEgg.type !== 1">
                <audio controls :src="firedEgg.content" v-if="firedEgg.type===2"></audio>
                <video controls :src="firedEgg.content" v-if="firedEgg.type===4"></video>
                <img :src="firedEgg.content" alt="" v-if="firedEgg.type===3">
              </div>
            </div>
          </div>

          <div class="modal-footer" v-if="!eggNotChecked">
            <div class="delete" v-if="eggIsMine">
              <button type="button" class="btn btn-link" v-if="!confirmDelete" @click="confirmDelete = true"><i class="fas fa-trash-alt"></i></button>
              <span class="delete-confirm" v-if="confirmDelete">
                Confirm Delete?
                <button class="btn btn-outline-danger btn-sm ml-1" @click="deleteEgg()" :disabled="isDeleting">Delete</button>
                <button class="btn btn-outline-primary btn-sm ml-1" @click="confirmDelete = false" :disabled="isDeleting">Cancel</button>
              </span>
            </div>
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
    this.show = this.$store.state.firedEgg !== null
    this.myName = this.$store.state.currentUser.username

    if (this.firedEgg) {
      this.eggExpire = new Date() > new Date(this.firedEgg.expire_time)
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
      defaultPortrait: require('../assets/portraits/default-portrait.svg'),
      eggURL: `${process.env.VUE_APP_STATIC}/icon/egg`,
      porURL: `${process.env.VUE_APP_HOST}/files/`,
      confirmDelete: false,
      isDeleting: false
    }
  },
  methods: {
    imgNotFound (event) {
      const img = event.srcElement
      img.src = this.defaultPortrait
      img.onerror = null
    },
    close () {
      this.$store.commit('resetFiredEgg')
      this.confirmDelete = false
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
    },
    deleteEgg () {
      console.log('delete')
      const _this = this
      this.isDeleting = true
      const egg = this.firedEgg
      const query = `/action?action=1&egg_id=${egg.id}&user_id=${egg.user.id}&uuname=${egg.user.username}`
      this.$store.commit('updateAlert', {
        msg: 'Deleting your egg...',
        type: 'primary',
        sync: true
      })
      this.axios.delete(query).then(res => {
        console.log(res)
        this.axios.delete(`/egg?uuname=${egg.user.username}&id=${egg.id}&name=${egg.name}`)
          .then(res => {
            console.log(res)
            this.$store.commit('updateAlert', {
              msg: 'Your egg successfully deleted!',
              type: 'success'
            })
            _this.close()
          })
      }).catch(_err => {
        _this.$store.commit('updateAlert', {
          msg: 'Deleting Error!',
          type: 'danger'
        })
      }).finally(() => {
        _this.isDeleting = false
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
  .egg-img {
    width: 4rem;
    height: 4rem;
    object-fit: contain;
    box-shadow: 1px 1px 3px #b5b5b5;
  }
  .egg-img:first-child {
    position: relative;
    left: 1.5rem;
  }
  .form-group.egg-info {
    display: flex;
    justify-content: space-between;
  }
  .egg-info .egg-type {
    color: #999999;
  }
  .media-box {
    width: 100%;
    min-height: 3rem;
    max-height: 12rem;
    display: flex;
    justify-content: center;
    border: 0.1rem solid #ccc;
    border-radius: 0.3rem;
  }
  .media-box img {
    object-fit: contain;
    width: 100%;
  }
  .media-box video {
    width: 100%;
  }
  .media-box audio {
    width: 100%;
    background: #f0f3f4;
    border-radius: 0.3rem;
    /* border: 0.1rem solid #ccc; */
  }
  .modal-footer .delete {
    display: flex;
    flex: 1;
    justify-content: flex-start;
    align-items: center;
  }
</style>
