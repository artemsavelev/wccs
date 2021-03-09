<template>
  <div>

    <div class="registration ml-7 mb-15 mt-3">

      <div class="font-weight-bold ml-3">
        {{ env.listComponent }}
      </div>


      <v-form
          ref="form"
          v-model="valid"
          lazy-validation>

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

          <div v-if="this.select === this.env.sectionDevice">
            <div v-for="item in allDevices"
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


          <div v-if="this.select === this.env.sectionMaterial">

            <div v-for="item in allMaterials"
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


          <div v-if="this.select === this.env.sectionWork">

            <div v-for="item in allWorks"
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

        </v-row>
      </v-form>

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


    }
  }
}
</script>

<style scoped>

</style>