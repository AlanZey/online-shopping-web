import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/', component: () => import('../views/Home.vue') },
  { path: '/login', component: () => import('../views/Login.vue') },
  { 
    path: '/cart', 
    component: () => import('../views/Cart.vue'),
    meta: { requiresAuth: true }
  },
  { 
    path: '/orders', 
    component: () => import('../views/Orders.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth && !userStore.token) {
    alert('请先登录')
    next('/login')
  } else {
    next()
  }
})

export default router