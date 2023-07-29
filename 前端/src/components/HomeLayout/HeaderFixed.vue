<template>
    <el-menu ref="el" :default-active="props.activeIndex" class="header" mode="horizontal" :ellipsis="false" @select="handleSelect">
        <router-link to="/">
            <el-menu-item index="0">LOGO</el-menu-item>
        </router-link>
        <div class="flex-grow" />
        <router-link to="/">
            <el-menu-item index="1">主页</el-menu-item>
        </router-link>
        <router-link to="/course">
            <el-menu-item index="2">课程</el-menu-item>
        </router-link>
        <RouterLink to="/courseLearn">
            <el-menu-item index="3">学习</el-menu-item>
        </RouterLink>
        <RouterLink :to="`/User/${userStore.username}`">
            <el-menu-item index="4">账户</el-menu-item>
        </RouterLink>
    </el-menu>
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
</script>
<style scoped>
.flex-grow {
    flex-grow: 1;
}

.header {
    width: 100%;
    position: fixed;
    top: 0px;
    left: 0px;
    margin-bottom: 20px;
    padding-left: 10vw;
    padding-right: 10vw;

    transform: translateY(-100%); 
    opacity: 0; 
    z-index: 999;
}
</style>
  
  
  