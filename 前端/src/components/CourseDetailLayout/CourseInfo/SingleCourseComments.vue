<script setup>
import { defineProps } from 'vue';

const props = defineProps(['message']);
// console.log(props.message);
</script>
<template>
    <el-card class="box-card">
        <el-row style="">
            <el-col :span=4>
                <el-avatar :size="100" :src=props.message.avatar style="margin-bottom: 30px;"/><br>
                <el-text class="mx-1" size="large">评分：{{ props.message.rating }}</el-text>
            </el-col>
            <el-col :span="20">
                <el-row style="height: 20px;">
                    <el-col :span="20">
                        <el-text class="mx-1" size="large">{{ props.message.userName }}</el-text>
                    </el-col>
                    <el-col :span="4" >
                        <el-text class="mx-1" size="large">{{ props.message.time }}</el-text>
                    </el-col>
                </el-row>
                <el-row style="height: 100%;padding-top: 30px;">
                    <el-col >
                        <el-text class="mx-1">{{ props.message.textBody }}</el-text>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </el-card>
</template>
  
<style scoped>
.box-card {
    width: 100%;
    height: 250px;
    margin-bottom: 15px;
    margin-top: 15px;
}
</style>
  