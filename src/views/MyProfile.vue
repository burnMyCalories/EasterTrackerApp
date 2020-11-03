<template>
  <div id="myProfile" class="my-container" v-if="user">
    <!-- <div class="setting">
      <button class="icon-btn"><i class="fas fa-cog"></i></button>
    </div> -->
    <div class="info-box">
      <div class="potrait">
        <img class="img img-thumbnail rounded-circle" src="../assets/portraits/portrait1.jpg" alt="">
        <span class="gender" :class="user.gender">
          <i class="fas fa-mars fa-lg" v-if="user.gender === 'M'"></i>
          <i class="fas fa-venus fa-lg" v-else-if="user.gender === 'F'"></i>
          <i class="fas fa-question fa-lg" v-else></i>
        </span>
      </div>
      <!-- <font-awesome-icon icon="user-secret" /> -->
      <div class="nickname">{{ user.nickname }}</div>
    </div>
    <hr>
    <!-- <div class="menu">
      <div class="btn-grp">
        <button class="btn btn-light">
          <i class="fas fa-egg"></i> My Eggs <span class="badge badge-light">{{ user.set_count }}</span>
        </button>
        <span style="width:0.2rem;"></span>
        <button class="btn btn-light">
          <i class="fas fa-search"></i> Found <span class="badge badge-light">{{ user.get_count }}</span>
        </button>
      </div>
      <button class="btn btn-light btn-block mt-2">
        <i class="fas fa-user-friends"></i> My Friends {{}}
      </button>
    </div> -->
    <button @click="logout" class="btn btn-outline-primary btn-block">Add Friend</button>
    <button @click="logout" class="btn btn-outline-primary btn-block">Change Profile</button>
    <button @click="logout" class="btn btn-outline-primary btn-block">Change Password</button>
    <button @click="logout" class="btn btn-outline-primary btn-block">About</button>
    <button @click="logout" class="btn btn-link btn-block mt-5">Log Out</button>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: null
    }
  },
  setup () {
    return {}
  },
  mounted () {
    this.user = this.$store.state.currentUser
  },
  methods: {
    logout () {
      this.axios.put('/logout', null, {
        params: {
          username: this.user.username
        }
      })
      this.$store.commit('logout')
      this.$router.push('/')
    },
    loadProfile () {
    }
  }
}
</script>

<style scoped>
.potrait {
  display: flex;
  justify-content: center;
  position: relative;
}
.potrait .img {
  width: 9rem;
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
