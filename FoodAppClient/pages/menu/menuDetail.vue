<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{menu.name}}</h2></view>
			<view>
				<image style="width: 200px; height: 200px;" src="https://web-assets.dcloud.net.cn/unidoc/zh/uni@2x.png"></image>
			</view>
		</uni-card>
		<!-- 收藏点赞评论 -->
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
				<u-col span="4">
					<u-icon  @click="toComment()" name="chat" color="#2979ff" size="28"></u-icon>
				</u-col>
			</u-row>
		</uni-card>
		
		<u-popup :show="show" mode="bottom" @close="close()" :closeable="true">
			<view style="height:500px;">
				<view style="text-align: center;"><h1>发布评论</h1></view>
				<uni-section title="评论" type="line" padding>
					<uni-easyinput type="textarea" autoHeight v-model="content" placeholder="请输入评论"></uni-easyinput>
				</uni-section>
				<button @click="submit()" >提交</button>
			</view>
			
		</u-popup>
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
		
		<!-- 评论 -->
		<uni-card>
			<h2>评论信息</h2>
			<view v-for="(item,key) in comments">
				<!-- <view>{{item.user.username}}</view> -->
				<view>{{item.releaseTime}}</view>
				<view>{{item.content}}</view>
				<u-divider></u-divider>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js';
	import ApiMenuLike from '@/api/menu/api_menulike.js';
	import ApiMenuCollection from '@/api/menu/api_menucollection.js';
	import ApiMenuComment from '@/api/menu/api_menucomment.js';
	export default {
		data() {
			return {
				show: false,
				content: '',
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
				comments:[
					{
						user:{
							
						},
						content:'',
						releaseTime:''
					}
				],
				isMenuLike: false,
				isMenuCollection: false,
			}
		},
		onLoad(option) {
			this.getMenu(option.id)
			this.listComments(option.id)
		},
		methods: {
			close(){
				this.show = false
			},
			listComments(menuId){
				var that = this;
				const data = {
					menuId: menuId
				}
				ApiMenuComment.selpage4menucomment(data).then(res => {
					console.log(res);
					if(res.code === 200){
						console.log(res.records);
						that.comments = res.data.records
					}
				})
			},
			submit(){
				if(this.content === ''){
					uni.showToast({
						title: `请输入`,
						icon: 'none'
					})
					return
				}
				const obj = {
					content: this.content,
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				ApiMenuComment.add4menucomment(obj).then(res => {
					console.log(res);
					if(res.code === 200){
						uni.showToast({
							title: `成功`,
							icon: 'none'
						})
						
						uni.navigateTo({
						  url: '/pages/menu/menuDetail?id=' + this.menu.id
						})
					}
				})
				
			},
			toComment(){
				console.log("123");
				this.show = true
			},
			getLikeAndCollection(){
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				console.log(this.menu.id);
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
			menuLike(){
				console.log(this.menu.id)
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				console.log(this.isMenuLike);
				if(this.isMenuLike === true){
					
					console.log('cancel like');
					ApiMenuLike.deleteMenuLikeByParams(obj).then(res => {
						console.log(res)
						if(res.code === 200){
							uni.showToast({
								title: `取消成功`,
								icon: 'none'
							})
						}
					})
				} else {
					ApiMenuLike.add4menulike(obj).then(res => {
						console.log(res)
						uni.showToast({
							title: `成功`,
							icon: 'none'
						})
					})
					
				}
				this.isMenuLike = !this.isMenuLike;
			},

			menuCollection(){
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				if(this.isMenuCollection === true){
					
					ApiMenuCollection.deleteMenuCollectionByParams(obj).then(res => {
						console.log(res)
						if(res.code === 200){
							uni.showToast({
								title: `取消成功`,
								icon: 'none'
							})
						}
					})
				} else {
					ApiMenuCollection.add4menucollection(obj).then(res => {
						console.log(res)
						if(res.code === 200){
							uni.showToast({
								title: `成功`,
								icon: 'none'
							})
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
						this.getLikeAndCollection()
					}
				})			
			}
			
		}
	}
</script>

<style lang="scss">
 
</style>
