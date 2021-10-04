<template>
  <div>
    <HeaderTable :typeSection="typeSection"
                 :sortName="sortByName"
                 :sortId="sortById"/>

    <DataContent v-for="item in materials"
                 :key="item.id"
                 @remove="removeMaterial"
                 :item="item"/>

    <ModalForm @transmitParentForm="addMaterial"
               :typeSection="typeSection"
               :extId="extId"
               :data="allMaterials"/>
  </div>
</template>

<script>


const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')
import {mapActions, mapGetters} from "vuex";

export default {
  name: "Materials",
  props: ['extId', 'materialsFromDb'],
  components: {DataContent, HeaderTable, ModalForm },
  computed: mapGetters(['allMaterials']),
  data() {
    return {
      ...mapActions(['fetchMaterials']),
      typeSection: 2,
      materials: []
    }
  },
  watch: {
    materialsFromDb(newVal) {
      this.materials = newVal
      this.$emit('transmit', this.materials)
    }
  },
  mounted() {
    // получаем данные с сервера (список материалов)
    this.fetchMaterials()
    // загружаем если есть данные по предыдущей смете
    if (this.materialsFromDb) {
      this.materials.push(...this.materialsFromDb)
      this.$emit('transmit', this.materials)
    }
  },
  methods: {

    ...mapActions(['showSnack']),


    // добавление записи в конец массива
    addMaterial(item) {
      this.materials.push(item)
      this.$emit('transmit', this.materials)
    },

    // удаление записи из массива по id
    removeMaterial(id, item) {
      this.materials = this.materials.filter(o => o.id !== id)
      this.$emit('transmit', this.materials)

      const data = {
        message: 'Запись с наименованием - "' + item + '" удалена из конструктора.',
        color: 'warning',
        icon: 'mdi-alert'
      }

      this.showSnack(data)
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