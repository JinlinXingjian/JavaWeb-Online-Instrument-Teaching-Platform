<script setup>
import { onMounted, ref } from 'vue';
import CourseSelect from '../../Course/CourseSelect.vue'
import { useCourseStore } from '@/stores/course'
import { getCourseListAPI } from '@/apis/course';
import { baseURL } from '@/utils/http';
import SingleCourse from '../UserManagement/SingleCourse.vue';
const courseStore = useCourseStore()
//存储全部课程的list
const newList = ref([])
const allList = ref([])
const count = ref(0)
//控制骨架片
const loading = ref(true)
//控制courseList的渲染
const disabled = ref(false)

const handle = async(value) => {
    /*
    处理子组件传来的事件信息
    首先判断是否为重置，是则解除滚动加载
    然后清空列表，然后调用load（）载入三个元素
    
    若为查询，则生成筛选过的数组，然后赋值给列表
    同时禁用滚动加载
    
    */
    if (value.reload == 'true') {
        ElMessage({
            message: '课程重置成功',
            type: 'success',
        })
        // disabled.value = false
        // newList.value = []
        // load()
        const res = await getCourseListAPI()
        allList.value = res.CourseList


        load()
        loading.value = false
        return
    }
    ElMessage({
        message: '课程筛选成功',
        type: 'success',
    })
    // 创建新的筛选结果数组
    const res=await getCourseListAPI();
    const CourseList = res.CourseList;
    const filteredList = CourseList.filter(item => {
        return (
            (value.instrument === '' || item.instrument === value.instrument) &&
            (value.teacher === '' || item.teacher === value.teacher) &&
            (value.difficulty === '' || item.difficulty === value.difficulty)
        )
    })
    newList.value = filteredList
    disabled.value = true
}

onMounted(async () => {
    //组件挂载就开始请求后端api
    const res = await getCourseListAPI()
    allList.value = res.CourseList
    load()
    loading.value = false
})
const load = () => {
    //TODO 将allList中三个值赋给newList，重复调用就赋值下三个，用count表示当前有多少加进去了
    const startIndex = count.value;
    const endIndex = startIndex + 3;
    const valuesToAdd = allList.value.slice(startIndex, endIndex);
    newList.value = [...newList.value, ...valuesToAdd];
    count.value += 3;
    if (count.value >= allList.value.length) {
        disabled.value = true
        ElMessage({
            message: '这就是全部的课程了',
            type: 'warning',
        })
    }
}
</script>
<template>
    <CourseSelect @queryCourse="handle" @reload="handle" />
    <div class="CourseList">
        <slot name="title"></slot>
        <el-skeleton style="width: 80vw" :loading="loading" animated :rows="9">
            <template #default>

                <ul v-infinite-scroll="load" class="infinite-list" :infinite-scroll-disabled="disabled">
                    <el-row>
                        <el-col v-for="item in newList" :key="item.id" :span="8">
                            <SingleCourse :item="item" @changeCourse="handle"/>
                        </el-col>
                    </el-row>
                </ul>


            </template>
        </el-skeleton>

    </div>
</template>



<style scoped>
.CourseList {
    width: 100%;
}

.el-des {
    width: 100%;
    margin: 0;
    padding: 0;
}

.el-card {
    margin: 15px;
}

.bottom {
    line-height: 12px;
    padding: 0px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: center;
}

.image {
    width: 100%;
    display: block;
}






.infinite-list {
    height: 700px;
    width: 100%;
    padding: 0;
    margin: 0;
    list-style: none;
}
</style>
