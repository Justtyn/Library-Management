<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">新增用户</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="90px" class="demo-form-inline">
        <el-form-item label="用户名:" prop="name">
            <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码:" prop="password">
            <el-input v-model.number="form.password" show-password placeholder="请输入密码 默认为1234"></el-input>
        </el-form-item>

        <el-form-item label="年龄:" prop="age">
            <el-input v-model.number="form.age" placeholder="请输入年龄"></el-input>
        </el-form-item>

        <el-form-item label="手机号:" prop="phone">
            <el-input v-model.number="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="地址:" prop="sex" label-width="90px">
            <el-input style="width: 183px;" v-model="form.address" placeholder="请输入地址"></el-input>
        </el-form-item>

        <el-form-item label="性别:" prop="sex">
            <el-radio style="margin-left: 5px;" v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
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
        // 检查年龄函数
        var checkAge = (rule, value, callback) => {
            if (!value) {
                return callback(new Error("年龄不能为空"));
            }
            if (!Number.isInteger(value)) {
                callback(new Error("请输入数字"));
            }
            if (value >= 120 || value <= 0) {
                callback(new Error("请输入合法年龄 「1 - 120」"));
            }
            callback();
        };
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
            form: {
                sex: "男",
            },
            // 表单格式判断规则
            rules: {
                name: [{
                    required: true,
                    message: "请输入用户名称",
                    trigger: "blur"
                }],
                age: [{
                    validator: checkAge,
                    trigger: "blur"
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
                    request.post("/user/save", this.form).then((res) => {
                        if (res.code === "200") {
                            this.$notify.success("新增成功");
                            this.$refs["ruleForm"].resetFields();
                            this.$router.push("/userList");
                            this.form = {
                                sex: "男"
                            };
                        } else {
                            this.$notify.error("新增失败" + "\nres.msg");
                        }
                    });
                } else {}
            });
        },
        nameCheck() {
            const name = this.form.name;
            request.get("/user/namecheck?id=", {
                params: {
                    name
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
                sex: "男",
            };
        },
    }
}
</script>

<style scoped></style>
