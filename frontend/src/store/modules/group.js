import req from '../request'
import api from '../../api/backendApi'
import store from '@/store'

export default {
    state: {
        groups: []
    },

    mutations: {
        fetchGroupPageMutation(state, groups) {
            state.groups = groups
        },
    },

    actions: {
        async fetchGroup({commit}) {
            try {
                const data = await req.request(api.API_GET_SECTION_GROUP)
                commit('fetchGroupPageMutation', data)
            } catch (e) {
                const dataError = {
                    message: 'Error "' + e.message + '"',
                    color: 'error',
                    icon: 'mdi-alert-circle'
                }
                await store.dispatch('showSnack', dataError)
            }
        }
    },

    getters: {
        allGroups(state) {
            return (state.groups || []).sort((a, b) => (a.id - b.id))
        }
    }
}