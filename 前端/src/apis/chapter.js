import http from "@/utils/http";
export function getChapterByCourseId(courseId){
    return http.post(('/getChapterByCourseId'),{
        courseId: courseId
    })
}