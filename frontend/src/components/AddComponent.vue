<template>
  <div>

      <div class="ml-7 mb-15 mt-3 font-s">

        <div class="font-weight-bold">
          {{ env.addComponent }}
        </div>
        <div class="font-weight-thin">
          {{ env.fields }}
        </div>

        <v-form
            ref="form"
            v-model="valid"
            lazy-validation>

          <v-row class="mt-5">
            <v-col cols="12" sm="6" md="5">
              <v-autocomplete
                  class="rounded-0"
                  :items="items"
                  item-text="key"
                  v-model="select"
                  :label="env.sectionItem + '*'"
                  required
                  outlined
                  dense
              ></v-autocomplete>
            </v-col>

            <v-col cols="12" sm="6" md="5">
              <v-autocomplete
                  class="rounded-0"
                  :disabled="!select"
                  :items="groups"
                  item-text="id"
                  v-model="group"
                  :label="env.sectionGroup + '*'"
                  required
                  outlined
                  dense
              ></v-autocomplete>
            </v-col>
          </v-row>

          <v-row class="mt-0">
            <v-col cols="12" sm="6" md="5">
              <v-text-field
                  :disabled="!group"
                  :rules="rules"
                  :hint="env.rules[1]"
                  hide-details="auto"
                  class="rounded-0"
                  :label="env.name + '*'"
                  v-model="name"
                  required
                  outlined
                  dense
                  counter/>
            </v-col>

            <v-col cols="2" sm="1" md="1">
              <v-text-field
                  :disabled="!group"
                  :rules="rules"
                  :hint="env.rules[1]"
                  class="rounded-0"
                  :label="env.dimension + '*'"
                  v-model="dimension"
                  required
                  outlined
                  dense
                  counter/>
            </v-col>

            <v-col cols="2" sm="1" md="1">
              <v-text-field
                  :disabled="!group"
                  :rules="rulesPrice"
                  :hint="env.rules[1]"
                  class="rounded-0"
                  :label="env.price + '*'"
                  v-model="price"
                  required
                  outlined
                  dense
                  counter/>
            </v-col>

            <v-col cols="12" sm="6" md="4">
              <v-text-field
                  :disabled="!group"
                  class="rounded-0"
                  :label="env.comment"
                  v-model="comment"
                  required
                  outlined
                  dense
                  counter/>
            </v-col>
          </v-row>

          <v-btn @click="saveComponent"
                 :disabled="!group"
                 :color="colorSave"
                 class="ml-0" tile>{{ env.keySave }}</v-btn>

          <v-btn @click="clear"
                 :disabled="!group"
                 :color="colorClear"
                 class="ml-3" tile>{{ env.keyClear }}</v-btn>

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
      group: '',
      name: '',
      dimension: '',
      price: '',
      comment: '',
      items: [
        { key: env.sectionDevice },
        { key: env.sectionMaterial },
        { key: env.sectionWork }
      ],
      groups: [
        { id: 1 },
        { id: 2 },
        { id: 3 }
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
        group: {
          id: this.groups.id
        }
      }
      console.log(component)

      if (this.name && this.group && this.dimension && this.price) {


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
      this.group = ''
      this.dimension = ''
      this.price = ''
      this.comment = ''
    },

  }
}
</script>

<style scoped>

input {
  font-size: 13px;
}
</style>