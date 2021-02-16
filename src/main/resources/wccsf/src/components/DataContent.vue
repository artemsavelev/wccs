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

      <div v-if="editedIndex" class="main-container-wrapper pa-1 column-100">
        <input type="text" placeholder="0" v-model="item.quantity" class="input" />
      </div>
      <div v-if="!editedIndex" class="main-container-wrapper pa-1 column-100">
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
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon v-on:click="remove">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>


export default {
  name: "DataContent",
  props: ['item'],
  data() {
    return {

      editedIndex : false
    }
  },
  methods: {
    edit() {
      this.editedIndex = true
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
.input {
  border: 1px solid gray;
  width: 70px;
  text-align: right;
  padding: 2px;
  outline:none;
}

.input:focus {
  border: 1px solid #d21919;
  width: 70px;
  outline:none;

}
</style>