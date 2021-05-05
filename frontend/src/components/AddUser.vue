<template>
  <div>

    <div class="registration ml-7 mb-15 mt-3 font-s">

      <div class="font-weight-bold ml-3">
        {{ env.addUser }}
      </div>
      <div class="ml-3  font-weight-thin">
        {{ env.fields }}
      </div>

      <v-form
          ref="form"
          v-model="valid"
          lazy-validation>

        <v-col cols="12" sm="6" md="4">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              hide-details="auto"
              class="rounded-0"
              dense
              outlined
              :label="env.lastName + '*'"
              v-model="lastName"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="4">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0"
              :label="env.firstName + '*'"
              v-model="firstName"
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="4">
          <v-text-field
              :rules="emailRules"
              dense
              outlined
              class="rounded-0"
              :label="env.email + '*'"
              v-model="email"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="4">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0"
              :label="env.login + '*'"
              v-model="username"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="4">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0"
              :label="env.password + '*'"
              :type="show ? 'text' : 'password'"
              @click:append="show = !show"
              v-model="password"
              required
              counter/>
        </v-col>

        <v-btn v-on:click="saveUser" color="primary" class="ml-3" tile>{{ env.keySave }}</v-btn>
      </v-form>

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
      valid: true,
      show: false,
      lastName: '',
      firstName: '',
      username: '',
      password: '',
      email: '',
      positions: ['position'],
      items1: ['foo', 'bar', 'fizz', 'buzz'],
      id: '',
      message: '',
      rules: [
        value => !!value || env.rules[0],
        value => (value && value.length >= 3) || env.rules[1],
      ],
      emailRules: [
        v => !!v || env.rules[0],
        v => /.+@.+\..+/.test(v) || env.rules[2],
      ],
    }
  },
  methods: {

    ...mapActions(['addUser', 'showSnack']),

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
        position: this.positions
      }



      if (this.lastName && this.firstName && this.username && this.password && this.email) {

        this.addUser(user)

      } else {

        this.$refs.form.validate()

        const data = {
          message: 'Поля формы не должны быть пустыми',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(data)
      }

      // this.lastName = ''
      // this.firstName = ''
      // this.username = ''
      // this.password = ''
      // this.email = ''

    }

  }

}
</script>

<style scoped>

</style>