<template>
  <div>
    <h2>我的订单</h2>
    <div v-if="orders.length === 0">暂无订单</div>
    <div class="order-card" v-for="order in orders" :key="order.id">
      <div class="order-header">
        <span>订单号: {{ order.id }}</span>
        <span>总价: ¥{{ order.totalPrice }}</span>
        <span class="status">{{ order.status === 'pending' ? '待付款' : order.status }}</span>
      </div>
      <div class="order-items">
        <div v-for="item in order.items" :key="item.id" class="item">
          商品ID: {{ item.productId }} | 数量: {{ item.quantity }} | 单价: ¥{{ item.price }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../api/request'

const orders = ref([])

onMounted(async () => {
  try {
    orders.value = await request.get('/orders')
  } catch (e) {}
})
</script>

<style scoped>
.order-card { border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 8px; }
.order-header { display: flex; justify-content: space-between; background: #f9f9f9; padding: 10px; font-weight: bold; margin-bottom: 10px; }
.status { color: #e4393c; }
.item { padding: 5px 0; border-bottom: 1px dashed #eee; font-size: 14px; color: #666; }
</style>