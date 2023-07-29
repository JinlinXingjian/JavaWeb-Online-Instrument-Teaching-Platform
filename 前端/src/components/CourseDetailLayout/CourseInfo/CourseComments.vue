<script setup>
import CourseSingleComments from './SingleCourseComments.vue';
import CourseSentComments from './CourseSentComments.vue';
import { onMounted,ref,getCurrentInstance } from 'vue';
import { getCourseCommentsAPI } from '@/apis/courseComments';
import { baseImgURL } from '@/utils/http';

const instance = getCurrentInstance();
const route = instance.appContext.config.globalProperties.$route;

let list = ref([]);
const isDataLoaded = ref(false);
onMounted(async()=>{
  const res=await getCourseCommentsAPI(route.params.id);
  list.value=res[0]
  list.value.forEach(item => {
      item.avatar=baseImgURL+item.avatar
  });
  isDataLoaded.value = true;
})
const handle=async()=>{
  const res=await getCourseCommentsAPI(route.params.id);
  list.value=res[0]
  list.value.forEach(item => {
      item.avatar=baseImgURL+item.avatar
  });
  isDataLoaded.value = true;
}


</script>
<template>
  <div class="CourseInfoMain">
    <CourseSentComments @reload="handle"/>
    <CourseSingleComments    v-if="isDataLoaded" v-for="item in list" :key="item.id" :message="item" />
  </div>
</template>
<style >
.CourseInfoMain{
  width: 100%;
}
</style>