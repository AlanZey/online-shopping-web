<template>
  <div>
    <h2>我的购物车</h2>
    <table class="cart-table" v-if="cartItems.length > 0">
      <thead>
        <tr><th>商品ID</th><th>数量</th><th>操作</th></tr>
      </thead>
      <tbody>
        <tr v-for="item in cartItems" :key="item.id">
          <td>{{ item.productId }}</td>
          <td>{{ item.quantity }}</td>
          <td><button @click="removeItem(item.id)">删除</button></td>
        </tr>
      </tbody>
    </table>
    <p v-else>购物车空空如也~</p>
    <button v-if="cartItems.length > 0" class="checkout-btn" @click="checkout">去结算</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../api/request'
import { useRouter } from 'vue-router'

const cartItems = ref([])
const router = useRouter()

const loadCart = async () => {
  try {
    cartItems.value = await request.get('/cart')
  } catch (e) {}
}

const removeItem = async (id) => {
  try {
    await request.delete(`/cart/${id}`)
    loadCart()
  } catch (e) {}
}

const checkout = async () => {
  try {
    const orderId = await request.post('/orders')
    alert(`下单成功！订单号: ${orderId}`)
    router.push('/orders')
  } catch (e) {}
}

onMounted(loadCart)
</script>

<style scoped>
.cart-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
.cart-table th, .cart-table td { border: 1px solid #ddd; padding: 12px; text-align: center; }
.checkout-btn { background: #e4393c; color: white; padding: 10px 30px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
</style>