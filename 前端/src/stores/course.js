import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useCourseStore = defineStore('course', () => {
  const CourseList = ref([])

  const setCourseList = (list) => {
    CourseList.value = list
  }

  return {
    CourseList,
    setCourseList
  }
})
