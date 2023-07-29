<script setup>
import { ref ,onMounted,getCurrentInstance} from 'vue'
import { useUserStore } from '@/stores/user'
import { loadUserInfo } from '@/utils/loadUserInfo';
import {sentCommentsAPI} from '@/apis/courseComments'
const emit = defineEmits(['reload']);
const userStore=useUserStore()
const textArea = ref('')
const rate = ref(null)
const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900'])
const instance = getCurrentInstance();
const route = instance.appContext.config.globalProperties.$route;
const submit= async()=>{
    const data={
        courseId: route.params.id,
        textArea: textArea.value,
        userName: userStore.username,
        rate: rate.value
    }
    const res=await sentCommentsAPI(data.courseId,data.textArea,data.userName,data.rate)
    if(res['message']=='addComment success'){
        ElMessage({
        message: '评论成功',
        type: 'success',
      })
      emit('reload')
    }
    else{
        ElMessage({
        message: '评论失败',
        type: 'error',
      })
    }
    textArea.value="";
    rate.value=null;
}
let userAvatar=ref('')
onMounted(async()=>{
    const temp=await loadUserInfo();
    userAvatar.value=userStore.avatar;
})
</script>
<template>
    <el-card class="box-card">
        <el-row>
            <el-col :span="4">
                <el-avatar :size="150" :src=userAvatar />
            </el-col>
            <el-col :span="16" style="display: flex;align-items: center;flex-direction: column;">
                <el-input v-model="textArea" :rows="5" type="textarea" placeholder="开始评论" />
                <el-text class="mx-1">评分</el-text><el-rate v-model="rate" :colors="colors" />
            </el-col>
            <el-col :span="4" style="display: flex;align-items: center;">
                <el-button type="primary" plain  @click="submit"  size="large" class="button_comment">发送</el-button>
            </el-col>
        </el-row>
    </el-card>
</template>
<style scoped>
.button_comment{
    height: 40%;
    width: 80%;
    margin-left: 50px;
}
.box-card {
    width: 100%;
    margin-bottom: 50px;
}
</style>