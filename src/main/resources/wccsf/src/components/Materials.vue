<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in materials"
                 :key="item.id"
                 v-bind:item="item"/>

    <ModalWin v-bind:typeSection="typeSection"
              v-on:add="addMaterial"
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
    addMaterial(item) {
      this.materials.push(item)
      console.log(this.materials)
    }


  },
  data() {
    return {
      typeSection: 2,
      data: [],
      materials: []

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