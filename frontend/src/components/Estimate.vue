<template>
  <v-dialog v-if="profile" v-model="dialog" width="90%" scrollable persistent @input="drag()">
    <template v-slot:activator="{ on }" class="">
      <v-btn icon v-on="on" class="my-1" tile>
        <v-icon dark>mdi-calculator</v-icon>
      </v-btn>
    </template>

    <v-card class="rounded-0">
      <v-card-title class="form draggable">
        <div class="font-xl color">{{formTitle}}</div>
        <v-checkbox v-model="ex"
                    @change="checkTypeEstimate"
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
          <div class="id mt-3">#: {{ order.id }}</div>
          <div class="ext-id">{{ env.request }}: {{ obj.extId ? obj.extId + '-' + order.extId : order.extId }}</div>
          <div class="address">{{ env.address }}: {{ order.address }}</div>
          <div class="">{{ env.customer }}: {{ order.customer }}</div>
          <div class="">{{ env.owner }}: {{ profile.lastName }} {{ profile.firstName}} {{ date }}</div>
          <div>
          </div>
        </div>
        <!-- загружаем модули -->
        <WorkDescription @transmit="transmitDescription"
                    :descriptionFromDb="this.obj.workDescription"
                    :ex="ex"/>
        <Device @transmit="transmitDevices"
                :deviseFromDb="this.obj.devices"/>
        <Materials @transmit="transmitMaterials"
                   :materialsFromDb="this.obj.materials"/>
        <Work @transmit="transmitWorks"
              :worksFromDb="this.obj.works"/>
        <!-- // загружаем модули -->

        <!-- диалоговое окно выбора сметы -->
        <EstimateChose :visibleEstimateChose="visibleEstimateChose" :estimates="estimates" @chose="chose" @visibleEstimateChose="estimateChose"/>
        <!-- диалоговое окно ввода номера, если самостоятельно переключаем тип сметы -->
        <ExtId :visibleExtId="visibleExtId" @sendValue="sendValue" @visibleExtId="extId"/>
      </v-card-text>

      <template>
        <v-progress-linear height="3" color="primary" :indeterminate="loading"></v-progress-linear>
        <div class="ml-6 mt-3 mb-3">
          <div class="btn-container">
            <div>
              <v-btn medium @click="create" color="primary" height="35" tile small outlined>
                <v-icon>mdi-content-save</v-icon>&nbsp;{{ env.keyMakeEstimate }}
              </v-btn>
              <Preview :preview="preview"/>
              <v-btn medium @click="download" :disabled="!active" class="ml-5" color="primary" height="35" tile small outlined>
                <v-icon color="green">mdi-microsoft-excel</v-icon>&nbsp;Выгрузить смету в Excel
              </v-btn>
              <v-btn medium @click="download" class="ml-5" color="primary" height="35" tile small outlined disabled>
                <v-icon color="green">mdi-file-pdf-box</v-icon>&nbsp;Выгрузить смету в PDF
              </v-btn>
            </div>
            <v-spacer></v-spacer>
            <v-btn class="mr-6" @click="get" color="primary" height="35" tile small outlined>
              <v-icon color="red">mdi-tray-arrow-up</v-icon>&nbsp;{{ env.load }}
            </v-btn>
          </div>
        </div>
      </template>
    </v-card>
  </v-dialog>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import api from '@/api/backendApi'
import env from '../../env.config.json'
import WorkDescription from './WorkDescription'
import Device from './Device'
import Materials from './Materials'
import Work from './Work'
import Preview from './Preview'
import {drag} from '@/utils/draggable'
import {encode} from '@/utils/encode'
const ExtId = () => import('@/components/ExtId')
const EstimateChose = () => import('@/components/EstimateChose')
const Component = () => import('@/components/Component')
const pause = ms => new Promise(resolve => setTimeout(resolve, ms))


