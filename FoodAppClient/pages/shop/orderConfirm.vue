<template>
  <view>
	<!-- 物品数据 -->
	<uni-card>
		<view style="margin-bottom: 10px;"><h2>{{shopItem.name}}</h2></view>
		<view>
			<image style="width: 100%; height: 200px;" 
					:src="shopItem.picture"></image>
		</view>
		<view>{{shopOrder.amount}}</view>
		<view>总价：{{shopOrder.price}} ¥</view>
	</uni-card>

	<uni-card>
		<u-row customStyle="margin-bottom: 10px">
			<u-col span="6">
				<u-button style="margin-top: 10px; margin-right: 10px;"  @click="cancel()">取消</u-button>
			</u-col>
			<u-col span="6">
				<u-button style="margin-top: 10px;" type="primary" @click="submit()">支付</u-button>
			</u-col>
		</u-row>
	</uni-card>
	
  </view>
</template>

<script>
	import ApiShopOrder from '@/api/shop/api_shoporder.js';
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	
	export default {
		data() {
		  return {
			shopOrder: {
				id:'',
				userId:localStorage.getItem('userId'),
				amount:'',
			},
			shopItem: {
				id:'1',
				name:'1',
				picture:'1',
				description:'1',
				price:'1'
			},
		  }
		},
		onLoad: function(options) {
			console.log(options.id);
			this.getShopItem(options.id);
		},
		methods: {
			// 物品信息
			getShopItem(id) {
				let that = this;
				ApiShopOrder.sel4shoporder(id).then(res => {
					console.log(res)
					if(res.code === 200){
						that.shopOrder = res.data;
					}
					
					ApiShopItem.sel4shopitem(res.data.shopItemId).then(res => {
						console.log(res);
						if(res.code === 200){
							that.shopItem = res.data
						}
					})
					
				})
			},
			
			submit(){
				uni.showToast({
					title: `下单成功`,
					icon: 'none'
				})
				uni.navigateTo({
				  url: '/pages/mine/shopOrder'
				})
			},
			
			cancel(){
				ApiShopOrder.del4shoporder(this.shopOrder.id).then(res => {
					uni.showToast({
						title: `取消成功`,
						icon: 'none'
					})
				})
			}
		}
	}
</script>

<style>
	

</style>
