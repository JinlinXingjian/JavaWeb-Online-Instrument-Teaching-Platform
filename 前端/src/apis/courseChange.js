import http from '@/utils/http';

export function changeCourseAPI(id, title, instrument, teacher, difficulty, description, status) {
    return http.post('/changeCourse', {
      "id": id,
      "title": title,
      "instrument": instrument,
      "teacher": teacher,
      "difficulty": difficulty,
      "description": description,
      "status": status
    });
  }
  
