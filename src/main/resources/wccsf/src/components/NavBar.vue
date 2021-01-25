<template>
  <v-app-bar app color="primary" dark dense flat>
    <v-toolbar-title>WCCS</v-toolbar-title>

    <v-spacer ></v-spacer>

    <OrderForm v-bind:orderVal="ordersList"/>

    <v-btn v-if="profile" v-on:click="orders" :disabled="$route.path === '/'" text tile>ORDERS</v-btn>
    <v-btn v-if="profile" v-on:click="userProfile" :disabled="$route.path === '/profile'" text tile>profile</v-btn>
    <v-btn class="mr-10" v-if="profile && profile.firstName === 'Артем'"
           v-on:click="admin" :disabled="$route.path === '/admin'" text tile>administration</v-btn>
    <v-btn v-if="profile" v-on:click="logout" text tile>
      <v-icon>{{ mdiExitToApp }}</v-icon>
    </v-btn>

  </v-app-bar>
</template>

<script>
import { mdiExitToApp } from '@mdi/js';
import OrderForm from "../views/OrderForm";
import { mapGetters } from "vuex";
export default {
  name: "NavBar",
  components: { OrderForm },

  computed: mapGetters(['profile']),

  data() {
    return {
      ordersList: [],
      dialog: false,
      mdiExitToApp
    }
  },

  methods: {
    orders() {
      this.$router.push('/')
    },
    userProfile() {
      this.$router.push('/profile')
    },
    admin() {
      this.$router.push('/admin')
    },
    async logout() {
      // localStorage.removeItem('user')
      await this.$store.dispatch('logout');
      await this.$router.push('/login')
    },

    close() {
      this.dialog = false;
    }

  }
}
</script>

<style scoped lang="scss">

</style>