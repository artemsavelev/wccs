<template>
  <div>
    <HeaderTable :typeSection="typeSection"/>

    <DataContent v-for="item in data"
                 :key="item.id"
                 @remove="removeObject"
                 :item="item"/>

    <ModalForm @transmitParentForm="addObject"
               @closeModalForm="closeModalForm"
               :openModalForm="openModalForm"
               :typeSection="typeSection"
               :data="data"/>
  </div>
</template>

<script>
import env from '../../env.config.json'
import {mapActions} from 'vuex'
const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')


export default {
  name: "Component",
  components: { DataContent, HeaderTable, ModalForm },
  props: ['dataFromDb', 'sectionData'],
  data() {
    return {
      env,
      typeSection: -1,
      data: [],
      openModalForm: false
    }
  },
  watch: {
    dataFromDb(newVal) {
      console.log(newVal)
      this.data = newVal
      this.$emit('transmit', this.data)
    },
    sectionData(value) {
      console.log(value)
      this.openModalForm = true
    }
  },

  methods: {
    ...mapActions(['showSnack']),

    // open() {
    //   this.openModalForm = true
    // },
    closeModalForm(val) {
      this.openModalForm = val
    },
    addObject(item) {
      this.data.push(item)
      this.$emit('transmit', this.data)
    },
    removeObject(id, item) {
      this.data = this.data.filter(o => o.id !== id)
      this.$emit('transmit', this.data)

      const data = {
        message: 'Запись с наименованием - "' + item + '" удалена из конструктора.',
        color: 'warning',
        icon: 'mdi-alert'
      }
      this.showSnack(data)
    }
  }
}
</script>

<style scoped>

</style>