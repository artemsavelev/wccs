import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({

    theme: {
        themes: {
            light: {
                primary: '#7D30FA', // #E53935
                secondary: '#FFCDD2', // #FFCDD2
                accent: '#3F51B5', // #3F51B5
                headerTable: '#2D4059', // #3F51B5
                headerForm: '#2D4059', // #3F51B5
            },
        },
    },

});
