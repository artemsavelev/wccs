import api from "../../api/backendApi";

const user = JSON.parse(localStorage.getItem('user'));
// console.log(user)

export default {
    state: {
        profile: user
    },

    mutations: {
        loginSuccess(state, profile) {
            state.profile = profile;
        },
        loginFailure(state) {
            localStorage.removeItem('user');
            state.profile = null;
        },
        logout(state) {
            localStorage.removeItem('user');
            state.profile = null;
        }
    },

    actions: {

        async login({ commit }, { username, password }) {
            try {
                const data = { username, password };
                // console.log(data)

                const response = await fetch(api.API_AUTH_URL, {
                    method: 'POST',
                    body: JSON.stringify(data),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (response.ok) {
                    const user = await response.json();
                    commit('loginSuccess', user);
                    localStorage.setItem('user', JSON.stringify(user));
                } else {
                    commit('setError', response.status);
                    await this.$router.push('/login')
                }

            } catch (e) {
                commit('loginFailure', e);
                throw e
            }
        },


        async logout({ commit }) {
            localStorage.removeItem('user');
            await commit('logout');
        }

    },

    getters: {
        profile(state) {
            // console.log(state.profile)
            return state.profile
        }
    }
}