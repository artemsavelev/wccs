<template>
  <v-card class="mx-auto mt-10" max-width="1000" tile>

    <v-img height="100%" width="100%"
           src="../../public/datacenter.jpg">

      <v-row align="end" class="fill-height">
        <v-col align-self="start" class="pa-0" cols="12">
          <v-avatar class="profile"
                    color="rgba(0, 0, 0, .1)"
                    size="400"
                    tile>

<!--            <v-img :src="`https://avataaars.io/${avatar}`"></v-img>-->
            <v-col>

            <!--- with some props ---->
              <avatars class="border"
                       :isCircle="isCircle"
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
            <ChangeAvatar/>
            </v-col>
          </v-avatar>

        </v-col>
        <v-col class="ml-11 py-0">
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
import avatars from "vuejs-avataaars";
import ChangeAvatar from "@/components/ChangeAvatar";

export default {
  name: "Profile",
  components: {ChangeAvatar, avatars},
  computed: mapGetters(['profile']),
  data: () => ({
    env,
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

  mounted() {
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
  }
}
</script>

<style scoped>
.border {
  width: 300px;
  height: 300px;
}
</style>