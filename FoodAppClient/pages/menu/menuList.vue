<!-- 匹配菜谱后的界面 -->
<template>
	<view>
		<view v-for="(item,index) in menuList">
			<MenuCard :menu="item"></MenuCard>
		</view>
	</view>
</template>

<script>
	import MenuCard from '@/pages/menu/components/menuCard.vue';
	import ApiMenu from '@/api/menu/menu.js'
	import ApiDict from '@/api/api_sysdict.js'
	
	export default {
		components:{
			MenuCard
		},
		
		data() {
		  return {
			  menuName:[],

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
				name:'',
				pageSize: '10'
			}
		  }
		},
		onLoad: function(option) {
			this.menuName = JSON.parse(option.menuName);
			this.getMenu();
		},
		methods: {
			getMenu() {
				let that = this;
				let names = [];
				for(let i = 0; i < this.menuName.length; i++){
					names.push(this.menuName[i].name);
				}
				const param = {
					name: JSON.stringify(names) 
				}
				ApiMenu.listMenuByNames(param).then(res => {
					if(res.code === 200){
						that.menuList = res.data
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
