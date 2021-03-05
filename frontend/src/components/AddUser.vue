<template>
  <div>

    <div class="registration ml-7 mb-15 mt-3">

      <div class="font-weight-bold ml-3">
        {{ env.addUser }}
      </div>
      <div class="ml-3  font-weight-thin">
        поля помеченные " * " обязательны к заполнению
      </div>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            :rules="[rules.required, rules.min]"
            hide-details="auto"
            class="pt-5"
            dense
            label="Фамилия*"
            v-model="lastName"
            counter/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            :rules="[rules.required, rules.min]"
            dense
            class="pt-5"
            label="Имя*"
            v-model="firstName"
            counter/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            :rules="[rules.required, rules.min]"
            dense
            class="pt-5"
            label="Email*"
            v-model="email"
            counter/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            :rules="[rules.required, rules.min]"
            dense
            class="pt-5"
            label="Логин*"
            v-model="username"
            counter/>
      </v-col>

      <v-col cols="12" sm="6" md="4">
        <v-text-field
            :rules="[rules.required, rules.min]"
            dense
            class="pt-5"
            label="Пароль*"
            v-model="password"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            counter/>
      </v-col>

      <v-btn v-on:click="saveUser" color="primary" class="ml-3" tile>{{ env.keySave }}</v-btn>


    </div>

  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import env from "../../env.config.json"

export default {
  name: "AddUser",

  computed: {
    ...mapGetters(['profile']),

  },

  data() {
    return {
      env,
      show: false,
      lastName: '',
      firstName: '',
      username: '',
      password: '',
      email: '',
      positions: [],
      items1: ['foo', 'bar', 'fizz', 'buzz'],
      id: '',
      message: '',
      rules: {
        required: value => !!value || 'Данное поле обязательное для заполнения.',
        min: v => v.length >= 3 || 'Длина должна быть не менее 3 символов',
      }
    }
  },
  methods: {

    ...mapActions(['addUser', 'showSnack']),

    saveUser() {

      if (!this.lastName) {

        const lastName = {
          message: 'Поле Фамилия не должно быть пустым',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(lastName)
      }

      if (!this.firstName) {

        const firstName = {
          message: 'Поле Имя не должно быть пустым',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(firstName)
      }

      if (!this.email) {

        const email = {
          message: 'Поле email не должно быть пустым',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(email)
      }

      if (!this.username) {

        const username = {
          message: 'Поле логина не должно быть пустым',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(username)
      }

      if (!this.password) {

        const password = {
          message: 'Поле пароля не должно быть пустым',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(password)
      }


      const user = {
        lastName: this.lastName,
        firstName: this.firstName,
        username: this.username,
        password: this.password,
        email: this.email,
        department: {
          id: this.profile.department.id
        },
        position: this.positions
      }


      if (this.lastName && this.firstName && this.username && this.password && this.email) {
        this.addUser(user)
      }

      this.lastName = ''
      this.firstName = ''
      this.username = ''
      this.password = ''
      this.email = ''

    }

  },

  filters: {
    // validateEmail(email) {
    //   const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    //   return re.test(String(email).toLowerCase());
    // }
  }


}
</script>

<style scoped>

</style>