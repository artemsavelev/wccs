import req from '@/store/request'
import api from '@/api/backendApi'


export default {
    state: {
        user: {},
    },
    mutations: {
        addUserMutation(state, user) {
            state.user = user
        }
    },
    actions: {
        async addUser({commit}, user) {

            const data = await req.request(api.API_REGISTRATION_URL, 'POST', user)
            commit('addUserMutation', data)

        },
    },
    getters: {

    }
}