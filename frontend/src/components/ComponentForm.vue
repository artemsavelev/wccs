<template>
  <div>

      <div class="ml-0 mb-15 mt-3 font-s" v-if="!mini">

        <div class="font-weight-bold ml-3">
          {{ formTitle }}
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
                :items="allGroups"
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
            <v-textarea
                class="rounded-0"
                :disabled="!group.id"
                :rules="rules"
                :hint="env.rules[1]"
                :label="env.name + '*'"
                :value="name"
                v-model="name"
                required
                outlined
                dense
                counter
                rows="3"
            ></v-textarea>
          </v-col>

          <v-row class="ml-0 mr-0 mt-0 mb-1">

            <v-col cols="12" sm="2" md="6">
              <v-autocomplete
                  class="rounded-0"
                  :disabled="!group.id"
                  :rules="rules"
                  :hint="env.rules[1]"
                  :items="dimension"
                  item-text="name"
                  item-value="name"
                  v-model="dimension.name"
                  :label="env.dimension + '*'"
                  required
                  outlined
                  dense
                  counter
              ></v-autocomplete>
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
            <v-textarea
                class="rounded-0"
                :disabled="!group.id"
                :label="env.comment"
                :value="comment"
                v-model="comment"
                required
                outlined
                dense
                counter
                rows="10"
            ></v-textarea>
          </v-col>


          <v-card-actions class="pa-0">

            <v-col cols="12" sm="6" md="6">
              <v-btn @click="saveComponent"
                     :disabled="!group.id"
                     :color="colorSave"
                     height="35"
                     tile small outlined>{{ env.keySave }}</v-btn>
            </v-col>

            <v-col cols="12" sm="6" md="6" class="d-flex justify-md-end">
              <v-btn @click="clear"
                     :disabled="!group.id"
                     :color="colorClear"
                     class="ml-3" height="35" tile small outlined>{{ env.keyClear }}</v-btn>
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
  name: 'ComponentForm',
  props: ['selectedItem', 'editComponent', 'mini'],
  computed: {
    ...mapGetters(['profile', 'allGroups']),
    formTitle() {
      return this.editedIndex === -1 ? env.addComponent : env.editComponent + ' # ' + this.id
    },
  },

  mounted() {
    // fetch section group from backend
    this.fetchGroup()
  },

  data() {
    return {
      ...mapActions(['fetchGroup']),
      env,
      valid: true,
      show: false,
      colorSave: 'primary',
      colorClear: 'primary',
      editedIndex: -1,

      id: '',
      select: '',
      name: '',
      dimension: ['м.', 'шт.', 'порт', 'м2.', 'смена', 'ед.'],
      price: '',
      comment: '',
      // items: [
      //   { key: env.sectionDevice },
      //   { key: env.sectionMaterial },
      //   { key: env.sectionWork }
      // ],
      group: [],

      rules: [
        value => !!value || env.rules[0],
        value => (value && value.length >= 2) || env.rules[1],
      ],
      rulesPrice: [
        value => !!value || env.rules[0],
        value => (value && value.length >= 2) || env.rules[1],
      ],

    }
  },

  watch: {
    editComponent(newVal) {
      this.editedIndex = 1
      this.id = newVal.id
      this.group.id = newVal.group.id
      this.name = newVal.name
      this.dimension.name = newVal.dimension
      this.price = newVal.price
      this.comment = newVal.note
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
    ...mapActions([
      'addDevice',
      'addMaterial',
      'addWork',
      'showSnack',
      'updateDevice',
      'updateMaterial',
      'updateWork'
    ]),

    saveComponent() {

      const component = {
        id: this.id,
        name: this.name,
        dimension: this.dimension.name,
        price: this.price,
        note: this.comment,
        group: {
          id: this.group.id,
          name: this.group.name
        }
      }

      if (this.name && this.group && this.dimension && this.price) {

        if (this.select === this.env.sectionDevice) {

          if (this.id) {
            this.updateDevice(component)
          } else {
            this.addDevice(component)
          }

        } else if (this.select === this.env.sectionMaterial) {

          if (this.id) {
            this.updateMaterial(component)
          } else {
            this.addMaterial(component)
          }

        } else {

          if (this.id) {
            this.updateWork(component)
          } else {
            this.addWork(component)
          }

        }

        this.id = ''
        this.editedIndex = -1
        this.colorSave = 'primary'
        this.colorClear = 'primary'
        this.$refs.form.resetValidation()
        this.name = ''
        this.group = [
          { id: 1, name: 'Test1' },
        ]
        this.dimension = ['м.', 'шт.', 'порт.', 'м2.', 'смена.', 'ед.']
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
      this.editedIndex = -1
      this.colorSave = 'primary'
      this.colorClear = 'primary'
      this.$refs.form.resetValidation()
      this.name = ''
      this.group = [
        { id: 1, name: 'Test1' },
      ]
      this.dimension = ['м.', 'шт.', 'порт.', 'м2.', 'смена.', 'ед.']
      this.price = ''
      this.comment = ''
    },

  }
}
</script>

<style scoped lang="scss">
.v-text-field input {
  font-size: 8px;
}

$font-size-root: 8px;
</style>