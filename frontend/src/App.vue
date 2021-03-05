<template>
  <v-app>
    <NavBar/>
    <v-main>
      <div v-if="!profile && $route.path !== '/login'" class="ma-5">
        Необходимо <router-link to="/login" tag="a" class="nav-link">авторизоваться</router-link>
      </div>

      <router-view/>
    </v-main>
    <Notifier/>
  </v-app>
</template>

<script>
import NavBar from "./components/NavBar";
const Notifier = () => import('@/components/Notifier');
import {mapGetters, mapMutations} from "vuex";
import {addHandler} from "@/utils/ws";

export default {
  name: 'App',
  components: { NavBar, Notifier },
  computed: mapGetters(['profile']),
  methods: mapMutations(['addOrderMutation']),
  created() {
    addHandler(data => {



      this.addOrderMutation(data.body)
    })
  },
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
