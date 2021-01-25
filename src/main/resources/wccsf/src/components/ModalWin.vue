<template>
  <v-dialog v-model="dialog" width="80%" scrollable persistent>

    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn v-on="on" small class="mb-10 mt-1" color="primary" tile>добавить</v-btn>
    </template>
    <v-card>

      <v-card-title class="headline grey lighten-2">
        <span class="header-modal">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>{{ mdiClose }}</v-icon>
        </v-btn>
      </v-card-title>


      <div v-if="typeSection !== 0">
        <v-container>

          <v-col cols="12" class="pa-0 ma-0 align-center">
            <v-text-field
                dense
                label="Поиск"
                outlined
                v-model="search"/>
          </v-col>


          <v-col cols="12">
            <HeaderTable v-bind:type="type"/>
          </v-col>

        </v-container>
      </div>

      <v-card-text>
        <v-container>

          <div v-if="typeSection === 0">
            <v-row>
              <v-col cols="12">
                <v-textarea
                    outlined
                    :auto-grow="true"
                    rows="4"
                    name="input-1"
                    label="Виды работ"
                    value="1.2.3."></v-textarea>
                <v-btn class="mb-5" v-on:click="add" small color="primary" tile>add</v-btn>

                <v-textarea
                    outlined
                    :auto-grow="true"
                    rows="3"
                    name="input-2"
                    label="Примечание"
                    v-model="this.text"></v-textarea>
              </v-col>
            </v-row>

          </div>



          <div v-else>
            <v-row>
              <v-col cols="12">

                <Item v-for="item of data"
                      :key="item.id"
                      v-on:add="addItem"
                      v-bind:item="item"/>

              </v-col>
            </v-row>
          </div>


        </v-container>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>
import { mdiClose } from '@mdi/js';
import Item from "@/components/Item";
import HeaderTable from "@/components/HeaderTable";



export default {
  name: "ModalWin",
  components: { HeaderTable, Item },
  props: ['typeSection', 'data'],
  data() {
    return {
      mdiClose,
      dialog: false,
      formTitle: '',
      search: '',
      type: 123,
      text: 'Смета ПРЕДВАРИТЕЛЬНАЯ Конечная цена и материал по согласованию строн могут измениться в большую или меньшую сторону. ' +
          'Цены в данной смете действительны в течении 5 (пяти) рабочих дней',
      items: []

    }
  },
  methods: {
    addItem(item) {
      this.items.push(item)
      // console.log(this.items)

      if (this.typeSection === 1) {
        localStorage.setItem('device', JSON.stringify(this.items))
      } else if (this.typeSection === 2) {
        localStorage.setItem('material', JSON.stringify(this.items))
      } else if (this.typeSection === 3) {
        localStorage.setItem('work', JSON.stringify(this.items))
      } else {
        localStorage.setItem('types', JSON.stringify(this.items))
      }

      console.log(localStorage.name)

    },

    add() {
      console.log('test')

    },
    close() {
      this.dialog = false
    }
  },

}
</script>

<style scoped>

</style>