<template>
  <div>

    <v-navigation-drawer v-if="profile" v-model="drawer" width="400" app clipped>

      <v-list dense nav>

        <v-list-group
            v-for="item in showItems"
            :key="item.title"
            v-model="item.active">

          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item
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

      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>WCCS</v-toolbar-title>

      <v-spacer ></v-spacer>

<!--      <OrderForm v-bind:orderVal="ordersList"/>-->

      <v-btn v-if="profile" v-on:click="orders" :disabled="$route.path === '/'" text tile>{{ env.keyOrder }}</v-btn>
      <!--      <v-btn v-if="profile" v-on:click="userProfile" :disabled="$route.path === '/profile'" text tile>{{ env.keyProfile }}</v-btn>-->
      <!--      <v-btn v-if="profile" v-on:click="settings" :disabled="$route.path === '/settings'" text tile>{{ env.keySetting }}</v-btn>-->
      <!--      <v-btn v-if="profile && this.isRoleAdmin"-->
      <!--             v-on:click="admin" :disabled="$route.path === '/admin'" text tile>{{ env.keyAdministration }}</v-btn>-->
      <!--      <v-btn class="mr-10" v-if="profile && this.isRoleSuper"-->
      <!--             v-on:click="system" :disabled="$route.path === '/system'" text tile>{{ env.keySystem }}</v-btn>-->
      <v-btn v-if="profile" v-on:click="logout" text tile>
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>


    </v-app-bar>

  </div>
</template>

<script>
import env from "../../env.config.json"
import { mapGetters } from "vuex";

export default {
  name: "NavBar",
  computed: {
    ...mapGetters(['profile']),

    showItems() {


      let items = [
        { title: env.keyProfile,
          active: true,
          items: [
            { title: env.keyProfile, icon: 'mdi-view-dashboard', route: '/profile' },
            { title: env.keySetting, icon: 'mdi-cog', route: '/settings' },
          ],
        },
      ]

      if (this.isRoleSuper) {

        items = [
          ...items,
          { title: env.keyAdministration,
            active: true,
            items: [
              { title: env.addUser, icon: 'mdi-account-plus', route: '/admin/add-user' },
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.addComponent, icon: 'mdi-database-plus', route: '/admin/add-component' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ],
          },
          { title: env.keySystem,
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
            active: true,
            items: [
              { title: env.addUser, icon: 'mdi-account-plus', route: '/admin/add-user' },
              { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
              { title: env.addComponent, icon: 'mdi-database-plus', route: '/admin/add-component' },
              { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
            ],
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
      selection: 'addOrder',
      ordersList: [],
      dialog: false,
      search:'',
      isRoleAdmin: false,
      isRoleSuper: false,
      drawer: true,
      items: [
        // { title: env.keyProfile,
        //   active: true,
        //   items: [
        //     { title: env.keyProfile, icon: 'mdi-view-dashboard', route: '/profile' },
        //     { title: env.keySetting, icon: 'mdi-cog', route: '/settings' },
        //   ],
        // },
        // { title: env.keyAdministration,
        //   active: true,
        //   items: [
        //     { title: env.addUser, icon: 'mdi-account-plus', route: '/admin/add-user' },
        //     { title: env.listUsers, icon: 'mdi-account-multiple', route: '/admin/users' },
        //     { title: env.addComponent, icon: 'mdi-database-plus', route: '/admin/add-component' },
        //     { title: env.listComponents, icon: 'mdi-database', route: '/admin/components' },
        //   ],
        // },
        // { title: env.keySystem,
        //   active: true,
        //   items: [
        //     { title: env.addUser, icon: 'mdi-account-plus', route: '/system/add-account' },
        //     { title: env.listUsers, icon: 'mdi-account-multiple', route: '/system/accounts' },
        //     { title: env.addComponent, icon: 'mdi-database-plus', route: '/system/add-component' },
        //     { title: env.listComponents, icon: 'mdi-database', route: '/system/components' },
        //     { title: env.keySystem, icon: 'mdi-database-cog', route: '/system/test-1' },
        //   ],
        // },
      ],
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
    // userProfile() {
    //   this.$router.push('/profile')
    // },
    // settings() {
    //   this.$router.push('/settings')
    // },
    // admin() {
    //   this.$router.push('/admin')
    // },
    // system() {
    //   this.$router.push('/system')
    // },
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