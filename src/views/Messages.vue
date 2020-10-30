<template>
  <div id="messages" class="my-container">
    <h1 class="title">Messages</h1>
    <ul class="message-list">
      <li>
        <div class="card">
          <div class="img-container">
            <img src="@/assets/logo.png" class="card-img-top" style="width:6rem;">
          </div>
          <div class="card-body">
            <h5 class="card-title">My first egg</h5>
            <p class="card-text">This is the description of eggs</p>
            <a href="#" class="btn btn-light">Find it on map</a>
          </div>
        </div>
      </li>
      <li>
        <div class="card">
          <div class="img-container">
            <img src="@/assets/logo.png" class="card-img-top" style="width:6rem;">
          </div>
          <div class="card-body">
            <h5 class="card-title">My second egg</h5>
            <p class="card-text">This is the description of eggs</p>
            <a href="#" class="btn btn-light">Find it on map</a>
          </div>
        </div>
      </li>
      <li>
        <div class="card">
          <div class="img-container">
            <img src="@/assets/logo.png" class="card-img-top" style="width:6rem;">
          </div>
          <div class="card-body">
            <h5 class="card-title">My third egg</h5>
            <p class="card-text">This is the description of eggs</p>
            <a href="#" class="btn btn-light">Find it on map</a>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  setup () {
    return {}
  }
}
</script>

<style scoped>
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
</style>
