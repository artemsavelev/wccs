<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"
                 v-bind:sortName="sortByName"
                 v-bind:sortId="sortById"/>

    <DataContent v-for="item in devices"
                 :key="item.id"
                 v-on:remove="removeDevice"
                 v-bind:extId="extId"
                 v-bind:item="item"/>

    <ModalForm v-on:transmitParentForm="addDevice"
               v-bind:typeSection="typeSection"
               v-bind:extId="extId"
               v-bind:data="allDevices"/>


  </div>
</template>

<script>
const ModalForm = () => import('./ModalForm')
const HeaderTable = () => import('@/components/HeaderTable')
const DataContent = () => import('@/components/DataContent')


import {mapActions, mapGetters} from "vuex";


export default {
  name: "ActiveDevice",
  components: {DataContent, HeaderTable, ModalForm },
    props: ['extId'],
  computed: {
    ...mapGetters(['allDevices']),
  },
  data() {
    return {
      ...mapActions(['fetchDevices']),
      typeSection: 1,
      devices: [],
      itemName: ''
    }
  },
  updated() {
    //
    // console.log('device', JSON.parse(localStorage.getItem(this.extId + '_' + this.itemName)))
  },
  mounted() {
    // получаем данные с сервера (список оборудования)
    this.fetchDevices();
  },
  methods: {


    ...mapActions(['showSnack']),

    // добавление записи в конец массива
    addDevice(item) {
      this.itemName = item.name
      this.devices.push(item)
      this.$emit('transmit', this.devices)

    },

    // удаление записи из массива по id
    removeDevice(id, item) {
      this.devices = this.devices.filter(o => o.id !== id)
      this.$emit('transmit', this.devices)

      const data = {
        message: 'Запись с именем - "' + item + '" удалена из конструктора.',
        color: 'warning'
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