<template>
  <v-dialog v-if="profile" v-model="dialog" width="90%" scrollable persistent @input="drag()">
    <template v-slot:activator="{ on }" class="">
      <v-btn icon v-on="on" class="my-1" @click="get" tile>
        <v-icon dark>mdi-calculator</v-icon>
      </v-btn>
    </template>

    <v-card class="rounded-0">
      <v-card-title class="form draggable">
        <div class="font-xl color">{{formTitle}}</div>
        <v-checkbox v-model="ex"
                    class="pt-0 pb-0 mt-0 mb-0 ml-5"
                    color="success"
                    :label="env.estimate + ' ' + env.estimatePreliminary"
                    dense
                    hide-details></v-checkbox>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>mdi-window-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text class="scroll">

        <div class="font-s">
          <div class="ext-id">
            {{ env.request }}: {{ extId }}
          </div>
          <div class="address">
            {{ env.address }}: {{ address }}
          </div>
          <div class="">
            {{ env.customer }}: {{ customer }}
          </div>
          <div class="">
            {{ env.owner }}: {{ profile.lastName }} {{ profile.firstName}}
            {{ date }}
          </div>
          <div>
          </div>
        </div>

          <!-- загружаем модули -->
        <TypeOfWork v-on:transmit="transmitDescription"
                    :descriptionFromDb="getEstimate.workDescription"
                    v-bind:ex="ex"/>
        <Device v-on:transmit="transmitDevices"
                :deviseFromDb="getEstimate.devices"
                v-bind:extId="extId"/>
        <Materials v-on:transmit="transmitMaterials"
                   :materialsFromDb="getEstimate.materials"/>
        <Work v-on:transmit="transmitWorks"
              :worksFromDb="getEstimate.works"/>
          <!-- // загружаем модули -->


      </v-card-text>

      <template>
        <v-progress-linear height="3"
                           color="primary"
                           :indeterminate="loading">
        </v-progress-linear>

        <div class="ml-4 mb-1">
          <v-card-actions class="">
            <v-btn medium v-on:click="create" color="primary" height="35" tile small outlined>{{ env.keyMakeEstimate }}</v-btn>
            <Preview :preview="preview"/>
          </v-card-actions>
        </div>
      </template>
    </v-card>

  </v-dialog>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import api from '@/api/backendApi'
import env from '../../env.config.json'
import TypeOfWork from './TypeOfWork'
import Device from './Device'
import Materials from './Materials'
import Work from './Work'
import Preview from './Preview'
import {drag} from '@/utils/draggable'


