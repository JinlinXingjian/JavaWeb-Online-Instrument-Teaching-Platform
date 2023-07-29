import http from "@/utils/http";
export function getCourseCommentsAPI(id) {
    return http.post('/getCourseComments', {
        courseId: id
    })
}


export function sentCommentsAPI(courseId,textArea,userName,rate) {
    return http.post('/addComment', {
        "courseId": courseId,
        "textArea": textArea,
        "userName": userName,
        "rate": rate
    })
}