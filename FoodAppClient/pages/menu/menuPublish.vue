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
					<uni-file-picker limit="1"></uni-file-picker>
				</uni-section>
				<uni-section title="菜谱类型" type="line">
				      <uni-data-select
				        v-model="formData.type"
				        :localdata="menuTypes">
					</uni-data-select>
				</uni-section>
				<!-- 菜谱原料 -->
				<uni-section title="菜谱原料" type="line"></uni-section>
				<uni-card v-for="(item,key) in ingredients">
					<view>
						<view class="example-body">
							<uni-file-picker limit="1"></uni-file-picker>
						</view>
						<uni-easyinput type="text" autoHeight v-model="item.name" placeholder="请输入名称"></uni-easyinput>
						<uni-easyinput type="text" autoHeight v-model="item.amount" placeholder="请输入数量"></uni-easyinput>
					</view>
					<button class="mini-btn" type="primary" size="mini" @click="removeIngredients(key)">删除原料</button>
				</uni-card>
				<button class="mini-btn" type="primary" size="mini" @click="addIngredients()">添加原料</button>
				<!-- 制作步骤 -->
				<uni-section title="制作步骤" type="line"></uni-section>
				<uni-card v-for="(item,key) in steps">
					<view>
						<view class="example-body">
							<uni-file-picker limit="1"></uni-file-picker>
						</view>
						<uni-easyinput type="textarea" autoHeight v-model="item.description" placeholder="请输入描述"></uni-easyinput>
					</view>
					<button class="mini-btn" type="primary" size="mini" @click="removeSteps(key)">删除步骤</button>
				</uni-card>
				<button class="mini-btn" type="primary" size="mini" @click="addSteps()">添加步骤</button>
			</uni-forms>
		</uni-card>
		<button type="primary" @click="submit()">提交</button>
	</view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	export default {
		data() {
		  return {
			// 表单数据
			formData: {
				name: '',
				description: "",
				ingredients:[],
				steps:[],
				// image: '',
				userId: localStorage.getItem('userId'),
				type:''
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
			
		},
		onReady() {
			// 需要在onReady中设置规则
			this.$refs.form.setRules(this.formRules)
		},
		methods: {
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
							title: key + ' is null',
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
					console.log('表单数据信息：', res);
					// 封装参数
					this.formData.ingredients = this.ingredients;
					this.formData.steps = this.steps;
					// this.fromData.userId = '123';
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
