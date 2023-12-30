<template>
	<view>
		<uni-card>
			<u-row gutter="16">
				<u-col  @click="toMenuPublish()" span="4" style="border: 1px red solid;">
					<image style="width: 50px; height: 50px;" 
						 src="http://127.0.0.1:9000/appsys/community.png"></image>
					<view>菜谱发布</view>
				</u-col>
				<u-col @click="toCommunityPublish()" span="4">
					<image style="width: 50px; height: 50px;" 
							src="http://127.0.0.1:9000/appsys/health.png"></image>
					<view>社区帖子发布</view>
				</u-col>
			</u-row>
		</uni-card>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	
	export default {
		data() {
		  return {
				
		  }
		},
		onLoad: function() {
			
		},
		methods: {
			toMenuPublish() {
				uni.navigateTo({
				  url: '/pages/menu/menuPublish',
				  events: {
				  },
				  success: function(res) {}
				})
			},
			toCommunityPublish(){
				uni.navigateTo({
				  url: '/pages/community/communityPublish',
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
