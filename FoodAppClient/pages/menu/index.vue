<template>
  <view>
  	<uni-search-bar  v-model="params.name" @confirm="search"></uni-search-bar>
	<uni-card>
		<view style="align-items: center; text-align: center; margin-bottom: 20px;"><h2>已选食材</h2></view>
		<view v-for="(item,index) in sysDictSelected" style="display: inline-block;">
			<u-tag 
				style="width: 80px; margin-right: 10px;" @click="deleteTag(index)"
				:text="item.name" 
				shape="circle"
				plain size="mini"></u-tag>
		</view>
		<u-button type='error' @click="searchByName()">匹配菜谱</u-button>
	</uni-card>
	
	<!-- 素菜 -->
	<uni-card>
		<view style="align-items: center; text-align: center; margin-bottom: 20px;"><h2>素菜</h2></view>
		<view v-for="(item,index) in sysDict" style="display: inline-block;">
			<u-tag 
				style="width: 80px; margin-right: 10px;" @click="addTag(index)"
				:text="item.name" 
				shape="circle"
				plain size="mini"></u-tag>
		</view>
	</uni-card>
	
	<!-- 肉菜 -->
	<uni-card>
		<view style="align-items: center; text-align: center; margin-bottom: 20px;"><h2>肉菜</h2></view>
		<view v-for="(item,index) in sysDict1" style="display: inline-block;">
			<u-tag style="width: 80px; margin-right: 10px;" 
				:text="item.name" 
				plain
				shape="circle"
				@click="addTag(index,1)"
				size="mini"></u-tag>
		</view>
	</uni-card>
	<u-toast ref="uToast"></u-toast>
  </view>
</template>

<script>
	import ApiMenu from '@/api/menu/menu.js'
	import ApiDict from '@/api/api_sysdict.js'
	
	export default {
		data() {
		  return {
			  // 选中的
			  sysDictSelected:[],
			  //素菜
			  sysDict:[
				  {
					  id:'',
					  name:'',
					  value:''
				  }
			  ],
			  // 肉菜
			  sysDict1:[],
			// 素菜菜谱
			meatMenu: [
				{
				 description: "",
				 id:'',
				 ingredients:"",
				 likes:"",
				 name:"",
				 steps:"" ,
				}
			],
			// 荤菜菜谱
			vegetableMenu: [
				{
				 description: "",
				 id:'',
				 ingredients:"",
				 likes:"",
				 name:"",
				 steps:"" ,
				}
			],
			// 查询参数
			params: {
				name:'',
				type: '',
				pageSize: '10'
			}
		  }
		},
		onLoad: function(option) {
			this.listSysDict();
			
		},
		methods: {
			listSysDict(){
				let param = {
					value: '0'
				}
				ApiDict.selpage4sysdict(param).then(res => {
					console.log(res);
					this.sysDict = res.data.records
				})
				param.value = '1';
				ApiDict.selpage4sysdict(param).then(res => {
					console.log(res);
					this.sysDict1 = res.data.records
				})
			},
			addTag(index, type){
				if( type === 1){
					this.sysDictSelected.push(this.sysDict1[index]);
					this.sysDict1.splice(index,1)
				} else {
					this.sysDictSelected.push(this.sysDict[index]);
					this.sysDict.splice(index,1)
				}
				
			},
			deleteTag(index){
				let v = this.sysDictSelected[index].value;
				
				if(v === "1"){
					this.sysDict1.push(this.sysDictSelected[index]);
				} else {
					this.sysDict.push(this.sysDictSelected[index]);
				}
				
				this.sysDictSelected.splice(index,1);
			},
			searchByName(){
				if( this.sysDictSelected.length === 0){
					this.$refs.uToast.show({
						type: "error",
						message: "请选择食材"
					})
					return ;
				}
				uni.navigateTo({
				  url: '/pages/menu/menuList?menuName=' + JSON.stringify(this.sysDictSelected)
				})
			},
			search() {
				this.getMeatMenu();
				this.getVegetableMenu();
				// uni.switchTab({
				// 	url: '/pages/menu/index?name' + this.name
				// });
			},
			toMenuDetail(index,type) {
				console.log(index);
				var id = this.vegetableMenu[index].id;
				if(type === 1){
					id = this.meatMenu[index].id;
				}
				uni.navigateTo({
				  url: '/pages/menu/menuDetail?id=' + id
				})
			}
		 
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
