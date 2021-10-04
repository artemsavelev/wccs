<template>
  <v-dialog v-model="dialog" width="80%" scrollable>

    <template v-slot:activator="{ on }">
      <v-btn medium v-on="on" class="ml-5" color="primary" height="35" tile small outlined>
        <v-icon color="warning">mdi-view-headline</v-icon>&nbsp;{{ env.keyPreview }}
      </v-btn>
    </template>
    <v-card class="rounded-0">

      <v-card-title>
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile @click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text class="scroll font-s">

          <div style="height: 140vh">
            <div class="title-estimate mt-5">{{ env.estimate + ' ' + preview.key }}</div>
            <div class="id">{{ env.request }}: {{ preview.extId }}</div>
            <div class="address">{{ env.address }}: {{ preview.address }}</div>
            <div class="customer">{{ env.customer }}: {{ preview.customer }}</div>
            <div class="owner">{{ env.owner }}: {{ preview.owner }}</div>
            <div class="empty"></div>
            <div class="type">{{ env.typeWork }}:</div>
            <div class="description">
              <div v-for="item in this.description">
                {{ item }}
              </div>
            </div>
            <div class="comment">{{ preview.comment }}</div>

            <div class="section-device font-weight-bold font-s">{{ env.sectionDevice }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.devices"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>

            <div class="subtotal-container font-s">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[0] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                {{ (preview.subSumDevice).toFixed(2) | format }}
              </div>
            </div>

            <div class="section-material font-weight-bold font-s">{{ env.sectionMaterial }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.materials"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>

            <div class="subtotal-container font-s">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[1] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                {{ (preview.subSumMaterial).toFixed(2) | format }}
              </div>
            </div>


            <div class="section-work font-weight-bold font-s">{{ env.sectionWork }}</div>
            <HeaderTable
                v-bind:sortName="sortByName"
                v-bind:sortId="sortById"/>
            <Section v-for="(item, idx) in preview.works"
                     :key="item.id + Math.floor(Math.random() * Math.max(Date.now()))"
                     v-bind:idx="idx"
                     v-bind:item="item"/>


            <div class="subtotal-container font-s">
              <div class="subtotal font-weight-bold">
                {{ env.subTotal[2] }}:
              </div>
              <div class="subtotal-price font-weight-bold">
                {{ (preview.subSumWork).toFixed(2) | format }}
              </div>
            </div>


            <div class="empty"></div>


            <div class="tax-container font-s">
              <div class="tax font-weight-bold">
                {{ env.tax + ' ' + env.taxPercent }}%:
              </div>
              <div class="tax-price font-weight-bold">
                {{ env.taxPercent * preview.totalSum / 100 | format }}
              </div>
            </div>


            <div class="total-container font-s">
              <div class="total font-weight-bold">
                {{ env.total }}:
              </div>
              <div class="total-price font-weight-bold">
                {{ (preview.totalSum).toFixed(2) | format }}
              </div>
            </div>

          </div>

      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>

import env from '../../env.config.json'
const Section = () => import('@/components/Section')
const HeaderTable = () => import('@/components/HeaderTable')

export default {
  name: 'Preview',
  props: ['preview'],
  components: { Section, HeaderTable },
  computed: {},
  data() {
    return {
      env,
      formTitle: env.title[2],
      dialog: false,
      typeSection: 4,
      description: []
    }
  },

  watch: {
    preview(val) {
      if (val !== null) {
        this.description = (val.workDescription || '').split('\n').map(e => {
          if (e.length > 4) {
            return e
          } else {
            return null
          }
        })
      }
    }
  },
  methods: {
    close() {
      this.dialog = false
    },
    // сортировка по id
    sortById() {
      this.preview.materials.sort((a, b) => a.id - b.id)
    },
    // сортировка по name
    sortByName() {
      this.preview.materials.sort((a, b) => a.name.localeCompare(b.name))
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
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
  flex-basis: 15.4%;
  text-align: left;
}

.tax-price {
  //border: green 1px solid;
  flex-basis: 15.4%;
  text-align: left;
}

.total-price {
  //border: green 1px solid;
  flex-basis: 15.4%;
  text-align: left;
}

</style>