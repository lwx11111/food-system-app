<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{menu.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		<!-- 描述 -->
		<uni-card>
			<h2>描述</h2>
			<view>{{menu.description}}</view>
		</uni-card>
		<!-- 原料 -->
		<uni-card>
			<h2>原料</h2>
			<view v-for="(item,key) in menu.ingredients">
				<view>{{item.name}}</view>
				<view>{{item.amount}}</view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		<!-- 步骤 -->
		<uni-card>
			<h2>步骤</h2>
			<view v-for="(item,key) in menu.steps">
				<view>{{item.description}}</view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	export default {
		data() {
		  return {
			  menu: {
				  description: "11",
				  id:1,
				  ingredients:[
					 {
					 	name: '',
					 	amount: '',
					 	img: ''
					 }
				  ],
				  likes:"11",
				  name:"2",
				  params:null,
				  steps:[
					  {
					  	description: "",
					  	img: ''
					  }
				  ] ,
			  }
		  }
		},
		onLoad(option) {
			this.getMenu(option.id)
		},
		methods: {
			getMenu(id){
				let that = this;
				ApiMenu.sel4menu(id).then(res => {
					console.log(res)
					if(res.code === 200){
						that.menu = res.data
					}
				})
			}
			
		}
	}
</script>

<style lang="scss">
 
</style>
