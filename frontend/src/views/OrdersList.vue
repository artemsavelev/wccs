<template>
  <div class="main-container">
      <div class="search-panel">
        <v-row class="ml-3 mt-3">
          <v-col cols="12" sm="6" md="2" class="pa-0">
            <v-text-field v-model="search"
                          :label="env.search"
                          id="s"
                          dense
                          outlined
                          @click:clear="flush"
                          style="left: -1px"
                          clearable
                          class="rounded-0 styled-input font-s"/>
          </v-col>
<!--          <v-col cols="12" sm="6" md="2" class="ml-3 pa-0">-->
<!--            <v-btn @click="flush" tile outlined color="primary" height="40">сбросить поиск</v-btn>-->
<!--          </v-col>-->
        </v-row>
      </div>

      <div>search
        <div v-if="allOrders.length" class="mt-14">
          <OrderItem v-for="order in allOrders"
                     :key="order.id"
                     :order="order"
                     @removeOrder="removeOrder"
                     @editOrder="editOrder"/>

          <!-- ленивая загрузка при прокрутке-->
          <LazyLoader/>
        </div>
        <div v-else class="no-content mr-16 mt-16">{{ env.noRecords }}</div>
      </div>

    <v-navigation-drawer width="400" :mini-variant.sync="mini" app clipped right>
      <v-btn class="mt-4 ml-2" icon @click.stop="mini = !mini" tile>
        <v-icon>{{ changeIcon }}</v-icon>
      </v-btn>
      <OrderForm :mini="this.mini"
                 :editOrder="objOrder"/>
    </v-navigation-drawer>
  </div>
</template>

<script>
import env from '../../env.config.json'
import LazyLoader from "@/components/LazyLoader"
import OrderItem from "@/components/OrderItem"
import {mapActions, mapGetters, mapMutations} from "vuex"
import { bus } from "@/utils/bus"
import {fromEvent} from 'rxjs'
import {debounceTime, distinctUntilChanged, filter, map, switchMap, tap} from "rxjs/operators";
import {ajax} from "rxjs/ajax";
import api from "@/api/backendApi";
import OrderForm from "@/components/OrderForm";


export default {
  name: "OrdersList",
  computed: {
    ...mapGetters(['allOrders', 'profile']),
    changeIcon() {
      return this.mini === true ? 'mdi-menu-left' : 'mdi-menu-right'
    },
  },
  components: { OrderForm, LazyLoader, OrderItem },

  data: () => ({
    ...mapActions(['fetchOrders', 'showSnack']),
    ...mapMutations(['searchOrderMutation']),
    mini: true,
    search: '',
    objOrder: {},
    env,
  }),

  methods: {
    ...mapActions(['deleteOrder']),
    flush() {
      this.search = ''
      Object.assign(this, this.fetchOrders());
    },

    editOrder(item) {
      this.mini = false
      this.objOrder = item
    },

    removeOrder(item) {
      this.deleteOrder(item)
      const data = {
        message: 'Запись с # "' + item.id + '" удалена ',
        color: 'warning',
        icon: 'mdi-alert'
      }
      this.showSnack(data)
    }
  },

  updated() {
    bus.$on('show-drawer', data => {
      this.drawerRight = data
      // console.log(data)
    })

    if (this.extId && this.customer && this.address) {
      this.colorSave = 'success'
      this.colorClear = 'error'
    }
  },

  mounted() {
    this.fetchOrders()

    // поиск по заявкам напрямую в бд
    const user = JSON.parse(localStorage.getItem('user'))
    const search = document.getElementById('s')
    const stream$ = fromEvent(search, 'input')
        .pipe(
            map(e => e.target.value.replace(/[/\\?,%*:|+"<>#--]/g, '').toLowerCase()),
            filter(searchValue => searchValue.length >= 3),
            debounceTime(1000),
            distinctUntilChanged(),
            tap(e => console.log(e)),
            switchMap(v => ajax({
              url: api.API_ORDER_SEARCH_URL + v,
              headers: {
                'Content-Type': 'application/json',
                'Authorization': 'bearer_' + user.token
              }
            }).pipe(
                map(response => {
                  this.searchOrderMutation(response.response)
                  // console.log(response.response)
                }),
            ))
        )
    stream$.subscribe()

  },
}
</script>

<style scoped lang="scss">
.search-panel {
  position: fixed;
  width: 100%;
  height: 5em;
  background: $backgroundSearchPanel;
  z-index: 1;
  border-bottom: 1px solid $border-bottom;
}
</style>