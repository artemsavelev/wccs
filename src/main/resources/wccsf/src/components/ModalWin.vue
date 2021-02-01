<template>
  <v-dialog v-model="dialog" width="80%" scrollable persistent>

    <template v-slot:activator="{ on }" class="mt-1">
      <v-btn v-on="on" small class="mb-10 mt-1" color="primary" tile>{{ env.keyAdd }}</v-btn>
    </template>
    <v-card>

      <v-card-title class="grey lighten-2">
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>{{ mdiClose }}</v-icon>
        </v-btn>
      </v-card-title>


      <div v-if="typeSection !== 0">
        <v-container>
          <v-col cols="5" class="pa-0 mt-0 mr-0 mb-0 ml-3">
            <v-text-field dense
                          label="Поиск"
                          outlined
                          clearable
                          v-model="search"/>
          </v-col>

          <v-col class="pt-0 pr-3 pb-0 pl-3">
            <HeaderTable v-bind:type="type"/>
          </v-col>
        </v-container>
      </div>

      <v-card-text class="ma-0 pa-0" style="height: 50vh">
        <v-container>

          <div v-if="typeSection === 0">
            <v-row>
              <v-col cols="12">
                <v-textarea outlined
                            :auto-grow="true"
                            rows="4"
                            name="input-1"
                            v-model="typeOfWork"
                            label="Виды работ"></v-textarea>

                <v-textarea outlined
                            :auto-grow="true"
                            rows="3"
                            name="input-2"
                            label="Примечание"
                            v-model="text"></v-textarea>
                <v-btn class="" v-on:click="add" small color="primary" tile>{{ env.keyAdd }}</v-btn>
              </v-col>
            </v-row>
          </div>

          <div v-else-if="(typeSection === 1 || typeSection === 2 || typeSection === 3) && filteredData.length">
            <v-row class="pl-3">
              <v-col>
                <Item v-for="item of filteredData"
                      :key="item.id"
                      v-on:add="addItem"
                      v-bind:item="item"/>
              </v-col>
            </v-row>
          </div>
          <div v-else class="pl-4">
            {{ env.noRecords }}
          </div>
        </v-container>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>
import env from "../../env.config.json";
import { mdiClose } from '@mdi/js';
import Item from "@/components/Item";
import HeaderTable from "@/components/HeaderTable";

export default {
  name: "ModalWin",
  components: { HeaderTable, Item },
  computed: {
    // поиск по данным
    filteredData() {
      let result = this.data
      if (this.search && this.search.length >= 3) {
        result = result.filter(item => {
          if (item.name.toLowerCase().indexOf(this.search.toLowerCase()) !== -1) {
            // console.log(this.search)
            // console.log(item.name)
            return item
          }
        })
      }
      return result;
    }
  },
  props: ['typeSection', 'data'],
  data() {
    return {
      env,
      mdiClose,
      dialog: false,
      formTitle: '',
      search: '',
      type: 123,
      typeOfWork: '',
      text: env.preliminary,
      items: []
    }
  },
  methods: {
    addItem(item) {
      this.$emit('add', item)
    },

    add() {
      let type = {
        typeOfWork: this.typeOfWork,
        text: this.text
      }
      this.$emit('add', type)

    },
    close() {
      this.search = '';
      this.dialog = false
    }
  },

}
</script>

<style lang="scss">


</style>