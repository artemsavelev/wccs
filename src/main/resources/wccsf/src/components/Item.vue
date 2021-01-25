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
        <input type="text" placeholder="0"/>
      </div>

      <div class="main-container-wrapper pa-1 column-100">
        {{ item.price }}
      </div>

      <div class="main-container-wrapper pa-1 column-600">
        {{ item.note }}
      </div>
      <div class="main-container-wrapper column-70">
        <v-card-actions>
<!--          <v-checkbox-->
<!--              v-model="add"-->
<!--              label=""-->
<!--              color="success"-->
<!--              value="success"-->
<!--              v-on:click="add"-->
<!--              hide-details></v-checkbox>-->
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


    }
  },

  methods: {
    add() {
      let obj = {
        id: this.item.id,
        name: this.item.name,
        dimension: this.item.dimension,
        price: this.item.price
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
  border-radius: 3px;
  padding-left: 5px;
  padding-right: 5px;
  text-align: center;
}
</style>