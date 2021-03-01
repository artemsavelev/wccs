<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"
                 v-bind:sortName="sortByName"
                 v-bind:sortId="sortById"/>

    <DataContent v-for="item in works"
                 :key="item.id"
                 v-on:remove="removeWork"
                 v-bind:item="item"/>

    <ModalForm v-on:transmitParentForm="addWork"
               v-bind:typeSection="typeSection"
               v-bind:extId="extId"
               v-bind:data="allWorks"/>
  </div>
</template>

<script>

const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')
import {mapActions, mapGetters} from "vuex";

export default {
  name: "Work",
  props: ['extId'],
  components: { DataContent, HeaderTable, ModalForm },
  computed: mapGetters(['allWorks']),
  data() {
    return {
      ...mapActions(['fetchWorks']),
      typeSection: 3,
      works: []

    }
  },
  mounted() {
    // получаем данные с сервера (список работ)
    this.fetchWorks()
  },
  methods: {
    // добавление записи в конец массива
    addWork(item) {
      this.works.push(item)
      this.$emit('transmit', this.works)
    },

    // удаление записи из массива по id
    removeWork(id) {
      this.works = this.works.filter(o => o.id !== id)
      this.$emit('transmit', this.works)
    },

    // сортировка по id
    sortById() {
      (this.works || []).sort((a, b) => a.id - b.id)
    },

    // сортировка по name
    sortByName() {
      (this.works || []).sort((a, b) => a.name.localeCompare(b.name))
    }
  }
}
</script>

<style scoped>

</style>