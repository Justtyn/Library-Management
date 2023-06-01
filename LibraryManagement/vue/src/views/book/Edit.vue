<template>
<div style="padding-top: 10px">
    <h2 style="padding-left: 35px">编辑分类</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="90px" class="demo-form-inline">
        <el-form-item label="名称:" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
        </el-form-item>
    </el-form>

    <div style="padding-left: 35px; padding-top: 10px">
        <el-button type="danger" @click="save">提交</el-button>
        <el-button type="primary" @click="$router.push('/categoryList')">取消</el-button>
    </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "EditCategory",
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
    created() {
        const id = this.$route.query.id;
        request.get("/category/" + id).then((res) => {
            this.form = res.data;
        });
    },
    methods: {
        save() {
            this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    request.put("/category/update", this.form).then((res) => {
                        if (res.code === "200") {
                            this.$notify.success("更新成功");
                            this.$router.push("/categoryList");
                        } else {
                            this.$notify.error("更新失败" + res.msg);
                        }
                    });

                }

            })
        }
    }
}
</script>

<style>
</style>
