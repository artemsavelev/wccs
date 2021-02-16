<template>
  <v-card>
    <v-snackbar v-model="snackbar"
                :bottom="y === 'bottom'"
                :color="color"
                :left="x === 'left'"
                :multi-line="mode === 'multi-line'"
                :right="x === 'right'"
                :timeout="timeout"
                :top="y === 'top'"
                :vertical="mode === 'vertical'">

      {{ message }}

      <v-btn dark text @click="snackbar = false" tile>
        Close
      </v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
export default {
  name: "Notifier",
  props: ['snackbar', 'message'],

  data () {
    return {

      color: 'error',
      mode: '',
      timeout: 6000,
      x: '',
      y: 'top',
    }
  },
}
</script>

<style scoped>

</style>