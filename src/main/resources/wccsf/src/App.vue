<template>
    <v-app>
        <NavBar/>
        <v-main>
            <div v-if="!profile && $route.path !== '/login'" class="ma-5">
                Необходимо <router-link to="/login" tag="a" class="nav-link">авторизоваться</router-link>
            </div>

            <router-view/>
        </v-main>
    </v-app>
</template>

<script>
    import NavBar from "./components/NavBar";
    import {mapGetters} from "vuex";

    export default {
        name: 'App',
        components: { NavBar },
        computed: mapGetters(['profile']),

    };
</script>

<style lang="scss">
    @import url('https://fonts.googleapis.com/css?family=Montserrat:200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap&subset=cyrillic,cyrillic-ext,latin-ext');
    @mixin font-montserrat {
        font-family: 'Montserrat', sans-serif;
    }
    #app {
        @include font-montserrat;
    }

    //$mainColor:
</style>
