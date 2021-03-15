import req from '../request';
import api from "../../api/backendApi";
import store from "@/store";

export default {
    state: {
        orders: []
    },
    mutations: {
        updateOrdersMutation(state, orders) {

            const updateIndex = state.orders.findIndex(item => item.id === orders.id)

            if (updateIndex > -1) {
                state.orders = [
                    ...state.orders.slice(0, updateIndex),
                    orders,
                    ...state.orders.slice(updateIndex + 1)
                ]
            } else {
                state.orders = orders
            }

        },

        addOrderMutation(state, orders) {
            state.orders = [
                ...state.orders,
                orders
            ]
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

            try{

                const data = await req.request(api.API_ORDER_URL);



                commit('updateOrdersMutation', data.orders);
                commit('updateTotalPagesMutation', data.totalPages)
                commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))



            } catch (e) {

                const dataError = {
                    message: 'Error "' + e.message + '"',
                    color: 'error',
                    icon: 'mdi-alert-circle'
                }

                await store.dispatch('showSnack', dataError)
            }

        },

        async addOrder({commit, state}, order) {

            try {

                const data = await req.request(api.API_ORDER_URL, 'POST', order)
                const index = state.orders.findIndex(item => item.id === data.id)
                // console.log(index);

                if (index > -1) {
                    commit('updateOrdersMutation', data)
                } else {
                    commit('addOrderMutation', data)
                }




            } catch (e) {

                const dataError = {
                    message: 'Error "' + e.message + '"',
                    color: 'error',
                    icon: 'mdi-alert-circle'
                }

                await store.dispatch('showSnack', dataError)
            }

        },

        async updateOrder({ commit }, order) {

            const data = await req.request(api.API_ORDER_URL, 'PUT', order)


            commit('updateOrdersMutation', data)

        },

        async loadPage({commit, state}) {
            try {

                const data = await req.request(api.API_ORDER_PAGE_URL + (state.currentPage + 1))
                commit('fetchOrderPageMutation', data.orders)
                commit('updateTotalPagesMutation', data.totalPages)
                commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))

            } catch (e) {

                const dataError = {
                    message: 'Error "' + e.message + '". Записей больше нет.',
                    color: 'info',
                    icon: 'mdi-information-variant'
                }

                await store.dispatch('showSnack', dataError)
            }

        }

    },
    getters: {
        allOrders(state) {
            return (state.orders || []).sort((a, b) => -(a.id - b.id))
        }

    }
}