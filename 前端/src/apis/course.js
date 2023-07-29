import http from '@/utils/http';
import { baseURL } from '@/utils/http';
export async function getCourseListAPI() { 
    const res = await http.post('/getCourseList', {});
    
    res.CourseList.forEach(item => {
      item.imgUrl = baseURL + item.imgUrl;
    });
    return res;
  }
  
  
  
  
export function getCourseAPI(id_new) {
    return http.post('/getCourse', {
        courseId: id_new
    })
}