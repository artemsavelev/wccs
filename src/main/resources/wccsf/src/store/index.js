import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import order from './modules/order'
import errors from "./modules/errors"
import estimate from "./modules/estimate"
import components from "@/store/modules/components";

Vue.use(Vuex);

export default new Vuex.Store({

  modules: {
    order,
    auth,
    errors,
    estimate,
    components
  }
})
