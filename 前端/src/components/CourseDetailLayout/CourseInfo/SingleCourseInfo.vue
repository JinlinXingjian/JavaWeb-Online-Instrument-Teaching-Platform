<script setup>
import { onMounted, ref } from 'vue';
import { getCourseAPI } from '@/apis/course';
import { baseURL } from '@/utils/http';
const props = defineProps(['courseId']);

// 访问父组件传递的数据
// console.log(props.courseId);

let course ={};
const isDataLoaded = ref(false);
onMounted(async () => {
    const res = await getCourseAPI(props.courseId);
    res.imgUrl=baseURL+res.imgUrl;
    course.value = res; // 通过 .value 属性更新 course 对象

    isDataLoaded.value = true;
    window.scrollTo(0, 0);

});
</script>

<template>
    <el-card v-if="isDataLoaded" class="box-card">
        <template #header>
            <div class="card-header">
                <span>课程信息</span>
                <RouterLink :to="`/courseLearn/${props.courseId}`">
                    <el-button class="button" text>学习课程</el-button>
                </RouterLink>
               
            </div>
        </template>
        <el-row >
            <el-col :span=6 >
                <el-image style="width: 200px; height: 100%" :src="course.value.imgUrl" fit="contain" />
            </el-col>
            <el-col :span="18">
                <el-descriptions title="" :span=2 size="large" >
                    <el-descriptions-item label="课程名字">{{ course.value.title }}</el-descriptions-item>
                    <el-descriptions-item label="乐器类型">{{ course.value.instrument }}</el-descriptions-item>
                    <el-descriptions-item label="教师">{{ course.value.teacher }}</el-descriptions-item>
                    <el-descriptions-item label="难度等级">{{ course.value.difficulty }}</el-descriptions-item>
                    <el-descriptions-item label="描述">{{ course.value.description }}</el-descriptions-item>
                    <el-descriptions-item label="课程状态">{{ course.value.status }}</el-descriptions-item>

                </el-descriptions>
            </el-col>
        </el-row>
    </el-card>
</template>
  
<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    width: 100%;
    margin-bottom: 50px;
}
</style>
  