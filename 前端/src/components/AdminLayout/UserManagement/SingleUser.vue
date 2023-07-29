<script setup>

import { ref } from 'vue'
import {changenStatusAPI}from '@/apis/userChange'
import { useUserStore } from '@/stores/user';
const userStore=useUserStore()
const props = defineProps(['message']);
console.log(props.message.username);
const value = ref('')
const emit=defineEmits(['changeSuccess'])
const options = [
  {
    value: '正常',
    label: '正常',
  },
  {
    value: '封禁',
    label: '封禁',
  },
]

const handChangeUserStatus=async()=>{
    const res = await changenStatusAPI(userStore.username,props.message.username,value.value)
    if(res['message']=='changeStatus success'){
        ElMessage({
        message: '修改用户状态成功',
        type: 'success',
      })
      emit('changeSuccess')
    }
    else if(res['message']=='username error'){
        ElMessage({
        message: '当前账户状态异常',
        type: 'error',
      })
    }
    else {
        ElMessage({
        message: '更改失败',
        type: 'error',
      })
    }
}
</script>
<template> 
    <div class="SingleUserMain">
        <el-card class="box-card">
            <el-row>
                <el-col :span="6">
                    <el-avatar :size="200" :src="props.message.avatar" />
                </el-col>
                <el-col :span="18">
                    <el-descriptions title="账户信息">
                        <el-descriptions-item label="id">
                            {{ props.message.id }}
                        </el-descriptions-item>
                        <el-descriptions-item label="用户名">
                            {{ props.message.username }}

                        </el-descriptions-item>
                        <el-descriptions-item label="邮箱">
                            {{ props.message.email }}
                        </el-descriptions-item>
                        <el-descriptions-item label="用户状态">
                            {{props.message.status}}
                        </el-descriptions-item>
                        <el-descriptions-item label="更改状态">
                          <el-select v-model="value" class="m-2" placeholder="Select" size="large">
                          <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                          />
                        </el-select>
                        <el-button type="primary" round  @click="handChangeUserStatus">提交</el-button>
                        </el-descriptions-item>
                    </el-descriptions>
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>
<style>
.SingleUserMain {
    width: 100%;
}
</style>