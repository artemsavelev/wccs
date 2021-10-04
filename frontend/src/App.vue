<template>
  <v-app>
    <NavBar/>
    <v-main>
      <div v-if="!profile && $route.path !== '/login'" class="ma-5">
        Необходимо
        <router-link to="/login" class="nav-link" custom v-slot="{ navigate }">
          <span @click="navigate" @keypress.enter="navigate" role="link" style="cursor: pointer; color: #1976d2">авторизоваться</span>
        </router-link>
      </div>
      <router-view/>
    </v-main>
    <Notifier/>
  </v-app>
</template>

<script>
import NavBar from './components/NavBar'
import {mapGetters, mapMutations} from 'vuex'
import env from '../env.config.json'
import {addHandler} from './utils/ws'
const Notifier = () => import('@/components/Notifier')

export default {
  name: 'App',
  components: { NavBar, Notifier },
  computed: mapGetters(['profile']),
  methods: mapMutations(['addOrderMutation', 'updateOrderMutation', 'removeOrderMutation']),
  data: () => ({
    env
  }),
  created() {
    addHandler(data => {

      // ObjectType class backend
      if (data.objectType === 'ORDER') {
        switch (data.eventType) {
          case 'CREATE':
            this.addOrderMutation(data.body);
            break;
          case 'UPDATE':
            this.updateOrderMutation(data.body);
            break;
          case 'REMOVE':
            this.removeOrderMutation(data.body);
            break;
          default:
            console.error(`Looks EventType class from backend "${data.eventType}"`)
        }
      } else {
        console.error(`Looks ObjectType class from backend "${data.objectType}"`)
      }
    })
  },

  mounted() {
    const theme = localStorage.getItem('darkTheme')
    if (theme) {
      this.$vuetify.theme.dark = theme === 'true'
    }
  }
}
</script>

<style lang="scss">
@import "src/assets/styles/styles";

#app {
  @include font-montserrat;
}
</style>
