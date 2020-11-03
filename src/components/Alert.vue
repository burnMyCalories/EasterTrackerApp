<template>
  <div class="my-alert" v-if="alert.msg">
    <div class="alert tip-box" :class="alertClass" role="alert">
      <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" v-if="alert.sync"></span>
      <span v-if="alert.type === 'danger'">Error!</span> {{ alert.msg }}
    </div>
  </div>
</template>

<script>
export default {
  setup () {
  },
  data () {
    return {
    }
  },
  beforeMount () {
    this.alert = this.$store.state.alert
  },
  computed: {
    alertClass () {
      return `alert-${this.alert.type}`
    }
  }
}
</script>

<style scoped>
.my-alert {
  width: 100%;
}
</style>
