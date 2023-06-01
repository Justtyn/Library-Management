<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">编辑管理员</h2>
    <el-form :inline="true" :model="form" style="width: 80%" label-width="90px" class="demo-form-inline">
        <el-form-item label="用户名:" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="phone">
            <el-input v-model.number="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="EMAIL:" prop="email">
            <el-input v-model="form.email" placeholder="请输入EMAIL"></el-input>
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
    name: "EditAdmin",
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

                phone: [{
                    validator: checkPhone,
                    trigger: "blur"
                }],
            },
        };
    },
    created() {
        const id = this.$route.query.id;
        request.get("/admin/" + id).then((res) => {
            this.form = res.data;
        });
    },
    methods: {
        save() {
            request.put("/admin/update", this.form).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("更新成功");
                    this.$router.push("/adminList");
                } else {
                    this.$notify.error("更新失败" + "\nres.msg");
                }
            });
        },
        backToUser() {
            this.$router.push("/adminList");
        },
    },
};
</script>

<style scoped></style>
