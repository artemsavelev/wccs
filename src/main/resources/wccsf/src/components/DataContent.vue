<template>
  <div class="main font-s mt-0">
    <div class="main-container">
      <div class="main-container-wrapper pa-1 column-80">

      </div>

      <div class="main-container-wrapper pa-1 column-700">

      </div>
      <div class="main-container-wrapper pa-1 column-100">

      </div>

      <div class="main-container-wrapper pa-1 column-100">

      </div>

      <div class="main-container-wrapper pa-1 column-100">

      </div>

      <div class="main-container-wrapper pa-1 column-100">

      </div>

      <div class="main-container-wrapper pa-1 column-600">

      </div>
      <div class="main-container-wrapper column-70">
        <v-card-actions>
          <v-btn v-on:click="remove" small color="primary" tile>
            <v-icon>{{ mdiClose }}</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>

import { mdiClose } from '@mdi/js';

export default {
  name: "DataContent",
  data() {
    return {
      mdiClose,
    }
  },
  methods: {
    remove(id) {
      console.log(id)
    }
  }
}
</script>

<style scoped>

</style>