const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: 'Estimate',
  props: ['extId', 'address', 'customer'],
  components: {TypeOfWork, Device, Materials, Work, Preview },
  computed: {
    ...mapGetters(['profile', 'getEstimate']),
    preview() {
      const subSumDevice = this.devices.reduce((total, device) => total + device.quantity * device.price, 0)
      const subSumMaterial = this.materials.reduce((total, material) => total + material.quantity * material.price, 0)
      const subSumWork = this.works.reduce((total, work) => total + work.quantity * work.price, 0)
      const totalSum = subSumDevice + subSumMaterial + subSumWork

      const key = this.ex ? 'ПРЕДВАРИТЕЛЬНАЯ' : 'ФАКТИЧЕСКАЯ'
      return {
        key: key,
        extId: this.extId,
        address: this.address,
        customer: this.customer,
        owner: this.profile.lastName + ' ' + this.profile.firstName + ' ' +this.date,
        workDescription: this.workDescription.workDescription,
        comment: this.workDescription.comment,
        devices: this.devices,
        materials: this.materials,
        works: this.works,
        subSumDevice: subSumDevice,
        subSumMaterial: subSumMaterial,
        subSumWork: subSumWork,
        totalSum: totalSum
      }
    },
  },

  data() {
    return {
      env,
      dialog: false,
      formTitle: env.title[1],
      ex: true,
      key: '',
      keyEstimate: '',
      date: new Date().toISOString().substr(0, 10) + ' ' +
          new Date().toTimeString().substr(0, 8),
      estimate: {},
      workDescription: {},
      devices: [],
      materials: [],
      works: [],
      fileName: '',
      loading: false
    }
  },

  watch: {

  },

  mounted() {
    // this.loadEstimate(6)
  },
  updated() {
  },

  methods: {
    drag,
    ...mapActions(['addEstimate', 'fetchEstimate', 'showSnack', 'loadEstimate']),
    close() {
      this.dialog = false
    },

    transmitDescription(description) {
      this.workDescription = description
    },

    transmitDevices(dev) {
      this.devices = dev
    },

    transmitMaterials(mat) {
      this.materials = mat
    },

    transmitWorks(works) {
      this.works = works
    },

    async create() {
      this.keyEstimate = this.ex ? this.key = 'ПРЕДВАРИТЕЛЬНАЯ' : this.key = 'ФАКТИЧЕСКАЯ'
      this.fileName = this.address + ' ' + this.customer + ' ' + this.extId + ' ' + this.keyEstimate + '.xlsx'
      this.fileName = this.fileName.replace(/[/\\?,%*:|"<>]/g, ' ')

      // создаем объект для отправки на сервер
      this.estimate = {
        key: this.keyEstimate,
        extId: this.extId,
        address: this.address,
        customer: this.customer,
        owner: this.profile.lastName + ' ' + this.date,
        workDescription: this.workDescription.workDescription,
        simpleText: this.workDescription.comment,
        devices: this.devices,
        materials: this.materials,
        works: this.works
      }

      // отправляем данные на сервер через store
      await this.addEstimate(this.estimate).then(() => {
        this.loading = true

        const data = {
          message: 'Файл с именем - "' + this.fileName + '" создан.',
          color: 'success',
          icon: 'mdi-check-circle'
        }
        this.showSnack(data)
      })

      // проверяем и загружаем файл
      await pause(2000)
      await fetch(api.API_GET_FILE + this.fileName, {
        method: 'GET',
      }).then(response => {

        // const file = response.url.split('/')
        // const estimateFileName = decodeURI(file[file.length - 1])
        response.blob()
            .then((blob) => {

              // проверяем существует ли файл
              if (blob.size !== 0 && response.ok) {

                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = this.fileName
                document.body.appendChild(a);
                a.click();
                a.remove();  //afterwards we remove the element again
                this.loading = false // останавливаем анимацию

              } else {
                const data = {
                  message: 'Ошибка, файл - "' + this.fileName + '" не скачать.',
                  color: 'error',
                  icon: 'mdi-alert-circle'
                }
                this.showSnack(data)
                this.loading = false // останавливаем анимацию
              }
            })
      })
    },

    async get() {
      // this.keyEstimate = this.ex ? this.key = 'ПРЕДВАРИТЕЛЬНАЯ' : this.key = 'ФАКТИЧЕСКАЯ'
      // this.fileName = this.address + ' ' + this.customer + ' ' + this.extId + ' ' + this.keyEstimate + '.xlsx'
      // this.fileName = this.fileName.replace(/[/\\?,%*:|"<>]/g, ' ')
      //
      // await fetch(api.API_CREATE_ESTIMATE_TEST_URL, {
      //   method: 'POST',
      //
      // }).then(response => {
      //   console.log(response)
      //   return response.text();
      // }).then(data => console.log(data))

      // this.loadEstimate(6)
      // console.log(this.getEstimate.id)
      // console.log(this.getEstimate.extId)
      // console.log(this.getEstimate.customer)
      // console.log(this.getEstimate.address)
      // console.log(this.getEstimate.workDescription)
      // console.log(this.getEstimate.devices)
      // console.log(this.getEstimate.materials)
      // console.log(this.getEstimate.works)
    },
  }
}
</script>

<style lang="scss">


</style>