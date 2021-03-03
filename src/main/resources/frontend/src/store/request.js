


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
                return await response.json()
            } else {
                console.warn(response.status)
                // await response.json().then((e) => {
                //     console.warn(e.message)
                // })
            }

        } catch (e) {
            console.warn('Error request: ', e.message)
        }
    }


}