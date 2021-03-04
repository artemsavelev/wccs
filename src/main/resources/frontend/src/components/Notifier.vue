<template>
  <v-card>
    <v-snackbar v-model="show"
                :color="color"
                :multi-line="mode === 'multi-line'"
                :min-width="1000"
                :top="y === 'top'"
                :right="x === 'right'"
                :bottom="y === 'bottom'"
                :left="x === 'left'"
                :timeout="timeout"
                :vertical="mode === 'vertical'">
      <v-icon class="ma=3" dark>{{ icon }}</v-icon>

      <template>

      {{ message }}

      </template>

      <template v-slot:action="{ attrs }">
        <v-btn dark text @click="show = false" tile v-bind="attrs">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script>

export default {
  name: "Notifier",

  created() {

    this.$store.subscribe((mutation, state) => {


      if (mutation.type === 'setSnack') {
        // console.log(mutation)
        // console.log(state)

        this.message = state.snackbar.payload.message;
        this.color = state.snackbar.payload.color;

        console.log(this.color)



        switch (this.color) {
          case "success":
            this.icon = 'mdi-check-circle';
            break;
          case "warring":
            this.icon = 'mdi-alert'
            break;
          case "error":
            this.icon = 'mdi-alert-circle'
            break;
        }


        this.show = true;
      }

    });

    // if (this.color === 'success') {
    //   this.icon = 'mdi-check-circle'
    // }
    // if (this.color === 'warring') {
    //   this.icon = 'mdi-alert'
    // }
    // if (this.color === 'error') {
    //   this.icon = 'mdi-alert-circle'
    // }

  },

  data () {
    return {
      // icon: 'mdi-check-circle',
      // icon: 'mdi-alert-circle',
      icon: '',
      show: false,
      message: '\t',
      mode: '',
      color: '',
      timeout: 6000,
      x: '',
      y: 'top',
    }
  },


}
</script>

<style scoped>

</style>