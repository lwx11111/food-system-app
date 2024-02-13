<template>
    <div>
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu
                default-active="1"
                :collapse="data.isCollapse"
                :unique-opened="true"
                :default-openeds="data.openeds"
                :collapse-transition="false"
                mode="vertical"
                active-text-color="#ffd04b"
                background-color="#545c64"
                text-color="#fff"
            >
            <sidebar-item v-for="route in data.indexDate" :key="route.funId.toString()" :item="route" :base-path="route.url"/>
            <sidebar-item v-for="route in data.menuData"
                          :key="route.funId.toString()"
                          :item="route"
                          :base-path="route.url"/>
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script lang="js" setup>
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import SidebarItem from './SidebarItem.vue'
    import { reactive } from "vue";

    const store = useStore();
    const router = useRouter()

    const data = reactive({
        showLogo: true,
        isCollapse: false,
        openeds: [],
        menuData: [],
        indexDate: [
            {
                'funId': '1',
                'funName': '首页',
                'url': '/homePage',
            },
            {
                'funId': '2',
                'funName': '菜谱管理',
                'url': '/menu/index',
                'icon':'KnifeFork',
                'children':[{
                    'funId': '21',
                    'funName': '菜谱评论管理',
                    'url': '/menucomment/index',
                    'icon': 'KnifeFork'
                },{
                    'funId': '22',
                    'funName': '菜谱列表',
                    'url': '/menu/index',
                    'icon': 'KnifeFork'
                },{
                    'funId': '22',
                    'funName': '菜谱收藏管理',
                    'url': '/menucollection/index',
                    'icon': 'KnifeFork'
                },{
                    'funId': '22',
                    'funName': '菜谱点赞管理',
                    'url': '/menulike/index',
                    'icon': 'KnifeFork'
                }]
            },
            {
                'funId': '3',
                'funName': '菜品管理',
                'icon': 'Dish',
                'children':[{
                    'funId': '31',
                    'funName': '菜品分类管理',
                    'url': '/shopitemcategory/index',
                    'icon': 'Dish'
                },{
                    'funId': '32',
                    'funName': '菜品列表',
                    'url': '/shopitem/index',
                    'icon': 'Dish'
                }]
            },
            {
                'funId': '4',
                'funName': '商城管理',
                'url': '/community/index',
                'icon': 'SoldOut',
            },
            {
                'funId': '5',
                'funName': '社区管理',
                'url': '/community/index',
                'icon': 'ChatLineRound',
                'children':[{
                    'funId': '51',
                    'funName': '社区分类管理',
                    'url': '/category/index',
                    'icon': 'ChatLineRound'
                },{
                    'funId': '52',
                    'funName': '社区列表',
                    'url': '/community/index',
                    'icon': 'ChatLineRound'
                }]
            },
            {
                'funId': '6',
                'funName': '健康管理',
                'url': '/cart',
                'icon': 'Help',
            },


        ]
    })
</script>

<style>
.sidebar-foot{
    margin-left: 20%;
    width: 50%;
    height: 50%;
}
.el-menu{
    height: 100%;
    background-color: #545c64;
}
</style>
