


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
            return await response.json()
        } catch (e) {
            // console.warn('Error request: ', e.message)
        }
    }

    // requestData(url, method) {
    //     const user = JSON.parse(localStorage.getItem('user'));
    //
    //     if (user && user.token) {
    //         const options = {
    //             method: method,
    //             headers: new Headers({
    //                     'Content-type': 'application/json',
    //                     'Authorization': 'bearer_' + user.token
    //                 },
    //             ),
    //         };
    //
    //         // console.log(user.token)
    //         return fetch(url, options).catch(err => console.warn(err))
    //
    //     }
    // },
    //
    // responseData(url, method, data) {
    //     const user = JSON.parse(localStorage.getItem('user'));
    //
    //     if (user && user.token) {
    //         const options = {
    //             method: method,
    //             headers: new Headers({
    //                     'Content-type': 'application/json',
    //                     'Authorization': 'bearer_' + user.token
    //                 },
    //             ),
    //             body: JSON.stringify(data)
    //         };
    //
    //         return fetch(url, options).catch(err => console.warn(err))
    //     }
    // },

}