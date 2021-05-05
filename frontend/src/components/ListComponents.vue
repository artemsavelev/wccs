<template>
  <div class="main-container">

    <div class="mb-5">

      <div class="font-weight-bold ml-3">
        {{ env.listComponent }}
      </div>



      <v-row class="ml-1">
      <v-col cols="12" sm="6" md="5">
        <v-autocomplete
            :items="items"
            item-text="key"
            v-model="select"
            :label="env.sectionItem"
            required
            outlined
            dense
            class="rounded-0 mt-4"
        ></v-autocomplete>
      </v-col>

      <v-col cols="12" sm="6" md="5">
        <v-text-field dense
                      :disabled="!select"
                      class="rounded-0 mt-4"
                      :label="env.search"
                      outlined
                      clearable
                      v-model="search"/>
      </v-col>
      </v-row>

        <v-row class="ml-4 pa-0" v-if="filteredData && filteredData.length">

          <div v-if="this.select === this.env.sectionDevice" class="width-container">
            <div v-for="item in filteredData"
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
                <v-btn icon v-on:click="add">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
              </div>
            </div>
          </div>



          <div v-if="this.select === this.env.sectionMaterial" class="width-container">

            <div v-for="item in filteredData"
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
                <v-btn icon v-on:click="add">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
              </div>
            </div>
          </div>


          <div v-if="this.select === this.env.sectionWork" class="width-container">

            <div v-for="item in filteredData"
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
                <v-btn icon v-on:click="add(item)">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
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

  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import env from '../../env.config.json'

export default {
  name: 'ListComponents',
  computed:  {
    ...mapGetters(['allDevices', 'allMaterials', 'allWorks', 'profile']),
    ...mapMutations(['addDeviceMutation', 'addMaterialMutation', 'addWorkMutation']),

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
      return null

      // if (this.select === this.env.sectionDevice) {
      //   return this.allDevices.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // } else if (this.select === this.env.sectionMaterial) {
      //   return this.allMaterials.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // } else if (this.select === this.env.sectionWork) {
      //   return this.allWorks.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      // }
      // return null
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

      this.fetchDevices()

      this.fetchMaterials()

      this.fetchWorks()

  },

  data() {
    return {
      ...mapActions(['fetchDevices', 'fetchMaterials', 'fetchWorks']),

      env,
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
    add() {
      // console.log(item.id)
    }
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

.pagination-container {
  margin-bottom: 2em;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  width: 100%;
  height: 100%
}


</style>