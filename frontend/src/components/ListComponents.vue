<template>
  <div class="main-container">

    <div class="mb-5">

      <div class="font-weight-bold ml-3">
        {{ env.listComponent }}
      </div>



      <div style="position: fixed; width: 100%; height: 5em; background-color: white; z-index: 1;">
        <v-row class="ml-1">
          <v-col cols="12" sm="6" md="2">
            <v-autocomplete
                :items="items"
                item-text="key"
                v-model="select"
                :label="env.sectionItem"
                :change="$emit('selectedItem', this.select)"
                required
                outlined
                dense
                class="rounded-0 mt-4"
            ></v-autocomplete>
          </v-col>

          <v-col cols="12" sm="6" md="2">
            <v-text-field dense
                          :disabled="!select"
                          class="rounded-0 mt-4"
                          :label="env.search"
                          outlined
                          clearable
                          v-model="search"/>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-btn class="mt-4 rounded-0"
                   height="40"
                   :disabled="!select"
                   @click="newItem()"
                   small
                   outlined>{{ env.title[0] }}</v-btn>
          </v-col>
        </v-row>
      </div>


      <v-row class="mt-16 ml-4 pa-0" v-if="filteredData && filteredData.length">

        <div v-if="this.select === this.env.sectionDevice" class="width-container">

          <div class="mb-10 ml-1 mr-3" v-for="(group, i) of Object.values(groupBy)" :key="group.id">
            <span class="font-weight-light font-xl"> {{ Object.keys(groupBy)[i] }} </span>

            <div v-for="item in group"
                 :key="item.id" class="section-row-container">
              <div class="col-idx">
                {{ item.id }}
              </div>
              <div class="col-name">
                {{ item.name }}
              </div>
              <div class="col-dimension">
                {{ item.dimension }}
              </div>
              <div class="col-price">
                {{ item.price.toLocaleString('ru-RU') }}
              </div>

              <div class="col-action-section">

                <v-btn v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="editComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn v-else icon class="ma-1 pa-0" @click="editComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn  v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="deleteComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>
                <v-btn  v-else icon class="ma-1 pa-0" @click="deleteComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>

                <div v-if="!item.departments.length">
                  <v-btn v-on:click="add(item)"
                         class="ml-1 my-1"
                         color="red"
                         tile small icon>
                    <v-icon>mdi-plus-circle</v-icon></v-btn>
                </div>

                <div v-for="dep of item.departments" :key="dep.id">
                  <div v-if="dep.name === key.department.name">
                    <v-btn v-on:click="del(item)"
                           class="ml-1 my-1"
                           color="green"
                           tile small icon>
                      <v-icon>mdi-minus-circle</v-icon></v-btn>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>



        <div v-if="this.select === this.env.sectionMaterial" class="width-container">

          <div class="mb-10 ml-1 mr-3" v-for="(group, i) of Object.values(groupBy)" :key="group.id">
            <span class="font-weight-light font-xl"> {{ Object.keys(groupBy)[i] }} </span>

            <div v-for="item in group"
                 :key="item.id" class="section-row-container">
              <div class="col-idx">
                {{ item.id }}
              </div>
              <div class="col-name">
                {{ item.name }}
              </div>
              <div class="col-dimension">
                {{ item.dimension }}
              </div>
              <div class="col-price">
                {{ item.price.toLocaleString('ru-RU') }}
              </div>
              <div class="col-comment">
                {{ item.note }}
              </div>

              <div class="col-action-section">

                <v-btn v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="editComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn v-else icon class="ma-1 pa-0" @click="editComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn  v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="deleteComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>
                <v-btn  v-else icon class="ma-1 pa-0" @click="deleteComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>

                <div v-if="item.departments.length === 0">
                  <v-btn v-on:click="add(item)"
                         class="ml-1 my-1"
                         color="red"
                         tile small icon>
                    <v-icon>mdi-plus-circle</v-icon></v-btn>
                </div>

                <div v-for="dep of item.departments" :key="dep.id">
                  <div v-if="dep.name === key.department.name">
                    <v-btn v-on:click="del(item)"
                           class="ml-1 my-1"
                           color="green"
                           tile small icon>
                      <v-icon>mdi-minus-circle</v-icon></v-btn>
                  </div>

                </div>

              </div>
            </div>
          </div>
        </div>


        <div v-if="this.select === this.env.sectionWork" class="width-container">

          <div class="mb-10 ml-1 mr-3" v-for="(group, i) of Object.values(groupBy)" :key="group.id">
            <span class="font-weight-light font-xl"> {{ Object.keys(groupBy)[i] }} </span>

            <div v-for="item in group"
                 :key="item.id" class="section-row-container">
              <div class="col-idx">
                {{ item.id }}
              </div>
              <div class="col-name">
                {{ item.name }}
              </div>
              <div class="col-dimension">
                {{ item.dimension }}
              </div>
              <div class="col-price">
                {{ item.price.toLocaleString('ru-RU') }}
              </div>
              <div class="col-comment">
                {{ item.note }}
              </div>

              <div class="col-action-section">

                <v-btn v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="editComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn v-else icon class="ma-1 pa-0" @click="editComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-pencil</v-icon>
                </v-btn>
                <v-btn  v-if="item.author.id === key.department.id" icon class="ma-1 pa-0" @click="deleteComponent(item)" tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>
                <v-btn  v-else icon class="ma-1 pa-0" @click="deleteComponent(item)" disabled tile small>
                  <v-icon color="iconLight">mdi-delete</v-icon>
                </v-btn>

                <div v-if="!item.departments.length">
                  <v-btn v-on:click="add(item)"
                         class="ml-1 my-1"
                         color="red"
                         tile small icon>
                    <v-icon>mdi-plus-circle</v-icon></v-btn>
                </div>

                <div v-for="dep of item.departments" :key="dep.id">
                  <div v-if="dep.name === key.department.name">
                    <v-btn v-on:click="del(item)"
                           class="ml-1 my-1"
                           color="green"
                           tile small icon>
                      <v-icon>mdi-minus-circle</v-icon></v-btn>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </v-row>

      <div v-else class="no-content">
        {{ env.noRecords }}
      </div>

    </div>

    <!--    <div class="pagination-container">-->
    <!--      <v-pagination-->
    <!--          v-model="page"-->
    <!--          :length="Math.ceil(length / perPage)"-->
    <!--          :total-visible="7"-->
    <!--      ></v-pagination>-->
    <!--    </div>-->

    <v-navigation-drawer width="400" v-model="drawerRight" app clipped right>
      <AddComponent :selectedItem="this.select"
                    :edit-component="obj"/>
    </v-navigation-drawer>




  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import env from '../../env.config.json'
