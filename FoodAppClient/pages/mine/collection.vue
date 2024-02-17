<template>
	<view>
		<u-tabs :list="list1" @click="click"></u-tabs>
		
		<uni-card v-for="(item,index) in menuCollection"
				  v-if="showIndex === 0"
				  @click="toMenuDetail(index)">
			<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		
		<!-- 物品数据 -->
		<uni-card v-for="(item,index) in shopItems" 
				v-if="showIndex === 1"
				@click="toShopItemDetail(index)">
			<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
			<view>{{item.price}}¥</view>
		</uni-card>
	</view>
</template>

<script>
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	import ApiShopCategory from '@/api/shop/api_shopitemcategory.js'
	import ApiMenu from '@/api/menu/menu.js'
	
	export default {
		data() {
			return {
				// 顶部tag
				list1: [{
				  name: '菜谱',
				}, {
				  name: '菜品',
				}],
				// 用来确定展示什么内容
				showIndex: 0,
				menuCollection: [],
				// 物品
				shopItems: [
					{
						id:'1',
						name:'1',
						picture:'1',
						description:'1',
						price:'1'
					},
				],
				// 查询参数
				params: {
					userId: localStorage.getItem('userId'),
					name:'',
					categoryId: '',
					pageSize: '10'
				}
			}
		},
		onLoad: function() {
			this.getMenuCollection();
		},
		methods: {
			
			/**
			 * 标签点击
			 * @param {Object} item
			 */
			click(item) {
				this.showIndex = item.index;
				if(item.index === 0){
					this.getMenuCollection();
				} else if (item.index === 1){
					this.getShopItem();
				}
			},
			// 菜谱收藏
			getMenuCollection() {
				let that = this;
				ApiMenu.getMenuCollectionByUserId(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.menuCollection = res.data.records;
					}
				})
				
			},
			// 物品信息
			getShopItem() {
				let that = this;
				ApiShopItem.selpage4shopitem(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.shopItems = res.data.records;
					}
				})
			},
			// 页面跳转
			toShopItemDetail(index){
				uni.navigateTo({
				  url: '/pages/shop/shopItemDetail?id=' + this.shopItems[index].id,
				})
			},
			toMenuDetail(index) {
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + this.menu[index].id,
				  events: {
				    // 为指定事件添加一个监听器，获取被打开页面传送到当前页面的数据
				    acceptDataFromOpenedPage: function(data) {
				      console.log(data)
				    },
				    someEvent: function(data) {
				      console.log(data)
				    }
				  },
				  success: function(res) {
				    // 通过eventChannel向被打开页面传送数据
				    res.eventChannel.emit('acceptDataFromOpenerPage', { data: 'data from starter page' })
				  }
				})
			},
			
		 
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
