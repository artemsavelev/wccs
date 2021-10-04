<template>
  <div>
    <v-card class="ma-5 pt-1" elevation="0" width="600" tile outlined>
      <v-row justify="start" class="ml-13">
        <v-switch
            @change="change"
            inset
            v-model="switch1"
            :label="nameKey"
            persistent-hint>
        </v-switch>
      </v-row>
    </v-card>

    <v-card class="ma-5 pt-5 pb-5" elevation="0" width="600" tile outlined>
      <div class="snack-prop pt-0 pb-0 mb-0">
        <v-row justify="start" class="ml-13">
          <div class="mb-2 font-m">{{ env.snackProp }}</div>
        </v-row>
        <v-row justify="center">

          <v-col cols="12" sm="6" md="5">
            <v-autocomplete
                dense
                outlined
                :items="y"
                item-text="text"
                item-value="name"
                class="rounded-0 styled-input font-s"
                :label="env.snackPositionVertical"
                v-model="y.name">
            </v-autocomplete>
          </v-col>

          <v-col cols="12" sm="6" md="5">
            <v-autocomplete
                dense
                outlined
                :items="x"
                item-text="text"
                item-value="name"
                class="rounded-0 styled-input font-s"
                :label="env.snackPositionHorizontal"
                v-model="x.name">
            </v-autocomplete>
          </v-col>
        </v-row>

        <v-row justify="center">
          <v-col cols="12" sm="6" md="5">
            <v-text-field
                dense
                outlined
                class="rounded-0 styled-input font-s"
                :label="env.snackWidth"
                v-model="height"/>
          </v-col>

          <v-col cols="12" sm="6" md="5">
            <v-text-field
                dense
                outlined
                class="rounded-0 styled-input font-s"
                :label="env.snackHeight"
                v-model="width"/>
          </v-col>
        </v-row>

        <v-row justify="center">
          <v-col cols="12" sm="6" md="10">
            <v-textarea outlined
                        :auto-grow="true"
                        rows="3"
                        class="rounded-0 styled-input font-s"
                        name="input-2"
                        :label="env.snackTestText"
                        v-model="message"></v-textarea>
          </v-col>
        </v-row>

        <v-row justify="center">
          <v-col cols="12" sm="6" md="5">
          <v-switch
              class="styled-switch mt-1 mb-0"
              inset
              v-model="outlined"
              :label="env.outlined">
          </v-switch>
          </v-col>

          <v-col cols="12" sm="6" md="5">
            <v-text-field
                dense
                outlined
                class="rounded-0 styled-input font-s"
                :label="env.duration"
                v-model="duration"/>
          </v-col>
        </v-row>
        <v-row justify="start" class="ml-13">
          <v-btn v-on:click="save" tile height="35" class="mr-6" color="primary" small outlined>{{ env.keySave }}</v-btn>
          <v-btn v-on:click="preview" tile height="35" color="primary" small outlined>{{ env.keyPreview }}</v-btn>
        </v-row>
      </div>
    </v-card>

  </div>
</template>

<script>

import env from '../../env.config.json'
import {mapActions} from 'vuex'

export default {
  name: 'Settings',
  computed: {
  },
  data: () => ({
    env,
    y: env.y,
    x: env.x,
    height: 300,
    width: 700,
    message: env.preliminary + env.priceTimeout,
    outlined: false,
    duration: 6000,
    switch1: JSON.parse(localStorage.getItem('darkTheme')),
    nameKey: JSON.parse(localStorage.getItem('darkTheme')) ? 'Get Light Theme' : 'Get Dark Theme',
  }),
  updated() {
    this.nameKey = JSON.parse(localStorage.getItem('darkTheme')) ? 'Get Light Theme' : 'Get Dark Theme'
  },
  mounted() {
    if (localStorage.getItem('snackProp')) {
      const getSnackProp = JSON.parse(localStorage.getItem('snackProp'))
      // console.log(getSnackProp)
      // this.x = getSnackProp.x
      // this.y = getSnackProp.y
      this.height = getSnackProp.h
      this.width = getSnackProp.w
      this.outlined = getSnackProp.outlined
      this.duration = getSnackProp.duration
    }
  },
  methods: {
    ...mapActions(['showSnack']),
    change() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
      localStorage.setItem('darkTheme', this.$vuetify.theme.dark.toString())
    },
    save() {
      const snackProp = {
        x: {name: this.x.name, text: this.x.text},
        y: {name: this.y.name, text: this.y.text},
        h: +this.height,
        w: +this.width,
        outlined: this.outlined,
        duration: this.duration,
      }
      localStorage.setItem('snackProp', JSON.stringify(snackProp))
    },

    preview() {
      const data = {
        message: this.message,
        color: 'info',
        icon: 'mdi-check-circle'
      }
      this.showSnack(data)
    }

  }
}

</script>

<style scoped>

</style>