<template>
  <div class="ma-3" v-if="!mini">
    <div class="font-weight-bold font-s">
      {{ formTitle }}
    </div>
    <div class="font-weight-light font-s">
      {{ env.fields }}
    </div>
    <v-form
        ref="form"
        v-model="valid"
        lazy-validation>

      <v-col cols="12" class="pa-0">
        <v-text-field
            :rules="rules"
            class="pt-5 rounded-0"
            dense
            :label="env.numberOrder + '*'"
            required
            outlined
            v-model="extId"/>
      </v-col>

      <v-col cols="12" class="pa-0">
        <v-text-field
            :rules="rules"
            dense
            class="pt-5 rounded-0"
            :label="env.customer + '*'"
            required
            outlined
            v-model="customer"/>
      </v-col>

      <v-col cols="12" class="pa-0">
        <v-text-field
            :rules="rules"
            dense
            class="pt-5 rounded-0"
            :label="env.address + '*'"
            required
            outlined
            v-model="address"/>
      </v-col>

      <v-card-actions class="pa-0">
        <v-row>
          <v-col cols="12" sm="6" md="6">
            <v-btn v-on:click="save" :color="colorSave" class="mt-5" height="35" tile small outlined>{{ env.keySave }}</v-btn>
          </v-col>
          <v-col cols="12" sm="6" md="6" class="d-flex justify-md-end">
            <v-btn v-on:click="clear" :color="colorClear" class="mt-5" height="35" tile small outlined>{{ env.keyClear }}</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>

import env from "../../env.config.json";
import {mapActions} from "vuex";

export default {
  name: "OrderForm",
  props: ['mini', 'editOrder'],
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? env.title[0] : env.title[3] + ' # ' + this.id
    },
  },

  data: () => ({
    env,
    valid: true,
    editedIndex: -1,
    colorSave: 'primary',
    colorClear: 'primary',
    rules: [value => !!value || env.rules[0]],
    id: '',
    extId: '',
    customer: '',
    address: ''
  }),

  methods: {
    ...mapActions(['addOrder', 'updateOrder']),
    save() {
      const order = {
        id: this.id,
        extId: this.extId,
        customer: this.customer,
        address: this.address,
      }
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
  },

  watch: {
    editOrder(newVal) {
      this.editedIndex = 1
      this.id = newVal.id
      this.extId = newVal.extId
      this.customer = newVal.customer
      this.address = newVal.address
    }
  },

  updated() {
    if (this.extId && this.customer && this.address) {
      this.colorSave = 'success'
      this.colorClear = 'error'
    }
  }

}
</script>

<style lang="scss">

</style>