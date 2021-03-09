<template>
  <div>

    <div v-if="allOrders.length">
      <OrderItem v-for="order in allOrders"
                 :key="order.id"
                 v-bind:order="order"
                 v-on:removeOrder="removeOrder"
                 v-on:edit="editOrder"/>


      <LazyLoader/>
    </div>
    <div v-else-if="profile" class="no-content"> {{ env.noRecords }}</div>
  </div>
</template>

<script>
import env from '../../env.config.json'
import LazyLoader from "@/components/LazyLoader";
import OrderItem from "@/components/OrderItem";
import {mapActions, mapGetters} from "vuex";


export default {
  name: "OrdersList",
  computed: mapGetters(['allOrders', 'profile']),
  components: { LazyLoader, OrderItem },


  data: () => ({

    ...mapActions(['fetchOrders', 'showSnack']),

    env,
    loading: false
  }),
  methods: {


    editOrder(id) {
      const data = {
        message: '"' + id + '" ',
        color: 'success',
        icon: 'mdi-check-circle'
      }

      this.showSnack(data)
      //console.log(id)
    },
    removeOrder(id) {
      const data = {
        message: '"' + id + '" ',
        color: 'success',
        icon: 'mdi-check-circle'
      }

      this.showSnack(data)
      // this.allOrders = this.allOrders.filter(o => o.id !== id)
      //console.log(id)
    }
  },
  mounted() {

    this.fetchOrders().then(() => {
      this.loading = true
    })

  },


}
</script>

<style scoped>


</style>