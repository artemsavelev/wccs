<template>
  <div>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent max-width="600px">

    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn
          :disabled="$route.path === '/profile' ||
                     $route.path === '/admin' ||
                     $route.path === '/settings' ||
                     $route.path === '/system'"
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

        <div class="font-weight-light">
          {{ env.fields }}
        </div>

        <v-form
            ref="form"
            v-model="valid"
            lazy-validation>

          <v-col cols="12" class="pa-0">
            <v-text-field
                :rules="rules"
                class="pt-10"
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

        <v-card-actions class="pa-0">
          <v-btn v-on:click="save" color="primary" class="mt-5" tile>{{ env.keySave }}</v-btn>
        </v-card-actions>
        </v-form>
      </v-card-text>

    </v-card>
  </v-dialog>

  </div>
</template>

<script>
import env from "../../env.config.json"
import {mapActions, mapGetters} from "vuex";
// import {sendMessage} from "@/utils/ws";


export default {
  name: "OrderForm",
  computed: {
    ...mapGetters(['profile']),

    formTitle() {
      return this.editedIndex === -1 ? env.title[0] : env.title[3]
    },
  },
  data() {
    return {
      env,
      valid: true,
      rules: [value => !!value || env.rules[0]],
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

        this.$refs.form.resetValidation()
        // sendMessage(order)
        this.addOrder(order)

        this.dialog = false

        this.extId = ''
        this.customer = ''
        this.address = ''

      } else {

        this.$refs.form.validate()

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