<template>
    <div>
<!--        <logo></logo>-->
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
                <el-menu-item index="/cart">
                    <el-icon><User /></el-icon>
                    <span>用户管理</span>
                </el-menu-item>
<!--                <el-menu-item index="2">-->
<!--                    <el-icon><KnifeFork /></el-icon>-->
<!--                    <span>菜谱管理</span>-->
<!--                </el-menu-item>-->
<!--                <el-menu-item index="3">-->
<!--                    <el-icon><Dish /></el-icon>-->
<!--                    <span>菜品管理</span>-->
<!--                </el-menu-item>-->
<!--                <el-menu-item index="4">-->
<!--                    <el-icon><SoldOut /></el-icon>-->
<!--                    <span>商城管理</span>-->
<!--                </el-menu-item>-->
<!--                <el-menu-item index="5">-->
<!--                    <el-icon><ChatLineRound /></el-icon>-->
<!--                    <span>社区管理</span>-->
<!--                </el-menu-item>-->
<!--                <el-menu-item index="6">-->
<!--                    <el-icon><Help /></el-icon>-->
<!--                    <span>健康管理</span>-->
<!--                </el-menu-item>-->
            <sidebar-item v-for="route in data.indexDate" :key="route.funId.toString()" :item="route" :base-path="route.url"/>
            <sidebar-item v-for="route in data.menuData"
                          :key="route.funId.toString()"
                          :item="route"
                          :base-path="route.url"/>
            </el-menu>
            <img alt="name" src="../../assets/tree.png" class="sidebar-foot">
        </el-scrollbar>

    </div>
</template>

<script lang="js" setup>
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import Logo from './Logo.vue'
    import SidebarItem from './SidebarItem.vue'
    import {leftMenuApi} from '@/api/function'
    import {onMounted, reactive} from "vue";

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
                'url': '/cart',
                'icon':'KnifeFork'
            },
            {
                'funId': '3',
                'funName': '菜品管理',
                'url': '/cart',
            },
            {
                'funId': '4',
                'funName': '商城管理',
                'url': '/cart',
            },
            {
                'funId': '5',
                'funName': '社区管理',
                'url': '/cart',
            },
            {
                'funId': '6',
                'funName': '健康管理',
                'url': '/cart',
            },


        ]
    })

    // Mounted
    onMounted(() => {
        leftMenuApi().then(response => {
            // console.log(response)
            // 不加载权限系统的
            for (let i = 0;i < response.data.length; i++){
                if (response.data[i].label !== '权限系统监控' && response.data[i].label !== '系统管理' && response.data[i].label !== '权限管理'){
                    data.menuData.push(response.data[i])
                }
            }
            // this.menuData = response.data;
            //
            // const findFun = this.menuData.find(fun => fun.funName === '');
            // if (findFun !== undefined) {
            //   this.openeds.push(findFun.funId);
            // }
        });
    })
</script>

<style>
.sidebar-foot{
    margin-left: 20%;
    width: 50%;
    height: 50%;
}
.el-menu{
    height: 100vh;
    background-color: #545c64;
}
.el-col-12 {
    max-width: 100%;
}
</style>
