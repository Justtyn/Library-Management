<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">编辑用户</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="90px" class="demo-form-inline">
        <el-form-item label="UUID:" prop="UUID">
            <el-input v-model="form.username" disabled placeholder="这里是UUID"></el-input>
        </el-form-item>
        <el-form-item label="用户名:" prop="name">
            <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="年龄:" prop="age">
            <el-input v-model.number="form.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
            <el-input v-model.number="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="地址:" prop="address">
            <el-input v-model="form.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item label="性别:" prop="sex">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>

    </el-form>

    <div style="padding-left: 35px; padding-top: 10px">
        <el-button type="danger" @click="save">提交</el-button>
        <el-button type="primary" @click="backToUser">取消</el-button>
    </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "EditUser",
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
            form: {},
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
    created() {
        const id = this.$route.query.id;
        request.get("/user/" + id).then((res) => {
            this.form = res.data;
        });
    },
    methods: {
        save() {
            request.put("/user/update", this.form).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("更新成功");
                    this.$router.push("/userList");
                } else {
                    this.$notify.error("更新失败" + "\nres.msg");
                }
            });
        },
        backToUser() {
            this.$router.push("/userList");
        },
    },
};
</script>

<style scoped></style>
