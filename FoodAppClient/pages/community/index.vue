<template>
  <view>

	  <uni-search-bar  v-model="params.title" @confirm="search"></uni-search-bar>
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
	  
	  <view v-for="(item,index) in communities" v-if="index%2==0">
		<u-row gutter="16">
			<u-col span="6" @click="toCommunityDetail(index)">
				<uni-card>
					<view>
						<image style="width: 100px; height: 100px;" 
							:src="item.img"></image>
					</view>
						<view style="margin-bottom: 10px;"><h2>{{item.title}}</h2></view>
				</uni-card>
			</u-col>
			<u-col span="6" 
					@click="toCommunityDetail(index + 1)" 
					v-if="index+1 < communities.length">
				<uni-card>
					<view>
						<image style="width: 100px; height: 100px;" :src="communities[index + 1].img"></image>
					</view>
						<view style="margin-bottom: 10px;"><h2>{{ communities[index+1].title }}</h2></view>
				</uni-card>
			</u-col>
		</u-row>
	  </view>
	  
	  <u-button type="primary" size="small" 
		@click="toCommunityPublish()" style="width: 50px;margin-top: 200px;">去发布</u-button>
  </view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
	import ApiCategory from '@/api/community/api_category.js'
	export default {
		data() {
		  return {
			  // 游客模式不展示
			  isTourist: localStorage.getItem("isTourist"),
			  // 筛选
			  lastChoose: '',
			  // 分类
			  categoryData:[
			  	{
			  		inverted: true,
			  		name:'11'
			  	}
			  ],
			communities:[
				{
					title:'',
					content: '',
					id: '',
					likes: '',
					params: '',
					releaseTime:'', 
					userId: localStorage.getItem('userId')
				}
			],
			// 查询参数
			params: {
				categoryId: '',
				title:''
			}
		  }
		},
		onLoad: function(option) {
			this.getCategoryData();
			this.listCommunities();
		},
		methods: {
			toCommunityPublish(){
				if(this.isTourist){
					uni.showToast({
						title: `您是游客，该功能不开放`,
						icon: 'none'
					})
					return;
				}
				uni.navigateTo({
				  url: '/pages/community/communityPublish',
				})
			},
			
			// 分类信息
			getCategoryData() {
				let that = this;
				ApiCategory.selpage4category(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.categoryData = res.data.records;
						that.categoryData.forEach(item => {
							item.inverted = true;
						})
					}
				})
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
				if(index === 0){
					this.params.categoryId = '';
				} else {
					this.params.categoryId = this.categoryData[index].id;
				}
				console.log(this.categoryData[index].id)
				this.listCommunities();
			},
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
