<template>
  <div>


    <v-switch
        class="pt-0 pb-0 mt-10 mb-0 ml-5 "
        @change="change"
        inset
        :label="nameKey"
        persistent-hint>

    </v-switch>

    <!--  <v-btn v-on:click="change" small class="pt-0 pb-0 mt-10 mb-0 ml-5 " color="primary" tile>{{ this.nameKey }}</v-btn>-->


  </div>
</template>

<script>



export default {
  name: "Settings",
  computed: {
  },
  data: () => ({
    nameKey: 'Change Theme',
  }),

  methods: {

    change() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
      // this.$vuetify.theme.dark ? this.nameKey = 'Get Light Theme' : this.nameKey = 'Get Dark Theme'
      // this.$vuetify.theme.dark ? this.switch1 = true : this.switch1 = false
      localStorage.setItem('darkTheme', this.$vuetify.theme.dark.toString())
    },

  }
}

</script>

<style scoped>

</style>