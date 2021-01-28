<template>
  <v-container class="pa-0">
    <div class="main">
      <div class="main-container">
        <div class="main-container-wrapper pa-1 column-70">
          {{ order.id }}
        </div>

        <div class="main-container-wrapper pa-1 column-70">
          {{ order.extId }}
        </div>

        <div class="main-container-wrapper pa-1 column-200">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.createdDate }}</span>
            </template>
            <span>Дата создания: {{ order.createdDate }}</span>
          </v-tooltip>
        </div>

        <div class="main-container-wrapper pa-1 column-500">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.customer }}</span>
            </template>
            <span>{{ order.customer }}</span>
          </v-tooltip>
        </div>

        <div class="main-container-wrapper pa-1 column-500">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <span v-on="on">{{ order.address}}</span>
            </template>
            <span>{{ order.address }}</span>
          </v-tooltip>
        </div>

        <div class="main-container-wrapper pa-1 column-300">
          <span v-if="order.verificationDate === null" class="text-red">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-icon color="red darken-1" v-on="on">{{ mdiAlertCircle }}</v-icon>
              </template>
              <span>Статус: в работе</span>
            </v-tooltip>
          </span>

          <span v-else-if="order.verificationDate !== null" class="text-green">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-icon color="green darken-2" v-on="on">{{ mdiCheckAll }}</v-icon>
              </template>
              <span>Статус: завершено и проверено</span>
            </v-tooltip>
          </span>
        </div>

        <div class="main-container-wrapper column-200 pl-8">
          <v-btn icon v-on:click="edit">
            <v-icon>{{ mdiPencil }}</v-icon>
          </v-btn>

          <Estimate v-bind:extId="order.extId"
                    v-bind:customer="order.customer"
                    v-bind:address="order.address"/>

          <v-btn icon v-on:click="del">
            <v-icon>{{ mdiDelete }}</v-icon>
          </v-btn>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import { mdiAlertCircle, mdiCheck, mdiCheckAll, mdiPencil, mdiDelete } from '@mdi/js';
import {mapActions, mapGetters} from "vuex";
import Estimate from "../views/Estimate";
export default {
  name: 'OrderItem',
  props: ['order', 'editOrder', 'delOrder'],
  computed: mapGetters(['allOrders']),
  components: { Estimate },

  methods: {
    ...mapActions(['fetchOrders']),

    edit() {
      this.$emit('edit', this.order.id)
    },
    del() {
      this.$emit('remove', this.order.id)
    },


  },



  data: () => ({
    mdiAlertCircle, mdiCheck, mdiCheckAll, mdiPencil, mdiDelete,

    customer: '',
    address: '',
    dataOrder: {}

  }),


}
</script>

<style lang="scss">

  .main {
    border-bottom: 1px solid #ccc;
    font-size: small;
  }
  .main-container {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    &-wrapper {
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
    }
  }
  .column-50 {
    width: 50px;
    max-width: 50px;
  }
  .column-70 {
    width: 70px;
    max-width: 70px;
  }
  .column-100 {
    width: 100px;
    max-width: 100px;
  }
  .column-150 {
    width: 150px;
    max-width: 150px;
  }
  .column-200 {
    width: 200px;
    max-width: 200px;
  }
  .column-300 {
    width: 300px;
    max-width: 300px;
  }
  .column-400 {
    width: 400px;
    max-width: 400px;
  }
  .text-red {
    color: red;
  }
  .text-green {
    color: green;
  }
</style>