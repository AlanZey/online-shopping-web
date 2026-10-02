<template>
  <div class="login-box">
    <h2>{{ isRegister ? '注册' : '登录' }}</h2>
    <input v-model="form.username" placeholder="用户名" />
    <input v-model="form.password" type="password" placeholder="密码" />
    <template v-if="isRegister">
      <input v-model="form.email" placeholder="邮箱" />
      <input v-model="form.phone" placeholder="手机号" />
    </template>
    <button @click="handleSubmit">{{ isRegister ? '注册' : '登录' }}</button>
    <p @click="isRegister = !isRegister" class="toggle-link">
      {{ isRegister ? '已有账号？去登录' : '没有账号？去注册' }}
    </p>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../api/request'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const isRegister = ref(false)
const form = reactive({ username: '', password: '', email: '', phone: '' })

const handleSubmit = async () => {
  if (!form.username || !form.password) return alert('请输入账号密码')
  try {
    if (isRegister.value) {
      await request.post('/auth/register', form)
      alert('注册成功，请登录')
      isRegister.value = false
    } else {
      const token = await request.post('/auth/login', { 
        username: form.username, password: form.password 
      })
      userStore.setLoginInfo(token, form.username)
      alert('登录成功')
      router.push('/')
    }
  } catch (error) {
    // 错误已在拦截器统一处理
  }
}
</script>

<style scoped>
.login-box { max-width: 400px; margin: 50px auto; padding: 30px; border: 1px solid #ddd; border-radius: 8px; display: flex; flex-direction: column; gap: 15px; }
input { padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
button { padding: 10px; background: #42b983; color: white; border: none; border-radius: 4px; cursor: pointer; }
.toggle-link { color: #42b983; cursor: pointer; text-align: center; font-size: 14px; }
</style>