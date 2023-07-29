import { useUserStore } from '@/stores/user'
import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from '../App.vue'
import { getUserAPI } from '@/apis/user'
import { baseImgURL } from './http'
const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
export const loadUserInfo=async ()=>{
    const userStore=useUserStore()
    if(userStore.username==""){
        //如果用户名为空，则往后端请求数据填充Store
        const res=await getUserAPI()
        const user = JSON.parse(res.user)
        userStore.setUsername(user.username);
        userStore.setEmail(user.Email)
        const avatar = baseImgURL + user.avatar
        userStore.setAvatar(avatar)
        userStore.setStatus(user.status)
    }
}