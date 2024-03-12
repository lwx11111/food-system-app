<!-- 个人菜谱 -->
<template>
	<view>
		<uni-card v-for="(item,index) in menuList"
				  @click="toMenuDetail(index)">
			<view style="margin-bottom: 10px;"><h2>{{item.name}}</h2></view>
			<view>
				<image style="width: 300px; height: 200px;" :src="item.image"></image>
			</view>
		</uni-card>
	</view>
	
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'

	
	export default {
		data() {
		  return {

			menuList: [
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
				userId: localStorage.getItem("userId"),
				pageSize: '10'
			}
		  }
		},
		onLoad: function() {
			this.getMenu();
		},
		methods: {
			getMenu() {
				console.log(this.params);
				ApiMenu.selpage4menu(this.params).then(res => {
					if(res.code === 200){
						console.log(res);
						this.menuList = res.data.records
					}
				})
			},
			toMenuDetail(index) {

				var id = this.menuList[index].id;
				
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + id
				})
			}
		 
		}
	}
</script>

<style>
</style>
