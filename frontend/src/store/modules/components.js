import req from "@/store/request"
import api from "@/api/backendApi"


export default {
    state: {
        devices: [],
        materials: [],
        works: []
    },

    mutations: {
        // devices
        fetchDeviceMutation(state, devices) {
            state.devices = devices
        },
        updateDeviceMutation(state, device) {
            const updateIndex = state.devices.findIndex(item => item.id === device.id)
            state.devices = [
                ...state.devices.slice(0, updateIndex),
                device,
                ...state.devices.slice(updateIndex + 1)
            ]
        },
        addDeviceMutation(state, device) {
            state.devices = [
                ...state.devices,
                device
            ]
        },

        // materials
        fetchMaterialMutation(state, materials) {
            state.materials = materials
        },
        updateMaterialMutation(state, material) {
            const updateIndex = state.materials.findIndex(item => item.id === material.id)
            state.materials = [
                ...state.materials.slice(0, updateIndex),
                material,
                ...state.materials.slice(updateIndex + 1)
            ]
        },
        addMaterialMutation(state, materials) {
            state.materials = [
                ...state.materials,
                materials
            ]
        },

        // works
        fetchWorkMutation(state, works) {
            state.works = works
        },
        updateWorkMutation(state, work) {
            const updateIndex = state.works.findIndex(item => item.id === work.id)
            state.works = [
                ...state.works.slice(0, updateIndex),
                work,
                ...state.works.slice(updateIndex + 1)
            ]
        },
        addWorkMutation(state, works) {
            state.works = [
                ...state.works,
                works
            ]
        }
    },

    actions: {
        // get devices list from backend
        async fetchDevices({commit}) {
            const data = await req.request(api.API_DEVICE_URL)
            commit('fetchDeviceMutation', data)
        },
        async fetchDevicesForAdmin({commit}) {
            const data = await req.request(api.API_DEVICE_VIEW_SET_URL)
            commit('fetchDeviceMutation', data)
        },
        async addDevice({commit, state}, device) {
            const data = await req.request(api.API_DEVICE_URL, 'POST', device)
            const index = state.devices.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateDeviceMutation', data)
            } else {
                commit('addDeviceMutation', data)
            }
        },
        async updateDevice({commit}, device) {
            const data = await req.request(api.API_DEVICE_UPDATE_URL + device.id, 'PUT', device)
            commit('updateDeviceMutation', data)
        },
        async addToSetDevice({commit}, device) {
            const data = await req.request(api.API_DEVICE_TO_SET_URL + device.id, 'PATCH')
            commit('updateDeviceMutation', data)
        },
        async delSetDevice({commit}, device) {
            const data = await req.request(api.API_DEVICE_DEL_SET_URL + device.id, 'DELETE')
            commit('updateDeviceMutation', data)
        },


        // get materials list from backend
        async fetchMaterials({commit}) {
            const data = await req.request(api.API_MATERIAL_URL)
            commit('fetchMaterialMutation', data)
        },
        async fetchMaterialsForAdmin({commit}) {
            const data = await req.request(api.API_MATERIAL_VIEW_SET_URL)
            commit('fetchMaterialMutation', data)
        },
        async addMaterial({commit, state}, material) {
            const data = await req.request(api.API_MATERIAL_URL, 'POST', material)
            const index = state.materials.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateMaterialMutation', data)
            } else {
                commit('addMaterialMutation', data)
            }
        },
        async updateMaterial({commit}, material) {
            const data = await req.request(api.API_MATERIAL_UPDATE_URL + material.id, 'PUT', material)
            commit('updateMaterialMutation', data)
        },
        async addToSetMaterial({commit}, material) {
            const data = await req.request(api.API_MATERIAL_TO_SET_URL + material.id, 'PATCH')
            commit('updateMaterialMutation', data)
        },
        async delSetMaterial({commit}, material) {
            const data = await req.request(api.API_MATERIAL_DEL_SET_URL + material.id, 'DELETE')
            commit('updateMaterialMutation', data)
        },


        // get works list from backend
        async fetchWorks({commit}) {
            const data = await req.request(api.API_WORK_URL)
            commit('fetchWorkMutation', data)
        },
        async fetchWorksForAdmin({commit}) {
            const data = await req.request(api.API_WORK_VIEW_SET_URL)
            commit('fetchWorkMutation', data)
        },
        async addWork({commit, state}, work) {
            const data = await req.request(api.API_WORK_URL, 'POST', work)
            const index = state.works.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateWorkMutation', data)
            } else {
                commit('addWorkMutation', data)
            }
        },
        async updateWork({commit}, work) {
            const data = await req.request(api.API_WORK_UPDATE_URL + work.id, 'PUT', work)
            commit('updateWorkMutation', data)
        },
        async addToSetWork({commit}, work) {
            const data = await req.request(api.API_WORK_TO_SET_URL + work.id, 'PATCH')
            commit('updateWorkMutation', data)
        },
        async delSetWork({commit}, work) {
            const data = await req.request(api.API_WORK_DEL_SET_URL + work.id, 'DELETE')
            commit('updateWorkMutation', data)
        },

    },

    getters: {
        allDevices(state) {
            return (state.devices || []).sort((a, b) => (a.id - b.id))
        },

        allMaterials(state) {
            return (state.materials || []).sort((a, b) => (a.id - b.id))
        },

        allWorks(state) {
            return (state.works || []).sort((a, b) => (a.id - b.id))
        }
    }
}