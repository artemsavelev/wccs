<template>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent>
    <template v-slot:activator="{ on }" class="">
      <v-btn icon v-on="on" class="" @click="get">
        <v-icon>mdi-calculator</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="grey lighten-2">
        <div class="font-xl">{{formTitle}}</div>
        <v-checkbox v-model="ex"
                    class="pt-0 pb-0 mt-0 mb-0 ml-5"
                    color="success"
                    label="смета предварительная"
                    hide-details></v-checkbox>
        <v-spacer></v-spacer>
        <v-btn small text tile v-on:click="close">
          <v-icon>mdi-close</v-icon>
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
          <Device v-on:transmit="transmitDevices"/>
          <Materials v-on:transmit="transmitMaterials"/>
          <Work v-on:transmit="transmitWorks"/>
          <!-- // загружаем модули -->

      </v-card-text>

      <template>
        <v-progress-linear height="3"
                           color="primary"
                           :indeterminate="loading"
                           ></v-progress-linear>
        <div class="ml-4 mb-1">
          <v-card-actions class="">
            <v-btn medium v-on:click="create" color="primary" tile>{{ env.keyMakeEstimate }}</v-btn>
            <Preview v-bind:data="estimate"/>
          </v-card-actions>
        </div>
      </template>

    </v-card>

  </v-dialog>
</template>

<script>
import env from "../../env.config.json"
import Work from "./Work";
import Materials from "./Materials";
import Device from "./Device";
import TypeOfWork from "./TypeOfWork";
import Preview from "@/components/Preview";
import {mapActions, mapGetters} from "vuex";
import api from "@/api/backendApi";


// const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: "Estimate",
  props: ['extId', 'address', 'customer'],
  components: { TypeOfWork, Device, Materials, Work, Preview },
  computed: mapGetters(['profile']),
  data() {
    return {
      env,
      dialog: false,
      formTitle: env.title[1],
      ex: false,
      date: new Date().toISOString().substr(0, 10) + ' ' +
          new Date().toTimeString().substr(0, 8),
      estimate: {},
      workDescription: {},
      devices: [],
      materials: [],
      works: [],
      file: this.address + ' ' + this.customer + ' ' + this.extId + '.xlsx',
      loading: false,
      timerId: null,
      delay: 1000
    }
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

      // создаем объект для отправки на сервер
      this.estimate = {
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
      // console.log('estimate', this.estimate)
      await this.addEstimate(this.estimate) // отправляем данные на сервер через store
      this.loading = true // активируем анимацию загрузки

      // проверяем и загружаем файл по таймеру
      // this.timerId = setInterval(() => {
      // await pause(this.delay)
      await fetch(api.API_GET_FILE + this.file, {
          method: 'GET',
        }).then(response =>
            response.blob()
        ).then(blob => {
          // проверяем существует ли файл
          if (blob.size === 0) {
            // console.log('file size:', blob.size)
            // this.delay += 1000; // увеличиваем таймер на 1 секунду с каждым интервалом
            // console.log(this.delay)

          } else {

            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = this.file;
            document.body.appendChild(a);
            a.click();
            a.remove();  //afterwards we remove the element again
            this.loading = false // останавливаем анимацию
            // console.log('file size:', blob.size, 'file type:', blob.type)
            // clearInterval(pause) // останавливаем таймер
          }
        }).catch(err => console.warn(err))

      // }, this.delay);

    },

    get() {
      console.log('!')
    }


  }
}
</script>

<style lang="scss">

//.main {
//  border-bottom: 1px solid #ccc;
//}

</style>