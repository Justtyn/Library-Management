<template>
<div>
    <!-- 头部区域 -->
    <div style="border-radius: 10px;height: 60px; line-height: 60px; background-color: white; display: flex;">

        <div style="width: 300px;">
            <img src="@/assets/logo.png" alt="" style="
          width: 40px;
          height: 40px;
          position: relative;
          top: 10px;
          left: 20px;
        " />

            <span style="margin-left: 25px; font-size: 24px">图书管理系统</span>
            <span style="margin-left: 8px; font-size: 13px">By Justyn</span>
        </div>

        <div style="flex: 1; text-align: right;">
            <el-dropdown size="medium">
                <el-button type="primary" style="background-color: white; border-color: white; color: grey; font-size: 17px;" size="medium">当前用户&ensp;:&ensp;{{ user.username }}<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown" style="margin-top: -10px; margin-right: 13px;">
                    <!-- <el-dropdown-item>黄金糕</el-dropdown-item> -->
                    <!-- <el-dropdown-item>狮子头</el-dropdown-item> -->
                    <!-- <el-dropdown-item>螺蛳粉</el-dropdown-item> -->
                    <!-- <el-dropdown-item>双皮奶</el-dropdown-item> -->
                    <el-dropdown-item>
                        <div style="text-align: center;" @click="logout">退出</div>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>

    <!-- 侧边栏和主体 -->
    <div style="display: flex; margin-top: 10px">
        <!-- 侧边栏导航 -->
        <div style="
          border-radius: 10px;
          width: 200px;
          overflow: hidden;
          min-height: calc(100vh - 90px);
          background-color: white;
          margin-right: 10px;
        ">
            <el-menu :default-active="$route.path" router class="el-menu-demo">
                <el-menu-item index="/"><i class="el-icon-s-home"></i> <span>首页</span></el-menu-item>

                <!-- User -->
                <el-submenu index="user">
                    <template slot="title"><i class="
                        el-icon-menu"></i><span>会员管理</span></template>
                    <el-menu-item index="/addUser">会员添加</el-menu-item>
                    <el-menu-item index="/userList">会员列表</el-menu-item>
                </el-submenu>

                <!-- Admin -->
                <el-submenu index="admin">
                    <template slot="title"><i class="el-icon-user"></i><span>管理员管理</span></template>
                    <el-menu-item index="/addAdmin">管理员添加</el-menu-item>
                    <el-menu-item index="/adminList">管理员列表</el-menu-item>
                </el-submenu>

                <!-- Category -->
                <el-submenu index="category">
                    <template slot="title"><i class="el-icon-s-data"></i><span>图书分类管理</span></template>
                    <el-menu-item index="/addCategory">图书分类添加</el-menu-item>
                    <el-menu-item index="/categoryList">图书分类列表</el-menu-item>
                </el-submenu>

            </el-menu>
        </div>

        <!-- 主体数据 -->
        <div style="border-radius: 10px; flex: 1; background-color: white; padding: 10px">
            <router-view />
        </div>
    </div>

</div>
</template>

<script>
import Cookies from 'js-cookie'

export default {
    name: "Layout.vue",
    data() {
        return {
            user: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
        }
    },
    methods: {
        logout() {
            // 清楚浏览器用户数据
            Cookies.remove('admin')
            this.$router.push('/login')
        }
    }
}
</script>

<style scoped>

</style>
