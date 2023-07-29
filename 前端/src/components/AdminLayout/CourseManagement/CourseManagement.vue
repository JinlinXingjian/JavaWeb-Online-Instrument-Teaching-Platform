<script setup>
import { reactive,toRef } from 'vue'
import {useUserStore } from '@/stores/user';
import ShowAdminCourse from './AdminShowCourses.vue'

const userStore=useUserStore();
const userInfo = reactive({
  username: toRef(userStore.username),
  email: toRef(userStore.email),
  avatar: toRef(userStore.avatar),
  status: toRef(userStore.status)
})



</script>
<template>
    <el-card class="box-card">
      <el-row>
        <el-col :span="6">
          <el-avatar :size="200" :src="userInfo.avatar" />
        </el-col>
        <el-col :span="18">
          <el-descriptions title="账户信息">
            <el-descriptions-item label="用户名">
              {{ userInfo.username }}

            </el-descriptions-item>
            <el-descriptions-item label="邮箱">
              {{ userInfo.email }}
  
            </el-descriptions-item>
            <el-descriptions-item label="用户状态">normal</el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
    </el-card>
    <ShowAdminCourse/>
  </template>
  
  
  <style scoped>
  .el-card {
    margin: 0;
    min-height: 250px;
  }
  
  .el-row {
    display: flex;
    align-items: center;
  }
  
  .el-col {
    flex: 1;
  }
  
  .text {
    font-size: 14px;
  }
  
  .item {
    padding: 18px 0;
  }
  
  /* .box-card {
    width: 100%;
  } */
  </style>
  