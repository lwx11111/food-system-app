<template>
  <view>
  	<uni-card>
		<h1>卡路里计算器</h1>
		<u--form :model="form" :rules="rules" ref="uForm">
			<u-form-item label="年龄" prop="age">
				<u-input type="number" v-model="form.age" />
			</u-form-item>
			<u-form-item
					label="性别"
					prop="sex"
					borderBottom
					@click="show = true;"
					ref="item1">
				<u--input
						v-model="form.sex"
						disabled
						disabledColor="#ffffff"
						placeholder="请选择性别"
						border="none"
				></u--input>
				<u-icon
						slot="right"
						name="arrow-right"
				></u-icon>
			</u-form-item>
			<u-form-item label="体重(公斤)" prop="weight">
				<u-input type="number" v-model="form.weight"  />
			</u-form-item>
			<u-form-item label="身高(厘米)" prop="height">
				<u-input type="number" v-model="form.height"  />
			</u-form-item>
			<u-form-item
					label="活动程度"
					prop="type"
					borderBottom
					@click="showAct = true;"
					ref="item1">
				<u--input
						v-model="form.type"
						disabled
						disabledColor="#ffffff"
						placeholder="请选择活动程度"
						border="none"
				></u--input>
				<u-icon
						slot="right"
						name="arrow-right"
				></u-icon>
			</u-form-item>
		</u--form>
		<u-button type="primary" @click="submit()">提交</u-button>
		<u-action-sheet
				:show="show"
				:actions="sexs"
				title="请选择性别"
				@close="show = false"
				@select="sexSelect">
		</u-action-sheet>
		<u-action-sheet
				:show="showAct"
				:actions="actions"
				title="请选择性别"
				@close="showAct = false"
				@select="actSelect">
		</u-action-sheet>
	</uni-card>
	
	<uni-card>
		您今天可以摄取的卡路里数：{{bmr}}
	</uni-card>
	
	<uni-card>
		<view v-for="(item,index) in text">
			<text>{{item.keyy}}</text>
		</view>
		
		这些食物的卡路里数：{{foodNum}}
	</uni-card>
	
