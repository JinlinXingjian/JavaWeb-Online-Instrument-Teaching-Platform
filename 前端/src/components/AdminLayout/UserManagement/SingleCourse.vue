<script setup>
import { ref} from 'vue';
import { changeCourseAPI } from '@/apis/courseChange';
const props = defineProps(['item']);
const emit=defineEmits(['changeCourse'])
//课程状态的选项
const options = [
  {
    value: '正常',
    label: '正常',
  },
  {
    value: '封禁',
    label: '封禁',
  },
]
//难度的选项
const optionsDifficulty = [
  {
    value: '1',
    label: '1',
  },
  {
    value: '2',
    label: '2',
  },
  {
    value: '3',
    label: '3',
  },
  {
    value: '4',
    label: '4',
  },
  {
    value: '5',
    label: '5',
  },
  {
    value: '6',
    label: '6',
  },
  {
    value: '7',
    label: '7',
  },
  {
    value: '8',
    label: '8',
  },
  {
    value: '9',
    label: '9',
  },
  {
    value: '10',
    label: '10',
  },
]
//课程状态的value
const statusValue=ref('')
//课程难度的value
const difficultyValue=ref('')
const handChangeCourse = async() => {

  const res=await changeCourseAPI(
    props.item.id,
    props.item.title,
    props.item.instrument,
    props.item.teacher,
    difficultyValue.value==""? props.item.difficulty:difficultyValue.value ,
    props.item.description,
    statusValue.value==""? props.item.status:statusValue.value
    )
    if(res['message']=='changeCourse success'){
      const value={
        reload: 'true'
      }
      emit('changeCourse',value)
    }
};

</script>


<template>
    <el-card :body-style="{ padding: '0px' }">
        <img :src="item.imgUrl" class="image" />
        <div style="padding: 0px">
            <div class="bottom">
                <el-descriptions title="" direction="horizontal" :column="1" size="large" border class="el-des">
                    <el-descriptions-item label="标题">
                        <el-input v-model="item.title" />
                    </el-descriptions-item>
                    <el-descriptions-item label="乐器">
                        <el-input v-model="item.instrument" />
                    </el-descriptions-item>
                    <el-descriptions-item label="老师">
                        <el-input v-model="item.teacher" />
                    </el-descriptions-item>
                    <el-descriptions-item label="难度">
                        <el-select v-model="difficultyValue" class="m-2" :placeholder="item.difficulty" size="large">
                            <el-option v-for="item in optionsDifficulty" :key="item.value" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </el-descriptions-item>
                    <el-descriptions-item label="描述">
                        <el-input v-model="item.description" />
                    </el-descriptions-item>
                    <el-descriptions-item label="状态">
                        <el-select v-model="statusValue" class="m-2" :placeholder="item.status" size="large">
                            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                        </el-select>
                    </el-descriptions-item>
                    <el-descriptions-item label="更改">
                        <el-button type="primary" round @click="handChangeCourse">确认更改</el-button>
                    </el-descriptions-item>
                </el-descriptions>
            </div>
        </div>
    </el-card>
</template> 
<style>
.CourseList {
    width: 100%;
}

.el-des {
    width: 100%;
    margin: 0;
    padding: 0;
}

.el-card {
    margin: 15px;
}

.bottom {
    line-height: 12px;
    padding: 0px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: center;
}

.image {
    width: 100%;
    display: block;
}


</style>