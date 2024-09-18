import axios from '@/plugins/axios'

export function getWebData() {
    return axios({
        url: 'websites',
        method: 'GET'
    })
}
export function addViewsByWebname(webname) {
        return axios({
            url: 'webview',
            method: 'POST',
            params: {
                webname
            }
        })
}

