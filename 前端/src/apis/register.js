import http from "@/utils/http";


export function registerAPI(username, password,email) {
  return http.post('/register', {
    username,
    password,
    email
  });
}
