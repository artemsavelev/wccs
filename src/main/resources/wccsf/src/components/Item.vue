<template>
  <div class="main font-s mt-0">
    <div class="main-container">
      <div class="main-container-wrapper pa-1 column-70">
        {{ item.id }}
      </div>

      <div class="main-container-wrapper pa-1 column-700">
        {{ item.name }}
      </div>
      <div class="main-container-wrapper pa-1 column-100">
        {{ item.dimension }}
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        <input type="text" placeholder="0" v-model="quantity"/>
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.price }}
      </div>

      <div class="main-container-wrapper pa-1 column-600">
        {{ item.note }}
      </div>
      <div class="main-container-wrapper column-70">
        <v-card-actions>
          <v-btn v-on:click="add" small color="primary" tile>add</v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Item",
  props: ['item', 'typeSection'],
  data() {
    return {

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
        quantity: this.quantity
      }
      this.$emit('add', obj)
    }
  }
}
</script>

<style scoped>
input {
  border: 1px solid gray;
  width: 70px;
  border-radius: 0px;
  padding-left: 5px;
  padding-right: 5px;
  text-align: center;
}

input:focus {
  border: 1px solid #1976d2;
  width: 70px;
  border-radius: 0;

}
</style>