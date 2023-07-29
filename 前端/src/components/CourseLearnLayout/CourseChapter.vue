<script setup>
import { onMounted, ref, getCurrentInstance } from 'vue';
import { getChapterByCourseId } from '@/apis/chapter';
import router from '@/router';

const instance = getCurrentInstance();
const route = instance.appContext.config.globalProperties.$route;
const dataload = ref(false)
const chapterData = ref([])

const defaultProps = {
    children: 'children',
    label: 'chapter_name',
    id: 'id',
    is_chapter: 'is_chapter'
};

function handleCheckChange(node) {
    console.log(node.is_chapter);
    if(!node.is_chapter){
        router.push(`/courseVideo/${node.id}`)
    }
}

onMounted(async () => {
    const res = await getChapterByCourseId(route.params.id)
    //特殊格式
    chapterData.value = res[0]
    dataload.value = true
    console.log(chapterData.value[0].is_chapter);
})
</script>
<template>
    <div class="CourseChapter">
        <el-card class="box-card" v-if="dataload">
            <el-tree :data="chapterData" :props="defaultProps" @node-click="handleCheckChange">
                <template #default="{ node }">
                    <span  class="custom-tree-node">{{ node.label }}</span>
                </template>
            </el-tree>
        </el-card>
    </div>
</template>
<style>
.el-tree-node__children>.is-focusable>.el-tree-node__content>span{
    color: blue;
}
.CourseChapter {
    width: 100%;
}

.box-card {
    width: 100%;
}
</style>
