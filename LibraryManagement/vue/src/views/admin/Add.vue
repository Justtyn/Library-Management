<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">新增管理员</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 60%" label-width="90px" class="demo-form-inline">
        <el-form-item label="用户名:" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码 默认为1234"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
            <el-input v-model.number="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="EMAIL:" prop="email">
            <el-input v-model="form.email" placeholder="请输入EMAIL"></el-input>
        </el-form-item>
    </el-form>

    <div style="padding-left: 35px; padding-top: 10px">
        <el-button type="primary" @click="nameCheck">提交</el-button>
        <el-button type="danger" @click="reset">重置</el-button>
    </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "AddUser",
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
            form: {},
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
                    min: 2,
                    max: 10,
                    message: "密码长度不合规 「2 - 10」",
                    trigger: 'blur'
                }],
                phone: [{
                    validator: checkPhone,
                    trigger: "blur"
                }],
            },
        };
    },
    methods: {
        save() {
            this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    request.post("/admin/save", this.form).then((res) => {
                        if (res.code === "200") {
                            this.$notify.success("新增成功");
                            this.$refs["ruleForm"].resetFields();
                            this.$router.push("/adminList");
                            this.form = {};
                        } else {
                            this.$notify.error("新增失败" + res.msg);
                        }
                    });
                } else {}
            });
        },
        nameCheck() {
            const username = this.form.username;
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
                    this.save();
                } else {
                    console.log(2);
                    this.$notify.error("用户名重复 请重新输入" + "\nres.msg");
                    this.form.name = "";
                }
            });
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert("submit!");
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        reset() {
            this.form = {

            }
        }
    },
};
</script>

<style scoped></style>
