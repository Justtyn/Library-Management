<template>
<div style="height: 90vh; overflow: hidden; position: relative;">

    <!-- 滑块登陆验证模块 -->
    <el-card class="cover" v-if="loginAdmin.id">
        <slide-verify :l="42" :r="10" :w="310" :h="155" slider-text="向右滑动" @success="onSuccess" @fail="onFail" @refresh="onRefresh"></slide-verify>
    </el-card>

    <!-- 头部区域 -->
    <div style="border-radius: 10px; height: 60px; line-height: 60px; background-color: white">
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

    <!-- 登陆框 -->
    <div style="width: 500px; height: 350px; background-color: azure; border-radius: 20px;margin: 130px auto; padding: 50px;">
        <div style="margin: 10px;margin-bottom: 30px; margin-top: -20px; text-align: center; font-size: 30px; font-weight: bold; color: grey;">登 陆</div>
        <el-form :model="admin" ref="loginForm" :rules="rules">
            <el-form-item prop="username">
                <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="admin.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="admin.password"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button @click="login" style="width: 100%; size: medium;" type="primary">登陆</el-button>
            </el-form-item>
            <el-form-item>
                <el-button @click="$router.push('/register')" style="width: 100%; size: medium;" type="danger">注册</el-button>
            </el-form-item>

            <p style="text-align: center;">默认管理员账号:&ensp;admin&ensp;&ensp;默认密码:&ensp;123</p>

        </el-form>
    </div>
</div>
</template>

<script>
import request from '@/utils/request';
import Cookies from 'js-cookie'

export default {
    name: "Login",
    data() {
        return {
            loginAdmin: {},
            admin: {},
            rules: {
                username: [{
                    required: true,
                    message: "请输入用户名",
                    trigger: "blur"
                }, {
                    min: 2,
                    max: 10,
                    message: "用户名长度不合规 「2 - 10」",
                    trigger: 'blur'
                }],

                password: [{
                    required: true,
                    message: "请输入密码",
                    trigger: "blur"
                }, {
                    min: 3,
                    max: 10,
                    message: "用户名长度不合规 「3 - 10」",
                    trigger: 'blur'
                }],
            },
        }
    },
    methods: {
        login() {
            this.$refs["loginForm"].validate((valid) => {
                if (valid) {
                    request.post('/admin/login', this.admin).then(res => {
                        if (res.code === '200') {

                            this.loginAdmin = res.data; // 滑块组件显示
                            console.log(res.data.id + ":" + this.loginAdmin.id);

                        } else {
                            this.$notify.error(res.data)
                        }
                    })
                }
            })
        },
        onSuccess() {
            Cookies.set('admin', JSON.stringify(this.loginAdmin))
            this.$notify.success("登录成功")
            this.$router.push('/')
        },
        onFail() {
            this.$notify.error("认证失败")
        },
        onRefresh() {
            console.log('refresh')
        }

    }
}
</script>

<style>
/* 滑块界面 置顶 居中 */
.cover {
    width: fit-content;
    background-color: white;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1000;
}
</style>
