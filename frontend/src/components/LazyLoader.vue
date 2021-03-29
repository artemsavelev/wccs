<template>
  <div></div>

</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: "LazyLoader",
  methods: mapActions(['loadPage']),
  mounted() {
    window.onscroll = () => {
      const el = document.documentElement

      const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight


      if (isBottomOfScreen) {
        this.loadPage()
      }
    }
  },
  beforeDestroy() {
    window.onscroll = null
  }
}
</script>

<style scoped>

</style>