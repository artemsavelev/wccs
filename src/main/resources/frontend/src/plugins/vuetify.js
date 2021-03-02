import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({

    theme: {
        options: {
            customProperties: true,
        },
        themes: {
            light: {
                primary: '#7D30FA',
                table: '#2D4059',
                form: '#0085ff',
                line: '#ccc',
                hoverItem: '#f6f6f6',
                textColor: '#424242',
                borderColor: '#9E9E9E'
            },
            dark: {
                primary: '#512DA8',
                form: '#000000',
                table: '#424242',
                line: '#212121',
                line2: '#393939',
                iconLight: '#757575',
                hoverItem: '#212121',
                inputTextColor: '#fff',
                textColor: '#BDBDBD',
                borderColor: '#424242'
            },
        },
    },

});
