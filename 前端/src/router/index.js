import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/LoginLayout/Login.vue'
import Home from '../components/HomeLayout/Home.vue'
import Register from '../components/RegisterLayout/Register.vue'
import CourseLearn from '../components/CourseLearnLayout/CourseLearn.vue'
import CourseDetail from '../components/CourseDetailLayout/CourseDetail.vue'
import AdminLayout from '../components/AdminLayout/AdminLayout.vue'
import UserManagement from '../components/AdminLayout/UserManagement/UserManagement.vue'
import CourseManagement from '../components/AdminLayout/CourseManagement/CourseManagement.vue'
import CourseVideo from '../components/CourseLearnLayout/CourseVideo.vue'
import CourseInfo from '../components/CourseDetailLayout/CourseInfo/CourseInfo.vue'
import Course from '../components/CourseDetailLayout/CourseDetail.vue'
import User from '../components/UserLayout/User.vue'
import FindPassword from '../components/FindPasswordLayout/FindPassword.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/FindPassword',
      name: 'FindPassword',
      component: FindPassword,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/courseLearn',
      name: 'CourseLearnMain',
      component: User,
    },
    {
      path: '/courseLearn/:id',
      name: 'CourseLearn', 
      component: CourseLearn,
    },
    {
      path: '/courseVideo/:id',
      name: 'CourseVideo',
      component: CourseVideo,
    },
    {
      path: '/course',
      name: 'Course',
      component: Course,
    },
    {
      path: '/course/:id',
      name: 'CourseInfo',
      component: CourseInfo,
    },
    {
      path: '/user',
      name: 'userMain',
      component: User,
    },
    {
      path: '/user/:username',
      name: 'user',
      component: User,
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: 'user-management',
          name: 'UserManagement',
          component: UserManagement,
        },
        {
          path: 'course-management',
          name: 'CourseManagement',
          component: CourseManagement,
        },
      ],
    }
  ]
})
export default router
