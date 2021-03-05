<template>
  <v-card>
    <v-snackbar v-model="show"
                :color="color"
                :multi-line="mode === 'multi-line'"
                :top="y === 'top'"
                :right="x === 'right'"
                :bottom="y === 'bottom'"
                :left="x === 'left'"
                :timeout="timeout"
                :vertical="mode === 'vertical'">

      <div class="snackbar-container">
        <div class="snackbar-item">
          <v-icon dark>{{ icon }}</v-icon>
        </div>

        <div class="snackbar-item">
          {{ message }}
        </div>
      </div>

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

        this.message = state.snackbar.payload.message;
        this.color = state.snackbar.payload.color;
        this.icon = state.snackbar.payload.icon;


        // switch (this.color) {
        //   case "success":
        //     this.icon = 'mdi-check-circle';
        //     break;
        //   case "warring":
        //     this.icon = 'mdi-alert'
        //     break;
        //   case "error":
        //     this.icon = 'mdi-alert-circle'
        //     break;
        // }


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
      timeout: 6000,
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
}

.snackbar-item {
  //border: 1px solid black;
  margin-left: 20px;
}

</style>