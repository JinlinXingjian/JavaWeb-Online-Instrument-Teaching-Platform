<script lang="ts" setup>
import { onMounted, reactive, ref, toRef, watch } from 'vue'
import {useUserStore } from '@/stores/user';
import { changeUsernameAPI } from '@/apis/userChange';
import { changeEmailAPI } from '@/apis/userChange';

const dialogUsernameVisible = ref(false)
const dialogEmailVisible = ref(false)
const formLabelWidth = '140px'

const userStore=useUserStore();

const dataLoaded=ref(false)

let userInfo = reactive({
  username: toRef(userStore.username),
  email: toRef(userStore.email),
  avatar: toRef(userStore.avatar),
  status: toRef(userStore.status)
})
//pinia此时还没有加载，于是启动监听，当变化的时候，手动赋值
watch(userStore,()=>{
    userInfo = reactive({
    username: userStore.username,
    email: userStore.email,
    avatar: userStore.avatar,
    status: userStore.status
  })
  dataLoaded.value=true
})
//如果pinia中的值不是空的，就意味着上面的watch已经赋值了，所有就直接渲染
onMounted(()=>{
  if(userStore.username!=""){
    dataLoaded.value=true
  }
})
// 修改用户名表单
const formUsername = reactive({
  username: ''
})

// 修改邮箱表单
const formEmail = reactive({
  email: ''
})

// 更新用户名
const updateUsername = async() => {
  if (formUsername.username) {
    const res=await changeUsernameAPI(userInfo.username,formUsername.username)
    if(res['message']=='changeUsername success'){
      userInfo.username = formUsername.username
      dialogUsernameVisible.value = false
    }
  }
}

// 更新邮箱
const updateEmail =async () => {
  if (formEmail.email) {
    const res=await changeEmailAPI(userInfo.username,userInfo.email,formEmail.email)
    if(res['message']=='changeEmail success'){
      userInfo.email = formEmail.email
      dialogEmailVisible.value = false
    }
  }
}

// setTimeout(() => {
//   console.log(userInfo);
  
//   dataLoaded.value=true
// }, 1000);
onMounted(()=>{

})
</script>
<template>
    <el-card class="box-card">
      <el-row v-if="dataLoaded">
        <el-col :span="6">
          <el-avatar :size="200" :src="userInfo.avatar" />
        </el-col>
        <el-col :span="18">
          <el-descriptions title="账户信息">
            <el-descriptions-item label="用户名">
              {{ userInfo.username }}
              <!-- 修改用户名按钮 -->
              <el-button style="margin: 10px;" @click="dialogUsernameVisible = true">
                修改用户名
              </el-button>
  
              <!-- 修改用户名对话框 -->
              <el-dialog v-model="dialogUsernameVisible" title="修改用户名">
                <el-form :model="formUsername">
                  <el-form-item label="新用户名" :label-width="formLabelWidth">
                    <el-input v-model="formUsername.username" autocomplete="off" />
                  </el-form-item>
                </el-form>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogUsernameVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateUsername">确认</el-button>
                  </span>
                </template>
              </el-dialog>
            </el-descriptions-item>
            <el-descriptions-item label="邮箱">
              {{ userInfo.email }}
              <!-- 修改邮箱按钮 -->
              <el-button style="margin: 10px;" @click="dialogEmailVisible = true">
                修改邮箱
              </el-button>
  
              <!-- 修改邮箱对话框 -->
              <el-dialog v-model="dialogEmailVisible" title="修改邮箱">
                <el-form :model="formEmail">
                  <el-form-item label="新邮箱" :label-width="formLabelWidth">
                    <el-input v-model="formEmail.email" autocomplete="off" />
                  </el-form-item>
                </el-form>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogEmailVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateEmail">确认</el-button>
                  </span>
                </template>
              </el-dialog>
            </el-descriptions-item>
            <el-descriptions-item label="用户状态">
              {{ userInfo.status }}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
    </el-card>
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
  