<script setup>
import { reactive, ref, onMounted } from 'vue';
import { getCourseListAPI } from '@/apis/course';
const emit = defineEmits(['queryCourse', 'reload']);
const CourseList = ref([]);
const isDataLoaded = ref(false);

let instrument = ref([]);
let teacher = ref([]);
let difficulty = ref([]);

onMounted(async () => {
  const res = await getCourseListAPI();
  CourseList.value = res.CourseList;

  instrument.value = [...new Set(CourseList.value.map((course) => course.instrument))];
  teacher.value = [...new Set(CourseList.value.map((course) => course.teacher))];
  difficulty.value = [...new Set(CourseList.value.map((course) => course.difficulty))];

  isDataLoaded.value = true;
});

const value = reactive({
  instrument: '',
  teacher: '',
  difficulty: '',
  reload: '',
});

const queryCourse = () => {
  /*TODO*/
  emit('queryCourse', value);
};

const reload = () => {
  value.reload = 'true';
  emit('reload', value);
  value.instrument = '';
  value.teacher = '';
  value.difficulty = '';
  value.reload = '';
};
</script>

<template>
  <div v-if="isDataLoaded" class="CourseSelect">
    <h3>乐器种类：</h3>
    <el-select v-model="value.instrument" class="m-2" placeholder="Select" size="large">
      <el-option
        v-for="item in instrument"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
    <h3>难度种类：</h3>
    <el-select v-model="value.difficulty" class="m-2" placeholder="Select" size="large">
      <el-option
        v-for="item in difficulty"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
    <h3>老师：</h3>
    <el-select v-model="value.teacher" class="m-2" placeholder="Select" size="large">
      <el-option
        v-for="item in teacher"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
    <el-button type="primary" style="margin-left: 20px;" size="large" @click="queryCourse">查询</el-button>
    <el-button type="primary" style="margin-left: 20px;" size="large" @click="reload">重置</el-button>
  </div>
  <div v-else>
    <!-- 显示加载中的提示或其他内容 -->
  </div>
</template>

<style >
.CourseSelect {
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
  padding: 10px;
}
.el-select {
  margin-left: 20px;
  margin-right: 20px;
}
</style>
