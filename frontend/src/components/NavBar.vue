<template>
  <v-app-bar app color="primary" dark dense flat>
    <v-toolbar-title>WCCS</v-toolbar-title>

    <v-spacer ></v-spacer>

    <OrderForm v-bind:orderVal="ordersList"/>

    <v-btn v-if="profile" v-on:click="orders" :disabled="$route.path === '/'" text tile>{{ env.keyOrder }}</v-btn>
    <v-btn v-if="profile" v-on:click="userProfile" :disabled="$route.path === '/profile'" text tile>{{ env.keyProfile }}</v-btn>
    <v-btn v-if="profile" v-on:click="settings" :disabled="$route.path === '/settings'" text tile>{{ env.keySetting }}</v-btn>
    <v-btn class="mr-10" v-if="profile && this.isRoleAdmin"
           v-on:click="admin" :disabled="$route.path === '/admin'" text tile>{{ env.keyAdministration }}</v-btn>
    <v-btn class="mr-10" v-if="profile && this.isRoleSuper"
           v-on:click="system" :disabled="$route.path === '/system'" text tile>{{ env.keySystem }}</v-btn>
    <v-btn v-if="profile" v-on:click="logout" text tile>
      <v-icon>mdi-exit-to-app</v-icon>
    </v-btn>


  </v-app-bar>
</template>

<script>
import env from "../../env.config.json"
import OrderForm from "./OrderForm";
import { mapGetters } from "vuex";

export default {
  name: "NavBar",
  components: {OrderForm},
  computed: {
    ...mapGetters(['profile']),

  },
  data() {
    return {
      env,
      selection: 'addOrder',
      ordersList: [],
      dialog: false,
      search:'',
      isRoleAdmin: false,
      isRoleSuper: false
    }
  },

  updated() {
    if (this.profile) {
      this.profile.role.filter(r => {
        if (r.id === 2) {
          return this.isRoleAdmin = true
        }
      })
    }

    if (this.profile) {
      this.profile.role.filter(r => {
        if (r.id === 3) {
          return this.isRoleSuper = true
        }
      })
    }
  },
  methods: {
    orders() {
      this.$router.push('/')
    },
    userProfile() {
      this.$router.push('/profile')
    },
    settings() {
      this.$router.push('/settings')
    },
    admin() {
      this.$router.push('/admin')
    },
    system() {
      this.$router.push('/system')
    },
    async logout() {
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