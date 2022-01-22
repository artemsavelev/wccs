import store from '@/store'
import api from '../api/backendApi'


export default {
    async request(url, method = 'GET', data = null) {
        // try {
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

                const reader = response.body.getReader()
                const err = new TextDecoder().decode((await reader.read()).value)
                const error = JSON.parse(err)

                console.error(error)

                const dataError = {
                    message: new Date(error.timestamp).toLocaleString('ru-RU') + ' - Error code - ' + error.status + ': С сообщением "'
                        + error.error + ': ' + error.message + '".',
                    color: 'error',
                    icon: 'mdi-alert-circle'
                }

                if (error.status === 401) {

                    dataError.message = 'Error code - ' + error.status + ': Токен JWT истек или недействителен. Требуется авторизация.'
                    await store.dispatch('showSnack', dataError)
                    await store.dispatch('logout')

                } else {
                    await store.dispatch('showSnack', dataError)
                }

            }

        // } catch (e) {
        //     // console.warn(e)
        // }
    }


}