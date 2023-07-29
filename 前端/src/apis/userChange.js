import http from '@/utils/http';

export function changePasswordAPI(username, oldPassword, newPassword) {
  return http.post('/changePassword', {
    username,
    oldPassword,
    newPassword
  });
}

export function changeUsernameAPI(oldUsername, newUsername) {
  return http.post('/changeUsername', {
    "oldUsername": oldUsername,
    "newUsername": newUsername
  });
}

export function changeEmailAPI(username,oldEmail, newEmail) {
  return http.post('/changeEmail', {
    "username": username,
    "oldEmail": oldEmail,
    "newEmail": newEmail
  });
}

export function changenStatusAPI(username,changeUsername,newStatus) {
  return http.post('/changenStatus', {
    "username": username,
    "changeUsername": changeUsername,
    "newStatus": newStatus
  });
}
