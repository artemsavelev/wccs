<template>
  <div>
    <v-switch
        class="pt-0 pb-0 mt-10 mb-0 ml-5 "
        @change="change"
        inset
        v-model="switch1"
        :label="nameKey"
        persistent-hint>
    </v-switch>

  </div>
</template>

<script>



export default {
  name: 'Settings',
  computed: {
  },
  data: () => ({
    switch1: JSON.parse(localStorage.getItem('darkTheme')),
    nameKey: JSON.parse(localStorage.getItem('darkTheme')) ? 'Get Light Theme' : 'Get Dark Theme',
  }),
  updated() {
    this.nameKey = JSON.parse(localStorage.getItem('darkTheme')) ? 'Get Light Theme' : 'Get Dark Theme'
  },
  methods: {
    change() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
      localStorage.setItem('darkTheme', this.$vuetify.theme.dark.toString())
    },

  }
}

</script>

<style scoped>

</style>