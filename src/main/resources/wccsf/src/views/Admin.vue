<template>
<div>

  <Users/>

  <ModalWin v-bind:typeSection="typeSection"

            v-bind:data="data"/>

</div>
</template>

<script>




import Users from "@/components/Users";
import ModalWin from "@/components/ModalForm";
import req from "@/store/headers";
import api from "@/api/backendApi";

export default {
  name: "Admin",

  components: {ModalWin, Users},

  data () {
    return {
      typeSection: 1,
      data: [],
    }
  },

  mounted() {
    // получаем данные с сервера (список оборудования)
    req.requestData(api.API_DEVICE_URL, 'GET')
        .then(response => response.json())
        .then(json => this.data = json)
  },


  methods: {

  },
}
</script>

<style lang="scss">

</style>