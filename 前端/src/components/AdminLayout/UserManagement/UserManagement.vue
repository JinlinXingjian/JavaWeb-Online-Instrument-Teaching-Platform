<script setup>
import { reactive,  toRef,onMounted ,watch} from 'vue'
import {useUserStore } from '@/stores/user';
import ShowUsers from './AdminShowUsers.vue';
import { loadUserInfo } from '@/utils/loadUserInfo';
onMounted(()=>{
  loadUserInfo();
})
const userStore=useUserStore();
const userInfo = reactive({
  username: toRef(userStore.username),
  email: toRef(userStore.email),
  avatar: toRef(userStore.avatar),
  status: toRef(userStore.status)
})


// 监听 userStore 中的属性变化，并更新 userInfo 对象
watch(() => ({
  username: userStore.username,
  email: userStore.email,
  avatar: userStore.avatar,
  status: userStore.status
}), ({ username, email, avatar, status }) => {
  userInfo.username = username;
  userInfo.email = email;
  userInfo.avatar = avatar;
  userInfo.status
})

</script>
<template>
    <el-card class="box-card">
      <el-row>
        <el-col :span="6">
          <el-avatar :size="200" :src="userInfo.avatar" />
        </el-col>
        <el-col :span="18" >
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
    <ShowUsers/>
  </template>
  
  
  <style scoped>
  .box-card {
    width: 100%;
    margin-bottom: 40px;
  }
  </style>
  