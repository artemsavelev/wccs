<template>
  <div class="auth">
    <form @submit.prevent="auth">
      <v-text-field :label="env.login"
                    :rules="rules"
                    hide-details="auto"
                    v-model="username"
                    class="mb-5"
                    counter></v-text-field>

      <v-text-field :label="env.password"
                    :rules="rules"
                    hint="At least 3 characters"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show ? 'text' : 'password'"
                    @click:append="show = !show"
                    v-model="password"
                    class="mb-5"
                    counter></v-text-field>



      <v-btn color="primary" type="submit" class="auth-btn">{{ env.singIn }}</v-btn>

    </form>



  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
// import messages from "../utils/messages";
import env from "../../env.config.json"

export default {
  name: "auth",
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
    }
  }
}
</script>

<style scoped lang="scss">
.auth {
  width: 400px;
  margin: 25vh auto;

  &-btn{
    width: 400px;

  }
}

</style>