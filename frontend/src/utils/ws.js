
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

let stompClient = null
const handlers = []

// функция коннекта websocket
export function connect() {
    const user = JSON.parse(localStorage.getItem('user'));
    const socket = new SockJS('http://192.168.88.2/gs-guide-websocket')
    stompClient = Stomp.over(socket)
    stompClient.debug = () => {}
    stompClient.connect({
        'Content-type': 'application/json',
        'Authorization': 'bearer_' + user.token
    }, () => {
        //console.log(frame)
        stompClient.subscribe('/topic/messages', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        }, {
            'Content-type': 'application/json',
            'Authorization': 'bearer_' + user.token
        })
    })
}

// функция заполнения массива сообщениями
// addMessageMutations в компоненте App.vue
export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    //console.log("Disconnected")
}

// функция отправки сообщений через websocket
export function sendMessage(message) {
    const user = JSON.parse(localStorage.getItem('user'));
    // console.log(message)
    stompClient.send("/app/sendMessage", {
        'Content-type': 'application/json',
        'Authorization': 'bearer_' + user.token
    }, JSON.stringify(message))
}