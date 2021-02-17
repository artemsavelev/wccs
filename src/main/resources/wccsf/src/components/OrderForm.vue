<template>
  <v-dialog v-if="profile" v-model="dialog" scrollable width="80%">

    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn :disabled="$route.path === '/profile' || $route.path === '/admin' || $route.path === '/settings'" dark v-on="on" text tile>
        {{ env.keyCreate }}
      </v-btn>
    </template>
    <v-card>

      <v-card-title class="headline grey lighten-2">
        <span class="font-xl font-weight-medium ml-3">{{formTitle}}</span>
      </v-card-title>

      <v-card-text>
        <v-container>

          <v-row>
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                  dense
                  label="Номер заявки"
                  v-model="extId"/>
            </v-col>

            <v-col cols="12" sm="6" md="4">
              <v-text-field
                  dense
                  label="Заказчик"
                  v-model="customer"/>
            </v-col>



            <v-col cols="12" sm="6" md="4">
              <v-text-field
                  dense
                  label="Адрес"
                  v-model="address" />
            </v-col>
          </v-row>

        </v-container>
        <v-card-actions>
          <v-btn v-on:click="save" color="primary" class="mr-2" tile>{{ env.keySave }}</v-btn>
          <v-btn v-on:click="close" color="primary" tile>{{ env.keyClose }}</v-btn>
        </v-card-actions>
      </v-card-text>

    </v-card>
  </v-dialog>

</template>

<script>
import env from "../../env.config.json"
import { mapActions, mapGetters } from "vuex";

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
    ...mapActions(['addOrder', 'addOrderMutation']),
    // сохранение новой записи
    save() {
      const order = {
        extId: this.extId,
        customer: this.customer,
        address: this.address,
      };

      if (this.extId && this.customer && this.address) {
        this.addOrder(order);
        console.log(order);
        this.dialog = false;
      }
    },
    close() {
      this.dialog = false;
    }
  },
  watch: {

    orderVal(newVal){
      console.log(newVal)

      this.dialog = true;
      this.id = newVal.id;
      this.extId = newVal.extId;
      this.customer = newVal.customer;
      this.editedIndex = this.id
    }

  },

}

</script>

<style scoped lang="scss">
.container-form {
  margin: auto 0;
}

</style>