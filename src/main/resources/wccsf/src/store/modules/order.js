import req from '../request';
import api from "../../api/backendApi";

export default {
    state: {
        orders: []
    },
    mutations: {
        updateOrdersMutation(state, orders) {
            state.orders = orders;
        },
        addOrderMutation(state, orders) {
            state.orders = [
                ...state.orders,
                orders
            ];
        },

        fetchOrderPageMutation(state, orders) {
            const targetOrders = state.orders
                .concat(orders)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})

            state.orders = Object.values(targetOrders)
        },

        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.currentPage = currentPage
        }
    },
    actions: {
        async fetchOrders({commit}) {
            const data = await req.request(api.API_ORDER_URL);
            commit('updateOrdersMutation', data.orders);
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))
        },

        async addOrder({commit}, order) {
            const data = await req.request(api.API_ORDER_URL, 'POST', order);
            // const index = state.orders.findIndex(item => item.id === data.id);
            // console.log(index);
            commit('addOrderMutation', data)
        },

        async loadPage({commit, state}) {
            try {
                const data = await req.request(api.API_ORDER_PAGE_URL + (state.currentPage + 1));

                // console.log('currentPage', data.currentPage)
                // console.log('totalPages', data.totalPages)
                // console.log('state currentPage', state.currentPage)
                // console.log('state totalPages', state.totalPages)
                // console.log('min', Math.min(data.currentPage, data.totalPages))

                commit('fetchOrderPageMutation', data.orders)
                commit('updateTotalPagesMutation', data.totalPages)
                commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))
            } catch (e) {
                // console.warn(e.message)
            }


        }

    },
    getters: {
        allOrders(state) {
            return (state.orders || []).sort((a, b) => -(a.id - b.id))
        }

    }
}