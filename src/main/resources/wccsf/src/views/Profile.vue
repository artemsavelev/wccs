<template>
    <v-card class="mx-auto mt-2" max-width="1000" tile>

        <v-img height="100%" width="100%"
               src="https://ves-rf.ru/sites/default/files/article-img/20161119/6.jpg">

            <v-row align="end" class="fill-height">

                <v-col align-self="start" class="pa-0" cols="12">

                    <v-avatar class="profile"
                              color="grey"
                              size="364"
                              tile>

                        <v-img src="https://cdn.vuetifyjs.com/images/profiles/marcus.jpg"></v-img>

                    </v-avatar>

                </v-col>
                <v-col class="py-0">

                    <v-list-item color="rgba(0, 0, 0, .4)" dark>

                        <v-list-item-content>
                            <v-list-item-title class="title">{{profile.lastName}} {{profile.firstName}}</v-list-item-title>
                            <v-list-item-subtitle>Login: {{profile.username}}</v-list-item-subtitle>
                            <v-list-item-subtitle>E-mail: {{profile.email}}</v-list-item-subtitle>
                            <v-list-item-subtitle>Department: </v-list-item-subtitle>
                            <v-list-item-subtitle>Position: Network Engineer</v-list-item-subtitle>
                            <v-list-item-subtitle>{{profile.roles}}</v-list-item-subtitle>
                        </v-list-item-content>

                    </v-list-item>

                </v-col>
            </v-row>
        </v-img>
    </v-card>
</template>

<script>



    import {mapGetters} from "vuex";

    export default {
        name: "Profile",
        computed: mapGetters(['profile']),

    }
</script>

<style scoped>

</style>