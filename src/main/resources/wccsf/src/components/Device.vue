<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in devices"
                 :key="item.id"
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
    req.requestData(api.API_DEVICE_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)

    // this.devices = JSON.parse(localStorage.getItem('device'))
  },
  methods: {
    addDevice(item) {
      this.devices.push(item)
      console.log(this.devices)
    }

  }
}
</script>

<style scoped>

</style>