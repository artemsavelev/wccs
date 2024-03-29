import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import './assets/styles/styles.scss'
// import {connect} from "@/utils/ws";



Vue.config.productionTip = false;

// вызываем функцию коннекта по websocket
// connect();

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app');
