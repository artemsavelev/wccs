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
        <v-progress-linear height="5"
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
      ext: 'upload',
      type: this.address + ' ' + this.customer + '.xlsx',
      loading: true
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
      this.estimate = {
        extId: this.extId,
        address: this.address,
        customer: this.customer,
        owner: this.profile.lastName + ' ' + this.date,
        workDescription: this.workDescription.typeOfWork,
        devices: this.devices,
        materials: this.materials,
        works: this.works
      }
      console.log(this.estimate)
      this.addEstimate(this.estimate).then(() => {
        this.loading = false
      })

      const user = JSON.parse(localStorage.getItem('user'));
      //
      //
      // fetch('http://localhost:8080/uploads/Address customer 7654321.xlsx', {
      // // fetch('file:///home/maverick/uploads', {
      //   responseType: 'blob',
      //   method: 'GET',
      //   type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      //   headers: new Headers({
      //         'Authorization': 'bearer_' + user.token
      //       },
      //   ),
      // }).then((response) => {
      //   const url = window.URL.createObjectURL(new Blob([response.data]));
      //   const link = document.createElement('a');
      //   link.href = url;
      //   link.setAttribute('download', 'Address customer 7654321.xlsx');
      //   document.body.appendChild(link);
      //   link.click();
      //   console.log(response)
      // })
      fetch(`http://localhost:8080/uploads/`, {
        method: 'get',
        responseType: 'blob',
        headers: new Headers({
              'Authorization': 'bearer_' + user.token
            },
        ),
      }).then((response) => {
        console.log(response)
      })


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