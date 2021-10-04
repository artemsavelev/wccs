

export function encode(item) {
    return encodeURIComponent(item).replace(/[!'()*]/g, (c) => {
        return '%' + c.charCodeAt(0).toString(16)
    })
}