<template>
  <Alert class="alert"/>
  <router-view/>
</template>
<script>
import Alert from '@/components/Alert.vue'

export default {
  setup () {
  },
  components: { Alert }
}
</script>
<style>
.my-container {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: #fff;
    padding: 2rem;
    overflow: auto !important;
}
.my-container .title {
  margin-top: 1rem;
  margin-bottom: 3rem;
}
.my-container .title.lg {
  margin-top: 3rem;
}
.icon-btn {
  border: none;
  background: transparent;
  color: #666;
  padding: 0.5rem;
  outline: none !important;
}
.icon-btn :focus{
  outline: none;
}
.alert {
  z-index: 1000;
}
</style>
