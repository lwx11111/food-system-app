<!-- 社区个人列表 -->
<template>
  <view>

<!-- 	  <uni-search-bar  v-model="params.title" @confirm="search"></uni-search-bar>-->
	  <!-- 分类标签 -->
<!-- 	  <view>
	  	<view>
	  		<uni-tag v-for="(item,index) in categoryData" :circle="true" 
	  				:inverted="item.inverted" 
	  				:text="item.name" 
	  				type="primary" 
	  				@click="setInverted(index)">
	  		</uni-tag>
	  	</view>
	  </view> -->
	  
	  <view v-for="(item,index) in communities">
		<uni-card @click="toCommunityDetail(index)">
			<view>
				<image style="margin-left: 50px; width: 80%; height: 200px;" :src="item.img"></image>
			</view>
				<view style="margin-bottom: 10px;"><h2>{{ item.title }}</h2></view>
		</uni-card>
	  </view>
	  
	  <!-- <u-button type="primary" size="small" 
		@click="toCommunityPublish()" style="width: 50px;margin-top: 200px;">去发布</u-button> -->
  </view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
		import ApiCategory from '@/api/community/api_category.js'
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
				communities:[
					{
						title:'',
						content: '',
						id: '',
						likes: '',
						params: '',
						releaseTime:'', 
					}
				],
				// 查询参数
				params: {
					userId:localStorage.getItem("userId"),
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

<style>
</style>
