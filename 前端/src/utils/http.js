import axios from 'axios';

export const baseURL = 'http://localhost';
 

export const baseImgURL = 'http://localhost/static/avatar/';

const http = axios.create({
  baseURL: baseURL,
  timeout: 10000
}); 
// 设置全局的 baseURL
// axios.defaults.baseURL = baseURL
// const http=axios



// axios请求拦截器
http.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token'); // 从本地存储中获取 Token
    config.headers['Authorization'] = `${token}`; // 设置请求头中的 Authorization 字段
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// axios响应拦截器
http.interceptors.response.use(
  response => {
    if(response.data==undefined){
      console.log("注意！");
    }
    return response.data;
  },
  error => {
    if(error.response==undefined){
      return Promise.reject(error);
    }
    if (error.response.status === 401) {
        ElMessage({
          message: '未登录,请先登录',
          type: 'error',
        })
        setTimeout(() => {
                // 未授权，跳转到登录页面
        window.location.href = '/login';
        }, 1500);

    }
    return Promise.reject(error);
  }
);

export default http;
