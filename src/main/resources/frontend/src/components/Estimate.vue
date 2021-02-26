<template>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent>
    <template v-slot:activator="{ on }" class="">
      <v-btn icon v-on="on" class="" @click="get">
        <v-icon>mdi-calculator</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="form">
        <div class="font-xl">{{formTitle}}</div>
        <v-checkbox v-model="ex"
                    class="pt-0 pb-0 mt-0 mb-0 ml-5"
                    color="success"
                    label="смета предварительная"
                    hide-details></v-checkbox>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close" aria-label="">
          <v-icon>mdi-window-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text>

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
                    v-bind:ex="ex"/>
        <Device v-on:transmit="transmitDevices"
                v-bind:extId="extId"/>
        <Materials v-on:transmit="transmitMaterials"/>
        <Work v-on:transmit="transmitWorks"/>
          <!-- // загружаем модули -->

      </v-card-text>

      <template>
        <v-progress-linear height="3"
                           color="primary"
                           :indeterminate="loading">
        </v-progress-linear>
        <div class="ml-4 mb-1">
          <v-card-actions class="">
            <v-btn medium v-on:click="create" color="primary" tile>{{ env.keyMakeEstimate }}</v-btn>
            <Preview :preview="preview"/>
          </v-card-actions>
        </div>
      </template>

    </v-card>

  </v-dialog>
</template>

<script>
import {mapActions, mapGetters} from "vuex"
import api from "@/api/backendApi"
import env from "../../env.config.json"
const TypeOfWork = () => import('./TypeOfWork')
const Device = () => import('./Device')
const Materials = () => import('./Materials')
const Work = () => import('./Work')
const Preview = () => import('@/components/Preview')


// const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: "Estimate",
  props: ['extId', 'address', 'customer'],
  components: { TypeOfWork, Device, Materials, Work, Preview },
  computed: {
    ...mapGetters(['profile']),
    preview() {
      return {
        // keyEstimate: this.ex ? this.key = 'ФАКТИЧЕСКАЯ' : this.key = 'ПРЕДВАРИТЕЛЬНАЯ',
        extId: this.extId,
        address: this.address,
        customer: this.customer,
        owner: this.profile.lastName + ' ' + this.profile.firstName + ' ' +this.date,
        workDescription: this.workDescription.workDescription,
        comment: this.workDescription.comment,
        devices: this.devices,
        materials: this.materials,
        works: this.works
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

  methods: {
    ...mapActions(['addEstimate']),
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

      // console.log(this.estimate)

      // отправляем данные на сервер через store
      await this.addEstimate(this.estimate).then(() => {
        this.loading = true
      })

      // проверяем и загружаем файл
      await fetch(api.API_GET_FILE + this.fileName, {
          method: 'GET',
        }).then(response =>
            response.blob()
        ).then(blob => {

          // проверяем существует ли файл
          if (blob.size === 0) {
            //

          } else {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = this.fileName;
            document.body.appendChild(a);
            a.click();
            a.remove();  //afterwards we remove the element again
            this.loading = false // останавливаем анимацию
          }
        })

    },

    get() {
      console.log('При нажатии на кнопку калькулятор')
    }

  }
}
</script>

<style lang="scss">


</style>