<!-- 创新界面 -->
<template>
  <view>

	  <uni-search-bar  v-model="params.title" @confirm="search"></uni-search-bar>
	  <!-- 分类标签 -->
	  <view>
	  	<view>
	  		<uni-tag v-for="(item,index) in menuCategoryData" :circle="true" 
	  				:inverted="item.inverted" 
	  				:text="item.name" 
	  				type="primary" 
	  				@click="setInverted(index)">
	  		</uni-tag>
	  	</view>
	  </view>
	  
	  <view v-for="(item,index) in menus" v-if="index%2==0">
		<u-row gutter="16">
			<u-col span="6" @click="toMenuDetail(index)">
				<MenuCard :menu="item"></MenuCard>
			</u-col>
			<u-col span="6" 
					@click="toMenuDetail(index + 1)" 
					v-if="index+1 < menus.length">
					<MenuCard :menu="menus[index + 1]"></MenuCard>
				<!-- <uni-card>
					<view style="margin-bottom: 10px;"><h2>{{menus[index + 1].name}}</h2></view>
					<view>
						<image style="width: 200px; height: 200px;" :src="menus[index + 1].image"></image>
					</view>
				</uni-card> -->
			</u-col>
		</u-row>
	  </view>
	  
	  <!-- <u-tabbar :fixed="true">
	  	<u-tabbar-item text="去发布" icon="home" @click="toMenuPublish()" ></u-tabbar-item>
	  </u-tabbar> -->
  </view>
</template>

<script>
	import Api from '@/api/menu/menu.js'
	import ApiMenuCategory from '@/api/menu/api_menucategory.js'
	import MenuCard from '@/pages/menu/components/menuCard.vue';
	
	export default {
		components:{
			MenuCard
		},
		data() {
		  return {
			  value1: 0,
			  // 游客模式不展示
			  isTourist: localStorage.getItem("isTourist"),
			  // 筛选
			  lastChoose: '',
			  // 分类
			  menuCategoryData:[
			  	{
			  		name:'全部',
					id:'0'
			  	}
			  ],
			menus:[
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
				categoryId: '',
				title:''
			}
		  }
		},
		onLoad: function(option) {
			this.getMenuCategoryData();
			this.listMenus();
		},
		methods: {
			click1(e) {
				console.log('click1', e);
			},
			toMenuPublish(){
				if(this.isTourist){
					uni.showToast({
						title: `您是游客，该功能不开放`,
						icon: 'none'
					})
					return;
				}
				uni.navigateTo({
				  url: '/pages/menu/menuPublish',
				})
			},
			
			// 分类信息
			getMenuCategoryData() {
				let that = this;
				const params = {};
				ApiMenuCategory.selpage4menucategory(params).then(res => {
					console.log(res.data.records);
					if(res.code === 200){
						that.menuCategoryData = res.data.records;
						that.menuCategoryData.unshift(
							{
								id:'0',
								name:'全部'
							}
						)
						that.menuCategoryData.forEach(item => {
							item.inverted = true;
						})
					}
				})
			},
			// 标签栏
			setInverted(index) {
				// 改变上次
				if(this.lastChoose !== undefined && this.lastChoose !== ''){
					this.menuCategoryData[this.lastChoose].inverted = !this.menuCategoryData[this.lastChoose].inverted;
				}
				// 改变这次
				this.menuCategoryData[index].inverted = !this.menuCategoryData[index].inverted;
				this.lastChoose = index;
				//由于 JavaScript 的限制，Vue 不能检测数组和对象的变化。
				this.$forceUpdate();
				// 获取数据
				if(index === 0){
					this.params.categoryId = '';
				} else {
					this.params.categoryId = this.menuCategoryData[index].id;
				}
				console.log(this.menuCategoryData[index].id)
				this.listMenus();
			},
			search() {
				this.listMenus();
			},
			listMenus() {
				let that = this;
				Api.selpage4menu(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						// 剔除非民族
						for(let i = 0; i < res.data.records.length; i++){
							if( res.data.records[i].categoryId === null || res.data.records[i].categoryId === '' ){
								res.data.records.splice(i, 1);
							}
						}
						that.menus = res.data.records
					}
				})
			},
			toMenuDetail(index) {
				let id = this.menus[index].id
				uni.navigateTo({
				  url: '/pages/Menu/MenuDetail?id=' + id
				})
			}
		 
		}
	}
</script>

