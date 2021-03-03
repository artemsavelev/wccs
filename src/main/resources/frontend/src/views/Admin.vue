<template>
  <div>

    <div class="registration ml-7 mb-15 mt-3">

      <div class="font-weight-bold ml-3">
        {{ env.addUser }}
      </div>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            class="pt-5"
            dense
            label="Фамилия"
            v-model="lastName"
            clearable/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            dense
            class="pt-5"
            label="Имя"
            v-model="firstName"
            clearable/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            dense
            class="pt-5"
            label="Email"
            v-model="email"
            clearable/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            dense
            class="pt-5"
            label="Логин"
            v-model="username"
            clearable/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            dense
            class="pt-5"
            label="Пароль"
            v-model="password"
            clearable/>
      </v-col>



<!--      <v-col cols="12" sm="6" md="4">-->
<!--        <v-autocomplete-->
<!--            v-model="positions"-->
<!--            :items="items1"-->
<!--            item-text="name"-->
<!--            item-value="id"-->
<!--            chips-->
<!--            clearable-->
<!--            label="Должность"-->
<!--            multiple-->
<!--        ></v-autocomplete>-->
<!--      </v-col>-->

<!--      <v-col cols="12" sm="6" md="4">-->
<!--        <v-autocomplete-->
<!--            v-model="roles"-->
<!--            :items="items2"-->
<!--            item-text="roles"-->
<!--            item-value="id"-->
<!--            chips-->
<!--            clearable-->
<!--            label="Роли"-->
<!--            multiple-->
<!--        ></v-autocomplete>-->
<!--      </v-col>-->

      <v-btn v-on:click="saveUser" color="primary" class="ml-3" tile>{{ env.keySave }}</v-btn>


    </div>






    <div class="ml-10 mr-10">

      <div class="font-weight-bold">
        {{ env.sectionDevice }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchDevice"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allDevices"
          :items-per-page="10"
          :search="searchDevice"
          class="elevation-1 mb-10"
      ></v-data-table>

      <div class="font-weight-bold">
        {{ env.sectionMaterial }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchMaterial"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allMaterials"
          :items-per-page="10"
          :search="searchMaterial"
          class="elevation-1 mb-10"
      ></v-data-table>

      <div class="font-weight-bold">
        {{ env.sectionWork }}
      </div>

      <v-col cols="4">
        <v-text-field
            v-model="searchWork"
            append-icon="mdi-magnify"
            label="Поиск"
            single-line
            clearable
            hide-details
        ></v-text-field>
      </v-col>
      <v-data-table
          dense
          show-select
          :headers="headers"
          :items="allWorks"
          :items-per-page="10"
          :search="searchWork"
          class="elevation-1"
      ></v-data-table>

    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import env from "../../env.config.json"

export default {
  name: "Admin",
  computed: mapGetters(['allDevices', 'allMaterials', 'allWorks', 'profile']),


  data () {
    return {
      ...mapActions(['fetchDevices', 'fetchMaterials', 'fetchWorks']),
      env,
      searchDevice: '',
      searchWork: '',
      searchMaterial: '',
      headers: [
        { text: 'id', value: 'id' },
        { text: 'name', value: 'name' },
      ],
      lastName: '',
      firstName: '',
      username: '',
      password: '',
      email: '',
      positions: [],
      roles: [],
      items1: ['foo', 'bar', 'fizz', 'buzz'],
      items2: ['foo', 'bar', 'fizz', 'buzz'],
      id: ''
    }
  },

  mounted() {
    // получаем данные с сервера (список оборудования)
    this.fetchDevices();
    this.fetchMaterials();
    this.fetchWorks();
  },


  methods: {
    ...mapActions(['addUser']),
    saveUser() {


      const user = {
        lastName: this.lastName,
        firstName: this.firstName,
        username: this.username,
        password: this.password,
        email: this.email,
        department: {
          id: this.profile.department.id
        },
        position: this.positions,
        roles: this.roles
      }
      console.log(user)

      this.addUser(user)
    }

  },
}
</script>

<style lang="scss">

</style>