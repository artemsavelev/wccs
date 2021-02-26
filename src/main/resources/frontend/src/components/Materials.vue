<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"
                 v-bind:sortName="sortByName"
                 v-bind:sortId="sortById"/>

    <DataContent v-for="item in materials"
                 :key="item.id"
                 v-on:edit="editMaterial"
                 v-on:remove="removeMaterial"
                 v-bind:item="item"/>

    <ModalForm v-on:transmitParentForm="addMaterial"
               v-bind:typeSection="typeSection"
               v-bind:extId="extId"
               v-bind:data="allMaterials"/>
  </div>
</template>

<script>


const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')
import {mapActions, mapGetters} from "vuex";

export default {
  name: "Materials",
  props: ['extId'],
  components: {DataContent, HeaderTable, ModalForm },
  computed: mapGetters(['allMaterials']),
  data() {
    return {
      ...mapActions(['fetchMaterials']),
      typeSection: 2,
      materials: []

    }
  },
  mounted() {
    // получаем данные с сервера (список материалов)
    this.fetchMaterials();
  },
  methods: {
    // добавление записи в конец массива
    addMaterial(item) {
      this.materials.push(item)
      this.$emit('transmit', this.materials)
    },
    // редактироване записи по id
    editMaterial() {

    },
    // удаление записи из массива по id
    removeMaterial(id) {
      this.materials = this.materials.filter(o => o.id !== id)
      this.$emit('transmit', this.materials)
    },
    // сортировка по id
    sortById() {
      (this.materials || []).sort((a, b) => a.id - b.id)
    },
    // сортировка по name
    sortByName() {
      (this.materials || []).sort((a, b) => a.name.localeCompare(b.name))
    }
  },

}
</script>

<style scoped>

</style>