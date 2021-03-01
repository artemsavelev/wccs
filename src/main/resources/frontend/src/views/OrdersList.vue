<template>
  <div>
    <div v-if="allOrders.length">
      <OrderItem v-for="order in allOrders"
                 :key="order.id"
                 v-bind:order="order"
                 v-on:remove="removeOrder"
                 v-on:edit="editOrder"/>

      <LazyLoader/>
    </div>
    <div v-else class="no-content"> {{ env.noRecords }}</div>
  </div>
</template>

<script>
import env from '../../env.config.json'
import LazyLoader from "@/components/LazyLoader";
import OrderItem from "@/components/OrderItem";
import {mapActions, mapGetters} from "vuex";


export default {
  name: "OrdersList",
  computed: mapGetters(['allOrders']),
  components: { LazyLoader, OrderItem },
  data: () => ({
    ...mapActions(['fetchOrders']),
    env
  }),
  methods: {
    editOrder(id) {
      console.log(id)
    },
    removeOrder(id) {
      // this.allOrders = this.allOrders.filter(o => o.id !== id)
      console.log(id)
    }
  },
  mounted() {
    this.fetchOrders()

  },


}
</script>

<style scoped>


</style>