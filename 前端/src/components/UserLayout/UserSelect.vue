<template>
  <div class="UserInfo">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">账户信息</el-menu-item>
      <el-menu-item index="2">修改头像</el-menu-item>
      <el-menu-item index="3">修改密码</el-menu-item>
    </el-menu>
    <div v-if="activeIndex === '1'"><UserInfo/></div>
    <div v-else-if="activeIndex === '2'" ><UserChangeAvatar /></div>
    <div v-else-if="activeIndex === '3'"><UserChangePassword/></div>
  </div>
</template>
<script  setup>
import { ref } from 'vue'
import UserInfo from './UserInfo.vue';
import  UserChangePassword from './UserChangePassword.vue'
import UserChangeAvatar from './UserChangeAvatar.vue';
const activeIndex = ref('1')
const handleSelect = (index) => {
  activeIndex.value = index;
};
</script>
<style>
</style>
  