<template>
  <div class="auth">
    <form @submit.prevent="auth">
      <v-text-field label="Login"
                    :rules="rules"
                    hide-details="auto"
                    v-model="username"
                    class="mb-5"
                    counter></v-text-field>

      <v-text-field label="Password"
                    :rules="[rules1.required, rules1.min]"
                    hint="At least 3 characters"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show ? 'text' : 'password'"
                    @click:append="show = !show"
                    v-model="password"
                    class="mb-5"
                    counter></v-text-field>



      <v-btn color="primary" type="submit" class="auth-btn">Login</v-btn>

    </form>

    <Notifier v-bind:message="message"
              v-bind:snackbar="snackbar"/>

  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import messages from "../utils/messages";
import Notifier from "../components/Notifier";

export default {
  name: "auth",
  data: () => ({
    rules: [
      value => !!value || 'Required.',
      value => (value && value.length >= 3) || 'Min 3 characters',
    ],
    rules1: {
      required: value => !!value || 'Required.',
      min: v => v.length >= 3 || 'Min 3 characters',
    },
    show: false,
    username: '',
    password: '',
    snackbar: false,
    message: ''
  }),

  components: { Notifier },

  computed: {
    ...mapGetters(['error']),

  },

  watch: {
    error(err) {
      // console.log(error)

      this.message = messages[err];
      // this.snackbar = true;


    }


  },

  methods: {

    ...mapActions(['login']),
    async auth() {

      const data = {
        username: this.username,
        password: this.password
      }

      try {

        await this.login(data)

        await this.$router.push('/orders')

      } catch (e) {
        // console.warn('Error auth: ', e.message)
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