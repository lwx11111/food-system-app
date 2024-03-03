<template>
	<view>
		<view style="width: ;">
			<uni-search-bar v-model="params.name" @confirm="search"></uni-search-bar>
			<!-- <u-search placeholder="请搜索" v-model="params.name" @search="search"></u-search> -->
		</view>

		<!-- 应用列表 -->
		<view style="display: flex; justify-content: space-between; margin: 0 -8px;">
			<uni-card style="border-radius: 10px;"> 
					<u-col @click="toCommunity()">
						<image style="width: 100px; height: 100px;" 
							 src="http://8.130.120.92:9000/appsys/community.png"></image>
						<view>社区管理</view>
					</u-col>
			</uni-card>
			<uni-card style="border-radius: 10px;"> 
					<u-col @click="toHealth()">
						<image style="width: 100px; height:100px;" 
								src="http://8.130.120.92:9000/appsys/health.png"></image>
						<view>健康管理</view>
					</u-col>
			</uni-card>
		</view>
		
		<view>
			<h1 style="margin-left: 30px;">每日推荐</h1>
			<uni-card v-for="(item,index) in dailyMenu"
					  @click="toMenuDetail(index)" style="border-radius: 10px;">
				<view>
					<image style="width: 400px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
				</view>
				<uni-card style="border-radius: 10px;">
						<view>
							<text>{{ item.name }}</text>
						</view>
					</uni-card>
			</uni-card>
		</view>
				
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	
	export default {
		data() {
		  return {
			  // 游客模式不展示
			  isTourist: localStorage.getItem("isTourist"),
				// 每日推荐
				dailyMenu : [
					{
					 description: "11",
					 id:1,
					 ingredients:"11",
					 likes:"11",
					 name:"2",
					 params:null,
					 steps:"11" ,
					}
				],
				// 搜索
				name:'',
				// 查询参数
				params: {
					name:'',
					pageSize: '10'
				},
				globalConfig: getApp().globalData.config,
			
		  }
		},
		onLoad: function() {
			// this.getMenuList();
			this.getDailyRecommendation();
		},
		methods: {
			toHealth(){
				uni.navigateTo({
				  url: '/pages/health/index',
				})
			},
			search() {
				this.getMenuList();
				// uni.switchTab({
				// 	url: '/pages/menu/index?name' + this.name
				// });
			},

			getDailyRecommendation() {
				let that = this;
				ApiMenu.getDailyRecommendation(this.params).then(res => {
					if(res.code === 200){
						that.dailyMenu = res.data.records
					}
				})
			},
			toMenuDetail(index) {
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + this.dailyMenu[index].id,
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
			toCommunity(){
				if(this.isTourist){
					uni.showToast({
						title: `您是游客，该功能不开放`,
						icon: 'none'
					})
					return;
				}
				uni.navigateTo({
				  url: '/pages/community/index',
				})
			},
		}
	}
</script>

<style>
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .logo {
    height: 200rpx;
    width: 200rpx;
    margin-top: 200rpx;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 50rpx;
  }

  .text-area {
    display: flex;
    justify-content: center;
  }

  .title {
    font-size: 36rpx;
    color: #8f8f94;
  }
</style>
