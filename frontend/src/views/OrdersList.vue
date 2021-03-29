<template>

  <div>

    <div>


      <div v-if="allOrders.length" class="left">
        <OrderItem v-for="order in allOrders"
                   :key="order.id"
                   v-bind:order="order"
                   v-on:removeOrder="removeOrder"
                   v-on:edit="editOrder"/>




        <LazyLoader/>

      </div>
      <div v-else class="no-content mr-16"> {{ env.noRecords }}</div>

    </div>



    <v-navigation-drawer width="400" v-model="drawerRight" app clipped right>

      <v-list dense>

        <v-card-text>

          <div class="font-weight-bold">
            {{ formTitle }}
          </div>

          <div class="font-weight-light">
            {{ env.fields }}
          </div>

          <v-form
              ref="form"
              v-model="valid"
              lazy-validation>


            <v-row class="pt-5">
              <v-col cols="12" sm="6" md="6">
                <v-menu v-model="menuStartDate"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="100px">

                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="startDate"
                                  :label="env.startDate"
                                  dense
                                  disabled
                                  readonly
                                  v-show="false"
                                  v-on="on">
                    </v-text-field>
                  </template>

                  <v-date-picker
                      v-model="startDate"
                      @input="menuStartDate = false">
                  </v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="12" sm="6" md="6">
                <v-menu v-model="menuStartTime"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="100px">

                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="startTime"
                                  :label="env.startTime"
                                  dense
                                  disabled
                                  readonly
                                  v-show="false"
                                  v-on="on">
                    </v-text-field>
                  </template>

                  <v-time-picker
                      v-model="startTime"
                      format="24hr"
                      @input="menuStartTime = false">
                  </v-time-picker>
                </v-menu>
              </v-col>
            </v-row>

            <v-col cols="12" class="pa-0">
              <v-text-field
                  :rules="rules"
                  class="pt-5"
                  dense
                  :label="env.numberOrder + '*'"
                  required
                  v-model="extId"/>
            </v-col>

            <v-col cols="12" class="pa-0">
              <v-text-field
                  :rules="rules"
                  dense
                  class="pt-5"
                  :label="env.customer + '*'"
                  required
                  v-model="customer"/>
            </v-col>

            <v-col cols="12" class="pa-0">
              <v-text-field
                  :rules="rules"
                  dense
                  class="pt-5"
                  :label="env.address + '*'"
                  required
                  v-model="address"/>
            </v-col>


            <v-col cols="12" class="pa-0">
              <v-autocomplete v-model="workGroup.id"
                              :items="group"
                              label="Тип работ"
                              item-text="groupList"
                              item-value="id"
                              class="mt-5"
                              dense
                              disabled
                              v-show="false"
                              required>
              </v-autocomplete>
            </v-col>

            <v-autocomplete v-model="users"
                            :items="executors"
                            label="Исполнители"
                            item-text="name"
                            item-value="userId"
                            class="mt-5"
                            dense
                            required
                            disabled
                            v-show="false"
                            multiple>
            </v-autocomplete>

            <v-col cols="12" class="pa-0">
              <v-text-field
                  :rules="rules"
                  dense
                  class="pt-5"
                  :label="env.typeWork + '*'"
                  disabled
                  v-show="false"
                  v-model="description"/>
            </v-col>

            <v-row class="pt-5">
              <v-col cols="12" sm="6" md="6">
                <v-menu v-model="menuEndDate"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="100px">

                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="endDate"
                                  :label="env.endDate"
                                  dense
                                  disabled
                                  readonly
                                  v-show="false"
                                  v-on="on">
                    </v-text-field>
                  </template>

                  <v-date-picker
                      v-model="endDate"
                      @input="menuEndDate = false">
                  </v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="12" sm="6" md="6">
                <v-menu v-model="menuEndTime"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="100px">

                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="endTime"
                                  :label="env.endTime"
                                  dense
                                  disabled
                                  readonly
                                  v-show="false"
                                  v-on="on">
                    </v-text-field>
                  </template>

                  <v-time-picker
                      v-model="endTime"
                      format="24hr"
                      @input="menuEndTime = false">
                  </v-time-picker>
                </v-menu>
              </v-col>
            </v-row>

            <v-card-actions class="pa-0">
              <v-row>
                <v-col cols="12" sm="6" md="6">
                  <v-btn v-on:click="save" :color="colorSave" class="mt-5" tile>{{ env.keySave }}</v-btn>
                </v-col>
                <v-col cols="12" sm="6" md="6" class="d-flex justify-md-end">
                  <v-btn v-on:click="clear" :color="colorClear" class="mt-5" tile>{{ env.keyClear }}</v-btn>
                </v-col>
              </v-row>
            </v-card-actions>
          </v-form>
        </v-card-text>

      </v-list>



    </v-navigation-drawer>


  </div>


</template>

<script>
import env from '../../env.config.json'
import LazyLoader from "@/components/LazyLoader"
import OrderItem from "@/components/OrderItem"
import {mapActions, mapGetters} from "vuex"
import { bus } from "@/utils/bus"


export default {
  name: "OrdersList",
  computed: {
    ...mapGetters(['allOrders', 'profile']),
    formTitle() {
      return this.editedIndex === -1 ? env.title[0] : env.title[3] + ' # ' + this.id
    },
  },
  components: { LazyLoader, OrderItem },


  data: () => ({

    ...mapActions(['fetchOrders', 'showSnack']),

    env,
    valid: true,
    loading: false,
    drawerRight: true,
    rules: [value => !!value || env.rules[0]],
    message: '',
    color: '',
    colorSave: 'primary',
    colorClear: 'primary',
    id: '',
    extId: '',
    customer: '',
    address: '',
    creationDate: '',
    editedIndex: -1,

    startDate: new Date().toISOString().substr(0, 10),
    startTime: new Date().getHours() + ':' + new Date().getMinutes(),
    menuStartDate: false,
    menuStartTime: false,
    endDate: null,
    endTime: null,
    menuEndDate: false,
    menuEndTime: false,
    executors: [],
    users: [],
    userId: '',
    group: [],
    workGroup: {
      id: '',
      groupList: '',
    },
    description: '',

  }),

  methods: {

    ...mapActions(['addOrder', 'updateOrder']),

    // сохранение новой записи
    save() {

      const order = {
        id: this.id,
        extId: this.extId,
        customer: this.customer,
        address: this.address,
      };

      if (this.extId && this.customer && this.address) {

        if (this.id) {
          this.updateOrder(order)
        } else {
          this.addOrder(order)
        }

        this.editedIndex = -1
        this.$refs.form.resetValidation()
        this.id = ''
        this.extId = ''
        this.customer = ''
        this.address = ''
        this.colorSave = 'primary'
        this.colorClear = 'primary'

      } else {
        this.$refs.form.validate()
      }

    },

    clear() {
      this.colorSave = 'primary'
      this.colorClear = 'primary'
      this.editedIndex = -1
      this.$refs.form.resetValidation()
      this.id = ''
      this.extId = ''
      this.customer = ''
      this.address = ''
    },

    editOrder(item) {
      this.colorSave = 'success'
      this.colorClear = 'error'
      this.drawerRight = true
      this.editedIndex = 1
      this.id = item.id
      this.extId =  item.extId
      this.customer = item.customer
      this.address = item.address
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

  },


}
</script>

<style scoped>


</style>