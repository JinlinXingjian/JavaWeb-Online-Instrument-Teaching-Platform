<template>
    <el-card class="box-card">
        <el-form :label-position="labelPosition" label-width="200px" :model="form"
            style="max-width: 50vw;margin-left: 10vw;">
            <el-form-item label="旧密码">
                <el-input v-model="form.oldPassword" />
            </el-form-item>
            <el-form-item label="新密码">
                <el-input v-model="form.newPassword" @input="validateNewPassword" />
                <div v-if="!isPasswordValid" class="error-message">新密码不能和旧密码一样</div>
            </el-form-item>
            <el-form-item label="再次输入新密码">
                <el-input v-model="form.newAgainPassword" @input="validateNewAgainPassword" />
                <div v-if="!isPasswordMatch" class="error-message">两次输入的密码不一致</div>
            </el-form-item>
            <el-form-item label="">
                <el-button @click="changePassword" :disabled="!isFormValid">
          <el-tooltip class="box-item" effect="light" :content="isFormValid ? '修改密码' : '请先完成表单验证'" placement="top">
            提交
          </el-tooltip>
        </el-button>


            </el-form-item>
        </el-form>
    </el-card>
</template>
  
<script setup>
import { reactive, ref } from 'vue'
import { changePasswordAPI } from '@/apis/userChange.js';

import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus'
import router from '@/router';

const userStore=useUserStore();

const labelPosition = ref('right')
const form = reactive({
    oldPassword: '',
    newPassword: '',
    newAgainPassword: ''
})

const isPasswordValid = ref(true)
const isPasswordMatch = ref(true)
const isFormValid = ref(false)
function validateNewPassword() {
    isPasswordValid.value = form.newPassword !== form.oldPassword
    updateFormValidity()
}

function validateNewAgainPassword() {
    isPasswordMatch.value = form.newAgainPassword === form.newPassword
    updateFormValidity()
}

function updateFormValidity() {
    isFormValid.value = isPasswordValid.value && isPasswordMatch.value
}


async function  changePassword() {
    const res=await changePasswordAPI(userStore.username,form.oldPassword,form.newPassword) 
    if(res['message']=='changePassword success'){
        ElMessage({
          message: '修改密码成功,即将跳转到登录页面',
          type: 'success',
        })
        setTimeout(() => {
            router.push('/login')
        }, 1500);
    }
    else if(res['message']=='password error'){
        ElMessage.error('密码错误')
    }
}

</script>
  
<style scoped>
.el-card {
    margin: 0;
    min-height: 300px;
}

.el-button {
    margin: 0 auto;
}

.error-message {
    color: red;
}
</style>
  