<template>
    <div class="main">
        <div class="title">
        <h1 style="color: aliceblue;">在线乐理学习平台</h1>
    </div>
    <el-form ref="loginForm" :model="form" label-width="80px" class="login-form">
        <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="输入你的用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password" placeholder="输入你的密码"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm">登录</el-button>
            <RouterLink to="/register">
                <el-button type="primary">注册</el-button>
            </RouterLink>
            <RouterLink to="/findpassword">
                <el-button type="primary">找回密码</el-button>
            </RouterLink>
        </el-form-item>
    </el-form>
    </div>
</template>
  
<script setup>
import { ref } from 'vue';
import { loginAPI } from '@/apis/login.js';
import router from '@/router';
import { useUserStore } from '@/stores/user';
import { baseImgURL } from '@/utils/http'


const form = ref({
    username: '',
    password: ''
});

const submitForm = async () => {
    const isValid = validateForm();

    if (isValid) {
        const res=await loginAPI(form.value.username,form.value.password)
        console.log(res.message);
        if(res.message=='login success'){
            // 从响应中获取Token并保存到本地存储
            const token = res.token;
            localStorage.setItem('token', token);
            // 进行其他操作，比如重定向到其他页面
            const userStore = useUserStore();
            const user = JSON.parse(res.user)
            userStore.setUsername(user.username);
            userStore.setEmail(user.Email)
            const avatar = baseImgURL + user.avatar
            userStore.setAvatar(avatar) 
            userStore.setStatus(user.status)
            //如果是管理员，跳转到管理员界面
            if(user.role=='admin'){
                router.push('/admin')
            }
            else{
                router.push('/'); 
            }
            ElMessage({
              message: '登录成功',
              type: 'success',
            })
        }
        else if (res.message=='login fail') {
            ElMessage({
              message: '登录失败',
              type: 'error',
            })
        }
        else if (res.message=='user ban') {
            ElMessage({
              message: '账号已封禁',
              type: 'error',
            })
        }
        else{
            ElMessage({
              message: '异常',
              type: 'error',
            }) 
        }
    } 
    else {
        console.log('Form validation failed.');
    }
};

const validateForm = () => {
    return new Promise((resolve) => {
        resolve(form.value.username !== '' && form.value.password !== '');
    });
};



</script>
  
<style scoped>
.main{
    min-width: 80vw;
    min-height: 100vh;
    background-image: url(http://localhost/static/swiper/2.jpg);
    background-size: cover;
}
.login-form {
    max-width: 400px;
    margin: 0 auto;
    padding-top: 100px;
}
.title{
    width: 100%;
    text-align: center;
    margin-top: 100px;
}
</style>
  