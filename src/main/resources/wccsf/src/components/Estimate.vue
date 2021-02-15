<template>
  <v-dialog v-if="profile" v-model="dialog" scrollable persistent>
    <template v-slot:activator="{ on }" class="mt-10">
      <v-btn icon v-on="on" class="">
        <v-icon>mdi-calculator</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="grey lighten-2">
        <div class="ml-5 font-xl">{{formTitle}}</div>
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
        <v-container>
          <div class="main-header">
            <div class="font-s">
              {{ env.request }}: {{ extId }}
            </div>
            <div class="font-s">
              {{ env.address }}: {{ address }}
            </div>
            <div class="font-s">
              {{ env.customer }}: {{ customer }}
            </div>
            <div class="font-s">
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
        </v-container>
      </v-card-text>

      <template>
        <v-progress-linear height="3"
                           color="primary"
                           :indeterminate="loading"
                           ></v-progress-linear>
        <div class="pl-8 pb-5">
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
      delay: 2000
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
    create() {

      // создаем объект для отправки на сервер
      this.estimate = {
        extId: this.extId,
        address: this.address,
        customer: this.customer,
        owner: this.profile.lastName + ' ' + this.date,
        workDescription: this.workDescription.typeOfWork,
        simpleText: this.workDescription.text,
        devices: this.devices,
        materials: this.materials,
        works: this.works
      }
      // console.log('estimate', this.estimate)
      this.addEstimate(this.estimate) // отправляем данные на сервер через store

      // // таймер через который начинается скачивание
      // let delay = 2000;

      this.loading = true // активируем анимацию загрузки

      // проверяем и загружаем файл по таймеру
      this.timerId = setInterval(() => {
        fetch(api.API_GET_FILE + this.file, {
          method: 'GET',
        }).then(response =>
            response.blob()
        ).then(blob => {

          // проверяем существует ли файл
          if (blob.size === 0) {

            console.log('file size:', blob.size)
            this.delay += 1000; // увеличиваем таймер на 1 секунду с каждым интервалом
            console.log(this.delay)

          } else {

            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = this.file;
            document.body.appendChild(a); // we need to append the element to the dom -> otherwise it will not work in firefox
            a.click();
            a.remove();  //afterwards we remove the element again

            console.log('file size:', blob.size, 'file type:', blob.type)
            this.loading = false // останавливаем анимацию
            clearInterval(this.timerId) // останавливаем таймер

          }
        })

      }, this.delay);

    },


  }
}
</script>

<style lang="scss">
.font-s {
  font-size: small;
}
.font-m {
  font-size: medium;
}
.font-xl {
  font-size: large;
}
.main-header {

}

.main-header-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  &-wrapper {
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
}

.main {
  border-bottom: 1px solid #ccc;
}
.main-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  &-wrapper {
    //border: 1px solid #ccc;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
}
.column-70 {
  width: 70px;
  max-width: 70px;
}
.column-100 {
  width: 100px;
  max-width: 100px;
}
.column-80 {
  width: 80px;
  max-width: 80px;
}
.column-150 {
  width: 150px;
  max-width: 150px;
}
.column-200 {
  width: 200px;
  max-width: 200px;
}
.column-300 {
  width: 300px;
  max-width: 300px;
}
.column-400 {
  width: 400px;
  max-width: 400px;
}
.column-500 {
  width: 500px;
  max-width: 500px;
}
.column-600 {
  width: 600px;
  max-width: 600px;
}
.column-700 {
  width: 700px;
  max-width: 700px;
}
.column-800 {
  width: 800px;
  max-width: 800px;
}
.column-900 {
  width: 900px;
  max-width: 900px;
}

.border-top {
  border-top: 1px solid #ccc;
  width: 100%;
}


</style>