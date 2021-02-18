<template>

  <div class="section-row font-s mt-0 ">
    <div class="section-row-container">
      <div class="section-cols col-idx" v-bind:style="this.done">
        {{ item.id }}
      </div>

      <div class="section-cols col-name" v-bind:style="this.done">
        {{ item.name }}
      </div>
      <div class="section-cols col-dimension" v-bind:style="this.done">
        {{ item.dimension }}
      </div>

      <div class="section-cols col-quantity">
        <input type="text" placeholder="0" v-model="quantity" class="input" />
      </div>

      <div class="section-cols col-price" v-bind:style="this.done">
        {{ item.price.toLocaleString('ru-RU') }}
      </div>

      <div class="section-cols col-comment" v-bind:style="this.done">
        {{ item.note }}
      </div>
      <div class="section-cols col-action-section">
        <v-card-actions class="ma-0 pa-0">

<!--          <v-checkbox v-on:change="add()"-->
<!--                      class="pt-0 pb-0 mt-0 mb-0 ml-0"-->
<!--                      color="error"-->
<!--                      label=""-->
<!--                      hide-details></v-checkbox>-->
          <v-btn icon v-on:click="add" :disabled="addedItem === 1">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-card-actions>
      </div>

    </div>
  </div>

</template>

<script>
import env from "../../env.config.json"

export default {
  name: "SectionItem",
  props: ['item', 'typeSection'],
  data() {
    return {
      env,
      quantity: '',
      done: '',
      addedItem: 0

    }
  },

  methods: {
    add() {
      let obj = {
        id: this.item.id,
        name: this.item.name,
        dimension: this.item.dimension,
        price: this.item.price,
        quantity: this.quantity === '' ? 1 : this.quantity
      }
      this.addedItem = 1
      // this.done = 'color: red; text-decoration: line-through;'
      this.done = 'color: red;'
      this.$emit('add', obj)
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style scoped>

.input:focus {
  border: 1px solid #1976d2;
}

</style>