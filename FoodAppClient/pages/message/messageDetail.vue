<!-- 私信详情 -->
<template>
	<view>
		<uni-card v-for="(item, index) in message">
			<u-row>
				<u-col span="4">
					<u--image src="https://cdn.uviewui.com/uview/album/1.jpg" shape="circle"></u--image>
				</u-col>
				<u-col span="8">
					<u-row>
						{{item.sendUserName}}
					</u-row>
					<u-row>
						{{item.content}}
					</u-row>
				</u-col>
			</u-row>
		</uni-card>
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
					sendUserName:'',
					receiveUserId:'',
					content:'',
					sendTime:'',
					parentId:''
				}
				
			],
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
			}
			
		 
		}
	}
</script>

<style lang="scss">

</style>
