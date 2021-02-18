<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in materials"
                 :key="item.id"
                 v-on:edit="editMaterial"
                 v-on:remove="removeMaterial"
                 v-bind:item="item"/>

    <ModalForm v-bind:typeSection="typeSection"
              v-on:add="addMaterial"
              v-bind:data="data"/>
  </div>
</template>

<script>
import ModalForm from "./ModalForm";
import req from "../store/headers";
import api from "../api/backendApi";
import HeaderTable from "@/components/HeaderTable";
import DataContent from "@/components/DataContent";

export default {
  name: "Materials",
  components: {DataContent, HeaderTable, ModalForm },
  data() {
    return {
      typeSection: 2,
      data: [],
      materials: []

    }
  },
  mounted() {
    // получаем данные с сервера (список материалов)
    req.requestData(api.API_MATERIAL_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
  methods: {
    // добавление записи в конец массива
    addMaterial(item) {
      this.materials.push(item)
      this.$emit('transmit', this.materials)
    },
    // редактироване записи по id
    editMaterial() {

    },
    // удаление записи из массива по id
    removeMaterial(id) {
      this.materials = this.materials.filter(o => o.id !== id)
      this.$emit('transmit', this.materials)
    }
  },


}
</script>

<style scoped>

</style>