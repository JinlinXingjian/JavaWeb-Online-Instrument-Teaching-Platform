<script  setup>
import { ref } from 'vue'
import UserManagement from './UserManagement/UserManagement.vue';
import CourseManagement from './CourseManagement/CourseManagement.vue';

const activeIndex = ref('1')

const handleSelect = (index) => {
  activeIndex.value = index;
};


</script>
<template>
    <div class="adminMain">
        <el-menu
            :default-active="activeIndex"
          class="el-menu"
          @select="handleSelect"
        >
        <el-menu-item index="1">
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="2">
            <span>课程管理</span>
          </el-menu-item>
        </el-menu>
        <div v-if="activeIndex === '1'"><UserManagement/></div>
        <div v-else-if="activeIndex === '2'" ><CourseManagement /></div>
    </div>

  </template>
<style scoped>
.adminMain{
    width: 80vw;
}
.el-menu{
    min-height: 100vh;
    position: fixed;
    left: 0px;
    top: 0px;
    min-height: 100vh;
}
</style>
  

  