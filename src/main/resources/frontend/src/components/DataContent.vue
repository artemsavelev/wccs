<template>
  <div class="section-row font-s mt-0">
    <div class="section-row-container">
      <div class="section-cols col-idx pl-2">
        {{ item.id }}
      </div>

      <div class="section-cols col-name">
        {{ item.name }}
      </div>

      <div class="section-cols col-dimension">
        {{ item.dimension }}
      </div>

      <div v-if="editedIndex" class="section-cols col-quantity">
        <input type="text" placeholder="0" v-model="item.quantity" class="input" />
      </div>

      <div v-if="!editedIndex" class="section-cols col-quantity">
        {{ item.quantity.toLocaleString('ru-RU') }}
      </div>

      <div class="section-cols col-price">
        {{ item.price.toLocaleString('ru-RU') }}
      </div>

      <div class="section-cols col-sum">
        {{ item.price * item.quantity | format }}
      </div>

      <div class="section-cols col-action-section">
        <v-card-actions class="pa-0 ma-0">
          <v-btn icon v-on:click="edit">
            <v-icon color="iconLight">mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon v-on:click="remove">
            <v-icon color="iconLight">mdi-delete</v-icon>
          </v-btn>
        </v-card-actions>
      </div>
    </div>
  </div>
</template>

<script>


export default {
  name: "DataContent",
  props: ['item', 'extId'],
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
      localStorage.removeItem(this.extId + '_' + this.item.name);
      this.$emit('remove', this.item.id)
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style lang="scss">

.input:focus {
  border: 1px solid #F8425F;
}

</style>