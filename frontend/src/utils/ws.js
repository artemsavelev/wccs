import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'
import api from '@/api/backendApi'

let stompClient = null
const handlers = []
const user = JSON.parse(localStorage.getItem('user'))

// функция инициализации соединения по websocket
export function connect() {

    stompClient = Stomp.over(() => {
        return new SockJS(api.host + ':8080/gs-guide-websocket/')
    })
    stompClient.debug = (e) => {
        console.log(e)
    }
    stompClient.reconnect_delay = 5000
    stompClient.connect({}, () => {
        if (user) {
            stompClient.subscribe('/topic/messages/' + user.department.id, message => {
                handlers.forEach(handler => {
                    handler(JSON.parse(message.body))
                })
            })
        }
    })
}

// функция заполнения массива сообщениями в компоненте App.vue
export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
}

// функция отправки сообщений через websocket
// не используется если есть WsSender на бекэнде
export function sendMessage(message) {
    const username = user.username
    const order = {
        ...message,
        author: {
            username,
        }
    }
    stompClient.send("/app/sendMessage", {}, JSON.stringify(order))
}