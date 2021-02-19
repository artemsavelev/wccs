<template>
  <div>
    <div>
      <OrderItem v-for="order in allOrders"
                 :key="order.id"
                 v-bind:order="order"
                 v-on:remove="removeOrder"
                 v-on:edit="editOrder"/>

    </div>
  </div>
</template>

<script>
const OrderItem = () => import('../components/OrderItem')
import {mapActions, mapGetters} from "vuex";


export default {
  name: "OrdersList",
  computed: mapGetters(['allOrders']),
  components: { OrderItem },
  data: () => ({
    ...mapActions(['fetchOrders']),
    order:[]
  }),
  methods: {
    editOrder(id) {
      console.log(id)
    },
    removeOrder(id) {
      this.order = this.order.filter(o => o.id !== id)
      // console.log(id)
    }
  },
  mounted() {
    this.fetchOrders();
  },


}
</script>

<style scoped>


</style>