import req from '@/store/request'
import api from '@/api/backendApi'

export default {
    state: {
        users: []

    },
    mutations: {
        fetchUsersMutations(state, users) {
            state.users = users
        }
    },
    actions: {
        async fetchUsers({commit}) {
            const data = await req.request(api.API_ADMIN_URL)
            commit('fetchUsersMutations', data)
        }

    },
    getters: {
        allUsers(state) {
            return state.users
        }
    }
}