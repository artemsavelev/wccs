import req from '@/store/request'
import api from '@/api/backendApi'

export default {
    state: {
        positions: []
    },
    mutations: {
        fetchPositionMutations(state, positions) {
            state.positions = positions
        },
    },
    actions: {
        async fetchPosition({commit}) {
            const data = await req.request(api.API_ALL_POSITION_URL)
            commit('fetchPositionMutations', data)
        },
    },
    getters: {
        allPositions(state) {
            return state.positions
        }
    }
}