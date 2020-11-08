/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The Social Circle Page, presents the information of uers' friends and eggs
 */
<template>
  <div id="circle" class="my-container">

    <div class="sections">

      <div class="top">
        <div class="form-group">
          <label for="sectionRadios1" name="sectionRadios" class="btn btn-light" :class="{active: choosenSection === 1}" @click="updateMyEggs()">
            <img class="icon" src="../../static/icons/myEgg.png" alt=""> My Eggs</label>
          <label for="sectionRadios2" name="sectionRadios" class="btn btn-light" :class="{active: choosenSection === 2}" @click="updateFound()">
            <img class="icon" src="../../static/icons/foundEggs.png" alt=""> Found Eggs</label>
        </div>
        <div class="form-group">
          <label for="sectionRadios3" name="sectionRadios" class="btn btn-light" :class="{active: choosenSection === 3}" @click="updateMyFriends()">
            <img class="icon" src="../../static/icons/friend.png" alt=""> My Friends</label>
          <label for="sectionRadios4" name="sectionRadios" class="btn btn-light" :class="{active: choosenSection === 4}" @click="updateFriendsEggs()">
            <img class="icon" src="../../static/icons/otherEggs.png" alt=""> Friends' Eggs</label>
        </div>
        <div class="hidden">
          <input type="radio" name="sectionRadios" id="sectionRadios1" value="1" v-model="choosenSection">
          <input type="radio" name="sectionRadios" id="sectionRadios2" value="2" v-model="choosenSection">
          <input type="radio" name="sectionRadios" id="sectionRadios3" value="3" v-model="choosenSection">
          <input type="radio" name="sectionRadios" id="sectionRadios4" value="4" v-model="choosenSection">
        </div>
        <hr>
      </div>

      <div class="down">
        <div class="info-list" v-if="choosenSection !== 3">
          <ul>
            <li v-for="egg in eggList" :key="egg.id">
              <div class="left">
                <div class="img-box">
                  <img class="img img-thumbnail rounded-circle" :src="imgURL + 'egg'+ egg.type +'.png'" alt="">
                </div>
                <div class="text"><span class="egg-name">{{ egg.name }}</span><small class="tips">{{typeDict[egg.type]}} Egg</small></div>
              </div>
              <div class="tools">
                <button @click="detailEgg(egg)" v-if="choosenSection !== 3"><i class="fas fa-info"></i></button>
                <button @click="showEgg(egg)"><i class="fas fa-map-marker-alt"></i></button>
                <!-- <button @click="deleteEgg(egg)" v-if="choosenSection === 1"><i class="fas fa-trash-alt"></i></button> -->
              </div>
            </li>
          </ul>
        </div>

        <div class="info-list" v-if="choosenSection === 3">
          <ul>
            <li v-for="f in myFriends" :key="f.id">
              <div class="left">
                <div class="img-box">
                  <img class="img img-thumbnail rounded-circle" :src="porURL + f.id + '_portrait.jpg'" @error="imgNotFound($event)" alt="">
                </div>
                <div class="text">
                  <span class="egg-name">
                    {{f.nickname || f.username}}
                    <span class="gender">
                      <i v-if="f.gender==='M'" class="fas fa-mars"></i>
                      <i v-else-if="f.gender==='F'" class="fas fa-venus"></i>
                      <i v-else class="fas fa-question"></i>
                    </span>
                  </span>
                  <small class="tips">Hide {{f.set_count}} | Found {{f.get_count}}</small>
                </div>
              </div>
              <div class="tools">
                <button><i class="fas fa-trash-alt"></i></button>
              </div>
            </li>
          </ul>
        </div>

        <div class="loading" v-show="loading">
          <div class="loading-wrap">
            <div class="spinner-border spinner-border-sm text-primary" role="status">
              <span class="sr-only">Loading...</span>
            </div>
            <span class="ml-3">Loading...</span>
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
      choosenSection: 1,
      myEggs: [],
      username: '',
      actions: [],
      myFriends: [],
      typeDict: {
        1: 'Text',
        2: 'Audio',
        3: 'Image',
        4: 'Video'
      },
      loading: false,
      otherEggs: [],
      eggList: [],
      imgURL: `${process.env.VUE_APP_STATIC}/icon/`,
      porURL: `${process.env.VUE_APP_HOST}/files/`,
      defaultPortrait: require('../assets/portraits/default-portrait.svg')
    }
  },
  mounted () {
    this.$store.state.myEggs = this.myEggs
    console.log(this.$store.state, this.myEggs)
    this.username = this.$store.state.currentUser.username
    this.userid = this.$store.state.currentUser.id
    console.log(this.username)
    this.updateMyEggs()
    // this.updateMyFriends()
  },
  methods: {
    filterEggFromAction (actions) {
      for (const action of actions) {
        action.egg.user = action.user
        action.egg.action_id = action.id
        action.egg.action = action.action
        action.egg.action_time = action.update_time
        this.eggList.push(action.egg)
      }
      console.log(this.eggList)
    },
    imgNotFound (event) {
      const img = event.srcElement
      img.src = this.defaultPortrait
      img.onerror = null
    },
    updateActionEgg (type) {
      // type1 hide; type2 seek
      const _this = this
      this.choosenSection = type
      this.eggList = []
      this.loading = true
      this.axios.get(`/action?uuname=${this.username}&user_id=${this.userid}&action=${type}`)
        .then(res => {
          console.log('eggs from action', type, res)
          _this.actions = res.data.result.data
          _this.filterEggFromAction(_this.actions)
        }).catch(err => {
          console.log(err.response.status)
        }).finally(() => {
          _this.loading = false
        })
    },
    updateMyEggs () {
      this.updateActionEgg(1)
    },
    updateFound () {
      this.updateActionEgg(2)
    },
    updateMyFriends () {
      const _this = this
      this.choosenSection = 3
      this.loading = true
      this.axios.get(`/user?uuname=${this.username}`)
        .then(res => {
          const userList = res.data.result.data
          _this.myFriends = userList.filter(u => u.id !== _this.userid)
          console.log(_this.myFriends)
        }).finally(() => {
          _this.loading = false
        })
    },
    updateFriendsEggs () {
      const _this = this
      this.choosenSection = 4
      this.loading = true
      this.eggList = []
      this.axios.get(`/othersegg?uuname=${this.username}`)
        .then(res => {
          _this.actions = res.data.result.data
          _this.filterEggFromAction(_this.actions)
          console.log('others', _this.otherEggs)
        }).finally(() => {
          _this.loading = false
        })
    },
    detailEgg (egg) {
      if (this.choosenSection === 2) {
        egg.unknownCreater = true
      }
      this.$store.dispatch('checkFiredEgg', egg)
      window.myMap.panTo({ lat: egg.latitude, lng: egg.longitude })
      this.$router.push('/home')
    },
    showEgg (egg) {
      console.log(egg, window.myMap)
      window.myMap.panTo({ lat: egg.latitude, lng: egg.longitude })
      this.$router.push('/home')
    },
    deleteEgg (egg) {
      console.log('delete', egg)
      // const _this = this
      const query = `/action?action=1&egg_id=${egg.id}&user_id=${this.userid}&uuname=${this.username}`
      this.axios.delete(query).then(res => {
        console.log(res)
        this.axios.delete(`/egg?uuname=${this.username}&id=${egg.id}&name=${egg.name}`)
          .then(res => {
            console.log(res)
          })
      })
    }
  }
}
</script>

