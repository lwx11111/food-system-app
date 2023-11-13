<template>
  <view>
  	<uni-search-bar style="border: 1px red solid;">
  
  	</uni-search-bar>
	<!-- 素菜 -->
	<view><h2>素菜</h2></view>
  	<view style="border: 1px red solid;">
  		<uni-card v-for="(item,index) in vegetableMenu"
  				  @click="toMenuDetail(index)">
  			<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
  			<view>
  				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
  			</view>
  		</uni-card>
  	</view>
	<!-- 肉菜 -->
	<view><h2>肉菜</h2></view>
	<view style="border: 1px red solid;">
		<uni-card v-for="(item,index) in meatMenu"
				  @click="toMenuDetail(index,1)">
			<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
	</view>
  </view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	export default {
		data() {
		  return {
			// 素菜菜谱
			meatMenu: [
				{
				 description: "",
				 id:'',
				 ingredients:"",
				 likes:"",
				 name:"",
				 steps:"" ,
				}
			],
			// 荤菜菜谱
			vegetableMenu: [
				{
				 description: "",
				 id:'',
				 ingredients:"",
				 likes:"",
				 name:"",
				 steps:"" ,
				}
			],
			// 查询参数
			params: {
				type: '',
				pageSize: '10'
			}
		  }
		},
		onLoad: function() {
			this.getMeatMenu();
			this.getVegetableMenu();
		},
		methods: {
			getMeatMenu() {
				let that = this;
				this.params.type = 1;
				ApiMenu.selpage4menu(this.params).then(res => {
					if(res.code === 200){
						that.meatMenu = res.data.records
					}
				})
			},
			getVegetableMenu() {
				let that = this;
				this.params.type = 0;
				ApiMenu.selpage4menu(this.params).then(res => {
					if(res.code === 200){
						that.vegetableMenu = res.data.records
					}
				})
			},
			toMenuDetail(index,type) {
				var id = this.vegetableMenu[index].id;
				if(type === 1){
					id = this.meatMenu[index].id;
				}
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + id
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
