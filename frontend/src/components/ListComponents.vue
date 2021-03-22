<template>
  <div class="main-container">

    <div class="mb-5">

      <div class="font-weight-bold ml-3">
        {{ env.listComponent }}
      </div>



        <v-col cols="12" sm="6" md="5">
          <v-autocomplete
              :items="items"
              item-text="key"
              v-model="select"
              :label="env.sectionItem"
              required
          ></v-autocomplete>
        </v-col>

        <v-row class="ml-4 pa-0">

          <div v-if="this.select === this.env.sectionDevice" class="width-container">
            <div v-for="item in visiblePages"
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
                {{ item.price }}
              </div>

              <div class="col-action-section">
                <v-btn icon v-on:click="add">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
              </div>
            </div>
          </div>


          <div v-if="this.select === this.env.sectionMaterial" class="width-container">

            <div v-for="item in visiblePages"
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
                {{ item.price }}
              </div>

              <div class="col-action-section">
                <v-btn icon v-on:click="add">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
              </div>
            </div>
          </div>


          <div v-if="this.select === this.env.sectionWork" class="width-container">

            <div v-for="item in visiblePages"
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
                {{ item.price }}
              </div>

              <div class="col-action-section">
                <v-btn icon v-on:click="add(item)">
                  <v-icon color="iconLight">mdi-plus</v-icon>
                </v-btn>
              </div>
            </div>
          </div>

        </v-row>


    </div>

    <div class="pagination-container">
      <v-pagination
          v-model="page"
          :length="Math.ceil(length / perPage)"
          :total-visible="7"
      ></v-pagination>
    </div>

  </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from "vuex";
import env from "../../env.config.json";

export default {
  name: "ListComponents",
  computed:  {
    ...mapGetters(['allDevices', 'allMaterials', 'allWorks', 'profile']),
    ...mapMutations(['addDeviceMutation', 'addMaterialMutation', 'addWorkMutation']),

    visiblePages() {

      if (this.select === this.env.sectionDevice) {
        return this.allDevices.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      } else if (this.select === this.env.sectionMaterial) {
        return this.allMaterials.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      } else if (this.select === this.env.sectionWork) {
        return this.allWorks.slice((this.page - 1) * this.perPage, this.page * this.perPage)
      }
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

      this.fetchDevices()

      this.fetchMaterials()

      this.fetchWorks()

  },

  data() {
    return {
      ...mapActions(['fetchDevices', 'fetchMaterials', 'fetchWorks']),

      env,
      page: 1,
      perPage: 15,
      valid: true,
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