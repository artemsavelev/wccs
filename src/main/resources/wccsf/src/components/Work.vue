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
  name: "Work",
  components: {DataContent, HeaderTable, ModalWin },
  data() {
    return {
      typeSection: 3,
      data: []

    }
  },
  mounted() {

    req.requestData(api.API_WORK_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
}
</script>

<style scoped>

</style>