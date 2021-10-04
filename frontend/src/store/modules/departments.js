import req from '@/store/request'
import api from '@/api/backendApi'

export default {
    state: {
        departments: []
    },
    mutations: {
        fetchDepartmentMutations(state, departments) {
            state.departments = departments
        },
    },
    actions: {
        async fetchDepartment({commit}) {
            const data = await req.request(api.API_ALL_DEPARTMENT_URL)
            commit('fetchDepartmentMutations', data)
        },
    },
    getters: {
        allDepartment(state) {
            return state.departments
        }
    }
}