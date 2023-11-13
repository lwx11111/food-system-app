<template>
	<view>
		<uni-search-bar style="border: 1px red solid;">

		</uni-search-bar>
		<!-- 菜谱列表 -->
		<view style="border: 1px red solid;">
			<uni-card v-for="(item,index) in menu"
					  @click="toMenuDetail(index)">
				<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
				<view>
					<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
				</view>
			</uni-card>
		</view>
		<view style="border: 1px red solid;">
			<h1>每日推荐</h1>
		</view>
		<view style="border: 1px red solid;">
			<uni-card v-for="(item,index) in dailyMenu">
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
				// 每日推荐
				dailyMenu : [],
				// 菜谱列表
				menu : [
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
				//
				params: {
					pageSize: '10'
				}
			
		  }
		},
		onLoad: function() {
			this.getMenuList();
			this.getDailyRecommendation();
		},
		methods: {
			getMenuList() {
				let that = this;
				ApiMenu.selpage4menu(this.params).then(res => {
					if(res.code === 200){
						that.menu = res.data.records
					}
				})
			},
			getDailyRecommendation() {
				let that = this;
				// ApiMenu.selpage4menu(this.params).then(res => {
				// 	if(res.code === 200){
				// 		that.dailyMenu = res.data.records
				// 	}
				// })
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
			}
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
