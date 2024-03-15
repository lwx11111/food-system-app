<template>
	<view>
		<!-- 基本信息 -->
		<uni-card>
			<view style="margin-bottom: 10px;"><h2>{{menu.name}}</h2></view>
			<view>
				<image style="width: 100%; height: 200px;" :src="menu.image"></image>
			</view>
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
				<!-- <image style="width: 100%; height: 200px;" :src="item.img"></image> -->
				<u-divider></u-divider>
			</view>
		</uni-card>
		
		<!-- 步骤 -->
		<uni-card>
			<h2>步骤信息</h2>
			<view v-for="(item,key) in menu.steps">
				<view>步骤{{key + 1}}</view>
				<view>{{item.description}}</view>
				<image style="width: 100%; height: 200px;" :src="item.img"></image>
				<u-divider></u-divider>
			</view>
		</uni-card>
		
		<!-- 评论 -->
		<uni-card v-if="!this.isTourist">
			<h2>评论信息</h2>
			<view v-for="(item,key) in comments">
				<u-row>
					<u-col span="2">
						<image style="width: 30px; height: 30px;"
								@click="toSendMessage(1)"
								src="/static/images/tabbar/gouwuche-s.png"></image>
					</u-col>
					<u-col span="10">
						<u-row>
							<!-- <view>{{item.user.username}}</view> -->
							123
						</u-row>
						<u-row>
							<view>{{item.releaseTime}}</view>
						</u-row>
					</u-col>
				</u-row>
				<u-row>
					<view>{{item.content}}</view>
				</u-row>
				<u-divider></u-divider>
			</view>
		</uni-card>
		
		<!-- 私信弹出层 -->
		<u-popup style :show="showPop" mode="center" @close="closeMsg">
			<view style="width:300px;">
				<uni-card>
					<u--textarea style="margin-bottom: 10px;" v-model="messageContent" placeholder="请输入私信内容"></u--textarea>
					<u-button @click="sendMessage()" type="primary" text="确定"></u-button>
				</uni-card>
			</view>
		</u-popup>
				
		<u-tabbar v-if="!this.isTourist" inactiveColor="#2979ff" :fixed="true">
			<u-tabbar-item text="评论" :badge="this.menu.chats" icon="chat" color="#2979ff" size="28" @click="toComment()" ></u-tabbar-item>
			<u-tabbar-item text="点赞" :badge="this.menu.likes" :icon="likeIcon" @click="menuLike()" ></u-tabbar-item>
			<u-tabbar-item text="收藏" :badge="this.menu.collections" :icon="collectionIcon" @click="menuCollection()" ></u-tabbar-item>
		</u-tabbar>
		
		<!-- 评论 -->
		<u-popup :show="show" mode="bottom" @close="close()" :closeable="true">
			<view style="height:500px;">
				<view style="text-align: center;"><h1>发布评论</h1></view>
				<uni-section title="评论" type="line" padding>
					<uni-easyinput type="textarea" autoHeight v-model="content" placeholder="请输入评论"></uni-easyinput>
				</uni-section>
				<button @click="submit()" >提交</button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js';
	import ApiMenuLike from '@/api/menu/api_menulike.js';
	import ApiMenuCollection from '@/api/menu/api_menucollection.js';
	import ApiMenuComment from '@/api/menu/api_menucomment.js';
	import ApiMessage from '@/api/api_message.js';
	
	export default {
		data() {
			return {
				// "thumb-up", "thumb-up-fill,
				likeIcon: "thumb-up",
				// collectionIcon:["star", "star-fill"],
				collectionIcon: "star",

				// 游客模式不展示
				isTourist: localStorage.getItem("isTourist"),
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
				// 私信展示
				showPop: false,
				// 私信内容
				messageContent: '',
				// 接受者UserId
				receiveUserId: '',
			}
		},
		onLoad(option) {
		
			this.getMenu(option.id)
			this.listComments(option.id)
			if(this.isTourist){
				uni.showToast({
					title: `您是游客，部分功能不展示`,
					icon: 'none'
				})
			}
		},
		methods: {
			
			/**
			 * 发送私信
			 */
			sendMessage(){
				var that = this;
				if(that.messageContent === ""){
					uni.showToast({
						title: `请输入私信内容`,
						icon: 'none'
					})
					return;
				}
				const messageObj = {
					sendUserId: localStorage.getItem('userId'),
					receiveUserId: that.receiveUserId,
					content: that.messageContent
				}
				ApiMessage.add4message(messageObj).then(res => {

					if(res.code === 200){
						uni.showToast({
							title: `发送成功`,
							icon: 'none'
						})
						that.closeMsg();
						
					}
				})
			},
			
			/**
			 * 打开私信面板
			 * @param {Object} receiveUserId
			 */
			toSendMessage(receiveUserId){
				this.receiveUserId = receiveUserId;
				this.showPop = true;
			},
			closeMsg(){
				this.showPop = false;
			},
			close(){
				this.show = false
			},
			listComments(menuId){
				var that = this;
				const data = {
					menuId: menuId
				}
				ApiMenuComment.selpage4menucomment(data).then(res => {

					if(res.code === 200){

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
						this.show = false;
						this.listComments();
						this.menu.chats  = this.menu.chats + 1;
					}
				})
				
			},
			toComment(){
				this.show = true
			},
			getLikeAndCollection(){
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				
				ApiMenuLike.selpage4menulike(obj).then(res => {
					if(res.data.records.length > 0){
						this.likeIcon = "thumb-up-fill";
					}
					
				})
				
				ApiMenuCollection.selpage4menucollection(obj).then(res => {
					if(res.data.records.length > 0){
						this.collectionIcon = "star-fill";
					}
					
				})
			},
			menuLike(){
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}

				if(this.likeIcon === "thumb-up-fill"){
					ApiMenuLike.deleteMenuLikeByParams(obj).then(res => {
						if(res.code === 200){
							uni.showToast({
								title: `取消成功`,
								icon: 'none'
							})
						}
					})
					this.likeIcon = "thumb-up";
					this.menu.likes = this.menu.likes - 1;
				} else {
					ApiMenuLike.add4menulike(obj).then(res => {
						uni.showToast({
							title: `成功`,
							icon: 'none'
						})
					})
					this.likeIcon = "thumb-up-fill";
					this.menu.likes = this.menu.likes + 1;
				}
			},

			menuCollection(){
				const obj = {
					userId: localStorage.getItem('userId'),
					menuId: this.menu.id
				}
				if(this.collectionIcon === "star-fill"){
					ApiMenuCollection.deleteMenuCollectionByParams(obj).then(res => {
						if(res.code === 200){
							uni.showToast({
								title: `取消成功`,
								icon: 'none'
							})
						}
					})
					this.menu.collections = this.menu.collections - 1;
					this.collectionIcon = "star"
				} else {
					ApiMenuCollection.add4menucollection(obj).then(res => {
						if(res.code === 200){
							uni.showToast({
								title: `成功`,
								icon: 'none'
							})
						}
					})
					this.menu.collections = this.menu.collections + 1;
					this.collectionIcon = "star-fill"
				}
			},
			getMenu(id){
				let that = this;
				ApiMenu.sel4menu(id).then(res => {

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
