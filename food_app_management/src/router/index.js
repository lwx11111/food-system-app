import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from 'vue-router'

import Login from '@/views/Login/index.vue';
import Register from '@/views/Login/register.vue'

import Frame from "@/frame/index.vue";
import HomePage from "@/views/homePage.vue";
import Cart from "@/views/cart/index.vue";

const MenuIndex = () => import('../views/menu/Index.vue')
const MenuCollectionIndex = () => import('../views/menucollection/Index.vue')
const MenuCommentIndex = () => import('../views/menucomment/Index.vue')
const MenuLikeIndex = () => import('../views/menulike/Index.vue')
const ShopItemIndex = () => import('../views/shopitem/Index.vue')
const ShopItemCategoryIndex = () => import('../views/shopitemcategory/Index.vue')
const ShopOrderIndex = () => import('../views/shoporder/Index.vue')
const CommunityIndex = () => import('../views/community/Index.vue')
const CommunityCommentIndex = () => import('../views/communitycomment/Index.vue')
const CommunityLikeIndex = () => import('../views/communitylike/Index.vue')
const OrderIndex = () => import('../views/order/Index.vue')

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
        path: '/register',
        component: Register,
        name: 'Register'
    },
    {
        path: '/',
        component: Frame,
        redirect: '/homePage',
        children: [
            {
                path: 'homePage',
                component: HomePage,
                name: 'homePage',
            },
            // 菜谱
            {
                path: '/name/menu/index',
                component: MenuIndex,
                name: 'MenuIndex'
            },
            {
                path: '/name/menucollection/index',
                component: MenuCollectionIndex,
                name: 'MenuCollectionIndex'
            },
            {
                path: '/name/menucomment/index',
                component: MenuCommentIndex,
                name: 'MenuCommentIndex'
            },
            {
                path: '/name/menulike/index',
                component: MenuLikeIndex,
                name: 'MenuLikeIndex'
            },
            // 商品
            {
                path: '/name/shopitem/index',
                component: ShopItemIndex,
                name: 'ShopItemIndex'
            },
            {
                path: '/name/shoporder/index',
                component: ShopOrderIndex,
                name: 'ShopOrderIndex'
            },
            {
                path: '/name/shopitemcategory/index',
                component: ShopItemCategoryIndex,
                name: 'ShopItemCategoryIndex'
            },
            // 社区
            {
                path: '/name/community/index',
                component: CommunityIndex,
                name: 'CommunityIndex'
            },
            {
                path: '/name/communitycomment/index',
                component: CommunityCommentIndex,
                name: 'CommunityCommentIndex'
            },
            {
                path: '/name/communitylike/index',
                component: CommunityLikeIndex,
                name: 'CommunityLikeIndex'
            },
            // 购物车
            {
                path: '/cart',
                component: Cart,
                name: 'cart',
            },
            // 订单
            {
                path: '/name/order/index',
                component: OrderIndex,
                name: 'OrderIndex'
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

