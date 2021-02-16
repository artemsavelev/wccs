


export default {
    requestData(url, method) {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user && user.token) {
            const options = {
                method: method,
                headers: new Headers({
                        'Content-type': 'application/json',
                        'Authorization': 'bearer_' + user.token
                    },
                ),
            };

            // console.log(user.token)
            return fetch(url, options).catch(err => console.warn(err))

        }
    },

    responseData(url, method, data) {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user && user.token) {
            const options = {
                method: method,
                headers: new Headers({
                        'Content-type': 'application/json',
                        'Authorization': 'bearer_' + user.token
                    },
                ),
                body: JSON.stringify(data)
            };

            return fetch(url, options).catch(err => console.warn(err))
        }
    },

}