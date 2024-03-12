<template>
	<view>
		<uni-card>
			<uni-forms ref="form" :modelValue="formData" :rules="formRules">
				<uni-section title="菜谱名" type="line" padding>
					<uni-forms-item label="菜谱名" name="name" required>
						<uni-easyinput v-model="formData.name" placeholder="请输入菜谱名"></uni-easyinput>
					</uni-forms-item>
				</uni-section>
				<uni-section title="菜谱描述" type="line" padding>
					<uni-easyinput type="textarea" autoHeight v-model="formData.description" placeholder="请输入菜谱描述"></uni-easyinput>
				</uni-section>
				<uni-section title="菜谱图片" type="line" padding>
					<MinioUpload @getUrl="getUrl"></MinioUpload>
				</uni-section>
				<uni-section title="菜谱类型" type="line">
				      <uni-data-select
				        v-model="formData.categoryId"
				        :localdata="menuCategoryData">
					</uni-data-select>
				</uni-section>
				<!-- 菜谱原料 -->
				<uni-section title="菜谱原料" type="line"></uni-section>
				<uni-card v-for="(item,key) in ingredients">
					<view>
						<view class="example-body">
							<MinioUpload key1="ingredient" 
										 :key2="key" 
										 @getUrl="getUrl">
							 </MinioUpload>
						</view>
						<uni-easyinput style="margin-top: 10px; margin-bottom: 10px;" 
									   type="text" 
									   autoHeight 
									   v-model="item.name" 
									   placeholder="请输入名称">
					   </uni-easyinput>
						<uni-easyinput type="text" 
									   autoHeight 
									   v-model="item.amount" 
									   placeholder="请输入数量">
					   </uni-easyinput>
					</view>
					<button class="mini-btn" 
							type="primary" 
							size="mini" 
							style="margin-top: 10px;" 
							@click="removeIngredients(key)">
							删除原料
					</button>
				</uni-card>
				<button style="margin-left: 35px;" class="mini-btn" type="primary" size="mini" @click="addIngredients()">添加原料</button>
				<!-- 制作步骤 -->
				<uni-section title="制作步骤" type="line"></uni-section>
				<uni-card v-for="(item,key) in steps">
					<view>
						<view class="example-body">
							<MinioUpload key1="steps"
										 :key2="key" 
										 @getUrl="getUrl">
							 </MinioUpload>
						</view>
						<uni-easyinput type="textarea" autoHeight v-model="item.description" placeholder="请输入描述"></uni-easyinput>
					</view>
					<button style="margin-top: 10px;" class="mini-btn" type="primary" size="mini" @click="removeSteps(key)">删除步骤</button>
				</uni-card>
				<button style="margin-left: 35px;" class="mini-btn" type="primary" size="mini" @click="addSteps()">添加步骤</button>
			</uni-forms>
		</uni-card>
		<button type="primary" @click="submit()">提交</button>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	import MinioUpload from '@/pages/common/minioUpload.vue'
	import ApiMenuCategory from '@/api/menu/api_menucategory.js'
	
	export default {
		components:{
			MinioUpload
		},
		
		data() {
		  return {
			  menuCategoryData:[],
			// 表单数据
			formData: {
				name: '',
				description: "",
				ingredients:[],
				steps:[],
				image: '',
				userId: localStorage.getItem('userId'),
				categoryId:''
			},
			// 菜谱类型 todo 配置字典
			menuTypes: [
			  { value: 0, text: "素菜" },
			  { value: 1, text: "荤菜" },
			],
			// 菜谱原料
			ingredients: [
				{
					name: '',
					amount: '',
					img: ''
				}
			],
			// 菜谱步骤
			steps: [
				{
					description: "",
					img: ''
				}
			],
			// 表单校验规则
			formRules: {
				name: {
					rules: [
						{
							required: true,
							errorMessage: '请输入菜谱名',
						},
					]
				}
			},
		  }
		},
		onLoad() {
			this.getMenuCategoryData();
		},
		onReady() {
			// 需要在onReady中设置规则
			this.$refs.form.setRules(this.formRules)
		},
		methods: {
			// 分类信息
			getMenuCategoryData() {
				let that = this;
				const params = {};
				ApiMenuCategory.selpage4menucategory(params).then(res => {
					console.log(res.data.records);
					if(res.code === 200){
						for(let i = 0; i < res.data.records.length; i++){
							let data = res.data.records[i]
							let obj = {
								value: data.id,
								text: data.name
							}
							that.menuCategoryData.push(obj);
						}
					}
				})
				
			},
			getUrl(url, key1, key2) {
				console.log(url);
				console.log(key1);
				console.log(key2);
				// url就是子组件传过来的值
				if(key1 === undefined && key2 === undefined ){
					this.formData.image = url
				} else if (key1 === 'ingredient'){
					this.ingredients[key2].img = url
				} else {
					this.steps[key2].img = url
				}
			  },
			 bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.detail.value)
				this.index = e.detail.value
			},
			addIngredients() {
				const item = {
					name: '',
					amount: '',
					img: ''
				}
				this.ingredients.push(item)
			},
			removeIngredients(key) {
				if(this.ingredients.length === 1){
					uni.showToast({
						title: '至少添加一条原料信息',
						duration: 2000,
						icon: "error"
					});
					return false;
				}
				this.ingredients.splice(key,1);
			},
			addSteps(){
				const item = {
					description: "",
					img: ''
				}
				this.steps.push(item)
			},
			removeSteps(key){
				if(this.steps.length === 1){
					uni.showToast({
						title: '至少添加一条步骤信息',
						duration: 2000,
						icon: "error"
					});
					return false;
				}
				this.steps.splice(key,1);
			},
			/**
			 * 复写 binddata 方法，如果只是为了校验，无复杂自定义操作，可忽略此方法
			 * @param {String} name 字段名称
			 * @param {String} value 表单域的值
			 */
			// binddata(name,value){
			// 通过 input 事件设置表单指定 name 的值
			//   this.$refs.form.setValue(name, value)
			// },
			myValidate(){
				for(var key in this.formData){
					console.log(key);
					if(this.formData[key] === undefined || this.formData[key] === null || this.formData[key] === ''){
						uni.showToast({
							title: key + ' 不能为空',
							duration: 2000,
							icon: "error"
						});
						return false;
					}
				}
				return true;
			},
			
			// 触发提交表单
			submit() {
				this.$refs.form.validate().then(res =>{
					if(!this.myValidate()){
						return 
					}
					
					for(let i = 0; i< this.ingredients.length; i++){
						console.log(this.ingredients[i]);
						for(var key in this.ingredients[i]){
							console.log(this.ingredients[i][key]);
							if(this.ingredients[i][key] === undefined || this.ingredients[i][key] === null || this.ingredients[i][key] === ''){
								uni.showToast({
									title: '原料信息' + key + '不能为空',
									duration: 2000,
									icon: "error"
								});
								return ;
							}
						}
					}
					
					for(let i = 0; i< this.steps.length; i++){
						for(var key in this.steps[i]){
							if(this.steps[i][key] === undefined || this.steps[i][key] === null || this.steps[i][key] === ''){
								uni.showToast({
									title: '步骤信息' + key + '不能为空',
									duration: 2000,
									icon: "error"
								});
								return false;
							}
						}
					}
					// 封装参数
					this.formData.ingredients = this.ingredients;
					this.formData.steps = this.steps;
					ApiMenu.add4menu(this.formData).then(res => {
						console.log(res)
						if(res.code === 200){
							uni.showToast({
								title: '发布成功',
								duration: 2000,
								success() {
									setTimeout( () => {
										uni.switchTab({
											url: '/pages/home'
										});
									}, 2000)
								}
							});
							
							
						}
					})
				}).catch(err =>{
					console.log('表单错误信息：', err);
				})
			},
			
		}
	}
</script>

<style lang="scss">
 
</style>
