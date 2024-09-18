import axios from '@/plugins/axios'

export function getOhter() {
    return axios({
        url: 'about',
        method: 'GET'
    })
}
