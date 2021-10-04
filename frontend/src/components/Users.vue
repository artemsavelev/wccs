<template>
  <div>
    <v-row class="pa-4" justify="space-between">
      <v-col cols="5">
        <v-treeview :active.sync="active"
                    :items="items"
                    :load-children="fetch"
                    :open.sync="open"
                    open-all
                    activatable
                    color="warning"
                    open-on-click
                    dense
                    transition>
          <template v-slot:prepend="{ item }">
              <v-icon v-if="!item.children">
                mdi-account
              </v-icon>
          </template>

        </v-treeview>
      </v-col>

      <v-divider vertical></v-divider>

      <v-col class="d-flex text-center">
        <v-scroll-y-transition mode="out-in">
          <div v-if="!selected"
               class="title grey--text text--lighten-1 font-weight-light"
               style="align-self: center;">
            {{ env.selectUser }}
          </div>
          <v-card v-else
                  :key="selected.id"
                  class="pt-6 mx-auto"
                  flat
                  max-width="600">

            <v-card-text>
              <v-avatar size="88">
                <!--- with some props ---->
<!--                <avatars v-if="avatar" :isCircle="isCircle"-->
<!--                         :circleColor="circleColor"-->
<!--                         :skinColor="skinColor"-->
<!--                         :facialHairType="facialHairType"-->
<!--                         :facialHairColor="facialHairColor"-->
<!--                         :eyebrowType="eyebrowType"-->
<!--                         :eyeType="eyeType"-->
<!--                         :mouthType="mouthType"-->
<!--                         :hairColor="hairColor"-->
<!--                         :accessoriesType="accessoriesType"-->
<!--                         :topType="topType"-->
<!--                         :topColor="topColor"-->
<!--                         :clotheType="clotheType"-->
<!--                         :clotheColor="clotheColor"-->
<!--                         :graphicType="graphicType">-->
<!--                </avatars>-->
<!--                <avatars v-else></avatars>-->
                <avatars></avatars>
              </v-avatar>
              <h3 class="headline mb-2">
                {{ selected.name }}
              </h3>
              <div class="blue--text subheading font-weight-bold">
                {{ env.login }}: {{ selected.username }}
              </div>
            </v-card-text>

            <v-divider></v-divider>

            <v-row class="text-left" tag="v-card-text">

              <v-col class="text-right mr-4 mb-2" tag="strong" cols="5">
                {{ env.department }}:
              </v-col>
              <v-col>{{ selected.department.name }}</v-col>

              <v-col class="text-right mr-4 mb-2" tag="strong" cols="5">
                {{ env.position }}:
              </v-col>
              <v-col>
                <span v-for="(pos, i) in selected.positions" :key="i">
                  <span>{{ pos.name }} </span>
                </span>
              </v-col>

              <v-col class="text-right mr-4 mb-2" tag="strong" cols="5">
                {{ env.email }}:
              </v-col>
              <v-col>
                <a class="blue--text"
                   :href="`mailto:${selected.email}`"
                   target="_blank">{{ selected.email }}</a>
              </v-col>

            </v-row>
          </v-card>
        </v-scroll-y-transition>
      </v-col>
    </v-row>


    <v-navigation-drawer width="400" app clipped right>
      <UserForm :editUser="selected"/>
    </v-navigation-drawer>



  </div>
</template>

<script>
import env from '../../env.config.json'
import {mapActions, mapGetters} from 'vuex'
import UserForm from '@/components/UserForm'
import avatars from 'vuejs-avataaars'


const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: 'Users',
  components: {UserForm, avatars},
  data: () => ({

    ...mapActions(['fetchUsers']),

    env,
    active: [],
    avatar: JSON.parse(localStorage.getItem('avatar')),
    open: [],
    users: [],

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

  computed: {

    ...mapGetters(['allUsers']),

    items () {
      return [
        {
          name:  env.listUsers,
          children: this.allUsers
        },
      ]
    },
    selected () {
      if (!this.active.length) return undefined

      const id = this.active[0]

      //console.log(this.allUsers.find(user => user.id === id))

      return this.allUsers.find(user => user.id === id)
    },
  },

  watch: {
    // selected: 'randomAvatar',
  },

  methods: {
    async fetch (item) {
      // Remove in 6 months and say
      // you've made optimizations! :)
      await pause(1000)

      // const user = JSON.parse(localStorage.getItem('user'));
      // const headers = {}
      // headers['Content-type'] = 'application/json'
      // headers['Authorization'] = 'bearer_' + user.token

      // return fetch(api.API_ADMIN_URL, {
      //   headers
      // }).then(res =>
      //     res.json()
      // ).then(json => {
      //   (item.children.push(...json))
      //   // console.log(json)
      // }).catch(err => console.warn(err))

      const data = this.allUsers
      return item.children.push(...data)
    },


  },


  mounted() {
    this.fetchUsers()

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
}
</script>

<style scoped>

</style>