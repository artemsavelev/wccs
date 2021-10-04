<template>
  <div>
    <HeaderTable :typeSection="typeSection"
                 :sortName="sortByName"
                 :sortId="sortById"/>

    <DataContent v-for="item in works"
                 :key="item.id"
                 @remove="removeWork"
                 :item="item"/>

    <ModalForm @transmitParentForm="addWork"
               :typeSection="typeSection"
               :extId="extId"
               :data="allWorks"/>
  </div>
</template>

<script>

const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')
import { mapActions, mapGetters } from "vuex";

export default {
  name: "Work",
  props: ['extId', 'worksFromDb'],
  components: { DataContent, HeaderTable, ModalForm },
  computed: mapGetters(['allWorks']),
  data() {
    return {
      ...mapActions(['fetchWorks']),
      typeSection: 3,
      works: []
    }
  },
  watch: {
    worksFromDb(newVal) {
      this.works = newVal
      this.$emit('transmit', this.works)
    }
  },
  mounted() {
    // получаем данные с сервера (список работ)
    this.fetchWorks()
  },
  updated() {
  },

  methods: {

    ...mapActions(['showSnack']),

    // добавление записи в конец массива
    addWork(item) {
      this.works.push(item)
      this.$emit('transmit', this.works)
    },

    // удаление записи из массива по id
    removeWork(id, item) {
      this.works = this.works.filter(o => o.id !== id)
      this.$emit('transmit', this.works)

      const data = {
        message: 'Запись с наименованием - "' + item + '" удалена из конструктора.',
        color: 'warning',
        icon: 'mdi-alert'
      }

      this.showSnack(data)
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