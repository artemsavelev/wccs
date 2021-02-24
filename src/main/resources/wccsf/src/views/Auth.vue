<template>
  <div class="auth">
    <form @submit.prevent="auth">
      <v-text-field label="Login" :rules="rules" hide-details="auto" v-model="username" counter></v-text-field>
      <br/>
      <v-text-field v-model="password"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules1.required, rules1.min]"
                    :type="show ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password"
                    hint="At least 3 characters"
                    counter
                    @click:append="show = !show"></v-text-field>

      <br/>

      <v-btn color="primary" type="submit" class="auth-btn">Login</v-btn>

    </form>

    <Notifier v-bind:message="message"
              v-bind:snackbar="snackbar"/>

  </div>
</template>

<script>
import {mapGetters} from "vuex";
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
      emailMatch: () => ('The email and password you entered don\'t match'),
    },
    show: false,
    username: '',
    password: '',
    snackbar: false,
    message: ''
  }),

  components: { Notifier },

  computed: mapGetters(['error']),

  watch: {
    error(error) {
      // console.log(messages[error])
      this.message = messages[error];
      this.snackbar = true;
    }
  },

  methods: {

    async auth() {
      const formAuth = {
        username: this.username,
        password: this.password
      };
      try {
        await this.$store.dispatch('login', formAuth);
        await this.$router.push('/')

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