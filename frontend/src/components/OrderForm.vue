<template>
  <div>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent max-width="600px">

    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn :disabled="$route.path === '/profile' || $route.path === '/admin' || $route.path === '/settings'"
             dark v-on="on"
             text tile>
        {{ env.keyCreate }}
      </v-btn>
    </template>
    <v-card>

      <v-card-title class="form">
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text>

          <v-col cols="12" class="pa-0">
            <v-text-field
                class="pt-10"
                dense
                label="Номер заявки"
                v-model="extId"/>
          </v-col>

          <v-col cols="12" class="pa-0">
            <v-text-field
                dense
                class="pt-5"
                label="Заказчик"
                v-model="customer"/>
          </v-col>

          <v-col cols="12" class="pa-0">
            <v-text-field
                dense
                class="pt-5"
                label="Адрес"
                v-model="address" />
          </v-col>



        <v-card-actions class="pa-0">
          <v-btn v-on:click="save" color="primary" class="mt-5" tile>{{ env.keySave }}</v-btn>
        </v-card-actions>
      </v-card-text>

    </v-card>
  </v-dialog>



  </div>
</template>

<script>
import env from "../../env.config.json"
import { mapActions, mapGetters } from "vuex";
// import {sendMessage} from "@/utils/ws";


export default {
  name: "OrderForm",
  computed: {
    ...mapGetters(['profile']),

    formTitle() {
      return this.editedIndex === -1 ? env.title[0] : 'Редактирование записи '
    },
  },
  data() {
    return {
      env,
      message: '',
      color: '',
      dialog: false,
      orderId: '',
      extId: '',
      customer: '',
      address: '',
      creationDate: '',
      editedIndex: -1
    };
  },

  methods: {
    ...mapActions(['addOrder', 'showSnack']),

    // сохранение новой записи
    save() {

      const order = {
        extId: this.extId,
        customer: this.customer,
        address: this.address,
      };

      if (this.extId && this.customer && this.address) {


        // sendMessage(order)
        this.addOrder(order)

        this.dialog = false

        const data = {
          message: 'Запись #' + this.extId  + ' Заказчик:' + this.customer + ' Адрес:' + this.address + ' добавлена',
          color: 'success'
        }

        this.showSnack(data)

        this.extId = ''
        this.customer = ''
        this.address = ''

      }

    },


    close() {
      this.dialog = false;

    }
  },
  watch: {

    orderVal(newVal) {

      this.dialog = true;
      this.id = newVal.id;
      this.extId = newVal.extId;
      this.customer = newVal.customer;
      this.editedIndex = this.id
    }

  },

}

</script>

<style lang="scss">


</style>