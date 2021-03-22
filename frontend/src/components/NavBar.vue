<template>
  <div>

    <v-navigation-drawer v-if="profile" v-model="drawer" width="400" app clipped>

      <v-list dense class="pa-0 font-weight-light">

        <v-list-group v-for="item in showItems"
                      :key="item.title"
                      active-class="grey darken-2 rounded-0"
                      color="white"
                      class=""
                      v-model="item.active">

          <template v-slot:activator >
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item class="pink--text rounded-0"
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

    <v-app-bar app color="primary" dark dense flat clipped-right clipped-left>

      <v-app-bar-nav-icon v-if="profile" @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>WCCS</v-toolbar-title>

<!--      <v-spacer ></v-spacer>-->

<!--      <v-col cols="12" sm="6" md="3">-->
<!--        <v-text-field v-model="search"-->
<!--                      :label="env.search"-->
<!--                      dense-->
<!--                      v-show="$route.path === '/'"-->
<!--                      class="mt-5">-->
<!--        </v-text-field>-->
<!--      </v-col>-->

      <v-spacer ></v-spacer>


      <v-icon v-if="profile && $route.path === '/'" class="ml-5 pa-1 offset" @click="showDrawer">{{ iconVisibleDrawer }}</v-icon>

      <v-btn v-if="profile" v-on:click="orders" :disabled="$route.path === '/'" text tile>{{ env.keyOrder }}</v-btn>

<!--      <v-btn v-if="profile" v-on:click="logout" text tile>-->
        <v-icon v-if="profile" class="ml-5 mr-5 pa-1" @click="logout">mdi-exit-to-app</v-icon>
<!--      </v-btn>-->


    </v-app-bar>

  </div>
</template>

<script>
import env from "../../env.config.json"
import { mapGetters } from "vuex";
import { bus } from "@/utils/bus";

export default {
  name: "NavBar",
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
              { title: env.addUser, icon: 'mdi-account-plus', route: '/admin/add-user' },
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.addComponent, icon: 'mdi-database-plus', route: '/admin/add-component' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ]
          },
          { title: env.keySystem,
            icon: 'mdi-database-settings',
            active: true,
            items: [
              { title: env.addUser, icon: 'mdi-account-plus', route: '/system/add-account' },
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/system/accounts' },
              { title: env.addComponent, icon: 'mdi-database-plus', route: '/system/add-component' },
              { title: env.listComponents, icon: 'mdi-database', route: '/system/components' },
              { title: env.keySystem, icon: 'mdi-database-cog', route: '/system/test-1' },
            ],
          },
        ]

      } else if (this.isRoleAdmin) {

        items = [
          ...items,
          { title: env.keyAdministration,
            icon: 'mdi-tune',
            active: true,
            items: [
              { title: env.addUser, icon: 'mdi-account-plus', route: '/admin/add-user' },
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.addComponent, icon: 'mdi-database-plus', route: '/admin/add-component' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ]
          },
        ]


      }
      return items;
    }

  },
  data() {
    return {
      env,
      icon: 'mdi-magnify',
      iconVisibleDrawer: 'mdi-arrow-expand-horizontal',
      selection: 'addOrder',
      ordersList: [],
      dialog: false,
      search:'',
      isRoleAdmin: false,
      isRoleSuper: false,
      drawer: true,
      drawerRight: true,
      items: [],
      right: null,
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

    showDrawer() {

      this.drawerRight = !this.drawerRight
      // this.drawerRight ? this.iconVisibleDrawer = 'mdi-arrow-expand-right' : this.iconVisibleDrawer ='mdi-arrow-expand-left'

      bus.$emit('show-drawer', this.drawerRight)
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
.offset {
  margin-right: 127px;
}
</style>