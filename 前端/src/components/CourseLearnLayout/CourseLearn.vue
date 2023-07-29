<script setup>
import Header from '../HomeLayout/Header.vue'
import HeaderFixed from '../HomeLayout/HeaderFixed.vue'
import CourseChapter from './CourseChapter.vue'
import { getCourseListAPI } from '@/apis/course'
import { useCourseStore } from '@/stores/course'
import { baseURL } from '@/utils/http'
import { useUserStore } from '@/stores/user'

// vueUse
import { useScroll } from '@vueuse/core'
import { onBeforeMount } from 'vue'
const { y } = useScroll(window)

const userStore = useUserStore()

const getCourseList = async () => {
  const courseStore = useCourseStore()
  const res = await getCourseListAPI()
  //存储进pinia中
  courseStore.setCourseList(res.CourseList)
}



onBeforeMount(() => {
  getCourseList()
})
</script>
<template>
  <div class="HomeMain">
    <HeaderFixed :class="{ show: y > 78 }" />
    <Header />
    <CourseChapter/>
    <!-- <CourseVideo/> -->
  </div>
</template>
<style scoped>
.HomeMain {
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 110vh;

}

.show {
  transition: all 0.3s linear !important;
  transform: none !important;
  opacity: 1 !important;
}
</style>
  