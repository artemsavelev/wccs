import req from "@/store/headers";
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
            if (localStorage.getItem('user') !== null) {
                const data = await req.requestData(api.API_DEVICE_URL, 'GET')
                const devices = await data.json();
                commit('updateDeviceMutation', devices);
            } else {
                // errors
            }
        },
        async addDevice({commit}, device) {
            const data = await req.responseData(api.API_DEVICE_URL, 'POST', device)
            const dev = await data.json();
            commit('addDeviceMutation', dev)
        },


        // get materials list from backend
        async fetchMaterials({commit}) {
            if (localStorage.getItem('user') !== null) {
                const data = await req.requestData(api.API_MATERIAL_URL, 'GET')
                const materials = await data.json();
                commit('updateMaterialMutation', materials);
            } else {
                // errors
            }
        },
        async addMaterial({commit}, material) {
            const data = await req.responseData(api.API_MATERIAL_URL, 'POST', material)
            const mat = await data.json();
            commit('addMaterialMutation', mat)
        },


        // get works list from backend
        async fetchWorks({commit}) {
            if (localStorage.getItem('user') !== null) {
                const data = await req.requestData(api.API_WORK_URL, 'GET')
                const works = await data.json();
                commit('updateWorkMutation', works);
            } else {
                // errors
            }
        },
        async addWork({commit}, work) {
            const data = await req.responseData(api.API_WORK_URL, 'POST', work)
            const wor = await data.json();
            commit('addWorkMutation', wor)
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