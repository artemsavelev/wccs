<template>
  <div>
    <v-navigation-drawer v-if="profile" v-model="drawer" :mini-variant.sync="mini" width="400" app clipped>

      <v-list v-if="profile" dense class="pa-0 font-weight-light">
        <v-list-item class="px-2 mb-1 mt-1">
          <v-list-item-avatar>

            <!--- with some props ---->
            <avatars :isCircle="isCircle"
                     :circleColor="circleColor"
                     :skinColor="skinColor"
                     :facialHairType="facialHairType"
                     :facialHairColor="facialHairColor"
                     :eyebrowType="eyebrowType"
                     :eyeType="eyeType"
                     :mouthType="mouthType"
                     :hairColor="hairColor"
                     :accessoriesType="accessoriesType"
                     :topType="topType"
                     :topColor="topColor"
                     :clotheType="clotheType"
                     :clotheColor="clotheColor"
                     :graphicType="graphicType">
            </avatars>

          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>{{ profile.lastName }} {{ profile.firstName }}</v-list-item-title>
            <v-list-item-subtitle>{{ profile.email }}</v-list-item-subtitle>
          </v-list-item-content>

          <v-btn icon @click.stop="mini = !mini" tile>
            <v-icon>mdi-menu-left</v-icon>
          </v-btn>
        </v-list-item>

        <v-list-group active-class="primary v-list__group--active"
                      v-for="item in showItems"
                      :key="item.title"
                      color="white"
                      v-model="item.active">

          <template v-slot:activator>
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item active-class="indigo lighten-5"
                       color="black"
                       v-for="child in item.items"
                       :key="child.title"
                       :to="child.route"
                       link>

            <v-list-item-icon>
              <v-icon>{{ child.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ child.title }}</v-list-item-title>
            </v-list-item-content>

          </v-list-item>
        </v-list-group>
      </v-list>

    </v-navigation-drawer>

    <v-app-bar app color="primary" dense dark flat clipped-right clipped-left>
      <v-app-bar-nav-icon v-if="profile" @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>WCCS</v-toolbar-title>

      <v-spacer ></v-spacer>

      <v-btn v-if="profile" v-on:click="orders" :disabled="$route.path === '/'" text tile>{{ env.keyOrder }}</v-btn>

      <v-btn v-if="profile" class="ml-5 mr-5" @click="logout" tile small icon>
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
<!--      <v-btn v-if="profile && $route.path === '/'" class="ml-5 mr-5" @click="showDrawer"  tile small icon>-->
<!--        <v-icon>{{ iconVisibleDrawer }}</v-icon>-->
<!--      </v-btn>-->
    </v-app-bar>

  </div>
</template>

<script>
import env from '../../env.config.json'
import {mapGetters, mapMutations} from 'vuex'
import {bus} from '@/utils/bus'
import avatars from "vuejs-avataaars";

// const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: 'NavBar',
  components: {avatars},
  computed: {
    ...mapGetters(['profile']),

    showItems() {

      let items = [
        { title: env.main,
          icon: 'mdi-home',
          active: true,
          items: [
            { title: env.keyOrder, icon: 'mdi-view-list', route: '/' },
            { title: env.keyProfile, icon: 'mdi-account', route: '/profile' },
            { title: env.keySetting, icon: 'mdi-cog', route: '/settings' },
          ],
        },
      ]

      if (this.isRoleSuper) {

        items = [
          ...items,
          { title: env.keyAdministration,
            icon: 'mdi-tune',
            active: true,
            items: [
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ]
          },
          { title: env.keySystem,
            icon: 'mdi-database-settings',
            active: true,
            // items: [
            //   { title: env.addUser, icon: 'mdi-account-plus', route: '/system/add-account' },
            //   { title: env.listUsers, icon: 'mdi-account-multiple', route: '/system/accounts' },
            //   { title: env.addComponent, icon: 'mdi-database-plus', route: '/system/add-component' },
            //   { title: env.listComponents, icon: 'mdi-database', route: '/system/components' },
            //   { title: env.keySystem, icon: 'mdi-database-cog', route: '/system/test-1' },
            // ],
          },
        ]

      } else if (this.isRoleAdmin) {

        items = [
          ...items,
          { title: env.keyAdministration,
            icon: 'mdi-tune',
            active: true,
            items: [
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ]
          },
        ]
      }
      return items
    }

  },
  data: () => ({
      env,
      mini: true,
      avatar: null,
      icon: 'mdi-magnify',
      iconVisibleDrawer: 'mdi-dots-vertical',
      selection: 'addOrder',
      ordersList: [],
      dialog: false,
      isRoleAdmin: false,
      isRoleSuper: false,
      drawer: true,
      drawerRight: true,
      items: [],
      right: null,

      skinColor: '',
      eyebrowType: '',
      eyeType: '',
      mouthType: '',
      facialHairType: '',
      facialHairColor: '',
      hairColor: '',
      topType: '',
      topColor: '',
      accessoriesType: '',
      clotheType: '',
      clotheColor: '',
      graphicType: '',
      isCircle: true,
      circleColor: '',

  }),

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

  mounted() {
    // this.avatar = avatars[Math.floor(Math.random() * avatars.length)]


    if (localStorage.getItem('avatar')) {
      const ava = JSON.parse(localStorage.getItem('avatar'))

      this.skinColor = ava.skinColor
      this.eyebrowType = ava.eyebrowType
      this.eyeType = ava.eyeType
      this.mouthType = ava.mouthType
      this.facialHairType = ava.facialHairType
      this.facialHairColor = ava.facialHairColor
      this.hairColor = ava.hairColor
      this.topType = ava.topType
      this.topColor = ava.topColor
      this.accessoriesType = ava.accessoriesType
      this.clotheType = ava.clotheType
      this.clotheColor = ava.clotheColor
      this.graphicType = ava.graphicType
      this.isCircle = ava.isCircle
      this.circleColor = ava.circleColor
    }
  },

  methods: {
    ...mapMutations(['searchOrderMutation']),

    orders() {
      this.$router.push('/')
    },

    showDrawer() {
      this.drawerRight = !this.drawerRight
      bus.$emit('show-drawer', this.drawerRight)
    },

    async logout() {
      await this.$store.dispatch('logout')
      await this.$router.push('/login')
    },

    close() {
      this.dialog = false
    },

  }
}
</script>

<style scoped lang="scss">
.offset {
  margin-right: 127px;
}

</style>