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
                  dense
                  class="pt-5"
                  :label="env.dimension + '*'"
                  v-model="dimension"
                  required
                  counter/>
            </v-col>

            <v-col cols="2" sm="1" md="1">
              <v-text-field
                  return-masked-value
                  :disabled="!select"
                  :rules="rulesPrice"
                  mask="### ### ###.##"
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

          <v-btn v-on:click="saveUser" color="primary" class="ml-3" tile>{{ env.keySave }}</v-btn>
        </v-form>

      </div>

  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import env from "../../env.config.json";

export default {
  name: "AddComponent",
  computed: {
    ...mapGetters(['profile']),

  },

  data() {
    return {
      env,
      valid: true,
      show: false,

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

  // :rules="{
  //   required: true,
  //       digits: 7,
  //       regex: '^(71|72|74|76|81|82|84|85|86|87|88|89)\\d{5}$'
  // }"

    }
  },
  methods: {

    ...mapActions(['addDevice', 'addMaterial', 'addWork', 'showSnack']),

    saveUser() {

      const component = {
        name: this.name,
        dimension: this.dimension,
        price: this.price,
        note: this.comment,
      }


      console.log(this.select)

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


    }

  }
}
</script>

<style scoped>

</style>