<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">新增分类</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 60%" label-width="90px" class="demo-form-inline">
        <el-form-item label="名称:" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
    name: "AddCategory",
    data() {
        return {
            form: {},
            // 表单格式判断规则
            rules: {
                name: [{
                    required: true,
                    message: "请输入名称",
                    trigger: "blur"
                }],
            },
        };
    },
    methods: {
        save() {
            this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    request.post("/category/save", this.form).then((res) => {
                        if (res.code === "200") {
                            this.$notify.success("新增成功");
                            this.$refs["ruleForm"].resetFields();
                            this.$router.push("/categoryList");
                            this.form = {};
                        } else {
                            this.$notify.error("新增失败" + res.msg);
                        }
                    });
                }
            });
        },
        nameCheck() {
            const name = this.form.name;
            request.get("/category/namecheck?", {
                params: {
                    name
                }
            }).then((res) => {
                console.log(res.data);
                console.log(res.code);
                const a = res.data;
                if (a.length == 0) {
                    this.save();
                } else {
                    this.$notify.error("用户名重复 请重新输入" + res.msg);
                    this.form.name = "";
                }
            });
        },
        reset() {
            this.form = {};
        }
    },
};
</script>

<style>
</style>
