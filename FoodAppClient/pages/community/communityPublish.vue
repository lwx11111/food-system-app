<template>
	<view>
		<uni-card>
			<u-form :model="form" ref="uForm">
				<u-form-item label="标题" prop="title">
					<u-input v-model="form.title" />
				</u-form-item>
				<u-form-item label="内容" prop="content">
					<u-input type="textarea" height="300" :auto-height="false" v-model="form.content"  />
				</u-form-item>
			</u-form>
			<u-button @click="submit()">提交</u-button>
		</uni-card>
	</view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
	export default {
		data() {
		  return {
			// 表单数据
			form: {
				title: '',
				content: '',
				userId:''
			},
			// 表单校验规则
			rules: {
				title: [
					{ 
						required: true, 
						message: '请输入标题', 
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change','blur'],
					}
				],
				content: [
					{
						min: 5, 
						message: '内容不能少于5个字', 
						trigger: ['change','blur']
					}
				]
			},
			
		  }
		},
		onLoad() {
			
		},
		onReady() {
			// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
			this.$refs.uForm.setRules(this.rules);
			console.log("lwwwwwwwwwwwwwwwwwwww")
		},
		methods: {
			submit() {
				this.$refs.uForm.setRules(this.rules);
				console.log("lwx")
				console.log(this.form)
				console.log(this.$refs.uForm)
				Api.add4community(this.form).then(res => {
					console.log("success")
					uni.navigateTo({
					  url: '/pages/community/index',
					})
				})
				this.$refs.uForm.validate(valid => {
					console.log(valid)
					if (valid) {
						console.log('验证通过');
						Api.add4community(this.form).then(res => {
							console.log("success")
							uni.navigateTo({
							  url: '/pages/community/index',
							})
						})
					} else {
						console.log('验证失败');
					}
				});
			}
			
		}
	}
</script>

<style lang="scss">
 
</style>
