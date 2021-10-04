import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import order from './modules/order'
import errors from './modules/errors'
import estimate from './modules/estimate'
import components from './modules/components'
import users from './modules/users'
import positions from './modules/positions'
import departments from './modules/departments'
import snackbar from './modules/snackbar'
import group from './modules/group'

Vue.use(Vuex);

export default new Vuex.Store({

  modules: {
    order,
    auth,
    errors,
    estimate,
    components,
    users,
    positions,
    departments,
    snackbar,
    group
  }
})