<style scoped>
.gender i {
  margin-left: 0.3rem;
}
i.fa-mars {
  color: #0086d6;
}
i.fa-venus {
  color: #E91E63;
}
i.fa-question {
  color: #6D6D6D;
  font-size: 0.8rem;
}
.sections {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.top .icon {
  width: 1.5rem;
  margin-right: 0.3rem;
}
.sections .down {
  display: flex;
  flex: 1;
  overflow: auto;
}
.sections .down .loading {
  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 4rem;
  background: #ababab38;
  border-radius: 0.3rem;
  backdrop-filter: blur(2px);
  transition: .2s;
}
.sections .down .loading .loading-wrap {
  display: flex;
  align-items: center;
  background: #ffffffba;
  padding: .6rem 1rem;
  border-radius: 1rem;
}
.sections .form-group {
  display: flex;
  justify-content: space-between;
}
.sections .top .form-group {
  margin-bottom: .5rem;
}
.sections label {
  width: 49%;
  height: 3rem;
  display: flex;
  align-items: center;
  font-size: 0.9rem;
}
.sections .top label {
  margin-bottom: 0;
  color: #9e9e9e;
}
.sections .top label.active {
  color: #007bff;
  border-color: transparent;
  background-color: #e8f4fd;
}
.hidden {
  display: none;
}
.card {
  display: flex;
  margin-bottom: 1rem;
  flex-direction: row;
}
.card .img-container {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
}
.info-list {
  width: 100%;
}
.info-list ul {
  margin-bottom: 3rem;
}
.info-list li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 5rem;
  padding: 0.7rem;
  margin-bottom: 0.7rem;
  border-radius: 0.3rem;
  background: #f8f8f8;
}
.info-list li .left {
  display: flex;
  flex: 1;
}

.info-list li .left .text {
  margin-left: 0.7rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 0;
  flex: 1;
}
.info-list li .left .text > span{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.info-list li .left img {
  object-fit: contain;
  width: 100%;
  height: 100%;
}
.info-list li .left .img-box {
  width: 3.5rem;
  height: 3.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  flex-shrink: 0;
}
.info-list li .tools {
  flex-shrink: 0;
}
.info-list li .tools button {
  width: 2rem;
  height: 2rem;
  border: none;
  border-radius: 50%;
  color: #8c8c8c;
  margin-left: 0.5rem;
  background: #e8e8e8;
}
</style>
