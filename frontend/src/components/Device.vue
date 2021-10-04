<template>
  <div>
    <HeaderTable :typeSection="typeSection"
                 :sortName="sortByName"
                 :sortId="sortById"/>

    <DataContent v-for="item in devices"
                 :key="item.id"
                 @remove="removeDevice"
                 :item="item"/>

    <ModalForm @transmitParentForm="addDevice"
               :typeSection="typeSection"
               :extId="extId"
               :data="allDevices"/>
  </div>
</template>

<script>
const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')
import {mapActions, mapGetters} from 'vuex'

export default {
  name: "ActiveDevice",
  components: { DataContent, HeaderTable, ModalForm },
  props: ['extId', 'deviseFromDb'],
  computed: {
    ...mapGetters(['allDevices']),
  },
  data() {
    return {
      ...mapActions(['fetchDevices']),
      typeSection: 1,
      devices: [],
    }
  },
  watch: {
    deviseFromDb(newVal) {
      this.devices = newVal
      this.$emit('transmit', this.devices)
    }
  },
  mounted() {
    // получаем данные с сервера (список оборудования)
    this.fetchDevices()
  },

  methods: {
    ...mapActions(['showSnack']),

    // добавление записи в конец массива
    addDevice(item) {
      this.devices.push(item)
      this.$emit('transmit', this.devices)
    },

    // удаление записи из массива по id
    removeDevice(id, item) {
      this.devices = this.devices.filter(o => o.id !== id)
      this.$emit('transmit', this.devices)

      const data = {
        message: 'Запись с наименованием - "' + item + '" удалена из конструктора.',
        color: 'warning',
        icon: 'mdi-alert'
      }
      this.showSnack(data)
    },

    // сортировка по id
    sortById() {
      (this.devices || []).sort((a, b) => a.id - b.id)
    },
    // сортировка по name
    sortByName() {
      (this.devices || []).sort((a, b) => a.name.localeCompare(b.name))
    }
  }
}
</script>

<style scoped>

</style>