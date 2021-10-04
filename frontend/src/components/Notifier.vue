<template>
  <v-card>
    <v-snackbar v-model="show"
                tile
                transition="scroll-x-transition"
                :outlined="outlined"
                :color="color"
                :multi-line="mode === 'multi-line'"
                :top="y === 'top'"
                :right="x === 'right'"
                :bottom="y === 'bottom'"
                :left="x === 'left'"
                :timeout="timeout"
                :max-height="maxHeight"
                :max-width="maxWidth"
                :vertical="mode === 'vertical'">

      <div class="snackbar-container">
        <div class="snackbar-item">
          <v-icon class="item" :color="colorIcon" dark>{{ icon }}</v-icon>
        </div>

        <div class="snackbar-item">
          {{ message }}
        </div>
      </div>

      <template v-slot:action="{ attrs }">
          <v-icon @click="show = false" :color="colorIcon" v-bind="attrs" class="mr-5" dark>mdi-close</v-icon>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>

export default {
  name: "Notifier",

  updated() {
    const getSnackProp = JSON.parse(localStorage.getItem('snackProp'))
    this.maxHeight = getSnackProp.h
    this.maxWidth = getSnackProp.w
    this.x = getSnackProp.x.name
    this.y = getSnackProp.y.name
    this.outlined = getSnackProp.outlined
    this.timeout = getSnackProp.duration
    if (this.outlined) {
      this.colorIcon = this.color
    } else {
      this.colorIcon = ''
    }
  },

  created() {

    this.$store.subscribe((mutation, state) => {
      if (mutation.type === 'setSnack') {

        // switch (state.snackbar.payload.color) {
        //   case "success":
        //
        //     console.log(state.snackbar.payload.color)
        //     this.icon = 'mdi-check-circle'
        //     break
        //   case "warring":
        //     console.log(state.snackbar.payload.color)
        //     this.icon = 'mdi-alert'
        //     break
        //   case "error":
        //     console.log(state.snackbar.payload.color)
        //     this.icon = 'mdi-alert-circle'
        //     break
        //     // default: this.icon = ''
        //     //     break
        // }

        this.message = state.snackbar.payload.message
        this.color = state.snackbar.payload.color
        this.icon = state.snackbar.payload.icon
        this.show = true;
      }
    });

  },

  data () {
    return {
      icon: '',
      show: false,
      message: '\t',
      mode: '',
      color: '',
      outlined: false,
      colorIcon: '',
      timeout: 6000,
      maxHeight: 700,
      maxWidth: 700,
      x: '',
      y: 'top',
    }
  },


}
</script>

<style lang="scss">

.snackbar-container {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
  align-items: center;
}

.snackbar-item {
  margin-left: 20px;
}


</style>