<script setup>
import Header from '../HomeLayout/Header.vue'
import HeaderFixed from '../HomeLayout/HeaderFixed.vue';
import UserSelect from '../UserLayout/UserSelect.vue'
import Course from '../Course/Course.vue'
import { useScroll } from '@vueuse/core'
import { onMounted } from 'vue';
import { loadUserInfo } from '@/utils/loadUserInfo';
const { y } = useScroll(window)
onMounted(async()=>{
    loadUserInfo()
})

</script>
<template>
    <div class="UserMain">
        <HeaderFixed :class="{ show: y > 78 }"/>
        <Header/> 
        <UserSelect />
        <Course>
            <template v-slot:title>
                <h2>我已学习的课程</h2>
            </template>
        </Course>
    </div>
</template>
<style >
.UserMain{
    display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 110vh;
}
</style>