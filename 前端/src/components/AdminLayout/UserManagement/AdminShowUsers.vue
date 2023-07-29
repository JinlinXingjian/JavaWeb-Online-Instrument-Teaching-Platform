<script setup>
import { onMounted,ref } from 'vue';
import SingleUser from './SingleUser.vue';
import {getAllUserAPI} from '@/apis/user'
import { baseImgURL } from '@/utils/http';


let list = ref([]);
const  uploadList=async()=>{
    const res=await getAllUserAPI()
    if(res['message']=='getAllUsers success'){
        list.value=res['UserList']
        list.value.forEach(element => {
            element.avatar=baseImgURL+element.avatar
        });
    }
    else if(res['message']=='role error')
    {
        ElMessage({
            message: '您不是管理员',
            type: 'error',
        })
        // 跳转到指定的 URL
        window.location.href = '/';

    }
}
onMounted(()=>{
    uploadList()
})
</script>
<template>
    <div class="ShowUserMain">
        <h1>所有用户</h1>
        <SingleUser   v-for="item in list" :key="item.id" :message="item"  @changeSuccess="uploadList" />
    </div>
</template>
<style>
    .ShowUserMain{
        width: 100%;
    }
</style>