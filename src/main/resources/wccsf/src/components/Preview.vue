<template>
  <v-dialog v-model="dialog" width="80%"  scrollable persistent>

    <template v-slot:activator="{ on }">
      <v-btn medium v-on="on" class="ml-5" color="primary" tile>{{ env.keyPreview }}</v-btn>
    </template>
    <v-card>

      <v-card-title class="grey lighten-2">
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>{{ mdiClose }}</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>

          <div style="height: 120vh">
            <div class="title-estimate">{{ env.estimate }}</div>
            <div class="id">{{ env.request }}: {{ preview.extId }}</div>
            <div class="address">{{ env.address }}: {{ preview.address }}</div>
            <div class="customer">{{ env.customer }}: {{ preview.customer }}</div>
            <div class="owner">с{{ env.owner }}: {{ preview.owner }}</div>
            <div class="empty"></div>
            <div class="type">{{ env.typeWork }}:</div>
            <div class="description">{{ preview.workDescription }}</div>
            <div class="comment">{{ preview.comment }}</div>

            <div class="section-device font-weight-bold">{{ env.sectionDevice }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.devices"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>

            <div class="subtotal-container">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[0] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                subTotal
              </div>
            </div>

            <div class="section-material font-weight-bold">{{ env.sectionMaterial }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.materials"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>

            <div class="subtotal-container">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[1] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                {{  }}
              </div>
            </div>


            <div class="section-work font-weight-bold">{{ env.sectionWork }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.works"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>



            <div class="subtotal-container">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[2] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                subTotal
              </div>
            </div>


            <div class="empty"></div>


            <div class="tax-container">
              <div class="tax font-weight-bold">
                {{ env.tax }}:
              </div>
              <div class="tax-price font-weight-bold">
                tax
              </div>
            </div>


            <div class="total-container">
              <div class="total font-weight-bold">
                {{ env.total }}:
              </div>
              <div class="total-price font-weight-bold">
                total
              </div>
            </div>

          </div>

      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { mdiClose } from '@mdi/js';
import env from "../../env.config.json"
const Section = () => import('@/components/Section')
const HeaderTable = () => import('@/components/HeaderTable')

export default {
  name: "Preview",
  props: ['preview'],
  components: { Section, HeaderTable },
  computed: {},
  data() {
    return {
      env,
      mdiClose,
      formTitle: env.title[2],
      dialog: false,
      typeSection: 4,
    }
  },
  methods: {
    close() {
      this.dialog = false
    },

    click: function () {
      this.$refs.subTotal.setValue(2.0);
    },

    // сортировка по id
    sortById() {
      this.preview.materials.sort((a, b) => a.id - b.id)
    },
    // сортировка по name
    sortByName() {
      this.preview.materials.sort((a, b) => a.name.localeCompare(b.name))
    }
  }
}
</script>

<style lang="scss" scoped>
.title-estimate {
  font-size: 20pt;
  height: 40px;
  text-align: center;
}

.id {
  text-align: center;
}

.address {
  text-align: center;
}

.customer {
  text-align: center;
}

.owner {
  text-align: center;
}

.type {
  text-align: left;
}

.description {
  height: 100px;
  text-align: left;
}

.comment {
  color: red;
  height: 70px;
  text-align: left;
}


.empty {
  height: 10px;
}

.section-device  {
  display: table-cell;
  vertical-align: bottom;
  height: 40px;
}

.section-material {
  display: table-cell;
  vertical-align: bottom;
  height: 40px;
}

.section-work {
  display: table-cell;
  vertical-align: bottom;
  height: 40px;


}
.section-work {
  display: table-cell;
  vertical-align: bottom;
  height: 40px;


}

.subtotal-container {
  //border: 1px solid #ff0000;
  display: flex;
  justify-content: space-between;
}

.tax-container {
  //border: 1px solid #ff0000;
  display: flex;
  justify-content: space-between;
}

.total-container {
  //border: 1px solid #ff0000;
  display: flex;
  justify-content: space-between;
}

.subtotal {
  //border: green 1px solid;
  flex-basis: 83%;
  text-align: right;
}

.tax {
  //border: green 1px solid;
  flex-basis: 83%;
  text-align: right;
}

.total {
  //border: green 1px solid;
  flex-basis: 83%;
  text-align: right;
}

.subtotal-price {
  //border: green 1px solid;
  flex-basis: 13.5%;
  text-align: left;
}

.tax-price {
  //border: green 1px solid;
  flex-basis: 13.5%;
  text-align: left;
}

.total-price {
  //border: green 1px solid;
  flex-basis: 13.5%;
  text-align: left;
}

</style>