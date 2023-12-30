<template>
  <view>
	  <uni-search-bar  v-model="params.title" @confirm="search"></uni-search-bar>
	  <view v-for="(item,index) in communities" v-if="index%2==0">
		<u-row gutter="16">
			<u-col span="6" @click="toCommunityDetail(index)">
				<uni-card>
					<view>
						<image style="width: 100px; height: 100px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
					</view>
						<view style="margin-bottom: 10px;"><h2>{{item.title}}</h2></view>
				</uni-card>
			</u-col>
			<u-col span="6" 
					@click="toCommunityDetail(index + 1)" 
					v-if="index+1 < communities.length">
				<uni-card>
					<view>
						<image style="width: 100px; height: 100px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
					</view>
						<view style="margin-bottom: 10px;"><h2>{{ communities[index+1].title }}</h2></view>
				</uni-card>
			</u-col>
		</u-row>
	  </view>
  </view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
	export default {
		data() {
		  return {
			communities:[
				{
					title:'',
					content: '',
					id: '',
					likes: '',
					params: '',
					releaseTime:'', 
					userId: ''
				}
			],
			// 查询参数
			params: {
				title:''
			}
		  }
		},
		onLoad: function(option) {
			this.listCommunities();
		},
		methods: {
			search() {
				this.listCommunities();
			},
			listCommunities() {
				let that = this;
				Api.selpage4community(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.communities = res.data.records
					}
				})
			},
			toCommunityDetail(index) {
				let id = this.communities[index].id
				uni.navigateTo({
				  url: '/pages/community/communityDetail?id=' + id
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
