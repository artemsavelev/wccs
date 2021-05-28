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
                  max-width="400">

            <v-card-text>
              <v-avatar v-if="avatar" size="88">
                <v-img :src="`https://avataaars.io/${avatar}`" class="mb-6"></v-img>
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


    <v-navigation-drawer width="400" v-model="drawerRight" app clipped right>

      <AddUser/>

    </v-navigation-drawer>



  </div>
</template>

<script>
import env from '../../env.config.json'
// import api from '@/api/backendApi'
import {mapActions, mapGetters} from 'vuex'
import AddUser from "@/components/AddUser";

const avatars = [
  '?accessoriesType=Blank&avatarStyle=Circle&clotheColor=PastelGreen&clotheType=ShirtScoopNeck&eyeType=Wink&eyebrowType=UnibrowNatural&facialHairColor=Black&facialHairType=MoustacheMagnum&hairColor=Platinum&mouthType=Concerned&skinColor=Tanned&topType=Turban',
  '?accessoriesType=Sunglasses&avatarStyle=Circle&clotheColor=Gray02&clotheType=ShirtScoopNeck&eyeType=EyeRoll&eyebrowType=RaisedExcited&facialHairColor=Red&facialHairType=BeardMagestic&hairColor=Red&hatColor=White&mouthType=Twinkle&skinColor=DarkBrown&topType=LongHairBun',
  '?accessoriesType=Prescription02&avatarStyle=Circle&clotheColor=Black&clotheType=ShirtVNeck&eyeType=Surprised&eyebrowType=Angry&facialHairColor=Blonde&facialHairType=Blank&hairColor=Blonde&hatColor=PastelOrange&mouthType=Smile&skinColor=Black&topType=LongHairNotTooLong',
  '?accessoriesType=Round&avatarStyle=Circle&clotheColor=PastelOrange&clotheType=Overall&eyeType=Close&eyebrowType=AngryNatural&facialHairColor=Blonde&facialHairType=Blank&graphicType=Pizza&hairColor=Black&hatColor=PastelBlue&mouthType=Serious&skinColor=Light&topType=LongHairBigHair',
  '?accessoriesType=Kurt&avatarStyle=Circle&clotheColor=Gray01&clotheType=BlazerShirt&eyeType=Surprised&eyebrowType=Default&facialHairColor=Red&facialHairType=Blank&graphicType=Selena&hairColor=Red&hatColor=Blue02&mouthType=Twinkle&skinColor=Pale&topType=LongHairCurly',
]

const pause = ms => new Promise(resolve => setTimeout(resolve, ms))
export default {
  name: 'Users',
  components: {AddUser},
  data: () => ({

    ...mapActions(['fetchUsers']),

    env,
    drawerRight: true,
    active: [],
    avatar: null,
    open: [],
    users: [],
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

      return this.allUsers.find(user => user.id === id)
    },
  },

  watch: {
    selected: 'randomAvatar',
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

    randomAvatar () {
      this.avatar = avatars[Math.floor(Math.random() * avatars.length)]
    },
  },


  mounted() {
    this.fetchUsers()
  },
}
</script>

<style scoped>

</style>