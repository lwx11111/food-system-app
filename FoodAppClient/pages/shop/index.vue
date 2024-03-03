<template>
  <view>
  	<uni-search-bar  v-model="params.name" @confirm="search"></uni-search-bar>
	<!-- 分类标签 -->
	<view>
		<view>
			<uni-tag v-for="(item,index) in categoryData" :circle="true" 
					:inverted="item.inverted" 
					:text="item.name" 
					type="primary" 
					@click="setInverted(index)">
			</uni-tag>
		</view>
	</view>
	<!-- 物品数据 -->
	<view v-for="(item,index) in shopItems" v-if="index%2==0">
		<u-row gutter="16">
			<u-col span="6" @click="toShopItemDetail(index)">
				<uni-card>
					<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
					<view>
						<image style="width: 200px; height: 100px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
					</view>
					<view>{{item.price}}¥</view>
				</uni-card>
			</u-col>
			<u-col span="6" 
					@click="toShopItemDetail(index + 1)" 
					v-if=" index + 1 < shopItems.length">
				<uni-card>
					<view style="margin-bottom: 10px;"><h2>{{shopItems[index + 1].name}}</h2></view>
					<view>
						<image style="width: 200px; height: 100px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
					</view>
					<view>{{shopItems[index + 1].price}}¥</view>
				</uni-card>
			</u-col>
		</u-row>
	</view>
	
<!-- 	<uni-card v-for="(item,index) in shopItems" v-if="index%2==0" @click="toShopItemDetail(index)">
		<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
		<view>
			<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
		</view>
		<view>{{item.price}}¥</view>
	</uni-card> -->
  </view>
</template>

<script>
	import ApiShopItem from '@/api/shop/api_shopitem.js';
	import ApiShopCategory from '@/api/shop/api_shopitemcategory.js'
	
	export default {
		data() {
		  return {
			// 筛选
			lastChoose: '',
			// 分类
			categoryData:[
				{
					inverted: true,
					name:'11'
				}
			],
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
				name:'',
				categoryId: '',
				pageSize: '10'
			}
		  }
		},
		onLoad: function() {
			this.getCategoryData();
			this.getShopItem();
		},
		methods: {
			search() {
				this.getShopItem();
			},
			// 标签栏 
			setInverted(index) {
				// 改变上次
				if(this.lastChoose !== undefined && this.lastChoose !== ''){
					this.categoryData[this.lastChoose].inverted = !this.categoryData[this.lastChoose].inverted;
				}
				// 改变这次
				this.categoryData[index].inverted = !this.categoryData[index].inverted;
				this.lastChoose = index;
				//由于 JavaScript 的限制，Vue 不能检测数组和对象的变化。
				this.$forceUpdate();
				// 获取数据
				console.log(index);
				if(index === 0){
					this.params.categoryId = '';
				} else {
					this.params.categoryId = this.categoryData[index].id;
				}
				
				this.getShopItem();
			},
			// 分类信息
			getCategoryData() {
				let that = this;
				ApiShopCategory.selpage4shopitemcategory(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.categoryData = res.data.records;
						that.categoryData.forEach(item => {
							item.inverted = true;
						})
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
