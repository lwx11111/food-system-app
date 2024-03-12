<!-- 个人菜谱 -->
<template>
	<view>
		<view v-for="(item,index) in menuList">
			<MenuCard :menu="item"></MenuCard>
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
