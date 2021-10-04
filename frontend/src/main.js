import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {connect} from '@/utils/ws'
import vuetify from './plugins/vuetify'
import './assets/styles/styles.scss'



Vue.config.productionTip = false;

// вызываем функцию коннекта по websocket
connect();

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app');
