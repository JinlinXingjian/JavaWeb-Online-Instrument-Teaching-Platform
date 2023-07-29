<script setup>
import Header from '../../HomeLayout/Header.vue'
import HeaderFixed from '../../HomeLayout/HeaderFixed.vue'
import SingleCourseInfo from './SingleCourseInfo.vue';
import CourseComments from './CourseComments.vue';
// vueUse
import { useScroll } from '@vueuse/core'
const { y } = useScroll(window)






</script>
<template>
    <div class="CourseDetailMain">
      <HeaderFixed :class="{ show: y > 78 }"/>
      <Header/>
      <SingleCourseInfo :courseId="$route.params.id"/>
      <CourseComments />
    </div>
</template>
<style scoped>
.CourseDetailMain{
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 100vh;
}
.show{
    transition: all 0.3s linear !important;
    transform: none !important;
    opacity: 1 !important;
}</style>
  