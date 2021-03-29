<template>
  <v-card class="mx-auto mt-2" max-width="1000" tile>

    <v-img height="100%" width="100%"
           src="https://ves-rf.ru/sites/default/files/article-img/20161119/6.jpg">

      <v-row align="end" class="fill-height">
        <v-col align-self="start" class="pa-0" cols="12">
          <v-avatar class="profile"
                    color="white"
                    size="364"
                    tile>

            <v-img :src="`https://avataaars.io/${avatar}`"></v-img>
          </v-avatar>

        </v-col>
        <v-col class="py-0">
          <v-list-item color="rgba(0, 0, 0, .4)" dark>
            <v-list-item-content>
              <v-list-item-title class="title">{{ profile.lastName }} {{ profile.firstName }}</v-list-item-title>
              <v-list-item-subtitle>{{ env.login }}: {{ profile.username }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{ env.department }}: {{ profile.department.name }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{ env.position }}:
                <span v-for="(pos, i) in profile.position"
                      :key="i + 1">
                  <span>{{ pos.name }} </span>
                </span></v-list-item-subtitle>
              <v-list-item-subtitle>{{ env.email }}:
                <a class="blue--text"
                   :href="`mailto:${profile.email}`"
                   target="_blank">{{ profile.email }}</a>
              </v-list-item-subtitle>
              <v-list-item-subtitle>{{ profile.roles }}</v-list-item-subtitle>
            </v-list-item-content>

          </v-list-item>
        </v-col>
      </v-row>
    </v-img>
  </v-card>
</template>

<script>
import env from '../../env.config.json'
import { mapGetters } from 'vuex'

export default {
  name: "Profile",
  computed: mapGetters(['profile']),
  data() {
    return {
      env,
      avatar: '?clotheType=BlazerShirt&eyeType=Default&mouthType=Smile&skinColor=Pale&topType=ShortHairShortRound',
    }
  }

}
</script>

<style scoped>

</style>