<!-- 	
	废弃效果
	<uni-card>
		<u-cell-group>
			<u-cell
				@click="pickFood()"
				title="选择食物"
				isLink>
			</u-cell>
		</u-cell-group>
		<u-picker :show="showFood" keyName="keyy" ref="uPicker" :columns="columns" @cancel="showFood = false" @confirm="confirm" @change="changeHandler"></u-picker>
	</uni-card> -->

	<uni-card v-for="(item, key) in categoryNames">
		<h3>{{item.keyy}}</h3>
	
			<u-tag v-for="(i, k) in foods[key]" style="width: 50px;" plain size="mini" :text="i.keyy"></u-tag>					
	</uni-card>
	
  </view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js';
	import ApiDic from '@/api/api_dic.js'
	export default {
		data() {
			return {
				showFood: false,
				// 分类信息-废弃
				columns: [
					[{"id":1,"name":"赛尔威"},{"id":2,"name":"龙岗图书馆"}],
					['深圳', '厦门', '上海', '拉萨'] // 默认加载
				],
				// 食物信息-废弃
				columnData: [
					['深圳', '厦门', '上海', '拉萨'],
					['得州', '华盛顿', '纽约', '阿拉斯加']
				],
				categoryNames:[],
				foods:[],
				foodNum:0,
				text:[
					{
						id:'',
						keyy:'',
						value:''
					}
				],
				bmr:0,
				show: false,
				showAct: false,
				sexs:[
					{
					  
					  name: '男'
					},
					{
					  
					  name: '女'
					},		  
				],
				actions:[
					{
					  name:'不活动'
					},
					{
					  name:'一周活动一次'
					},
					{
					  name:'一周活动2-3次'
					},
					{
					  name:'一周活动4-6次'
					},
					{
					  name:'一周活动6-7次'
					}
				],
				form:{
					age:'',
					sex:'',
					weight:'',
					height:'',
					type:''
				},
				rules: {
					'age': {
						type: 'number',
						required: true,
						message: '请填写姓名',
						trigger: ['blur', 'change']
					},
					'sex': {
						type: 'string',
						max: 1,
						required: true,
						message: '请选择男或女',
						trigger: ['blur', 'change']
					},
					'weight': {
						type: 'number',
						required: true,
						message: '请填写体重',
						trigger: ['blur', 'change']
					},
					'height': {
						type: 'number',
						required: true,
						message: '请填写身高',
						trigger: ['blur', 'change']
					},
					'type': {
						type: 'string',
						required: true,
						message: '请选择活动类型',
						trigger: ['blur', 'change']
					},
				},
			}
		},
		onLoad: function(option) {
			ApiDic.listFoods().then(res => {
				console.log(res);
				// 废弃功能
				// this.columns = [];
				// this.columns.push(res.data.categoryNames);
				// this.columns.push(res.data.foods[0]);
				// console.log(this.columns);
				// this.columnData = [];
				// this.columnData = res.data.foods;
				// this.text = []
				if(res.code === 200){
					this.categoryNames = res.data.categoryNames;
					for(let i = 0;i < res.data.categoryNames.length; i++){
						if(res.data.categoryNames[i].keyy === '每日推荐id'){
							res.data.categoryNames.splice(i,1);
						}
					}
					this.foods = res.data.foods
				}
			})
		},
		methods: {
			pickFood(){
				this.showFood = true;
			},
			changeHandler(e) {
				console.log(e);
				const {
					columnIndex,
					value,
					values, // values为当前变化列的数组内容
					index,
					// 微信小程序无法将picker实例传出来，只能通过ref操作
					picker = this.$refs.uPicker
				} = e
				// 当第一列值发生变化时，变化第二列(后一列)对应的选项
				if (columnIndex === 0) {
					// picker为选择器this实例，变化第二列对应的选项
					picker.setColumnValues(1, this.columnData[index])
				}
			},
			// 回调参数为包含columnIndex、value、values
			confirm(e) {
				console.log('confirm', e)
				console.log(e.value[1]);
				this.text.push(e.value[1]);
				this.foodNum = this.foodNum  + parseInt(e.value[1].value);
				this.showFood = false
			},
			submit(){
				
				// 对于男性，公式为：BMR = 10 × 体重（公斤） + 6.25 × 身高（厘米） – 5 × 年龄（岁） + 5
				
				// 对于女性，公式为：BMR = 10 × 体重（公斤）+ 6.25 × 身高（厘米） – 5 × 年龄（岁） – 161
				
				// 计算 BMR 后，将结果乘以活动系数以计算体力活动水平。
				
				this.$refs.uForm.validate().then(res => {
					let bmr = 0;
					console.log(this.form.sex);
					if(this.form.sex === '男'){
						console.log(10 * this.form.weight);
						bmr = 10 * this.form.weight + 6.25 * this.form.height - 5 * this.form.age + 5;
					} else {
						bmr = 10 * this.form.weight + 6.25 * this.form.height - 5 * this.form.age - 161;
					}
					console.log(bmr);
					switch (this.form.type) {
						case '不活动' : console.log("123");this.bmr = bmr * 1.2; break;
						case '一周活动一次': this.bmr = bmr * 1.376; break;
						case '一周活动2-3次': this.bmr = bmr * 1.55; break;
						case '一周活动4-6次': this.bmr = bmr * 1.726; break;
						case '一周活动6-7次': this.bmr = bmr * 1.9; break;
					}
					
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			},
			sexSelect(e) {
				this.form.sex = e.name
				this.show = false;
				// this.$refs.form.validateField('sex')
			},
			actSelect(e) {
				this.form.type = e.name
				this.showAct = false;
				// this.$refs.form.validateField('type')
			},
			
		 
		}
	}
</script>

<style lang="scss">
  /* #ifndef APP-NVUE */
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  view {
    font-size: 14px;
    line-height: inherit;
  }

  /* #endif */

  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

  .grid-item-box {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 15px 0;
  }

  .uni-margin-wrap {
    width: 690rpx;
    width: 100%;
    ;
  }

  .swiper {
    height: 300rpx;
  }

  .swiper-box {
    height: 150px;
  }

  .swiper-item {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #fff;
    height: 300rpx;
    line-height: 300rpx;
  }

  @media screen and (min-width: 500px) {
    .uni-swiper-dot-box {
      width: 400px;
      /* #ifndef APP-NVUE */
      margin: 0 auto;
      /* #endif */
      margin-top: 8px;
    }

    .image {
      width: 100%;
    }
  }
</style>
