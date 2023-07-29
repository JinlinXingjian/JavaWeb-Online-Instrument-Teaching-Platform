import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const username = ref('')
  const email = ref('')
  const avatar = ref('')
  const status = ref('')

  const setUsername = (value) => {
    username.value = value
  }

  const setEmail = (value) => {
    email.value = value
  }

  const setAvatar = (value) => {
    avatar.value = value
  }

  const setStatus = (value) => {
    status.value = value
  }

  return { username, email, status, avatar,setUsername, setEmail, setStatus ,setAvatar}
})
