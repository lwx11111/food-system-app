<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{menu.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		<!-- 收藏点赞 -->
		<uni-card>
			<u-row gutter="16">
				<u-col span="4">
					<u-icon v-if="!this.isMenuLike" 
							@click="menuLike()" 
							name="thumb-up" 
							color="#2979ff" 
							size="28">
					</u-icon>
					<u-icon v-else @click="menuLike()" name="thumb-up-fill" color="#2979ff" size="28"></u-icon>
					
				</u-col>
				<u-col span="4">
					<u-icon v-if="!this.isMenuCollection" @click="menuCollection()" name="star" color="#2979ff" size="28"></u-icon>
					<u-icon v-else name="star-fill" @click="menuCollection()" color="#2979ff" size="28"></u-icon>
				</u-col>
			</u-row>
		</uni-card>
		<!-- 描述 -->
		<uni-card>
			<h2>描述</h2>
			<view>{{menu.description}}</view>
		</uni-card>
		<!-- 原料 -->
		<uni-card>
			<h2>原料信息</h2>
			<view v-for="(item,key) in menu.ingredients">
				<view>{{item.name}}</view>
				<view>{{item.amount}}</view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
				<u-divider></u-divider>
			</view>
			
		</uni-card>
		<!-- 步骤 -->
		<uni-card>
			<h2>步骤信息</h2>
			<view v-for="(item,key) in menu.steps">
				<view>步骤{{key + 1}}</view>
				<view>{{item.description}}</view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
				<u-divider></u-divider>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js';
	import ApiMenuLike from '@/api/menu/api_menulike.js';
	import ApiMenuCollection from '@/api/menu/api_menucollection.js';
	export default {
		data() {
			return {
				// 菜谱信息
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
				},
				isMenuLike: false,
				isMenuCollection: false,
			}
		},
		onLoad(option) {
			this.getMenu(option.id)
			const obj = {
				userId: '1',
				menuId: this.menu.id
			}
			ApiMenuLike.selpage4menulike(obj).then(res => {
				console.log(res);
				if(res.data.records.length > 0){
					this.isMenuLike = true;
				}
				
			})
			
			
			ApiMenuCollection.selpage4menucollection(obj).then(res => {
				console.log(res);
				if(res.data.records.length > 0){
					this.isMenuCollection = true;
				}
				
			})
			
		},
		methods: {
			menuLike(){
				const obj = {
					userId: '1',
					menuId: this.menu.id
				}
				if(this.isMenuLike === true){
					ApiMenuLike.add4menulike(obj).then(res => {
						console.log(res)
						
					})
				} else {
					ApiMenuLike.deleteMenuLikeByParams(obj).then(res => {
						console.log(res)
						if(res.code === 200){
							
						}
					})
					
				}
				this.isMenuLike = !this.isMenuLike;
			},
			menuCollection(){
				const obj = {
					userId: '1',
					menuId: this.menu.id
				}
				if(this.isMenuCollection === true){
					ApiMenuCollection.add4menucollection(obj).then(res => {
						console.log(res)
						
					})
				} else {
					ApiMenuCollection.deleteMenuCollectionByParams(obj).then(res => {
						console.log(res)
						if(res.code === 200){
							
						}
					})
					
				}
				this.isMenuCollection = !this.isMenuCollection;
			},
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
