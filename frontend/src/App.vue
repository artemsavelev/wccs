<template>
  <v-app>

    <NavBar/>

    <v-main>

      <div v-if="!profile && $route.path !== '/login'" class="ma-5">
        Необходимо
        <router-link to="/login" class="nav-link" custom v-slot="{ navigate }">
          <span @click="navigate" @keypress.enter="navigate" role="link">авторизоваться</span>
        </router-link>
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
import env from "../env.config.json";
// import {addHandler} from "@/utils/ws";

export default {
  name: 'App',
  components: { NavBar, Notifier },
  computed: mapGetters(['profile']),
  methods: {
    ...mapMutations(['addOrderMutation']),

  },
  data: () => ({
    env,

  }),

  // created() {
  //   addHandler(data => {
  //     console.log(data)
  //     this.addOrderMutation(data.body)
  //   })
  // },

  mounted() {
    const theme = localStorage.getItem('darkTheme');
    if (theme) {
      this.$vuetify.theme.dark = theme === "true";
    }
  },

};
</script>

<style lang="scss">
@import "src/assets/styles/styles";

#app {
  @include font-montserrat;
}
</style>
