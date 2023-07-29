<template>
    <div class="register-container">
      <h1>Register</h1>
      <el-form :model="userData" :rules="formRules" ref="registerForm" label-width="120px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="userData.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="userData.password"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input type="email" v-model="userData.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="registerUser">Register</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { registerAPI } from '@/apis/register';
import router from '@/router';
import {useUserStore} from '@/stores/user'
import { ElMessage } from 'element-plus';
  
  const registerForm = ref(null);
  
  const userData = ref({
    username: '',
    password: '',
    email: ''
  });
  
  const formRules = {
    username: [
      { required: true, message: 'Please enter the username', trigger: 'blur' }
    ],
    password: [
      { required: true, message: 'Please enter the password', trigger: 'blur' }
    ],
    email: [
      { required: true, message: 'Please enter the email', trigger: 'blur' },
      { type: 'email', message: 'Please enter a valid email', trigger: 'blur' }
    ]
  };
  
  const registerUser = async () => {
    try {
      const valid = await registerForm.value.validate();
      if (valid) {
        // 发送注册请求
        const res = await registerAPI(userData.value.username, userData.value.password, userData.value.email);
        console.log(res); // 处理后端响应
        if(res["message"]=="register success"){
            const userStore = useUserStore();
            userStore.username = userData.value.username; // 将用户名存储到user store中
            ElMessage({
            message: "注册成功，正在跳转到登录界面",
            type: 'success'
            })
            
            setTimeout(function() {
              router.push('/login');
            }, 1500); // 1500 毫秒即 1.5 秒
        }
        else{
          ElMessage({
            message: "错误,可能是账户名或者邮箱重复了，请重新输入",
            type: 'error'
        })
        }
      }
    } catch (error) {
      ElMessage({
        message: "错误,可能是账户名或者邮箱重复了，请重新输入",
        type: 'error'
      })
    }
  };
  </script>
  
  <style scoped>
  .register-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  </style>
  