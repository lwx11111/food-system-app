<template>
  <view>
	  <uni-card>
	  	<view>
	  		<image style="width: 100px; height: 100px;" :src="community.img"></image>
	  	</view>
		<u-line />
		<view style="margin-bottom: 10px;"><h2>{{community.title}}</h2></view>
	  </uni-card>
	  <uni-card>
		  <view style="margin-bottom: 10px;">{{community.content}}</view>
	  </uni-card>
  </view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
	export default {
		data() {
		  return {
			community:{
				content: '',
				id: '',
				likes: '',
				params: '',
				releaseTime:'', 
				userId: localStorage.getItem('userId')
			},
			// 查询参数
			params: {
				name:'',
				type: '',
				pageSize: ''
			}
		  }
		},
		onLoad: function(option) {
			this.community.id = option.id
			this.getCommunity();
		},
		methods: {
			getCommunity() {
				let that = this;
				Api.sel4community(this.community.id).then(res => {
					console.log(res)
					if(res.code === 200){
						that.community = res.data
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
