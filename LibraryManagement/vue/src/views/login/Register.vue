<template>
  <div style="height: 90vh; overflow: hidden;">

    <!-- 头部区域 -->
    <div style="border-radius: 10px; height: 60px; line-height: 60px; background-color: white">
      <img src="@/assets/logo.png" alt="" style="
              width: 40px;
              height: 40px;
              position: relative;
              top: 10px;
              left: 20px;
            "/>
      <span style="margin-left: 25px; font-size: 24px">图书管理系统</span>
      <span style="margin-left: 8px; font-size: 13px">By Justyn</span>
    </div>

    <!-- 登陆框 -->
    <div
        style="width: 500px; height: 450px; background-color: azure; border-radius: 20px;margin: 130px auto; padding: 50px;">
      <div
          style="margin: 10px;margin-bottom: 30px; margin-top: -20px; text-align: center; font-size: 30px; font-weight: bold; color: grey;">
        注 册
      </div>
      <el-form :model="admin" ref="registerForm" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="admin.username" size="medium" prefix-icon="el-icon-user"
                    placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="admin.password" size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码"
                    show-password></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model.number="admin.phone" size="medium" prefix-icon="el-icon-lock"
                    placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="admin.email" size="medium" prefix-icon="el-icon-lock" placeholder="请输入邮箱"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="nameCheck" style="width: 100%; size: medium;" type="primary">注册</el-button>
        </el-form-item>

        <el-form-item>
          <el-button @click="$router.push('/login')" style="width: 100%; size: medium;" type="danger">返回</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: "Register",
  data() {
    // 检查手机函数
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机不能为空"));
      }
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error("请输入合法的手机号"));
      }
      callback();
    };
    return {
      admin: {},
      // 表单格式判断规则
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
          message: "密码长度不合规 「3 - 10」",
          trigger: 'blur'
        }],
        phone: [{
          validator: checkPhone,
          trigger: "blur"
        }],
      },
    }
  },
  methods: {
    register() {
      this.$refs["registerForm"].validate((valid) => {
        if (valid) {
          request.post('/admin/register', this.admin).then(res => {
            if (res.code === '200') {
              this.$notify.success("注册成功")
              console.log(1);
              this.$router.push("/login")
              this.$refs["registerForm"].resetFields();
              this.admin = {};
            } else {
              console.log(res.data);
              this.$notify.error("新增失败" + res.data)
            }
          })
        }
      })
    },
    nameCheck() {
      const username = this.admin.username;
      request.get("/admin/namecheck?", {
        params: {
          username
        }
      }).then((res) => {
        console.log(res.data);
        console.log(res.code);
        const a = res.data;
        if (a.length == 0) {
          console.log(1);
          this.register();
        } else {
          this.$notify.error("用户名重复 请重新输入" + "\nres.msg");
          // this.$router.push("/register")
          console.log(2);
          this.admin.username = "";
          this.$refs["registerForm"].resetFields();
        }
      });
    },

  }
}
</script>

<style scoped>

</style>
