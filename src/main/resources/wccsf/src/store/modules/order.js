import req from '../headers';
import api from "../../api/backendApi";

export default {
    state: {
        orders: []
    },
    mutations: {
        updateOrdersMutation(state, orders) {
            state.orders = orders;
            // console.log(orders);
        },
        addOrderMutation(state, orders) {
            state.orders = [
                ...state.orders,
                orders
            ];
            // console.log(orders);
        }
    },
    actions: {
        async fetchOrders({commit}) {
            const data = await req.requestData(api.API_ORDER_URL, 'GET');
            const orders = await data.json();
            // console.log(orders);
            commit('updateOrdersMutation', orders);
        },

        async addOrder({commit, state}, order) {
            const data = await req.responseData(api.API_ORDER_URL, 'POST', order);
            const orders = await data.json();
            const index = state.orders.findIndex(item => item.id === orders.id);
            console.log(orders);
            console.log(index);
            commit('addOrderMutation', orders)
        }

    },
    getters: {
        allOrders(state) {
            return (state.orders || []).sort((a, b) => (a.id - b.id))
        }

    }
}