<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{shopItem.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" :src="shopItem.picture"></image>
			</view>
			<u-divider></u-divider>
			<view>{{shopItem.price}}¥</view>
		</uni-card>
		<!-- 描述 -->
		<uni-card>
			<h2>描述</h2>
			<view>{{shopItem.description}}</view>
		</uni-card>
		
		<!-- 购物导航 -->
		<view class="goods-carts" v-if="!this.isTourist">
			<uni-goods-nav :fill="true" :options="options" 
							:button-group="customButtonGroup" 
							@click="onClick"
							@buttonClick="buttonClick">
			</uni-goods-nav>
		</view>
		<!-- 购物车 -->
		<uni-popup ref="popup" type="dialog" v-if="!this.isTourist">
			<uni-popup-dialog mode="input":before-close="true" 
							  @close="close" 
							  @confirm="confirm">
				<uni-section title="数量" type="line" padding>
					<uni-number-box v-model="amount"></uni-number-box>
				</uni-section>
			</uni-popup-dialog>
		</uni-popup>
		
		<!-- /购买面板 -->
		<uni-popup ref="shopOrder" type="dialog" v-if="!this.isTourist">
			<uni-popup-dialog mode="input":before-close="true" 
							  @close="closeShopOrder" 
							  @confirm="confirmShopOrder">
							  
				
				<uni-section title="数量" type="line" padding>
					<uni-number-box v-model="amount"></uni-number-box>
				</uni-section>
			</uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	import ApiCart from '@/api/shop/api_cart.js';
	import ApiShopOrder from '@/api/shop/api_shoporder.js';
	
	export default {
		data() {
			return {
				// 游客模式不展示
				isTourist: localStorage.getItem("isTourist"),
				check: false,
				// 物品信息
				shopItem: {
					id:'1',
					name:'1',
					picture:'1',
					description:'1',
					price:1.1
				},
				user: {
					userId:localStorage.getItem('userId')
				},
				amount: 1,
				// 商品导航-左侧
				options: [
					// {
					// 	icon: 'chat',
					// 	text: '客服'
					// }, 
					{
						icon: 'shop',
						text: '店铺',
						infoBackgroundColor: '#007aff',
						infoColor: "#f5f5f5"
					}, 
					{
						icon: 'cart',
						text: '购物车',
					},
				],
				// 商品导航-右侧
				customButtonGroup: [{
					text: '加入购物车',
					backgroundColor: 'linear-gradient(90deg, #1E83FF, #0053B8)',
					color: '#fff'
				},
				{
					text: '立即购买',
					backgroundColor: 'linear-gradient(90deg, #60F3FF, #088FEB)',
					color: '#fff'
				}],
			}
		},
		onLoad(option) {
			this.getShopItem(option.id)
			if(this.isTourist){
				uni.showToast({
					title: `您是游客，部分功能不展示`,
					icon: 'none'
				})
			}
		},
		methods: {
			close() {
				// before-close 为true的情况下，手动执行 close 才会关闭对话框
				this.$refs.popup.close()
			},
			confirm() {
				const obj = {
					userId: localStorage.getItem('userId'),
					shopItemId: this.shopItem.id,
					amount: this.amount,
				}
				console.log(obj)
				ApiCart.add4cart(obj).then(res => {
					console.log(res)
					if(res.code === 200){
						uni.showToast({
							title: `加入购物车成功`,
							icon: 'success'
						})
					}
				})
				
				this.$refs.popup.close()
			},
			
			closeShopOrder() {
				// before-close 为true的情况下，手动执行 close 才会关闭对话框
				this.$refs.shopOrder.close()
			},
			confirmShopOrder() {
				const obj = {
					userId: localStorage.getItem('userId'),
					shopItemId: this.shopItem.id,
					amount: this.amount,
				}
				console.log(obj)
				ApiShopOrder.add4shoporder(obj).then(res => {
					console.log(res)
					if(res.code === 200){
						uni.showToast({
							title: `下单成功`,
							icon: 'success'
						})
						uni.navigateTo({
						  url: '/pages/mine/shopOrder'
						})
					}
				})
				
				this.$refs.popup.close()
			},
			getShopItem(id){
				let that = this;
				ApiShopItem.sel4shopitem(id).then(res => {
					console.log(res)
					if(res.code === 200){
						that.shopItem = res.data
					}
				})
			},
			toCustomerService() {
				uni.navigateTo({
				  url: '/pages/mine/customerService',
				})
			},
			toShop(){
				uni.showToast({
					title: `暂时只有一家店铺`,
					icon: 'none'
				})
			},
			toShoppingCart(){
				// uni.showToast({
				// 	title: `暂时没有购物车`,
				// 	icon: 'none'
				// })
				uni.navigateTo({
				  url: '/pages/mine/shoppingCart'
				})
			},
			// 商品导航-左侧点击事件
			onClick(e) {
				if(e.index === 0){
					// 客服
					this.toCustomerService();
				} else if(e.index === 1) {
					// 店铺
					this.toShop();
				} else {
					// 购物车
					this.toShoppingCart();
				}
			},
			// 商品导航-右侧点击事件
			buttonClick(e) {
				console.log(e)
				if(e.index === 0){
					// 购物车, 打开面板
					this.$refs.popup.open()
					// uni.showToast({
					// 	title: `暂时没有购物车`,
					// 	icon: 'none'
					// })
				} else {
					// 购买
					this.$refs.shopOrder.open()
				}
			}
			
		}
	}
</script>

<style>
.goods-carts {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		position: fixed;
		left: 0;
		right: 0;
		/* #ifdef H5 */
		left: var(--window-left);
		right: var(--window-right);
		/* #endif */
		bottom: 0;
	}
</style>
