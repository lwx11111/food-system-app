import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from 'vue-router'

import Login from '@/views/Login/index.vue';

import Frame from "@/frame/index.vue";
import HomePage from "@/views/homePage.vue";
import Cart from "@/views/cart/index.vue";

/**
 * 菜谱管理
 */
const MenuIndex = () => import('../views/Menu/Index.vue')
// 菜谱-收藏
const MenuCollectionIndex = () => import('@/views/Menu/MenuCollection/Index.vue')
// 菜谱-评论
const MenuCommentIndex = () => import('@/views/Menu/MenuComment/Index.vue')
// 菜谱-点赞
const MenuLikeIndex = () => import('@/views/Menu/MenuLike/Index.vue')

/**
 * 菜品管理
 */
const ShopItemIndex = () => import('../views/ShopItem/Index.vue')
// 菜品-物品分类
const ShopItemCategoryIndex = () => import('@/views/ShopItem/ShopItemCategory/Index.vue')

/**
 * 商城管理
 */
// 商城管理-订单管理
const ShopOrderIndex = () => import('@/views/Shop/ShopOrder/Index.vue')
const MessageIndex = () => import('@/views/Shop/Message/Index.vue')

/**
 * 社区管理
 */
const CommunityIndex = () => import('../views/Community/Index.vue')
// 社区-分类管理
const CategoryIndex = () => import('@/views/Community/Category/Index.vue')

/**
 * 健康管理
 */
const DicIndex = () => import('@/views/Health/Dic/Index.vue')

// 模块化路由
const routes = [
    // 路由守卫 vue3动态路由问题导致刷新完页面会爆出No match found for location with path
    {
        path: "/:pathMatch(.*)*", // 必备
        component: () => import("@/views/404.vue"),
    },
    {
        path: '/login',
        component: Login,
        name: 'Login'
    },
    {
        path: '/',
        component: Frame,
        redirect: '/homePage',
        name: '首页',
        children: [
            {
                path: 'homePage',
                component: HomePage,
                name: '欢迎页',
            },
            // 菜谱
            {
                path: '/menu',
                name: '菜谱管理',
                children: [
                    {
                        path: '/menu/index',
                        component: MenuIndex,
                        name: '菜谱列表'
                    },
                    {
                        path: '/menucollection/index',
                        component: MenuCollectionIndex,
                        name: '菜谱收藏'
                    },
                    {
                        path: '/menucomment/index',
                        component: MenuCommentIndex,
                        name: '菜谱评论'
                    },
                    {
                        path: '/menulike/index',
                        component: MenuLikeIndex,
                        name: '菜谱点赞'
                    },
                ]
            },
            // 商品
            {
                path: '/shopItem',
                name: '菜品管理',
                children: [
                    {
                        path: '/shopitem/index',
                        component: ShopItemIndex,
                        name: '菜品列表'
                    },
                    {
                        path: '/shopitemcategory/index',
                        component: ShopItemCategoryIndex,
                        name: '菜品分类'
                    },
                ]
            },
            // 商城
            {
                path: '/shop',
                name: '菜品管理',
                children: [
                    {
                        path: '/shoporder/index',
                        component: ShopOrderIndex,
                        name: '订单管理'
                    },
                    {
                        path: '/message/index',
                        component: MessageIndex,
                        name: '私信管理'
                    },
                ]
            },
            // 社区
            {
                path: '/community',
                name: '社区管理',
                children: [
                    {
                        path: '/category/index',
                        component: CategoryIndex,
                        name: '社区分类'
                    },
                    {
                        path: '/community/index',
                        component: CommunityIndex,
                        name: '社区列表'
                    },
                ]
            },
            // 健康
            {
                path: '/health',
                name: '健康管理',
                children: [
                    {
                        path: '/dic/index',
                        component: DicIndex,
                        name: '食物热量'
                    },
                ]
            },
        ]
    }

]

// 创建路由对象
const router = createRouter({
  // history: createWebHashHistory(), // hash模式：createWebHashHistory，有#
  history: createWebHistory(),  // history模式：createWebHistory
  routes
})

export default router

