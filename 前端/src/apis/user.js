import http from '@/utils/http';
export async function getUserAPI() { 
    const res=await http.post('/getUser', {
    })
    return  res
  }

export async function getAllUserAPI() { 
  const res=await http.post('/getAllUsers', {
  })
  return  res
}

  