<template>
<div>
  <v-row>
    <v-col cols="12">
      <v-textarea outlined
                  :auto-grow="true"
                  rows="4"
                  class="rounded-0"
                  name="input-1"
                  v-model="workDescription"
                  :label="env.typeWork"></v-textarea>

      <v-textarea outlined
                  :auto-grow="true"
                  rows="3"
                  class="rounded-0"
                  name="input-2"
                  :label="env.note"
                  v-model="visibleComment"></v-textarea>
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
  computed: {
    visibleComment() {
      return this.ex ? env.preliminary + env.priceTimeout : env.priceTimeout
    }
  },

  data() {
    return {
      env,
      workDescription: env.workDescription,
      comment: this.visibleComment
    }
  },

  methods: {
    add() {

      const description = {
        workDescription: this.workDescription,
        comment: this.visibleComment
      }

      this.$emit('add', description)

    },
  }
}
</script>

<style scoped>

</style>