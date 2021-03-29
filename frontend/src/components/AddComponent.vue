<template>
  <div>

      <div class="registration ml-7 mb-15 mt-3">

        <div class="font-weight-bold ml-3">
          {{ env.addComponent }}
        </div>
        <div class="ml-3  font-weight-thin">
          {{ env.fields }}
        </div>

        <v-form
            ref="form"
            v-model="valid"
            lazy-validation>

          <v-col cols="12" sm="6" md="5">
            <v-autocomplete
                :items="items"
                item-text="key"
                v-model="select"
                :label="env.sectionItem + '*'"
                required
            ></v-autocomplete>
          </v-col>

          <v-row class="ma-0 pa-0">

            <v-col cols="12" sm="6" md="5">
              <v-text-field
                  :disabled="!select"
                  :rules="rules"
                  :hint="env.rules[1]"
                  hide-details="auto"
                  class="pt-5"
                  dense
                  :label="env.name + '*'"
                  v-model="name"
                  required
                  counter/>
            </v-col>

            <v-col cols="2" sm="1" md="1">
              <v-text-field
                  :disabled="!select"
                  :rules="rules"
                  :hint="env.rules[1]"
                  dense
                  class="pt-5"
                  :label="env.dimension + '*'"
                  v-model="dimension"
                  required
                  counter/>
            </v-col>

            <v-col cols="2" sm="1" md="1">
              <v-text-field
                  :disabled="!select"
                  :rules="rulesPrice"
                  :hint="env.rules[1]"
                  dense
                  class="pt-5"
                  :label="env.price + '*'"
                  v-model="price"
                  required
                  counter/>
            </v-col>

            <v-col cols="12" sm="6" md="4">
              <v-text-field
                  :disabled="!select"
                  dense
                  class="pt-5"
                  :label="env.comment"
                  v-model="comment"
                  counter/>
            </v-col>
          </v-row>

          <v-btn @click="saveComponent"
                 :disabled="!select"
                 :color="colorSave"
                 class="ml-3" tile>{{ env.keySave }}</v-btn>

          <v-btn @click="clear"
                 :disabled="!select"
                 :color="colorClear"
                 class="ml-5" tile>{{ env.keyClear }}</v-btn>

        </v-form>

      </div>

  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import env from '../../env.config.json'

export default {
  name: 'AddComponent',
  computed: {
    ...mapGetters(['profile']),

  },

  data() {
    return {
      env,
      valid: true,
      show: false,
      colorSave: 'primary',
      colorClear: 'primary',

      select: '',
      name: '',
      dimension: '',
      price: '',
      comment: '',
      items: [
        { key: env.sectionDevice },
        { key: env.sectionMaterial },
        { key: env.sectionWork }
      ],

      rules: [
        value => !!value || env.rules[0],
        value => (value && value.length >= 3) || env.rules[1],
      ],

      rulesPrice: [
        value => !!value || env.rules[0],
        value => (value && value.length >= 3) || env.rules[1],
      ],

    }
  },

  updated() {



    if (this.name && this.dimension && this.price) {
      this.colorSave = 'success'
      this.colorClear = 'error'
    }


  },

  methods: {

    ...mapActions(['addDevice', 'addMaterial', 'addWork', 'showSnack']),

    saveComponent() {

      const component = {
        name: this.name,
        dimension: this.dimension,
        price: this.price,
        note: this.comment,
      }

      if (this.name && this.dimension && this.price) {


        if (this.select === this.env.sectionDevice) {
          this.addDevice(component)
        } else if (this.select === this.env.sectionMaterial) {
          this.addMaterial(component)
        } else {
          this.addWork(component)
        }

      } else {

        this.$refs.form.validate()

        const data = {
          message: 'Поля формы не должны быть пустыми',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(data)
      }
    },

    clear() {
      this.colorSave = 'primary'
      this.colorClear = 'primary'
      this.$refs.form.resetValidation()
      this.name = ''
      this.dimension = ''
      this.price = ''
      this.comment = ''
    },

  }
}
</script>

<style scoped>

</style>