const AddComponent = () => import('@/components/AddComponent')
//import AddComponent from "@/components/AddComponent"
// import {bus} from '@/utils/bus'

export default {
  name: 'ListComponents',
  components: {AddComponent},
  computed:  {
    ...mapGetters(['allDevices', 'allMaterials', 'allWorks', 'profile']),
    ...mapMutations(['addDeviceMutation', 'addMaterialMutation', 'addWorkMutation']),

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
      let result = this.visiblePages
      if (this.search && this.search.length >= 3) {
        result = result.filter(item => {
          if (item.name.toLowerCase().indexOf(this.search.toLowerCase()) !== -1) {
            return item
          }
        })
      }
      return result;
    },

    visiblePages() {

      if (this.select === this.env.sectionDevice) {
        return this.allDevices
      } else if (this.select === this.env.sectionMaterial) {
        return this.allMaterials
      } else if (this.select === this.env.sectionWork) {
        return this.allWorks
      }

      // if (this.select === this.env.sectionDevice) {
      //   return this.allDevices.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // } else if (this.select === this.env.sectionMaterial) {
      //   return this.allMaterials.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // } else if (this.select === this.env.sectionWork) {
      //   return this.allWorks.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // }

      return null

    },

    length() {

      if (this.select === this.env.sectionDevice) {
        return this.allDevices.length
      } else if (this.select === this.env.sectionMaterial) {
        return this.allMaterials.length
      } else if (this.select === this.env.sectionWork) {
        return this.allWorks.length
      }
      return [1]
    },
  },

  mounted() {
    this.fetchDevicesForAdmin()
    this.fetchMaterialsForAdmin()
    this.fetchWorksForAdmin()
  },

  updated() {

  },

  data() {
    return {
      ...mapActions(['fetchDevicesForAdmin', 'fetchMaterialsForAdmin', 'fetchWorksForAdmin']),
      obj: {},

      env,
      drawerRight: false,
      key: JSON.parse(localStorage.getItem('user')),
      val: false,
      page: 1,
      perPage: 18,
      valid: true,
      search: '',
      select: '',
      headers: [
        { text: 'id', value: 'id' },
        { text: 'name', value: 'name' },
      ],
      items: [
        { key: env.sectionDevice },
        { key: env.sectionMaterial },
        { key: env.sectionWork }
      ],
    }
  },

  methods: {
    ...mapActions(['addToSetDevice','addToSetMaterial', 'addToSetWork', 'delSetDevice', 'delSetMaterial', 'delSetWork']),

    add(item) {
      if (this.select === this.env.sectionDevice) {
        this.addToSetDevice(item)
      } else if (this.select === this.env.sectionMaterial) {
        this.addToSetMaterial(item)
      } else if (this.select === this.env.sectionWork) {
        this.addToSetWork(item)
      }
    },

    editComponent(item) {
      this.drawerRight = true
      this.obj = item
    },

    deleteComponent(item) {
      console.log(item.id)
    },

    newItem() {
      this.drawerRight = true
    },

    del(item) {
      if (this.select === this.env.sectionDevice) {
        this.delSetDevice(item)
      } else if (this.select === this.env.sectionMaterial) {
        this.delSetMaterial(item)
      } else if (this.select === this.env.sectionWork) {
        this.delSetWork(item)
      }


    }

    // changeItem() {
    //   // bus.$emit('selected-item', this.select)
    //
    // },
  }
}
</script>

<style scoped>
.main-container {
  font-size: small;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: flex-start;
}

.width-container {
  width: 98%;
}

.col-action-section {
  display: flex;
  /*z-index: 0;*/
  flex-basis: 5%;
}

/*.pagination-container {*/
/*  margin-bottom: 2em;*/
/*  display: flex;*/
/*  justify-content: center;*/
/*  align-items: flex-end;*/
/*  width: 100%;*/
/*  height: 100%*/
/*}*/

.no-content {
  margin-top: 15em;
}

/*.content {*/
/*  z-index: 0;*/
/*}*/


</style>