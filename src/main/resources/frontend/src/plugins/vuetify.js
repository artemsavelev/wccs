import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({

    theme: {
        themes: {
            light: {
                primary: '#7D30FA',
                secondary: '#FFCDD2',
                accent: '#3F51B5',
                table: '#2D4059',
                form: '#0085ff',
            },
        },
    },

});
