<template>
    <el-form ref="FindPasswordForm" :model="form" label-width="80px" class="FindPassword-form">
      <el-form-item label="邮箱地址" prop="email">
        <el-input v-model="form.email" placeholder="请输入你的邮箱地址"></el-input>
        <el-form-item>
        <el-button type="primary" @click="sentCode">发送</el-button>
      </el-form-item>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-input v-model="form.code" placeholder="输入发送到邮箱的验证码"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="form.newPassword" placeholder="请输入你的新密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">修改密码</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { FindPasswordAPI,VerificationCode } from '@/apis/findPassword.js';
import router from '@/router';
  
  const form = ref({
    email: '',
    code: '',
    newPassword: ''
  });
  
  const sentCode = async () => {
    const isValid = await valiEmail();
  
    if (isValid) {
      // 调用 API 发送找回密码请求
      const res = await FindPasswordAPI(form.value.email);
      console.log(res);
      if(res['message']=='sentCode success'){
        ElMessage({
        message: '验证码已经发送',
        type: 'success',
      })
      }
      else{
        ElMessage({
        message: '验证码发送失败',
        type: 'error',
      })
      }
    } else {
      console.log('Form validation failed.');
    }
  };
  const submitForm = async () => {
    const isValid = await validateForm();
  
    if (isValid) {
      // 调用 API 发送找回密码请求
      const res = await VerificationCode(form.value.email,form.value.code,form.value.newPassword);
      if(res['message']=='changePassword success'){
        ElMessage({
        message: '修改密码成功',
        type: 'success',
      })
      router.push('/login')
      }
      else{
        ElMessage({
        message: '修改密码失败',
        type: 'error',
      })
      }
    } else {
      console.log('Form validation failed.');
    }
  };
  const valiEmail = () => {
    return new Promise((resolve) => {
      resolve(form.value.email !== '');
    });
  };
  const validateForm = () => {
    return new Promise((resolve) => {
      resolve(form.value.email !== '' && form.value.code !== '' && form.value.newPassword !== '');
    });
  };
  </script>
  
  <style scoped>
  .FindPassword-form {
    max-width: 400px;
    margin: 0 auto;
    padding-top: 100px;
  }
  </style>
  