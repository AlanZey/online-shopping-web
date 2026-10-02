<template>
  <div class="app-container">
    <nav class="navbar">
      <div class="logo" @click="$router.push('/')">🛒 在线商城</div>
      <div class="links">
        <router-link to="/">首页</router-link>
        <router-link to="/cart" v-if="userStore.token">购物车</router-link>
        <router-link to="/orders" v-if="userStore.token">我的订单</router-link>
        <span v-if="userStore.token">
          欢迎, {{ userStore.username }} | 
          <a href="#" @click.prevent="handleLogout">退出</a>
        </span>
        <router-link to="/login" v-else>登录/注册</router-link>
      </div>
    </nav>
    <main class="content">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from './store/user'

const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-container { font-family: Arial, sans-serif; }
.navbar { display: flex; justify-content: space-between; padding: 15px 30px; background: #333; color: white; align-items: center; }
.logo { font-size: 20px; font-weight: bold; cursor: pointer; }
.links { display: flex; gap: 20px; align-items: center; }
.links a { color: #ddd; text-decoration: none; }
.links a:hover { color: white; }
.content { padding: 20px; max-width: 1200px; margin: 0 auto; }
</style>