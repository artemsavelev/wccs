import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import order from './modules/order'
import errors from "./modules/errors";

Vue.use(Vuex);

export default new Vuex.Store({

  modules: {
    order,
    auth,
    errors
  }
})
