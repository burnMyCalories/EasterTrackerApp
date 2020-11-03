<template>
  <div id="circle" class="my-container">
    <div class="sections">
      <div class="form-group">
        <label for="sectionRadios1" name="sectionRadios" class="btn btn-outline-primary">My Eggs</label>
        <label for="sectionRadios2" name="sectionRadios" class="btn btn-outline-primary">Found Eggs</label>
      </div>
      <div class="form-group">
        <label for="sectionRadios3" name="sectionRadios" class="btn btn-outline-primary">My Friends</label>
        <label for="sectionRadios4" name="sectionRadios" class="btn btn-outline-primary">Friends' Eggs</label>
      </div>
      <div class="hidden">
        <input type="radio" name="sectionRadios" id="sectionRadios1" value="1" v-model="choosenSection">
        <input type="radio" name="sectionRadios" id="sectionRadios2" value="2" v-model="choosenSection">
        <input type="radio" name="sectionRadios" id="sectionRadios3" value="3" v-model="choosenSection">
        <input type="radio" name="sectionRadios" id="sectionRadios4" value="4" v-model="choosenSection">
      </div>
    </div>
    <hr>
    <div class="info-list">
      <ul>
        <li v-for="egg in myEggs" :key="egg.id">
          <div class="left">
            <div class="img-box img img-thumbnail rounded-circle">
              <img src="../../static/egg&shell/egg/egg1.png" alt="">
            </div>
            <div class="text"><span class="egg-name">{{ egg.name }}</span><small class="tips">{{egg.type}} Egg</small></div>
          </div>
          <div class="tools">
            <button @click="detailEgg(egg)"><i class="fas fa-info"></i></button>
            <button @click="showEgg(egg)"><i class="fas fa-map-marker-alt"></i></button>
            <button @click="deleteEgg(egg)"><i class="fas fa-trash-alt"></i></button>
          </div>
        </li>
      </ul>
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
      username: ''
    }
  },
  mounted () {
    this.$store.state.myEggs = this.myEggs
    console.log(this.$store.state, this.myEggs)
    this.username = this.$store.state.currentUser.username
    console.log(this.username)
    this.updateMyEggs()
  },
  methods: {
    updateMyEggs () {
      const _this = this
      this.axios.get(`/egg?uuname=${this.username}`)
        .then(res => {
          console.log('eggs', res)
          const myEggs = res.data.result.data
          _this.$store.commit('updateMyEggs', myEggs)
        })
    }
  }
}
</script>

<style scoped>
.sections .form-group {
  display: flex;
  justify-content: space-between;
}
.sections label {
  width: 48%;
  height: 3rem;
  display: flex;
  align-items: center;
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
  margin-bottom: 3rem;
}
.info-list li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 0.05rem solid #ccc;
  height: 5rem;
  padding: 0.7rem;
  margin-bottom: 0.7rem;
  border-radius: 0.2rem;
}
.info-list li .left {
  display: flex;
}

.info-list li .left .text {
  margin-left: 0.7rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.info-list li .left img {
  width: 2.5rem;
}
.info-list li .left .img-box {
  width: 3.5rem;
  height: 3.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
.info-list li .tools button {
  width: 2rem;
  height: 2rem;
  border: 0.2rem solid #ccc;
  border-radius: 50%;
  color: #666;
  margin-left: 0.5rem;
}
</style>
