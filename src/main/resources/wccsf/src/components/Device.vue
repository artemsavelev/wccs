<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in devices"
                 :key="item.id"
                 v-on:edit="editDevice"
                 v-on:remove="removeDevice"
                 v-bind:item="item"/>

    <ModalWin v-bind:typeSection="typeSection"
              v-on:add="addDevice"
              v-bind:data="data"/>
  </div>
</template>

<script>
import ModalWin from "./ModalWin";
import req from "../store/headers";
import api from "../api/backendApi";
import HeaderTable from "@/components/HeaderTable";
import DataContent from "@/components/DataContent";

export default {
  name: "ActiveDevice",
  components: { DataContent, HeaderTable, ModalWin },
  data() {
    return {
      typeSection: 1,
      data: [],
      devices: []

    }
  },
  mounted() {
    // получаем данные с сервера (список оборудования)
    req.requestData(api.API_DEVICE_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
  methods: {
    // добавление записи в конец массива
    addDevice(item) {
      this.devices.push(item)
      this.$emit('transmit', this.devices)
    },
    // редактироване записи по id
    editDevice() {

    },
    // удаление записи из массива по id
    removeDevice(id) {
      this.devices = this.devices.filter(o => o.id !== id)
      this.$emit('transmit', this.devices)
    }

  }
}
</script>

<style scoped>

</style>