<template>
  <view>
	<!-- 物品数据 -->
	<uni-card v-for="(item,index) in carts" @click="toShopItemDetail(index)">
		<view style="margin-bottom: 10px;"><h2>{{item.shopItem.name}}</h2></view>
		<view>
			<image style="width: 100%; height: 200px;" 
					:src="item.shopItem.picture"></image>
		</view>
		<view style="color: red;">{{item.shopItem.price}}¥</view>
		<view>数量:{{item.amount}}</view>
	</uni-card>
  </view>
</template>

<script>
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	import ApiShopCategory from '@/api/shop/api_shopitemcategory.js'
	import ApiCart from '@/api/shop/api_cart.js';
	import ApiShopOrder from '@/api/shop/api_shoporder.js';
	
	export default {
		data() {
		  return {
			// 
			carts: [
				{
					id:'',
					userId:localStorage.getItem('userId'),
					amount:'',
					shopItem: {
						id:'1',
						name:'1',
						picture:'1',
						description:'1',
						price:'1'
					},
				}
			],
			
			// 查询参数
			params: {
				userId: '1',
				name:'',
				categoryId: '',
				pageSize: '10'
			}
		  }
		},
		onLoad: function() {
			this.getShopItem();
		},
		methods: {
			// 物品信息
			getShopItem() {
				let that = this;
				ApiCart.listCartByUserId(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.carts = res.data;
					}
				})
			},
			// 页面跳转
			toShopItemDetail(index){
				uni.navigateTo({
				  url: '/pages/shop/shopItemDetail?id=' + this.carts[index].shopItem.id,
				})
			}
			
		 
		}
	}
</script>

<style lang="scss">
  /* #ifndef APP-NVUE */
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  view {
    font-size: 14px;
    line-height: inherit;
  }

  /* #endif */

  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

  .grid-item-box {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 15px 0;
  }

  .uni-margin-wrap {
    width: 690rpx;
    width: 100%;
    ;
  }

  .swiper {
    height: 300rpx;
  }

  .swiper-box {
    height: 150px;
  }

  .swiper-item {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #fff;
    height: 300rpx;
    line-height: 300rpx;
  }

  @media screen and (min-width: 500px) {
    .uni-swiper-dot-box {
      width: 400px;
      /* #ifndef APP-NVUE */
      margin: 0 auto;
      /* #endif */
      margin-top: 8px;
    }

    .image {
      width: 100%;
    }
  }
</style>
