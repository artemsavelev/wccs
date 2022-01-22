import req from '@/store/request'
import api from '@/api/backendApi'

export default {
    state: {
        users: [],
    },

    mutations: {
        fetchUsersMutations(state, users) {
            state.users = users
        },
        addUserMutation(state, user) {
            state.users = [
                ...state.users,
                user
            ]
        },
        updateUserMutation(state, user) {
            if (user) {
                const updateIndex = state.users.findIndex(item => item.id === user.id)
                state.users = [
                    ...state.users.slice(0, updateIndex),
                    user,
                    ...state.users.slice(updateIndex + 1)
                ]
            }
        },
    },

    actions: {
        async fetchUsers({commit}) {
            const data = await req.request(api.API_ADMIN_URL)
            commit('fetchUsersMutations', data)
        },
        async addUser({commit}, user) {
            const data = await req.request(api.API_REGISTRATION_URL, 'POST', user)
            commit('addUserMutation', data)
        },
        async editUser({commit}, user) {
            const data = await req.request(api.API_REGISTRATION_URL + user.id, 'PUT', user)
            commit('updateUserMutation', data)
        },
    },

    getters: {
        allUsers(state) {
            return (state.users || []).sort((a, b) => (a.id - b.id))
        }
    }
}