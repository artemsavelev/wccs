
export default {

    state: {
        payload: {},
    },

    mutations: {
        setSnack (state, payload) {
            state.payload = payload

            // console.log(state.payload)
        }
    },
    actions: {
        showSnack({ commit }, payload) {
            commit('setSnack', payload)
        },
    },
    getters: {

        snackData(state) {
            return state.payload
        }
    }
}