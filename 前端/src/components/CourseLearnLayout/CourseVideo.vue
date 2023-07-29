<script setup>
import { onMounted,  getCurrentInstance } from 'vue';
import { useCourseStore } from '@/stores/course';
import CourseInfo from '../CourseDetailLayout/CourseInfo/CourseInfo.vue';
import HeaderFixed from '../HomeLayout/HeaderFixed.vue';
import Header from '../HomeLayout/Header.vue';
import { useScroll } from '@vueuse/core'
const { y } = useScroll(window)
const CourseList = useCourseStore().CourseList
const instance = getCurrentInstance();
const route = instance.appContext.config.globalProperties.$route;
CourseList.forEach(course => {
    if (route.params.id == course.id) {
        console.log(course.title);
    }
});
onMounted(()=>{
    console.log(route.params.id);
})
</script>
<template>
    <div class="CourseLearnMain">
        <HeaderFixed :class="{ show: y > 78 }" />
        <Header />
        <el-card class="box-card-video">
            <video id="my-player" class="video-js" controls preload="auto" width="1000px" height="500px"
                :poster="`http://localhost/video/${route.params.id}.png`" data-setup="{}">
                <source :src="`http://localhost/video/${route.params.id}.mp4`" type="video/mp4" />

            </video>
        </el-card>
    </div>
</template>
<style scoped>
.show {
  transition: all 0.3s linear !important;
  transform: none !important;
  opacity: 1 !important;
}
.CourseLearnMain {
    width: 100%;
    min-height: 100vh;
}

.box-card-video {
    width: 100%;
    margin-bottom: 50px;
    padding: 0;
    position: relative;
    /* 添加定位属性 */

}

.box-card-video::before {
    content: "";
    display: block;
    padding-top: 56.25%;
    /* 16:9 宽高比的百分比值 */
}

.video-js {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 90%;
}
</style>