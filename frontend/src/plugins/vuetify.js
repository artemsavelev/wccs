import Vue from 'vue'
import Vuetify from 'vuetify/lib'

Vue.use(Vuetify)

export default new Vuetify({

    theme: {
        options: {
            customProperties: true,
            // themeCache: {
            //     get: key => localStorage.getItem(key),
            //     set: (key, value) => localStorage.setItem(key, value),
            // },
        },
        themes: {
            light: {
                // primary: '#7D30FA',
                primary: '#5C6BC0',
                form: '#5C6BC0',
                // table: '#2D4059',
                table: '#616161',
                line: '#ccc',
                line2: '#ccc',
                hoverItem: '#f6f6f6',
                textColor: '#424242',
                borderColor: '#9E9E9E',
                scrollbarTrack: '#fff',
                backgroundSearchPanel: '#fff'
            },
            dark: {
                // primary: '#512DA8',
                primary: '#5C6BC0',
                form: '#000000',
                table: '#424242',
                line: '#424242',
                line2: '#393939',
                iconLight: '#757575',
                hoverItem: '#212121',
                textColor: '#BDBDBD',
                borderColor: '#424242',
                inputTextColor: '#fff',
                scrollbarTrack: '#424242',
                backgroundOrderItem: '#212121',
                backgroundSearchPanel: '#212121'
            },
        },
    },

});
