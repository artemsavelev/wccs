import store from '@/store'


export default {
    async request(url, method = 'GET', data = null) {
        try {
            const user = JSON.parse(localStorage.getItem('user'));
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

                // const data = {
                //     message: 'Status code - ' + response.status + ': Данные успешно добавлены.',
                //     color: 'success'
                // }
                //
                // await store.dispatch('showSnack', data)

                return await response.json()
            } else {

                await response.json().then((e) => {

                    const dataError = {
                        message: 'Error code - ' + response.status + ': С сообщением "' + e.message + '".',
                        color: 'error',
                        icon: 'mdi-alert-circle'
                    }
                    store.dispatch('showSnack', dataError)

                })

            }


        } catch (e) {

            // console.warn(e)

        }
    }


}