<template>
  <v-dialog v-model="dialog" @click:outside="close" width="50%" scrollable>
    <v-card class="rounded-0">
      <v-card-title>
        <div class="font-m font-weight-light">{{ env.estimateChose[0] }} {{ this.estimate.length }} {{ this.quantity }} {{ env.estimateChose[1] }}</div>
        <v-spacer></v-spacer>
        <v-btn small text tile @click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text class="pl-6 font-s">
        <div class="container ma-0 pa-0 white--text table">
          <div class="col-order-id">#</div>
          <div class="col-estimate-id">№ сметы</div>
          <div class="col-ext-id">№ RRSD</div>
          <div class="col-created-date">время создания</div>
          <div class="col-customer">заказчик</div>
          <div class="col-address">адрес</div>
          <div class="col-amount">общая сумма</div>
        </div>
        <div v-for="estimate in estimates" :key="estimate.id">
          <div class="container ma-0 pa-0">
            <div class="col-order-id">{{ estimate.orderId }}</div>
            <div class="col-estimate-id">{{ estimate.id }}</div>
            <div class="col-ext-id"><b><a @click="sendEstimate(estimate)">{{ estimate.extId }}</a></b></div>
            <div class="col-created-date">{{ estimate.createdDate }}</div>
            <div class="col-customer">{{ estimate.customer }}</div>
            <div class="col-address">{{ estimate.address }}</div>
            <div class="col-amount red--text"><i>{{ estimate.amount.toFixed(2) | format }}</i></div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import env from '../../env.config.json'

export default {
  name: 'EstimateChose',
  components: {},
  props: ['visibleEstimateChose', 'estimates'],
  computed: {
  },
  data: () => ({
    env,
    dialog: false,
    estimate: {},
    quantity: ''

  }),
  watch: {
    visibleEstimateChose(newVal) {
      this.dialog = newVal
    },
    estimates(newVal) {
      this.estimate = newVal

      const one = 'смета'
      const two = 'сметы'
      const three = 'смет'

      switch (newVal.length) {
        case 1:
        case 21:
        case 31:
          return this.quantity = one;
        case 2:
        case 3:
        case 4:
        case 22:
        case 23:
        case 24:
          return this.quantity = two;
        default:
          return this.quantity = three;
      }
    }
  },
  methods: {
    sendEstimate(estimate) {
      this.$emit('chose', estimate)
    },
    close() {
      this.dialog = false
      this.$emit('visibleEstimateChose', false)
    }
  },
  filters: {
    format: val => `${val}`.replace(/(\d)(?=(\d{3})+([^\d]|$))/g, '$1 '),
  },
}
</script>

<style lang="scss" scoped>

.container{
  display: flex;
  flex-direction: row;
}
.col-order-id{
  min-width: 5%;
  white-space: nowrap;
  overflow: hidden;
}
.col-estimate-id{
  min-width: 9%;
  white-space: nowrap;
  overflow: hidden;
}
.col-ext-id{
  min-width: 10%;
  white-space: nowrap;
  overflow: hidden;
}
.col-created-date{
  min-width: 15%;
  white-space: nowrap;
  overflow: hidden;
}
.col-customer{
  min-width: 23%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.col-address{
  min-width: 23%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.col-amount{
  text-align: center;
  min-width: 14%;
  white-space: nowrap;
  overflow: hidden;
}
a:hover{
  color: red;
}

</style>