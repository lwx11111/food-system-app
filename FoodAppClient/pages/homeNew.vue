<template>
	<view>
		<!-- 应用列表 -->
		<u-swiper
		            :list="list1"
		            @change="change"
		            @click="click(list1)"
					:height="250"
		></u-swiper>
		<view>
			<h1 style="margin-left: 30px;margin-top: 10px;">每日推荐</h1>
			<view v-for="(item,index) in menuList">
				<MenuCard :menu="item"></MenuCard>
			</view>
		</view>
				
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	import MenuCard from '@/pages/menu/components/menuCard.vue';
	
	export default {
		components:{
			MenuCard
		},
		data() {
			return {
				menuList:[],
				// 游客模式不展示
				isTourist: localStorage.getItem("isTourist"),
				linkIndex: 0,
				list1: [
					 '../static/images/民族美食banner.jpg',
					 '../static/images/健康管理banner.jpg',
					 '../static/images/社区交流banner.jpg',
					],
				// 查询参数
				params: {
					name:'',
					pageSize: ''
				},
				globalConfig: getApp().globalData.config,
			}
		},
		onLoad: function() {
			this.getDailyRecommendation();
		},
		methods: {
			change(e){
				this.linkIndex = e.current;
			},
			click(list){
				switch (this.linkIndex){
					case 0 : this.toMenuListNew(); break;
					case 1 : this.toHealth(); break;
					case 2 : this.toCommunity(); break;
				}
			},
			toMenuListNew(){
				uni.navigateTo({
				  url: '/pages/menu/menuListNew',
				})
			},
			toHealth(){
				uni.navigateTo({
				  url: '/pages/health/index',
				})
			},
			getDailyRecommendation() {
				let that = this;
				ApiMenu.getDailyRecommendation(this.params).then(res => {
					console.log(res);
					if(res.code === 200){
						that.menuList = res.data.records
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
