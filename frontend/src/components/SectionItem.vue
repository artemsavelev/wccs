<template>

  <div class="section-row font-s mt-0 ">
    <div class="section-row-container">
      <div class="section-cols col-idx pl-2" v-bind:style="this.styleAddedItem.done">
        {{ item.id }}
      </div>

      <div class="section-cols col-name" v-bind:style="this.styleAddedItem.done">
        <v-tooltip bottom class="col-name">
          <template v-slot:activator="{ on }">
            <span v-on="on">{{ item.name }}</span>
          </template>
          <span>{{ item.name }}</span>
        </v-tooltip>
      </div>

      <div class="section-cols col-dimension" v-bind:style="this.styleAddedItem.done">
        {{ item.dimension }}
      </div>

      <div class="section-cols col-quantity">
        <input type="text" placeholder="0" v-model="quantity" class="input" />
      </div>

      <div class="section-cols col-price" v-bind:style="this.styleAddedItem.done">
        {{ item.price.toLocaleString('ru-RU') }}
      </div>

      <div class="section-cols col-comment" v-bind:style="this.styleAddedItem.done">
        <v-tooltip bottom max-width="700">
          <template v-slot:activator="{ on }">
            <span v-on="on">{{ item.note }}</span>
          </template>
          <span>{{ item.note }}</span>
        </v-tooltip>
      </div>

      <div class="section-cols col-action-section">
        <v-card-actions class="ma-0 pa-0">
          <v-btn icon v-on:click="add" :disabled="this.styleAddedItem.key === 1">
            <v-icon color="iconLight">mdi-plus</v-icon>
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
  props: ['item', 'typeSection', 'extId'],
  computed: {

  },
  data() {
    return {
      env,
      styleAddedItem: {},
      quantity: '',
      done: '',
      key: 0,
    }
  },


  mounted() {
    if (localStorage.getItem(this.extId + '_' + this.item.name) !== null) {
      this.styleAddedItem = JSON.parse(localStorage.getItem(this.extId + '_' + this.item.name))
      // console.log('mounted SectionItem', this.styleAddedItem)
    }
  },

  methods: {

    add() {

      let item = {
        id: this.item.id,
        name: this.item.name,
        dimension: this.item.dimension,
        price: this.item.price,
        quantity: this.quantity === '' ? 1 : this.quantity
      }

      this.styleAddedItem = {
        key: this.styleAddedItem.key = 1,
        done: this.styleAddedItem.done = 'color: #F8425F;'
      }

      localStorage.setItem(this.extId + '_' + this.item.name, JSON.stringify(this.styleAddedItem));
      this.$emit('transmitItemInModalForm', item)
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