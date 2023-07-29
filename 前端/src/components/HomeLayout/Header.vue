<template>
       <div class="HeaderMain">
       <el-menu :default-active="props.activeIndex" class="header" mode="horizontal" :ellipsis="false" @select="handleSelect">
       <router-link to="/">
           <el-menu-item index="0">在线乐理学习平台</el-menu-item>
       </router-link>
       <div class="flex-grow" />
       <router-link to="/">
           <el-menu-item index="1">主页</el-menu-item>
       </router-link>
       <router-link to="/course">
           <el-menu-item index="2">课程</el-menu-item>
       </router-link>
       <RouterLink :to="`/User/${userStore.username}`">
           <el-menu-item index="4">账户</el-menu-item>
       </RouterLink>
   </el-menu>
   </div>

</template>
 
 
<script lang="ts" setup>
import { useUserStore } from '@/stores/user';
const userStore = useUserStore()

const props = defineProps({
   activeIndex: {
       type: String,
       default: '1',
   },
});

const handleSelect = (key: string, keyPath: string[]) => {
   console.log(key, keyPath);
};
</script >
<style scoped>
.HeaderMain{
   min-width: 80vw;
}
.flex-grow {
   flex-grow: 1;
}

.header {
   width: 100%;
   position: relative;
   top: 0px;
   left: 0;
   margin-bottom: 20px;
}


</style>
 
 
 