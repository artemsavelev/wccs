<template>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent>
    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn icon v-on="on" class="">
        <v-icon>{{ mdiCalculator }}</v-icon>
      </v-btn>
    </template>
    <v-card>
      <v-card-title class="headline grey lighten-2">
        <span class="header-modal ml-5">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>{{ mdiClose }}</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>

        <v-container>
          <div class="main-header">
            <div class="font-s">
              по заявке: {{ extId }}
            </div>
            <div class="font-s">
              по адресу: {{ address }}
            </div>
            <div class="font-s">
              от компании: {{ customer }}
            </div>
            <div class="font-s">
              составил: {{ profile.lastName }} {{ new Date().toISOString().substr(0, 10) + ' ' + new Date().toTimeString().substr(0, 8)}}
            </div>
          </div>

          <TypeOfWork/>

          <Device/>

          <Materials/>

          <Work/>

        </v-container>

      </v-card-text>



      <v-container class="pt-0 mt-0 ml-5">
        <div class="">
          <v-card-actions >
            <v-btn medium v-on:click="create" color="primary" tile>Создать смету</v-btn>
          </v-card-actions>
        </div>
      </v-container>

    </v-card>


  </v-dialog>
</template>

<script>
import Work from "../components/Work";
import Materials from "../components/Materials";
import Device from "../components/Device";
import TypeOfWork from "../components/TypeOfWork";
import { mdiCalculator, mdiClose } from '@mdi/js';
import {mapGetters} from "vuex";

export default {
  name: "Estimate",
  props: ['extId', 'address', 'customer'],
  components: {TypeOfWork, Device, Materials, Work},
  computed: mapGetters(['profile']),
  data() {
    return {
      mdiCalculator, mdiClose,
      dialog: false,
      formTitle: 'Смета',

    }
  },
  methods: {
    close() {
      this.dialog = false
    },

    create() {

    }
  }
}
</script>

<style lang="scss">
.font-s {
  font-size: small;
}
.font-m {
  font-size: medium;
}
.font-xl {
  font-size: large;
}
.main-header {


}
.main-header-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  &-wrapper {


    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
}

.main {
  border-bottom: 1px solid #ccc;
}
.main-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  &-wrapper {
    //border: 1px solid #ccc;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    &-key {
      justify-content: flex-end;
    }
  }
}
.column-70 {
  width: 70px;
  max-width: 70px;
}
.column-100 {
  width: 100px;
  max-width: 100px;
}
.column-80 {
  width: 80px;
  max-width: 80px;
}
.column-150 {
  width: 150px;
  max-width: 150px;
}
.column-200 {
  width: 200px;
  max-width: 200px;
}
.column-300 {
  width: 300px;
  max-width: 300px;
}
.column-400 {
  width: 400px;
  max-width: 400px;
}
.column-500 {
  width: 500px;
  max-width: 500px;
}
.column-600 {
  width: 600px;
  max-width: 600px;
}
.column-700 {
  width: 700px;
  max-width: 700px;
}
.column-800 {
  width: 800px;
  max-width: 800px;
}
.column-900 {
  width: 900px;
  max-width: 900px;
}

.border {
  border: 1px solid gray;
  width: 100%;
}


</style>