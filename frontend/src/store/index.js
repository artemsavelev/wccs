import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import order from './modules/order'
import errors from './modules/errors'
import estimate from './modules/estimate'
import components from './modules/components'
import registration from './modules/registration'
import snackbar from './modules/snackbar'

Vue.use(Vuex);

export default new Vuex.Store({

  modules: {
    order,
    auth,
    errors,
    estimate,
    components,
    registration,
    snackbar
  }
})
