import req from "@/store/headers";
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
        async addEstimate({commit}, estimate) {
            const data = await req.responseData(api.API_CREATE_ESTIMATE_URL, 'POST', estimate);
            const est = await data.json();
            commit('addEstimateMutation', est)
        },

    },
    getters: {

    }
}