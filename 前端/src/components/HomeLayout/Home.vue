<script setup>
import Header from './Header.vue'
import HeaderFixed from './HeaderFixed.vue'
import Slider from './Slider.vue'
import Course from '../Course/Course.vue'
// vueUse 
import { useScroll } from '@vueuse/core'
const { y } = useScroll(window)


</script>
<template>
  <div class="HomeMain">
    <HeaderFixed :class="{ show: y > 78 }" />
    <Header />
    <Slider />
    <Course>
      <template v-slot:title>
        <h2>乐器课程</h2>
      </template>
    </Course>

  </div>
</template>
<style scoped>
.HomeMain {
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 110vh;

}

.show {
  transition: all 0.3s linear !important;
  transform: none !important;
  opacity: 1 !important;
}
</style>
  