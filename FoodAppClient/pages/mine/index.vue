<!-- 我的首页 -->
<template>
	<view class="mine-container" :style="{height: `${windowHeight}px`}">
		<!--顶部个人信息栏-->
		<view class="header-section">
			<view class="flex padding justify-between">
				<view class="flex align-center">
					<!-- 没有头像 -->
					<view v-if="!account.avatar" class="cu-avatar xl round bg-white">
						<view class="iconfont icon-people text-gray icon"></view>
					</view>
					<image v-if="account.avatar" @click="handleToEditInfo" :src="avatar" class="cu-avatar xl round" mode="widthFix"></image>
					<!-- 用户名展示 -->
					<view v-if="name" @click="handleToEditInfo" class="user-info">
						<view class="u_title">
						  用户名：{{ name }}
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 操作栏 -->
		<view class="content-section">
			
			<view class="mine-actions grid col-4 text-center">
				<view class="action-item" @click="toShoppingCart">
					<view class="iconfont icon-friendfill text-pink icon"></view>
					<text class="text">购物车</text>
				</view>
				<view class="action-item" @click="toCollection">
					<view class="iconfont icon-service text-blue icon"></view>
					<text class="text">我的收藏</text>
				</view>
				<view class="action-item" @click="toOrders">
					<view class="iconfont icon-community text-mauve icon"></view>
					<text class="text">我的订单</text>
				</view>
				<view class="action-item" @click="toMessages">
					<view class="iconfont icon-community text-mauve icon"></view>
					<text class="text">我的私信</text>
				</view>
			</view>

			<view class="menu-list">
				<view class="list-cell list-cell-arrow" @click="toMyMenu()">
				  <view class="menu-item-box">
					<view class="iconfont icon-help menu-icon"></view>
					<view>我的菜谱</view>
				  </view>
				</view>
				<view class="list-cell list-cell-arrow" @click="toMyCommunity()">
				  <view class="menu-item-box">
					<view class="iconfont icon-help menu-icon"></view>
					<view>我的帖子</view>
				  </view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleLocation">
				  <view class="menu-item-box">
					<view class="iconfont icon-help menu-icon"></view>
					<view>地址管理</view>
				  </view>
				</view>
				<view class="list-cell list-cell-arrow" @click="handleToSetting">
				  <view class="menu-item-box">
					<view class="iconfont icon-setting menu-icon"></view>
					<view>应用设置</view>
				  </view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import storage from '@/utils/storage'
  
	export default {
		data() {
			return {
				// 游客模式不展示
				isTourist: localStorage.getItem("isTourist"),
				name: localStorage.getItem("userName"),
				version: getApp().globalData.config.appInfo.version,
				account: this.$store.state.account
			}
		},
		computed: {
			avatar() {
				return this.$store.state.user.avatar
			},
			windowHeight() {
				return uni.getSystemInfoSync().windowHeight - 50
			}
		},
		mounted() {
			console.log(localStorage.getItem("userId"));
			if (localStorage.getItem("userId") === null){
				this.$tab.reLaunch('/pages/login')
			}

		},
		methods: {
			toMyMenu(){
				uni.navigateTo({
					url:'/pages/menu/menuMyList'
				})
			},
			toMyCommunity(){
				uni.navigateTo({
					url:'/pages/community/communityMyList'
				})
			},
			/**
			 * 导航栏点击事件
			 */
			onTabItemTap() {
				if(this.isTourist){
					localStorage.removeItem('isTourist');
					this.$tab.reLaunch('/pages/login')
				}
			},
			toShoppingCart() {
				this.$tab.navigateTo('/pages/mine/shoppingCart')
			},
			toCollection() {
				this.$tab.navigateTo('/pages/mine/collection')
			},
			toOrders(){
				this.$tab.navigateTo('/pages/mine/shopOrder')
			},
			toMessages(){
				this.$tab.navigateTo('/pages/message/index')
			},
			/**
			 * 修改个人信息
			 */
			handleToEditInfo() {
				this.$tab.navigateTo('/pages/mine/info/edit')
			},
			
			/**
			 * 应用设置
			 */
			handleToSetting() {
				this.$tab.navigateTo('/pages/mine/info/setting')
			},
		}
	}
</script>

<style lang="scss">
  page {
    background-color: #f5f6f7;
  }

  .mine-container {
    width: 100%;
    height: 100%;


    .header-section {
      padding: 15px 15px 45px 15px;
      background-color: #3c96f3;
      color: white;

      .login-tip {
        font-size: 18px;
        margin-left: 10px;
      }

      .cu-avatar {
        border: 2px solid #eaeaea;

        .icon {
          font-size: 40px;
        }
      }

      .user-info {
        margin-left: 15px;

        .u_title {
          font-size: 18px;
          line-height: 30px;
        }
      }
    }

    .content-section {
      position: relative;
      top: -50px;

      .mine-actions {
        margin: 15px 15px;
        padding: 20px 0px;
        border-radius: 8px;
        background-color: white;

        .action-item {
          .icon {
            font-size: 28px;
          }

          .text {
            display: block;
            font-size: 13px;
            margin: 8px 0px;
          }
        }
      }
    }
  }
</style>
