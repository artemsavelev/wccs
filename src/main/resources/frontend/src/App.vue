<template>
  <v-app>
    <NavBar/>
    <v-main>
      <div v-if="!profile && $route.path !== '/login'" class="ma-5">
        Необходимо <router-link to="/login" tag="a" class="nav-link">авторизоваться</router-link>
      </div>

      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import NavBar from "./components/NavBar";
import {mapGetters} from "vuex";

export default {
  name: 'App',
  components: { NavBar },
  computed: mapGetters(['profile']),
  mounted() {
    const theme = localStorage.getItem('darkTheme');
    if (theme) {
      this.$vuetify.theme.dark = theme === "true";
    }
  },

};
</script>

<style lang="scss">
@import "./assets/styles/styles";

#app {
  @include font-montserrat;
}
</style>