export default {
  name: 'Estimate',
  props: ['order'],
  components: {ExtId, WorkDescription, Device, Materials, Work, Preview, EstimateChose, Component},
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
        extId: this.order.extId,
        address: this.order.address,
        customer: this.order.customer,
        owner: this.profile.lastName + ' ' + this.profile.firstName + ' ' + this.date,
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
      loading: false,
      obj: {},
      estimates: [],
      visibleEstimateChose: false,
      visibleExtId: false,
      active: false
    }
  },
  methods: {
    drag,
    ...mapActions(['fetchDevices','addEstimate', 'showSnack', 'loadEstimate', 'fetchOrders']),
    async get() {
      // если есть предыдущие сметы то загружаем их
      const data = {
        customer: this.order.customer.trim(),
        address: this.order.address.trim()
      }
      await this.loadEstimate(data)

      if (this.getEstimate.length === 1) {
        this.obj = Object.assign(...this.getEstimate)
        this.ex = false
        this.order.extId = this.order.extId.split('-')[0]
      } else if (this.getEstimate.length > 1) {
        this.estimates = this.getEstimate
        this.visibleEstimateChose = true
      }
    },
    close() {
      this.dialog = false

      // без этого не хотело чиститься
      this.getEstimate.length = 0

      this.obj.id = null
      this.obj.extId = ''
      this.obj.customer = ''
      this.obj.address = ''
      this.obj.workDescription = ''
      this.obj.comment = ''
      this.obj.devices = []
      this.obj.materials = []
      this.obj.works = []

      this.ex = true
      this.active = false //
    },
    transmitDescription(description) {
      this.workDescription = description
    },

    // transmitObject(data) {
    //   this.devices = data
    //   this.materials = data
    //   this.works = data
    // },

    transmitDevices(dev) {
      this.devices = dev
    },
    transmitMaterials(mat) {
      this.materials = mat
    },
    transmitWorks(works) {
      this.works = works
    },
    chose(value) {
      this.obj = value
      this.ex = false
      this.order.extId = this.order.extId.split('-')[0]
    },
    estimateChose(value) {
      this.visibleEstimateChose = value
    },
    extId(value) {
      this.visibleExtId = value
    },
    checkTypeEstimate() {
      if (!this.ex) {
        this.visibleExtId = true
      } else {
        this.obj.extId = ''
        this.order.extId = this.order.extId.split('-')[0]
      }
    },
    sendValue(value) {
      this.order.extId = this.order.extId + '-' + value
    },
    async create() {
      const extId = this.obj.extId ? this.obj.extId + '-' + this.order.extId : this.order.extId
      this.keyEstimate = this.ex ? this.key = 'ПРЕДВАРИТЕЛЬНАЯ' : this.key = 'ФАКТИЧЕСКАЯ'
      // this.fileName = this.order.address + ' ' + this.order.customer + ' ' + extId + ' ' + this.keyEstimate + '.xlsx'
      // this.fileName = this.fileName.replace(/[/\\?,%*:|"+<>]/g, ' ')

      // создаем объект для отправки на сервер
      this.estimate = {
        key: this.keyEstimate,
        order: {
          id: this.order.id
        },
        extId: extId,
        address: this.order.address,
        customer: this.order.customer,
        owner: this.profile.lastName + ' ' + this.date,
        workDescription: this.workDescription.workDescription,
        simpleText: this.workDescription.comment,
        devices: this.devices,
        materials: this.materials,
        works: this.works
      }

      // сохраняем в БД
      await this.addEstimate(this.estimate).then(() => {
        const data = {
          message: 'Смета - "' + this.order.address + ' ' + this.order.customer + ' ' + extId + ' ' + this.keyEstimate + '" сохранена.',
          color: 'success',
          icon: 'mdi-check-circle'
        }
        this.showSnack(data)
        this.active = true // включаем кнопку
        console.info(data.message)
      })
    },

    // выгружаем файл после сохранения в БД
    async download() {
      const extId = this.obj.extId ? this.obj.extId + '-' + this.order.extId : this.order.extId
      const id = encode(extId)
      const customer = encode(this.order.customer)
      const address = encode(this.order.address)

      this.loading = true

      // на всякий случай
      await pause(2000)

      await fetch(api.API_GET_FILE + '?extId=' + id + '&customer=' + customer + '&address=' + address, {
        method: 'GET',
      }).then(response => {
        const filename = decodeURIComponent(response.headers.get('Content-disposition').split('=UTF-8\'\'')[1])
        response.blob().then((blob) => {

          // проверяем существует ли файл
          if (blob.size !== 0 && response.ok) {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = filename
            document.body.appendChild(a);
            a.click();

            const data = {
              message: 'Файл - "' + filename + '" скачивается...',
              color: 'success',
              icon: 'mdi-check-circle'
            }
            this.showSnack(data)

            console.info(data.message)
            a.remove();  // afterwards we remove the element again
            this.loading = false // останавливаем анимацию
            this.active = false // отключаем кнопку

          } else {
            const data = {
              message: 'Ошибка, файл - "' + filename + '" не скачать.',
              color: 'error',
              icon: 'mdi-alert-circle'
            }
            this.showSnack(data)
            this.loading = false // останавливаем анимацию
            this.active = false // отключаем кнопку
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.btn-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}


</style>