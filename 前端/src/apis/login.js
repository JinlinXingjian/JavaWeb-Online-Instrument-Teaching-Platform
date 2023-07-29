import http from '@/utils/http';

export function  loginAPI(username, password) {
  return http.post('/login', {
    username,
    password
  });
}
