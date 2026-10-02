import axios from 'axios'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus' // 如果没有用UI库，可暂时注释，用 alert 代替

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

// 请求拦截器：自动携带 Token
request.interceptors.request.use(config => {
  const userStore = useUserStore()
  if (userStore.token) {
    config.headers['Authorization'] = `Bearer ${userStore.token}`
  }
  return config
})

// 响应拦截器：统一处理返回结果
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res.data // 直接返回 data 部分，方便页面使用
    } else {
      alert(res.message || '请求失败')
      return Promise.reject(new Error(res.message || 'Error'))
    }
  },
  error => {
    if (error.response && error.response.status === 401) {
      alert('登录已过期，请重新登录')
      const userStore = useUserStore()
      userStore.logout()
      window.location.href = '/login'
    } else {
      alert(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default request