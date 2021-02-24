<template>
  <div>
    <div class="ml-10 mr-10">

      <div class="font-weight-bold">
        {{ env.sectionDevice }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchDevice"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allDevices"
          :items-per-page="10"
          :search="searchDevice"
          class="elevation-1 mb-10"
      ></v-data-table>

      <div class="font-weight-bold">
        {{ env.sectionMaterial }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchMaterial"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allMaterials"
          :items-per-page="10"
          :search="searchMaterial"
          class="elevation-1 mb-10"
      ></v-data-table>

      <div class="font-weight-bold">
        {{ env.sectionWork }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchWork"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allWorks"
          :items-per-page="10"
          :search="searchWork"
          class="elevation-1"
      ></v-data-table>

    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import env from "../../env.config.json"

export default {
  name: "Admin",
  computed: mapGetters(['allDevices', 'allMaterials', 'allWorks']),


  data () {
    return {
      ...mapActions(['fetchDevices', 'fetchMaterials', 'fetchWorks']),
      env,
      searchDevice: '',
      searchWork: '',
      searchMaterial: '',
      headers: [
        { text: 'id', value: 'id' },
        { text: 'name', value: 'name' },
      ],

    }
  },

  mounted() {
    // получаем данные с сервера (список оборудования)
    this.fetchDevices();
    this.fetchMaterials();
    this.fetchWorks();
  },


  methods: {

  },
}
</script>

<style lang="scss">

</style>