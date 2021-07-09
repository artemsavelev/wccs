<template>
  <v-dialog v-model="dialog" width="80%" scrollable persistent>

    <template v-slot:activator="{ on }" class="mt-1">
      <v-btn v-on="on" @click="open" class="mb-10 mt-1" color="primary" height="35" tile small outlined>{{ env.keyAdd }}</v-btn>
    </template>
    <v-card style="height: 90vh" class="rounded-0">

      <v-card-title class="form">
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <div v-if="typeSection !== 0">
        <v-container>
          <!--  search in component -->
          <v-col cols="5" class="pa-0 mt-0 mr-0 mb-0 ml-3">
            <v-text-field dense
                          class="rounded-0"
                          :label="env.search"
                          outlined
                          clearable
                          v-model="search"/>
          </v-col>
          <!-- // search in component -->
          <v-col class="pt-0 pr-3 pb-0 pl-3">

            <HeaderTable v-bind:type="type"
                         v-bind:sortName="sortByName"
                         v-bind:sortId="sortById"/>
          </v-col>
        </v-container>
      </div>

      <v-card-text class="ma-0 pa-0 scroll" style="height: 50vh">
        <v-container>
          <div v-if="typeSection === 0">
            <!--   load component add work description   -->
            <WorkDescription v-bind:ex="ex"
                             v-on:add="addWorkDescription"/>
            <!--   // load component add work description -->
          </div>

          <div v-else-if="(typeSection === 1 || typeSection === 2 || typeSection === 3) && filteredData.length">
            <v-row class="pl-3">
              <v-col>
                <!--   load module add device, material, work    -->
                <div class="mb-10 ml-1 mr-3" v-for="(group, i) of Object.values(groupBy)"
                     :key="group.id"> <span class="font-weight-light font-xl"> {{ Object.keys(groupBy)[i] }} </span>

                  <SectionItem v-for="item of group"
                               :key="item.id"
                               v-on:transmitItemInModalForm="addItem"
                               v-bind:extId="extId"
                               v-bind:item="item"/>

                </div>
                <!--  // load module add device, material, work  -->
              </v-col>

            </v-row>
          </div>
          <div v-else class="no-content">
            {{ env.noRecords }}
          </div>
        </v-container>
      </v-card-text>

    </v-card>
  </v-dialog>
</template>

<script>
import env from '../../env.config.json'
import {mapActions} from 'vuex'

const SectionItem = () => import('@/components/SectionItem')
const HeaderTable = () => import('@/components/HeaderTable')
const WorkDescription = () => import('@/components/WorkDescription')

export default {
  name: 'ModalWin',
  components: { WorkDescription, HeaderTable, SectionItem },
  props: ['typeSection', 'data', 'ex', 'extId'],
  computed: {

    //
     groupBy() {

      return Object.values(this.filteredData).reduce((acc, obj) => {

        let key = obj['group']

        if (!acc[key.name]) {
          acc[key.name] = []
        }

        acc[key.name].push(obj)

        return acc
      }, {})
    },


    // поиск по данным
    filteredData() {

      let result = this.data
      // let result = this.groupBy(this.data, 'group')
      if (this.search && this.search.length >= 3) {
        result = result.filter(item => {
          if (item.name.toLowerCase().indexOf(this.search.toLowerCase()) !== -1) {
            return item
          }
        })
      }
      return result;
    },

    // наименование модальных окон
    formTitle() {
      if (this.typeSection === 0) {
        return env.typeWork;
      } else if (this.typeSection === 1) {
        return env.sectionDevice;
      } else if (this.typeSection === 2) {
        return env.sectionMaterial;
      } else {
        return env.sectionWork;
      }
    },

  },
  data() {
    return {
      env,
      dialog: false,
      search: '',
      type: 123,
    }
  },

  updated() {
    // console.log(Object.values(this.groupBy))
  },

  methods: {
    ...mapActions(['showSnack']),

    addItem(item) {

      //console.log(item)

      this.$emit('transmitParentForm', item)

      const data = {
        message: 'Запись с именем - "' + item.name + '" добавлена в конструктор.',
        color: 'success',
        icon: 'mdi-check-circle'
      }

      this.showSnack(data)

    },

    addWorkDescription(item) {
      this.$emit('addParentFormDescription', item)
      this.dialog = false
    },

    open() {

      //console.log('open modal form')
    },

    close() {
      this.search = '';
      this.dialog = false
    },
    // сортировка по id
    sortById() {
      (this.filteredData || []).sort((a, b) => a.id - b.id)
    },
    // сортировка по name
    sortByName() {
      (this.filteredData || []).sort((a, b) => a.name.localeCompare(b.name))
    }
  },

}
</script>

<style lang="scss">
.group {

}

</style>