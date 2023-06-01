<template>
<div style="padding: 35px">
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
        <!-- 检索输入框 -->
        <el-input style="width: 140px" placeholder="请输入用户名" v-model="params.username"></el-input>
        <el-input style="margin-left: 5px; width: 160px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
        <el-input style="margin-left: 5px; width: 160px" placeholder="请输入电子邮箱" v-model="params.email"></el-input>

        <el-button @click="load" style="margin-left: 5px" type="primary"><i class="el-icon-search"></i><span>搜索</span></el-button>
        <el-button @click="reset" style="margin-left: 5px" type="warning"><i class="el-icon-refresh"></i><span>重置</span></el-button>

        <el-button style="float: right; margin-right: 13px" @click="$router.push('/addAdmin')" type="danger"><i class="el-icon-refresh"></i><span>添加管理员</span></el-button>
        <el-button @click="(index = !index), switchSort(index)" style="float: right; margin-right: 0px" type="primary"><i class="el-icon-loading"></i><span>{{ index ? "倒序" : "正序" }}</span></el-button>
    </div>

    <el-table :data="tableData" stripe>
        <el-table-column prop="id" width="90" label="编号"></el-table-column>
        <el-table-column prop="username" width="100" label="用户名"></el-table-column>
        <el-table-column prop="phone" width="150" label="联系方式"></el-table-column>
        <el-table-column prop="email" width="170" label="EMAIL"></el-table-column>
        <el-table-column prop="createtime" width="150" label="创建时间"></el-table-column>
        <el-table-column prop="updatetime" width="150" label="更新时间"></el-table-column>

        <el-table-column label="状态" width="110">
            <template v-slot="scope">
                <el-switch @change="changeStatus(scope.row)" v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="250">
            <template v-slot="scope">
                <el-button style="float: ;" @click="$router.push('/editAdmin?id=' + scope.row.id)" type="primary" size="mini">编辑</el-button>
                <el-popconfirm style="margin-left: 10px; float: ;" title="您确定删除这行数据嘛？" @confirm="del(scope.row.id)">
                    <el-button type="danger" size="mini" slot="reference">删除</el-button>
                </el-popconfirm>
                <el-button style="float: ;margin-left: 10px; " @click="handleChangePass(scope.row)" type="warning" size="mini">修改密码</el-button>

            </template>
        </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 10px">
        <div class="block">
            <el-pagination current-page="params.pageNum" page-size="params.pageSize" layout="prev, pager, next" @current-change="handelCurrentChange" :total="total">
            </el-pagination>
        </div>
    </div>

    <!-- 修改密码弹窗 -->
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
            <el-form-item label="新密码" prop="newPass">
                <el-input v-model="form.newPass" autocomplete="off" show-password></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="savePass">确 定</el-button>
        </div>
    </el-dialog>

</div>
</template>

<script>
import router from "@/router";
import request from "@/utils/request";
//   import { load } from "mime";
import Cookies from 'js-cookie'

export default {
    name: "AdminList",

    data() {
        return {
            admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
            tableData: [],
            total: 0,
            dialogFormVisible: false,
            form: {},
            params: {
                pageNum: 1,
                pagesize: 10,
                name: "",
                phone: "",
                email: "",
            },
            index: false,
            rules: {
                newPass: [{
                    required: true,
                    message: "请输入新密码",
                    trigger: "blur"
                }, {
                    min: 3,
                    max: 10,
                    message: "密码长度不合规 「3 - 10」",
                    trigger: 'blur'
                }],

            }
        };
    },
    created() {
        this.load();
    },
    methods: {
        load() {
            request
                .get("/admin/page", {
                    params: this.params,
                })
                .then((res) => {
                    if ((res.code = "200")) {
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    }
                });
        },
        loadAsc() {
            request
                .get("/admin/pageAsc", {
                    params: this.params,
                })
                .then((res) => {
                    if ((res.code = "200")) {
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    }
                });
        },
        del(id) {
            request.delete("/admin/delete/" + id).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("删除成功");
                    this.load();
                } else {
                    this.$notify.error("删除失败");
                }
            });
        },
        reset() {
            this.params = {
                pageNum: 1,
                pagesize: 10,
                name: "",
                phone: "",
                email: ""
            };
            this.load();
        },
        handelCurrentChange(pNum) {
            // 点击分页按钮 触发分页
            console.log(pNum);
            this.params.pageNum = pNum;
            this.load();
        },
        switchSort(index) {
            if (this.index === true) {
                this.loadAsc();
                console.log(index);
            } else {
                this.load();
                console.log(index);
            }
        },
        handleChangePass(row) {
            this.form = JSON.parse(JSON.stringify(row))
            this.dialogFormVisible = true;
        },
        savePass() {
            this.$refs["formRef"].validate((valid) => {
                if (valid) {
                    request.put('/admin/changePass', this.form).then(res => {
                        if (res.code === '200') {
                            this.$notify.success('修改成功');
                            //  当前修改的管理员 id == 当前登陆的管理员 id 则修改成功之后需要重新登陆 
                            if (this.form.id === this.admin.id) {
                                Cookies.remove('admin')
                                this.$router.push('/login')
                            } else {
                                // 刷新界面 重新请求
                                this.load()
                                // 关闭弹窗
                                this.dialogFormVisible = false;
                            }
                        } else {
                            this.$notify.error('修改失败')
                        }
                    })

                }
            })

        },
        changeStatus(row) {
            // 不允许禁用当前账户的状态
            console.log(this.admin.id + ";" + row.id);
            if (this.admin.id === row.id && !row.status) {
                row.status = true;
                this.$notify.error('您操作不合法!')
                return
            }
            request.put("/admin/update", row).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("更新成功");
                    this.load();
                } else {
                    this.$notify.error("更新失败" + res.msg);
                }
            });
        }
    },
};
</script>
