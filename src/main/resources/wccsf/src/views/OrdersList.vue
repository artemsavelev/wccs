<template>
  <div>
    <div>
      <OrderItem v-for="order in allOrders"
                 :key="order.id"
                 v-bind:order="order"
                 v-on:remove="removeOrder"
                 v-on:edit="editOrder"/>

      <LazyLoader/>
    </div>
  </div>
</template>

<script>
import LazyLoader from "@/components/LazyLoader";
// const LazyLoader = () => import('../components/LazyLoader')
const OrderItem = () => import('../components/OrderItem')
import {mapActions, mapGetters} from "vuex";


export default {
  name: "OrdersList",
  computed: mapGetters(['allOrders']),
  components: { LazyLoader, OrderItem },
  data: () => ({
    ...mapActions(['fetchOrders']),
  }),
  methods: {
    // editOrder(id) {
    //   // console.log(id)
    // },
    // removeOrder(id) {
    //   // this.allOrders = this.allOrders.filter(o => o.id !== id)
    //   // console.log(id)
    // }
  },
  mounted() {
    this.fetchOrders();
  },


}
</script>

<style scoped>


</style>