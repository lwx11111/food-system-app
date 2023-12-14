<template>
	<view>
		<uni-card @click="toMenuPublish()">
			<view><h2>菜谱发布</h2></view>
			<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
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
