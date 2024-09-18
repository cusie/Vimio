import axios from 'axios';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import { Message } from 'element-ui';

const request = axios.create({
    baseURL: 'http://up.qbox.me',  // 您可能需要替换成七牛云的 API 地址
    timeout: 30000,
});

// request interceptor
request.interceptors.request.use(config => {
    NProgress.start();
    config.headers['Accept'] = 'application/json';
    const token = localStorage.getItem('qiniuToken');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, error => {
    console.info(error);
    return Promise.reject(error);
});

// response interceptor
request.interceptors.response.use(response => {
    NProgress.done();
    return response.data;
}, error => {
    console.info(error);
    Message.error(error.message);
    return Promise.reject(error);
});

// 获取存储空间下指定目录的文件列表
export function getBucketContents(bucket, path) {
    path = path.startsWith('/')? path : `/${path}`;
    return request({
        url: `/${bucket}${path}`,
        method: 'GET',
    });
}

// 删除文件
export function delFile(bucket, path) {
    return request({
        url: `/${bucket}${path}`,
        method: 'DELETE',
    });
}

// 上传文件至仓库指定目录下
export function upload(bucket, path, fileName, file) {
    path = path.startsWith('/') ? path : `/${path}`;
    path = path.endsWith('/') ? path : `${path}/`;

    const token = localStorage.getItem('qiniuToken');
    const uploadUrl = `http://up.qbox.me/resources/${fileName}`;  // 七牛云的上传 API 地址

    const formData = new FormData();
    formData.append('token', token);
    formData.append('key', fileName);
    formData.append('file', file);

    return axios({
        url: uploadUrl,
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/octet-stream',
        },
        data: file,
    });
}
