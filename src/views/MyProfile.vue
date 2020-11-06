<template>
  <div id="myProfile" class="my-container" v-if="user">
    <div class="info-box">
      <div class="potrait">
        <img class="img img-thumbnail rounded-circle" :src="porURL + user.id + '_portrait.jpg'" alt="" id="myPortraitImg" @error="loadDefaultPortrait($event)">
        <span class="gender" :class="user.gender">
          <i class="fas fa-mars fa-lg" v-if="user.gender === 'M'"></i>
          <i class="fas fa-venus fa-lg" v-else-if="user.gender === 'F'"></i>
          <i class="fas fa-question fa-lg" v-else></i>
        </span>
      </div>
      <div class="nickname">{{ user.nickname }}</div>
      <div class="text-center"><small class="tips">Hide {{ user.set_count }} | Found {{ user.get_count }}</small></div>
    </div>
    <hr>
    <button @click="logout" class="btn btn-light btn-block">Add Friend</button>
    <button @click="editProfile()" class="btn btn-light btn-block">Edit Profile</button>
    <button @click="logout" class="btn btn-light btn-block">Change Password</button>
    <button @click="showAbout()" class="btn btn-light btn-block">About</button>
    <button @click="logout" class="btn btn-light btn-block mt-5">Log Out</button>

  </div>
</template>

<script>
export default {
  data () {
    return {
      user: null,
      defaultPortrait: require('../assets/portraits/default-portrait.svg'),
      porURL: `${process.env.VUE_APP_HOST}/files/`
    }
  },
  setup () {
    return {}
  },
  mounted () {
    this.user = this.$store.state.currentUser
  },
  methods: {
    loadDefaultPortrait (event) {
      const img = event.srcElement
      img.src = this.defaultPortrait
      img.onerror = null
    },
    logout () {
      this.axios.put('/logout', null, {
        params: {
          username: this.user.username
        }
      })
      this.$store.commit('logout')
      this.$router.push('/')
    },
    editProfile () {
      this.$store.commit('editProfile')
      this.$router.push('/addProfile')
    },
    showAbout () {
      this.$store.commit('showAbout', true)
    }
  }
}
</script>

<style scoped>
.potrait {
  display: flex;
  justify-content: center;
  position: relative;
  width: 9rem;
  height: 9rem;
  margin: 0 auto;
}
.potrait .img {
  width: 9rem;
  object-fit: cover;
}
.nickname {
  text-align: center;
  font-size: 2rem;
}
.gender {
  position: absolute;
  bottom: 0;
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 4.5rem;
  background: #ececec;
  border: 0.3rem solid #989898;
  color: #717171;
}
.gender.M{
  background: #d5edff;
  border: 0.3rem solid #00c4ff;
  color: #1277a5;
}
.gender.F{
  background: #fff6fa;
  border: 0.3rem solid #ff5292;
  color: #ff63a3;
}
.menu .btn-grp {
  display: flex;
  justify-content: space-between;
}
.menu .btn-grp button {
  flex: 1;
}
.setting {
  position: absolute;
  right: 1rem;
}
</style>
