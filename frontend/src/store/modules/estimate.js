import req from '@/store/request'
import api from '@/api/backendApi'
import store from '@/store'
import {encode} from '@/utils/encode'


export default {
    state: {
        estimate: [],
        file: null
    },
    mutations: {
        loadEstimateMutation(state, estimate) {
            state.estimate = estimate
        },
        addEstimateMutation(state, estimate) {
            state.estimate = estimate
        }
    },
    actions: {
        async loadEstimate({commit}, obj) {

            //
            const customer = encode(obj.customer)
            const address = encode(obj.address)

            const data = await req.request(api.API_GET_ESTIMATE_URL + '?customer=' + customer + '&address=' + address)
            if (data.length) {
                commit('loadEstimateMutation', data)
            } else {
                const dataError = {
                    message: 'Сметы заказчиком: ' + obj.customer + ' и адресом: '+ obj.address + ' в базе нет',
                    color: 'warning',
                    icon: 'mdi-alert'
                }
                await store.dispatch('showSnack', dataError)
            }
        },

        async addEstimate({commit}, estimate) {
            const data = await req.request(api.API_CREATE_ESTIMATE_URL, 'POST', estimate)
            commit('addEstimateMutation', data)
        },

    },
    getters: {
        getEstimate(state) {
            // return (state.estimate || []).sort((a, b) => -(a.id - b.id))
            return state.estimate
        }
    }
}