<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent/>

    <ModalWin v-bind:typeSection="typeSection"
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
  components: {DataContent, HeaderTable, ModalWin },
  data() {
    return {
      typeSection: 1,
      data: []

    }
  },
  mounted() {
    req.requestData(api.API_DEVICE_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
  methods: {

  }
}
</script>

<style scoped>

</style>