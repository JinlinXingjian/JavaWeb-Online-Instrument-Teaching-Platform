import http from '@/utils/http';

export function  FindPasswordAPI(Email) {
  return http.post('/FindPassword', {
    "Email": Email
  });
}

export function  VerificationCode(Email,Verification_code,newPassword) {
    return http.post('/VerificationCode', {
      "Email": Email,
      "Verification_code": Verification_code,
      "newPassword": newPassword
    });
}
