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
  name: "Materials",
  components: {DataContent, HeaderTable, ModalWin },
  methods: {


  },
  data() {
    return {
      typeSection: 2,
      data: []

    }
  },
  mounted() {

    req.requestData(api.API_MATERIAL_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },

}
</script>

<style scoped>

</style>