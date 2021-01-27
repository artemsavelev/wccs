<template>
  <div>
    <HeaderTable v-bind:typeSection="typeSection"/>

    <DataContent v-for="item in works"
                 :key="item.id"
                 v-on:edit="editWork"
                 v-on:remove="removeWork"
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
    // получаем данные с сервера (список работ)
    req.requestData(api.API_WORK_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },
  methods: {
    // добавление записи в конец массива
    addWork(item) {
      this.works.push(item)
      this.$emit('transmit', this.works)
    },
    // редактироване записи по id
    editWork() {

    },
    // удаление записи из массива по id
    removeWork(id) {
      this.works = this.works.filter(o => o.id !== id)
      this.$emit('transmit', this.works)
    }
  }
}
</script>

<style scoped>

</style>