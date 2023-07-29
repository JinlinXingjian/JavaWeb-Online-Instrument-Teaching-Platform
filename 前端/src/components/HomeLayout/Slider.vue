<script setup>
const fit='scale-down'
const urls =[
"http://localhost/static/swiper/1.jpg",
"http://localhost/static/swiper/2.jpg",
"http://localhost/static/swiper/3.jpg",
"http://localhost/static/swiper/4.jpg",
"http://localhost/static/swiper/5.jpg"
]
</script>

<template>
    <div class="home-banner">
        <el-carousel :interval="4000" type="card" height="300px">
            <el-carousel-item v-for="item in urls" :key="item">
                <el-image style="width: 100%; " :src=item :fit="fit" />
            </el-carousel-item>
        </el-carousel>
    </div>
</template>
  
<style scoped>
img{
    width: 100%;
}
.home-banner {
    width: 100%;
    max-height: 300 px;
    margin-bottom: 20px;
}
</style>
  