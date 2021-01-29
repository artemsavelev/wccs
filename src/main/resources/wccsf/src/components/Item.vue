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
        <input type="text" placeholder="0" v-model="quantity" class="input" />
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.price.toLocaleString('ru-RU') }}
      </div>

      <div class="main-container-wrapper pa-1 column-600">
        {{ item.note }}
      </div>
      <div class="main-container-wrapper column-70 ">
        <v-card-actions class="ma-0 pa-0">
          <v-btn v-on:click="add" icon>
            <v-icon>{{ mdiPlus }}</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>
import env from "../../env.config.json"
import { mdiPlus } from '@mdi/js';

export default {
  name: "Item",
  props: ['item', 'typeSection'],
  data() {
    return {
      env,
      mdiPlus,
      quantity: ''

    }
  },

  methods: {
    add() {
      let obj = {
        id: this.item.id,
        name: this.item.name,
        dimension: this.item.dimension,
        price: this.item.price,
        quantity: this.quantity === '' ? 1 : 0
      }

      this.$emit('add', obj)
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style scoped>
.input {
  border: 1px solid gray;
  width: 70px;
  text-align: right;
  padding: 2px;
  outline:none;
}

.input:focus {
  border: 1px solid #1976d2;
  width: 70px;
  outline:none;

}
.t-align-right {
  text-align: right;
}

</style>