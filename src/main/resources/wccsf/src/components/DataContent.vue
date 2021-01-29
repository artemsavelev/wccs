<template>
  <div class="main font-s mt-0">
    <div class="main-container">
      <div class="main-container-wrapper pa-1 column-80">
        {{ item.id }}
      </div>

      <div class="main-container-wrapper pa-1 column-700">
        {{ item.name }}
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        {{ item.dimension }}
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.quantity.toLocaleString('ru-RU') }}
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.price.toLocaleString('ru-RU') }}
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.price * item.quantity | format }}
      </div>


      <div class="main-container-wrapper column-100">
        <v-card-actions class="pa-0 ma-0">
          <v-btn icon v-on:click="edit">
            <v-icon>{{ mdiPencil }}</v-icon>
          </v-btn>
          <v-btn icon v-on:click="remove">
            <v-icon>{{ mdiDelete }}</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>

import { mdiPencil, mdiDelete } from '@mdi/js';

export default {
  name: "DataContent",
  props: ['item'],
  data() {
    return {
      mdiPencil,
      mdiDelete,
    }
  },
  methods: {
    edit() {
      this.$emit('edit', this.item.id)
    },
    remove() {
      this.$emit('remove', this.item.id)
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style scoped>

</style>