<template>
  <div>
    <h2>商品列表</h2>
    <div class="product-grid">
      <div class="product-card" v-for="product in products" :key="product.id">
        <img :src="product.imageUrl || 'https://via.placeholder.com/200'" alt="商品图片">
        <h3>{{ product.name }}</h3>
        <p class="desc">{{ product.description }}</p>
        <p class="price">¥{{ product.price }}</p>
        <p class="stock">库存: {{ product.stock }}</p>
        <button @click="addToCart(product.id)" :disabled="product.stock <= 0">
          {{ product.stock > 0 ? '加入购物车' : '已售罄' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../api/request'
import { useUserStore } from '../store/user'
import { useRouter } from 'vue-router'

const products = ref([])
const userStore = useUserStore()
const router = useRouter()

onMounted(async () => {
  try {
    products.value = await request.get('/products')
  } catch (e) {}
})

const addToCart = async (productId) => {
  if (!userStore.token) {
    alert('请先登录')
    return router.push('/login')
  }
  try {
    await request.post('/cart', { productId, quantity: 1 })
    alert('已加入购物车！')
  } catch (e) {}
}
</script>

<style scoped>
.product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 20px; }
.product-card { border: 1px solid #eee; padding: 15px; border-radius: 8px; text-align: center; }
.product-card img { width: 100%; height: 180px; object-fit: cover; }
.price { color: #e4393c; font-size: 18px; font-weight: bold; }
.stock { color: #999; font-size: 12px; }
button { width: 100%; padding: 8px; background: #ff9900; color: white; border: none; border-radius: 4px; cursor: pointer; }
button:disabled { background: #ccc; cursor: not-allowed; }
</style>