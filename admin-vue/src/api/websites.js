import axios from '@/util/request'

// 获取网站数据，根据查询信息和类型
export function getWebDatas(queryInfo) {
    const { type, ...params } = queryInfo; // 解构出类型
    return axios({
        url: type ? `website/${type}` : 'websites', // 根据是否有类型选择 URL
        method: 'GET',
        params: {
            ...params
        }
    });
}
export function saveWebsite(form) {
    return axios({
        url: 'website',
        method: 'POST',
        data: {
            ...form
        }
    })
}

export function updateWebsite(form) {
    return axios({
        url: 'website',
        method: 'PUT',
        data: {
            ...form
        }
    })
}

export function deleteWebsiteId(id) {
    return axios({
        url: 'website',
        method: 'DELETE',
        params: {
            id
        }
    })
}
