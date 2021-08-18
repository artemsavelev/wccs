<template>
  <v-dialog v-model="dialog" width="50%" scrollable persistent @input="drag()" class="">
    <template v-slot:activator="{ on }" class="mt-1">
      <v-btn v-on="on" class="mt-10 red" color="white" tile small>{{ env.keyChange }}</v-btn>
    </template>
    <v-card class="rounded-0">

      <v-card-title class="form draggable">
        <span class="font-xl">{{formTitle}}</span>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text class="scroll">
        <v-row align="start">
          <v-col cols="12" sm="4" md="7">
            <!--- with some props ---->
            <avatars class="border"
                     :isCircle="isCircle"
                     :circleColor="circleColor"
                     :skinColor="skinColor"
                     :facialHairType="facialHairType"
                     :facialHairColor="facialHairColor"
                     :eyebrowType="eyebrowType"
                     :eyeType="eyeType"
                     :mouthType="mouthType"
                     :hairColor="hairColor"
                     :accessoriesType="accessoriesType"
                     :topType="topType"
                     :topColor="topColor"
                     :clotheType="clotheType"
                     :clotheColor="clotheColor"
                     :graphicType="graphicType">
            </avatars>

            <v-radio-group row v-model="data1" @change="change" class="ml-7">
              <v-radio label="С фоном" :value="true"></v-radio>
              <v-radio label="Прозрачный фон" :value="false"></v-radio>
            </v-radio-group>

            <v-color-picker
                v-show="data1"
                class="ml-7"
                @input="getColor"
                show-swatches
            ></v-color-picker>
          </v-col>

          <v-col cols="12" sm="4" md="5" class="mt-11">
            <v-autocomplete
                @change="changeSkinColor"
                class="rounded-0 styled-input font-s"
                :items="avatar.skinColor"
                :label="'Выбрать цвет кожи'"
                v-model="avatar.skinColor.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeTopType"
                class="rounded-0 styled-input font-s"
                :items="avatar.topType"
                :label="'Выбрать волосы или головной убор'"
                v-model="avatar.topType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeTopColor"
                class="rounded-0 styled-input font-s"
                :items="avatar.topColor"
                :label="'Выбрать цвет головного убора'"
                v-model="avatar.topColor.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeHairColor"
                class="rounded-0 styled-input font-s scroll"
                :items="avatar.hairColor"
                :label="'Выбрать цвет волос'"
                v-model="avatar.hairColor.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeEyebrowType"
                class="rounded-0 styled-input font-s"
                :items="avatar.eyebrowType"
                :label="'Выбрать брови'"
                v-model="avatar.eyebrowType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeEyeType"
                class="rounded-0 styled-input font-s"
                :items="avatar.eyeType"
                :label="'Выбрать глаза'"
                v-model="avatar.eyeType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeMouthType"
                class="rounded-0" styled-input font-s
                :items="avatar.mouthType"
                :label="'Выбрать рот'"
                v-model="avatar.mouthType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeFacialHairType"
                class="rounded-0 styled-input font-s"
                :items="avatar.facialHairType"
                :label="'Выбрать бороду'"
                v-model="avatar.facialHairType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeFacialHairColor"
                class="rounded-0 styled-input font-s"
                :items="avatar.facialHairColor"
                :label="'Выбрать цвет бороды'"
                v-model="avatar.facialHairColor.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeAccessoriesType"
                class="rounded-0 styled-input font-s"
                :items="avatar.accessoriesType"
                :label="'Выбрать очки'"
                v-model="avatar.accessoriesType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeClotheType"
                class="rounded-0 styled-input font-s"
                :items="avatar.clotheType"
                :label="'Выбрать одежду'"
                v-model="avatar.clotheType.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeClotheColor"
                class="rounded-0 styled-input font-s"
                :items="avatar.clotheColor"
                :label="'Выбрать цвет одежды'"
                v-model="avatar.clotheColor.name"
                outlined
                dense
            ></v-autocomplete>

            <v-autocomplete
                @change="changeGraphicType"
                class="rounded-0 styled-input font-s"
                :items="avatar.graphicType"
                :label="'Выбрать надпись на одежде (GraphicShirt)'"
                v-model="avatar.graphicType.name"
                outlined
                dense
            ></v-autocomplete>
          </v-col>
        </v-row>
        <v-btn medium v-on:click="save" color="primary" class="mt-5 ml-7" tile outlined>{{ env.keySave }}</v-btn>
        <v-btn medium v-on:click="clear" color="primary" class="mt-5 ml-7" tile outlined>{{ env.keyClear }}</v-btn>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>

import avatars from 'vuejs-avataaars'
import env from "../../env.config.json"
import avatar from "../utils/avatar"
import {mapActions} from "vuex"
import {drag} from '@/utils/draggable'

