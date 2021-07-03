import store from '@/store'


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
                        message: 'Status code - ' + response.status + ': Данные ' + Object.values(data) + ' успешно добавлены.',
                        color: 'success',
                        icon: 'mdi-check-circle'
                    }

                    await store.dispatch('showSnack', dataSuccess)
                }

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
            console.log(e)

            // const dataError = {
            //     message: 'Error code - ' + e.status + ': С сообщением "' + e.message + '".',
            //     color: 'error',
            //     icon: 'mdi-alert-circle'
            // }
            //
            // await store.dispatch('showSnack', dataError)
            //
            // throw new e
        }

    }


}