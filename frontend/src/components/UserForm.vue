<template>
  <div>
    <div class="registration mb-15 mt-3 font-s">
      <div class="font-weight-bold ml-3">
        {{ formTitle }}
      </div>
      <div class="ml-3  font-weight-thin">
        {{ env.fields }}
      </div>

      <v-form
          ref="form"
          v-model="valid"
          lazy-validation>

        <v-col cols="12" sm="6" md="12">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              hide-details="auto"
              class="rounded-0 styled-input font-s"
              dense
              outlined
              :label="env.lastName + '*'"
              v-model="lastName"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="12">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0 styled-input font-s"
              :label="env.firstName + '*'"
              v-model="firstName"
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="12">
          <v-autocomplete
              class="rounded-0 styled-input font-s"
              :items="allPositions"
              item-text="name"
              item-value="id"
              v-model="position"
              :label="env.position + '*'"
              required
              outlined
              dense
              multiple
          ></v-autocomplete>
        </v-col>

        <v-col cols="12" sm="6" md="12">
          <v-text-field
              :rules="emailRules"
              dense
              outlined
              class="rounded-0 styled-input font-s"
              :label="env.email + '*'"
              v-model="email"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="12">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0 styled-input font-s"
              :label="env.login + '*'"
              v-model="username"
              required
              counter/>
        </v-col>

        <v-col cols="12" sm="6" md="12">
          <v-text-field
              :rules="rules"
              :hint="env.rules[1]"
              dense
              outlined
              class="rounded-0 styled-input font-s"
              :label="env.password + '*'"
              :type="show ? 'text' : 'password'"
              @click:append="show = !show"
              v-model="password"
              required
              counter/>
        </v-col>

        <v-card-actions class="pa-0">
          <v-col cols="12" sm="6" md="6">
            <v-btn v-on:click="saveUser" :color="colorSave" tile height="35" small outlined>{{ env.keySave }}</v-btn>
          </v-col>

          <v-col cols="12" sm="6" md="6" class="d-flex justify-md-end">
            <v-btn @click="clear"
                   :color="colorClear"
                   class="ml-3" height="35" tile small outlined>{{ env.keyClear }}</v-btn>
          </v-col>
        </v-card-actions>
      </v-form>

    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import env from '../../env.config.json'

export default {
  name: "AddUser",
  props: ['edit'],
  computed: {
    ...mapGetters(['profile', 'allPositions']),
    formTitle() {
      return this.editedIndex === -1 ? env.addUser : env.editUser + ' ' + this.lastName + ' ' + this.firstName
    },
  },
  watch: {
    edit(newVal) {
      this.$refs.form.resetValidation()
      this.editedIndex = 1
      this.id = newVal.id
      this.firstName = newVal.firstName
      this.lastName = newVal.lastName
      this.email = newVal.email
      this.name = newVal.name
      this.position = newVal.positions
      this.department = newVal.department
      this.username = newVal.username
      this.password = null
    }
  },
  mounted() {
    this.fetchPosition();
  },
  updated() {
    if (this.lastName && this.firstName && this.position && this.username && this.password && this.email) {
      this.colorSave = 'success'
      this.colorClear = 'error'
    }
  },
  data() {
    return {
      ...mapActions(['fetchPosition']),
      env,
      editedIndex: -1,
      colorSave: 'primary',
      colorClear: 'primary',
      valid: true,
      show: false,
      lastName: '',
      firstName: '',
      username: '',
      password: '',
      email: '',
      position: [],
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
    ...mapActions(['addUser', 'editUser', 'showSnack']),
    saveUser() {
      const positions = this.position.map(pos => {
        return this.id ? { ...pos } : { id: pos }
      })

      if (this.lastName && this.firstName && this.position && this.username && this.password && this.email) {

        this.email = this.email.replace(/[/\\<>]/g, '').toLowerCase()

        const user = {
          id: this.id,
          lastName: this.lastName.trim(),
          firstName: this.firstName.trim(),
          username: this.username.trim(),
          password: this.password.trim(),
          email: this.email.trim(),
          department: {
            id: this.profile.department.id
          },
          positions: positions
        }

        if (!this.id) {
          this.addUser(user)
        } else {
          this.editUser(user)
        }
      } else {
        this.$refs.form.validate()
        const data = {
          message: 'Поля формы не должны быть пустыми',
          color: 'error',
          icon: 'mdi-alert-circle'
        }
        this.showSnack(data)
      }
    },

    clear() {
      this.editedIndex = -1
      this.colorSave = 'primary'
      this.colorClear = 'primary'
      this.$refs.form.resetValidation()
      this.lastName = ''
      this.firstName = ''
      this.position = []
      this.username = ''
      this.password = ''
      this.email = ''
    },
  }
}
</script>

<style scoped>

</style>