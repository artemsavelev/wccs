<template>
  <div class="auth">
    <div>

        <v-text-field :label="env.login"
                      @keypress.enter="auth"
                      :rules="rules"
                      :hint="env.rules[1]"
                      hide-details="auto"
                      v-model="username"
                      class="mb-5"
                      counter></v-text-field>

        <v-text-field :label="env.password"
                      @keypress.enter="auth"
                      :rules="rules"
                      :hint="env.rules[1]"
                      :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="show ? 'text' : 'password'"
                      @click:append="show = !show"
                      v-model="password"
                      class="mb-5"
                      counter></v-text-field>



        <v-btn tile color="primary" @click="auth" class="auth-btn">{{ env.singIn }}</v-btn>


    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import env from '../../env.config.json'

export default {
  name: 'auth',
  data: () => ({
    env,
    rules: [
      value => !!value || env.rules[0],
      value => (value && value.length >= 3) || env.rules[1],
    ],

    show: false,
    username: '',
    password: '',
  }),

  computed: {
    ...mapGetters(['error']),
  },

  watch: {

  },

  methods: {

    ...mapActions(['login', 'showSnack']),

    async auth() {

      const data = {
        username: this.username,
        password: this.password
      }

      try {

        await this.login(data)

        await this.$router.push('/')

      } catch (e) {

        const dataError = {
          message: 'Error code - ' + this.error + ': Ошибка авторизации, неверный логин или пароль.',
          color: 'error',
          icon: 'mdi-alert-circle'
        }

        this.showSnack(dataError)

      }
    },


  }
}
</script>

<style scoped lang="scss">
.auth {
  //border: 1px solid black;
  display: flex;
  justify-content: center;
  //width: 400px;
  height: 90%;

  //margin: 25vh auto;
  align-items: center;

  &-btn{
    width: 400px;

  }
}

</style>