export default {
  name: "ChangeAvatar",
  components: {
    avatars
  },

  computed: {
    formTitle() {
      return 'Сменить аватар'
    },
  },

  data: () => ({
    env,
    avatar,
    dialog: false,
    skinColor: '',
    eyebrowType: '',
    eyeType: '',
    mouthType: '',
    facialHairType: '',
    facialHairColor: '',
    hairColor: '',
    topType: '',
    topColor: '',
    accessoriesType: '',
    clotheType: '',
    clotheColor: '',
    graphicType: '',
    isCircle: true,
    circleColor: '',
    data1: true
  }),

  methods: {
    drag,
    ...mapActions(['showSnack']),
    close() {
      this.dialog = false
    },

    save() {
      const avatar = {
        skinColor: this.skinColor,
        eyebrowType: this.eyebrowType,
        eyeType: this.eyeType,
        mouthType: this.mouthType,
        facialHairType: this.facialHairType,
        facialHairColor: this.facialHairColor,
        hairColor: this.hairColor,
        topType: this.topType,
        topColor: this.topColor,
        accessoriesType: this.accessoriesType,
        clotheType: this.clotheType,
        clotheColor: this.clotheColor,
        graphicType: this.graphicType,
        isCircle: this.isCircle,
        circleColor: this.circleColor,
      }

      localStorage.setItem('avatar', JSON.stringify(avatar))
      const data = {
        message: 'Аватар создан.',
        color: 'success',
        icon: 'mdi-check-circle'
      }
      this.showSnack(data)
    },

    clear() {
      this.skinColor = ''
      this.eyebrowType = ''
      this.eyeType = ''
      this.mouthType = ''
      this.facialHairType = ''
      this.facialHairColor = ''
      this.hairColor = ''
      this.topType = ''
      this.topColor = ''
      this.accessoriesType = ''
      this.clotheType = ''
      this.clotheColor = ''
      this.graphicType = ''
      this.isCircle = ''
      this.circleColor = ''

      const avatar = {
        skinColor: this.skinColor,
        eyebrowType: this.eyebrowType,
        eyeType: this.eyeType,
        mouthType: this.mouthType,
        facialHairType: this.facialHairType,
        facialHairColor: this.facialHairColor,
        hairColor: this.hairColor,
        topType: this.topType,
        topColor: this.topColor,
        accessoriesType: this.accessoriesType,
        clotheType: this.clotheType,
        clotheColor: this.clotheColor,
        graphicType: this.graphicType,
        isCircle: this.isCircle,
        circleColor: this.circleColor,
      }

      localStorage.setItem('avatar', JSON.stringify(avatar))
      const data = {
        message: 'Аватар создан.',
        color: 'success',
        icon: 'mdi-check-circle'
      }
      this.showSnack(data)

    },


    change(item) {
      this.isCircle = item
    },

    getColor(color) {
      this.circleColor = color.hex
    },
    changeSkinColor(item) {
      this.skinColor = item
    },
    changeClotheType(item) {
      this.clotheType = item
    },
    changeClotheColor(item) {
      this.clotheColor = item
    },
    changeAccessoriesType(item) {
      this.accessoriesType = item
    },
    changeEyebrowType(item) {
      this.eyebrowType = item
    },
    changeEyeType(item) {
      this.eyeType = item
    },
    changeMouthType(item) {
      this.mouthType = item
    },
    changeHairColor(item) {
      this.hairColor = item
    },
    changeFacialHairType(item) {
      this.facialHairType = item
    },
    changeFacialHairColor(item) {
      this.facialHairColor = item
    },
    changeGraphicType(item) {
      this.graphicType = item
    },

    changeTopType(item) {
      this.topType = item
    },
    changeTopColor(item) {
      this.topColor = item
    },

  },

  mounted() {

    if (localStorage.getItem('avatar')) {
      const ava = JSON.parse(localStorage.getItem('avatar'))


      this.skinColor = ava.skinColor
      this.eyebrowType = ava.eyebrowType
      this.eyeType = ava.eyeType
      this.mouthType = ava.mouthType
      this.facialHairType = ava.facialHairType
      this.facialHairColor = ava.facialHairColor
      this.hairColor = ava.hairColor
      this.topType = ava.topType
      this.topColor = ava.topColor
      this.accessoriesType = ava.accessoriesType
      this.clotheType = ava.clotheType
      this.clotheColor = ava.clotheColor
      this.graphicType = ava.graphicType
      this.isCircle = ava.isCircle
      this.circleColor = ava.circleColor
    }
  }





}
</script>

<style scoped lang="scss">
.border {
  /*border: 1px solid black;*/
  width: 350px;
  height: 350px;

}

</style>