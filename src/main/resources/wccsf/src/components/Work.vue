<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in works"
                 :key="item.id"
                 v-bind:item="item"/>

    <ModalWin v-bind:typeSection="typeSection"
              v-on:add="addWork"
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
      data: [],
      works: []

    }
  },
  mounted() {

    req.requestData(api.API_WORK_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
  methods: {
    addWork(item) {
      this.works.push(item)
      console.log(this.works)

    }
  }
}
</script>

<style scoped>

</style>