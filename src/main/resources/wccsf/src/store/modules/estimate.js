import req from "@/store/request";
import api from "@/api/backendApi";


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
        async fetchEstimate() {
            // const data = await req.request(api.API_GET_ESTIMATE_URL);
        },


        async addEstimate({commit}, estimate) {
            const data = await req.request(api.API_CREATE_ESTIMATE_URL, 'POST', estimate);
            commit('addEstimateMutation', data)
        },

    },
    getters: {

    }
}