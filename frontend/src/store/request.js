import store from '@/store'
import router from '@/router'
import api from '../api/backendApi'


export default {
    async request(url, method = 'GET', data = null) {
        try {
            const user = JSON.parse(localStorage.getItem('user'))
            const headers = {}
            let body

            if (data) {
                headers['Content-type'] = 'application/json'
                headers['Authorization'] = 'bearer_' + user.token
                body = JSON.stringify(data)
            } else {
                headers['Content-type'] = 'application/json'
                headers['Authorization'] = 'bearer_' + user.token
            }

            const response = await fetch(url, {
                method,
                headers,
                body
            })

            if (response.ok) {
                if (data) {
                    const dataSuccess = {
                        message: 'Status code - ' + response.status + ': Запись ' + Object.values(data) + ' успешно добавлены.',
                        color: 'success',
                        icon: 'mdi-check-circle'
                    }
                    await store.dispatch('showSnack', dataSuccess)
                }
                return await response.json()
            } else {
                // if (url === api.API_ORDER_URL && response.status === 401) {
                //     localStorage.removeItem('user')
                //     await router.push({ path: '/login'})
                // }
                response.text().then(e => {
                    if (url === api.API_ORDER_URL && response.status === 401) {
                        const dataError = {
                            message: 'Error code - ' + response.status + ': Токен JWT истек или недействителен. Требуется авторизация',
                            color: 'error',
                            icon: 'mdi-alert-circle'
                        }
                        store.dispatch('showSnack', dataError)
                        router.push('/login')
                    } else {
                        const dataError = {
                            message: 'Error code - ' + response.status + ': С сообщением "' + e + '".',
                            color: 'error',
                            icon: 'mdi-alert-circle'
                        }
                        store.dispatch('showSnack', dataError)
                    }
                })
            }

        } catch (e) {
            console.warn(e)
        }
    }
}