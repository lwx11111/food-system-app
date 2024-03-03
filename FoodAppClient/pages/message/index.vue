<template>
	<view>
		<uni-card v-for="(item, index) in messageList" @click="toSendMessage(item.receiveUserId)">
			<u-row>
				<u-col span="3">
					<u--image width="80px" height="80px" src="https://cdn.uviewui.com/uview/album/1.jpg" shape="circle"></u--image>
				</u-col>
				<u-col span="9" style="margin-left: 10px;">
					<u-row>
						<u-col span="3">
							<h3>{{item.sendUserName}}123</h3>
						</u-col>
						<u-col span="9">
							{{item.sendTime}}
						</u-col>
					</u-row>
					<u-row style="margin-top: 10px;">
						{{item.content}}
					</u-row>
				</u-col>
			</u-row>
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
	</view>
</template>

<script>
	import ApiMessage from '@/api/api_message.js';
	
	export default {
		data() {
		  return {
			messageList:[
				{
					id:'',
					sendUserId:'',
					sendUserName:'123',
					receiveUserId:'',
					content:'',
					sendTime:'',
					parentId:''
				}
				
			],
			// 私信展示
			showPop: false,
			// 私信内容
			messageContent: '',
			// 接受者UserId
			receiveUserId: '',
			// 查询参数
			params: {
				sendUserId: '',
				pageSize: '10'
			}
		  }
		},
		onLoad: function() {
			this.params.sendUserId = localStorage.getItem("userId");
			this.listMessages();
		},
		methods: {
			search() {
				this.getShopItem();
			},
			
			listMessages() {
				let that = this;
				ApiMessage.selpage4message(this.params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.messageList = res.data.records;
					}
				})
			},
			
			// 页面跳转
			toMessageDetail(index){
				uni.navigateTo({
				  url: '/pages/message/messageDetail?id=' + this.messageList[index].id,
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
					console.log(res);
					if(res.code === 200){
						uni.showToast({
							title: `发送成功`,
							icon: 'none'
						})
						that.closeMsg();
					}
				})
			},
			
		 
		}
	}
</script>

<style lang="scss">
  
</style>
