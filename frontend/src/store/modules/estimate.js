import req from '@/store/request'
import api from '@/api/backendApi'


export default {
    state: {
        estimate: {},
        file: null
    },
    mutations: {
        addEstimateMutation(state, estimate) {
            state.estimate = estimate
        }
    },
    actions: {
        // async fetchEstimate({commit}, file) {
        //     console.log(file)
        //     const data = await req.request(api.API_GET_FILE + file)
        //     console.log(data)
        //     commit('addEstimateMutation', data)
        // },

        async loadEstimate({commit}, id) {
            const data = await req.request(api.API_GET_ESTIMATE_URL + id)
            commit('addEstimateMutation', data)
        },

        async addEstimate({commit}, estimate) {
            const data = await req.request(api.API_CREATE_ESTIMATE_URL, 'POST', estimate)
            commit('addEstimateMutation', data)
        },

    },
    getters: {
        getEstimate(state) {
            return state.estimate
        }
    }
}