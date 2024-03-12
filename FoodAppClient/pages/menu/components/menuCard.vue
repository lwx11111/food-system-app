<!-- 列表展示菜谱的card -->
<template>
	<view>
		<uni-card @click="toMenuDetail()">
			<view style="margin-bottom: 10px;"><h2>{{menu.name}}</h2></view>
			<view>
				<image style="width: 100%; height: 200px;" :src="menu.image"></image>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	
	export default {
		data() {
			return {
				// menu:{
				// 	description: "11",
				// 	id:1,
				// 	ingredients:[
				// 		{
				// 			name: '',
				// 			amount: '',
				// 			img: ''
				// 		}
				// 	],
				// 	likes:"11",
				// 	name:"2",
				// 	image:'',
				// 	chats:'',
				// 	collections:'',
				// 	categoryId:'',
				// 	type:'',
				// 	steps:[
				// 		{
				// 			description: "",
				// 			img: ''
				// 		}
				// 	],
				// }
			}
		},
		props:{
			menu:{
				type: Object,
				required: true
			}
		},
		onLoad: function(option) {
			
		},
		methods: {
			toMenuDetail() {
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + this.menu.id
				})
			}
		 
		}
	}
</script>

<style>
</style>
