<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{shopItem.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		<!-- 收藏 -->
		<uni-section title="收藏" type="line">
			<view class="example-body">
				<uni-fav :checked="check" class="favBtn" @click="favClick(0)" />
			</view>
		</uni-section>
		<!-- 描述 -->
		<uni-card>
			<h2>描述</h2>
			<view>{{shopItem.description}}</view>
		</uni-card>
		<!-- 购物导航 -->
		<view class="goods-carts">
			<uni-goods-nav :fill="true" :options="options" :button-group="customButtonGroup" 
							@click="onClick"
							@buttonClick="buttonClick">
			</uni-goods-nav>
		</view>
		<!-- 购物车/购买面板 -->
		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog mode="input":before-close="true" 
							  @close="close" 
							  @confirm="confirm">
				<uni-section title="数量" type="line" padding>
					<uni-number-box v-model="amount"></uni-number-box>
				</uni-section>
			</uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	
	export default {
		data() {
			return {
				check: false,
				// 物品信息
				shopItem: {
					id:'1',
					name:'1',
					picture:'1',
					description:'1'
				},
				user: {
					userId:''
				},
				amount: 1,
				// 商品导航-左侧
				options: [
					{
						icon: 'chat',
						text: '客服'
					}, 
					{
						icon: 'shop',
						text: '店铺',
						info: 2,
						infoBackgroundColor: '#007aff',
						infoColor: "#f5f5f5"
					}, 
					{
						icon: 'cart',
						text: '购物车',
						info: 2
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
		},
		methods: {
			close() {
				// before-close 为true的情况下，手动执行 close 才会关闭对话框
				this.$refs.popup.close()
			},
			confirm() {
				// todo 加入购物车
				uni.showToast({
					title: `加入购物车成功`,
					icon: 'success'
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
				uni.navigateTo({
				  url: '/pages/shop/shoppingCart?id=' + this.user.userId,
				})
			},
			// 商品导航-左侧点击事件
			onClick(e) {
				console.log(e)
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
				uni.showToast({
					title: `点击${e.content.text}`,
					icon: 'none'
				})
			},
			// 商品导航-右侧点击事件
			buttonClick(e) {
				console.log(e)
				if(e.index === 0){
					// 购物车
					this.$refs.popup.open()
				} else {
					// 购买
					this.$refs[e].open()
				}
				this.options[2].info++
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
