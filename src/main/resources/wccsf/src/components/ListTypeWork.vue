<template>
<div>
  <v-row>
    <v-col cols="12">
      <v-textarea outlined
                  :auto-grow="true"
                  rows="4"
                  name="input-1"
                  v-model="typeOfWork"
                  label="Виды работ"></v-textarea>

      <v-textarea outlined
                  :auto-grow="true"
                  rows="3"
                  name="input-2"
                  label="Примечание"
                  :disabled="!ex"
                  v-model="text"></v-textarea>
      <v-btn class="" v-on:click="add" small color="primary" tile>{{ env.keyAdd }}</v-btn>
    </v-col>
  </v-row>
</div>
</template>

<script>
import env from '../../env.config.json'

export default {
  name: "ListTypeWork",
  props: ['ex'],
  data() {
    return {
      env,
      typeOfWork: env.workDescription,
      text: env.preliminary,
    }
  },
  methods: {
    add() {
      let type = {
        typeOfWork: this.typeOfWork,
        text: this.ex ? this.text : null
      }
      this.$emit('add', type)
    },
  }
}
</script>

<style scoped>

</style>