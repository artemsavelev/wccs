import req from '../request'
import api from '../../api/backendApi'
import store from '@/store'

export default {
    state: {
        orders: []
    },
    mutations: {
        updateOrderMutation(state, order) {

            const updateIndex = state.orders.findIndex(item => item.id === order.id)

            // if (updateIndex > -1) {
                state.orders = [
                    ...state.orders.slice(0, updateIndex),
                    order,
                    ...state.orders.slice(updateIndex + 1)
                ]
            // } else {
            //     state.orders = order
            // }

        },

        removeOrderMutation(state, order) {

            console.log(order.id)


            const deletionIndex = state.orders.findIndex(item => item.id === order.id)
            console.log(deletionIndex)
            if (deletionIndex > -1) {
                state.orders = [
                    ...state.orders.slice(0, deletionIndex),
                    ...state.orders.slice(deletionIndex + 1)
                ]
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

                const data = await req.request(api.API_ORDER_URL)

                commit('fetchOrderPageMutation', data.orders)
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
                // console.log(index)

                if (index > -1) {
                    commit('updateOrderMutation', data)
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
            const data = await req.request(api.API_ORDER_URL + order.id, 'PUT', order)
            commit('updateOrderMutation', data)
        },

        async deleteOrder({ commit }, order) {
            console.log(order)
            const data = await req.request(api.API_ORDER_URL + order.id, 'DELETE')
            console.log(data)
            commit('removeOrderMutation', data)
        },

        async loadPage({commit, state}) {
            try {
                const data = await req.request(api.API_ORDER_PAGE_URL + (state.currentPage + 1))
                commit('fetchOrderPageMutation', data.orders)
                commit('updateTotalPagesMutation', data.totalPages)
                commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages))

            } catch (e) {

                const dataError = {
                    // message: 'Error "' + e.message + '". Записей больше нет.',
                    message: 'Записей больше нет.',
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