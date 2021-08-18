<template>
  <div class="main-container">
    <div class="search-panel">
      <v-row class="ml-2">
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
              class="rounded-0 mt-3 styled-input font-s"
          ></v-autocomplete>
        </v-col>

        <v-col cols="12" sm="6" md="2">
          <v-text-field dense
                        :disabled="!select"
                        class="rounded-0 mt-3 styled-input font-s"
                        :label="env.search"
                        outlined
                        clearable
                        v-model="search"/>
        </v-col>
      </v-row>
    </div>

    <div class="mb-5">
      <v-row class="mt-15 pa-10" v-if="allDevices.length > 0 || allMaterials.length > 0 || allMaterials.length > 0">

        <div v-if="this.select === this.env.sectionDevice" class="width-container">
          <ComponentItem :loadComponent="allDevices"
                         :searchString="this.search"
                         @editItem="editItem"
                         @deleteItem="deleteItem"
                         @addToSet="addItemToSet"
                         @delFromSet="delItemFromSet"/>
        </div>

        <div v-if="this.select === this.env.sectionMaterial" class="width-container">
          <ComponentItem :loadComponent="allMaterials"
                         :searchString="this.search"
                         @editItem="editItem"
                         @deleteItem="deleteItem"
                         @addToSet="addItemToSet"
                         @delFromSet="delItemFromSet"/>
        </div>

        <div v-if="this.select === this.env.sectionWork" class="width-container">
          <ComponentItem :loadComponent="allWorks"
                         :searchString="this.search"
                         @editItem="editItem"
                         @deleteItem="deleteItem"
                         @addToSet="addItemToSet"
                         @delFromSet="delItemFromSet"/>
        </div>
      </v-row>

      <div v-else class="no-content font-weight-thin">
        {{ env.noRecords }}
      </div>
    </div>

    <v-navigation-drawer width="400" :mini-variant.sync="mini" app clipped right>
      <v-btn class="mt-4 ml-2" icon @click.stop="mini = !mini" tile>
        <v-icon>{{ changeIcon }}</v-icon>
      </v-btn>
        <ComponentForm :selectedItem="this.select"
                       :mini="this.mini"
                       :editComponent="objComponent"/>
    </v-navigation-drawer>

  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import env from '../../env.config.json'
import ComponentItem from "@/components/ComponentItem";
const ComponentForm = () => import('@/components/ComponentForm')

export default {
  name: 'ListComponents',
  components: {ComponentItem, ComponentForm},
  computed:  {
    ...mapGetters(['allDevices', 'allMaterials', 'allWorks']),
    changeIcon() {
      return this.mini === true ? 'mdi-menu-left' : 'mdi-menu-right'
    },
  },

  mounted() {
    this.fetchDevicesForAdmin()
    this.fetchMaterialsForAdmin()
    this.fetchWorksForAdmin()
  },

  data: () => ({
    ...mapActions(['fetchDevicesForAdmin', 'fetchMaterialsForAdmin', 'fetchWorksForAdmin']),
    objComponent: {},
    mini: true,
    env,
    key: JSON.parse(localStorage.getItem('user')),
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
  }),

  methods: {
    ...mapActions([
      'addToSetDevice',
      'addToSetMaterial',
      'addToSetWork',
      'delSetDevice',
      'delSetMaterial',
      'delSetWork'
    ]),

    editItem(item) {
      this.mini = false
      this.objComponent = item
    },

    deleteItem(item) {
      console.log(item.id)
    },

    addItemToSet(item) {
      if (this.select === this.env.sectionDevice) {
        this.addToSetDevice(item)
      } else if (this.select === this.env.sectionMaterial) {
        this.addToSetMaterial(item)
      } else if (this.select === this.env.sectionWork) {
        this.addToSetWork(item)
      }
    },

    delItemFromSet(item) {
      if (this.select === this.env.sectionDevice) {
        this.delSetDevice(item)
      } else if (this.select === this.env.sectionMaterial) {
        this.delSetMaterial(item)
      } else if (this.select === this.env.sectionWork) {
        this.delSetWork(item)
      }
    },
  }
}
</script>

<style scoped lang="scss">

.main-container {
  font-size: small;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: flex-start;
}

.width-container {
  width: 100%;
}

.col-action-section {
  display: flex;
  z-index: 0;
  flex-basis: 5%;
}

.search-panel {
  position: fixed;
  width: 100%;
  height: 5em;
  background: $backgroundSearchPanel;
  z-index: 1;
  border-bottom: 1px solid $border-bottom;
}

.no-content {
  margin-top: 15em;
}

ul {
  list-style: none;
}

</style>