<!-- 社区发布 -->
<template>
	<view>
		<uni-card>
			<u-form :model="form" 
					:rules="rules"
					ref="uForm">
				<u-form-item label="标题" prop="title">
					<u-input v-model="form.title" />
				</u-form-item>
				<u-form-item label="内容" prop="content">
					<u-textarea v-model="form.content" placeholder="请输入内容" ></u-textarea>
				</u-form-item>
				<u-form-item
						label="类型"
						prop="categoryId"
						borderBottom
						@click="show = true;"
						ref="item1">
					<u--input
							v-model="form.categoryName"
							disabled
							disabledColor="#ffffff"
							placeholder="请选择类型"
							border="none"
					></u--input>
					<u-icon
							slot="right"
							name="arrow-right"
					></u-icon>
				</u-form-item>
				
				<u-form-item label="图片" prop="img">
					<MinioUpload @getUrl="getUrl"></MinioUpload>
				</u-form-item>
				
			</u-form>
			<u-button style="margin-top: 10px;" type="primary" @click="submit()">提交</u-button>
		</uni-card>
		
		<u-action-sheet
				:show="show"
				:actions="categoryData"
				title="请选择类型"
				@close="show = false"
				@select="categorySelect">
		</u-action-sheet>
	</view>
</template>

<script>
	import Api from '@/api/community/api_community.js'
	import ApiCategory from '@/api/community/api_category.js'
	import MinioUpload from '@/pages/common/minioUpload.vue'
	
	export default {
		components:{
			MinioUpload
		},
		
		data() {
		  return {
			  // 展示选择栏
			  show: false,
			categoryData:[],
			// 表单数据
			form: {
				categoryName:'',
				categoryId:'',
				img:'',
				title: '',
				content: '',
				userId:localStorage.getItem('userId')
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
						required: true, 
						min: 5, 
						message: '内容不能少于5个字', 
						trigger: ['change','blur']
					}
				],
				categoryId:[
					{
						required: true, 
						message: '请选择分类', 
						trigger: ['change','blur']
					}
				],
				img:[
					{
						required: true, 
						message: '请上传图片', 
						trigger: ['change','blur']
					}
				]
			},
			
		  }
		},
		onReady() {
			// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
			this.$refs.uForm.setRules(this.rules);
		},
		onLoad(){
			this.getCategoryData()
		},
		methods: {
			// 分类信息
			getCategoryData() {
				let that = this;
				const params = {};
				ApiCategory.selpage4category(params).then(res => {
					console.log(res)
					if(res.code === 200){
						that.categoryData = res.data.records;
					}
				})
			},
			categorySelect(e) {
				console.log(e);
				this.form.categoryId = e.id
				this.form.categoryName = e.name
				this.show = false;
			},
			getUrl(url) {
				console.log(url);
				// url就是子组件传过来的值
				this.form.img = url
			  },
			submit() {
				this.$refs.uForm.validate().then(res => {
					uni.$u.toast('校验通过')
					Api.add4community(this.form).then(res => {
						console.log("success")
						uni.navigateTo({
						  url: '/pages/community/index',
						})
					})
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			}
			
		}
	}
</script>

<style lang="scss">
 
</style>
