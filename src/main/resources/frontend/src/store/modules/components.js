import req from "@/store/request";
import api from "@/api/backendApi";


export default {
    state: {
        devices: [],
        materials: [],
        works: []
    },
    mutations: {
        // devices
        updateDeviceMutation(state, devices) {
            state.devices = devices;
        },
        addDeviceMutation(state, device) {
            state.devices = device
        },

        // materials
        updateMaterialMutation(state, materials) {
            state.materials = materials;
        },
        addMaterialMutation(state, materials) {
            state.materials = materials
        },

        // works
        updateWorkMutation(state, works) {
            state.works = works;
        },
        addWorkMutation(state, works) {
            state.works = works
        }
    },
    actions: {
        // get devices list from backend
        async fetchDevices({commit}) {
            const data = await req.request(api.API_DEVICE_URL)
            commit('updateDeviceMutation', data);

        },
        async addDevice({commit}, device) {
            const data = await req.request(api.API_DEVICE_URL, 'POST', device)
            commit('addDeviceMutation', data)
        },


        // get materials list from backend
        async fetchMaterials({commit}) {
            const data = await req.request(api.API_MATERIAL_URL)
            commit('updateMaterialMutation', data);
        },
        async addMaterial({commit}, material) {
            const data = await req.request(api.API_MATERIAL_URL, 'POST', material)
            commit('addMaterialMutation', data)
        },


        // get works list from backend
        async fetchWorks({commit}) {
            const data = await req.request(api.API_WORK_URL)
            commit('updateWorkMutation', data);
        },
        async addWork({commit}, work) {
            const data = await req.request(api.API_WORK_URL, 'POST', work)
            commit('addWorkMutation', data)
        }

    },
    getters: {
        allDevices(state) {
            return state.devices
        },

        allMaterials(state) {
            return state.materials
        },

        allWorks(state) {
            return state.works
        }

    }
}