<template>
  <div>

      <div class="ml-0 mb-15 mt-3 font-s">

        <div class="font-weight-bold ml-3">
          {{ env.addComponent }}
        </div>
        <div class="font-weight-thin ml-3">
          {{ env.fields }}
        </div>

        <v-form
            ref="form"
            v-model="valid"
            lazy-validation>


<!--          <v-col cols="12" sm="6" md="12" class="ml-0">-->
<!--            <v-autocomplete-->
<!--                class="rounded-0"-->
<!--                :items="items"-->
<!--                item-text="key"-->
<!--                v-model="selectedItem"-->
<!--                :label="env.sectionItem + '*'"-->
<!--                required-->
<!--                outlined-->
<!--                dense-->
<!--            ></v-autocomplete>-->
<!--          </v-col>-->

          <v-col cols="12" sm="6" md="12">
            <v-autocomplete
                class="rounded-0"
                :disabled="!selectedItem"
                :items="group"
                item-text="name"
                item-value="id"
                v-model="group.id"
                :label="env.sectionGroup + '*'"
                required
                outlined
                dense
            ></v-autocomplete>
          </v-col>

          <v-col cols="12" sm="6" md="12">
            <v-text-field
                :disabled="!group.id"
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

          <v-row class="ml-0 mr-0 mt-0 mb-1">

            <v-col cols="12" sm="2" md="6">
              <v-text-field
                  :disabled="!group.id"
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

            <v-col cols="12" sm="2" md="6">
              <v-text-field
                  :disabled="!group.id"
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
          </v-row>

          <v-col cols="12" sm="6" md="12">
            <v-text-field
                :disabled="!group.id"
                class="rounded-0"
                :label="env.comment"
                v-model="comment"
                required
                outlined
                dense
                counter/>
          </v-col>


          <v-card-actions class="pa-0">

            <v-col cols="12" sm="6" md="6">
              <v-btn @click="saveComponent"
                     :disabled="!group.id"
                     :color="colorSave"
                     tile>{{ env.keySave }}</v-btn>
            </v-col>

            <v-col cols="12" sm="6" md="6" class="d-flex justify-md-end">
              <v-btn @click="clear"
                     :disabled="!group.id"
                     :color="colorClear"
                     class="ml-3" tile>{{ env.keyClear }}</v-btn>
            </v-col>
          </v-card-actions>

        </v-form>

      </div>

  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import env from '../../env.config.json'
// import { bus } from "@/utils/bus"

export default {
  name: 'AddComponent',
  props: ['selectedItem'],
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
      group: [
        { id: 1, name: 'Test1' },
        { id: 2, name: 'Test2' },
        { id: 3, name: 'Test3' }
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
    this.select = this.selectedItem

    // bus.$on('selected-item', data => {
    //   this.select = data
    //   console.log(data)
    // })


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
          id: this.group.id
        }
      }


      if (this.name && this.group && this.dimension && this.price) {


        if (this.select === this.env.sectionDevice) {
          this.addDevice(component)
        } else if (this.select === this.env.sectionMaterial) {
          this.addMaterial(component)
        } else {
          this.addWork(component)
        }

        this.colorSave = 'primary'
        this.colorClear = 'primary'
        this.$refs.form.resetValidation()
        this.name = ''
        this.group = [
          { id: 1, name: 'Test1' },
          { id: 2, name: 'Test2' },
          { id: 3, name: 'Test3' }
        ]
        this.dimension = ''
        this.price = ''
        this.comment = ''

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
      this.group = [
        { id: 1, name: 'Test1' },
        { id: 2, name: 'Test2' },
        { id: 3, name: 'Test3' }
      ]
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