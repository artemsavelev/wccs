<template>
  <div v-if="groupBy">
    <div class="mb-10" v-for="(group, i) in Object.values(groupBy)" :key="group.id">
      <span class="font-weight-light font-xl"> {{ Object.keys(groupBy)[i] }} </span>
      <div v-for="item in group" :key="item.id" class="section-row-container">
        <div class="col-idx pr-5">
          {{ item.id }}
        </div>
        <div class="col-name pr-5">
          {{ item.name }}
        </div>
        <div class="col-dimension pr-5">
          {{ item.dimension }}
        </div>
        <div class="col-price pr-5">
          {{ item.price.toLocaleString('ru-RU') }}
        </div>
        <div class="col-comment pr-5">
          {{ item.note }}
        </div>

        <div class="col-action-section">
          <v-btn v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="editComponent(item)" tile small>
            <v-icon color="iconLight">mdi-pencil</v-icon>
          </v-btn>
          <v-btn v-else icon class="ma-1 pa-0" @click="editComponent(item)" disabled tile small>
            <v-icon color="iconLight">mdi-pencil</v-icon>
          </v-btn>
          <v-btn  v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="deleteComponent(item)" tile small>
            <v-icon color="iconLight">mdi-delete</v-icon>
          </v-btn>
          <v-btn  v-else icon class="ma-1 pa-0" @click="deleteComponent(item)" disabled tile small>
            <v-icon color="iconLight">mdi-delete</v-icon>
          </v-btn>

          <div v-if="item.departments.length === 0">
            <v-btn v-on:click="add(item)"
                   class="ml-1 my-1"
                   color="red"
                   tile small icon>
              <v-icon>mdi-plus-circle</v-icon></v-btn>
          </div>

          <div v-for="dep of item.departments" :key="dep.id + Math.random()">
            <div v-if="dep.name === key.department.name">
              <v-btn v-on:click="del(item)"
                     class="ml-1 my-1"
                     color="green"
                     tile small icon>
                <v-icon>mdi-minus-circle</v-icon></v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "ComponentItem",
  props: ['loadComponent', 'searchString'],
  computed: {
    // группировка компонентов
    groupBy() {
      return Object.values(this.filteredData).reduce((acc, obj) => {
        let key = obj['group']
        if (!acc[key.name]) {
          acc[key.name] = []
        }
        acc[key.name].push(obj)
        return acc
      }, {})
    },

    // поиск по данным
    filteredData() {
      let result = this.loadComponent
      if (this.searchString && this.searchString.length >= 3) {
        result = result.filter(item => {
          if (item.name.toLowerCase().indexOf(this.searchString.toLowerCase()) !== -1) {
            return item
          }
        })
      }
      return result;
    },

  },

  methods: {
    add(item) {
      this.$emit('addToSet', item)
    },
    del(item) {
      this.$emit('delFromSet', item)
    },

    editComponent(item) {
      this.$emit('editItem', item)
    },

    deleteComponent(item) {
      this.$emit('deleteItem', item)
    },
  },

  data: () => ({
    key: JSON.parse(localStorage.getItem('user')),
  }),

}
</script>

<style scoped>
.col-action-section {
  display: flex;
  z-index: 0;
  flex-basis: 5%;
}
</style>