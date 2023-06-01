<template>
<div style="padding: 35px">
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
        <el-input style="width: 140px" placeholder="请输入用户名" v-model="params.name"></el-input>

        <el-input style="margin-left: 5px; width: 140px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
        <el-button @click="load" style="margin-left: 5px" type="primary"><i class="el-icon-search"></i><span>搜索</span></el-button>
        <el-button @click="reset" style="margin-left: 5px" type="warning"><i class="el-icon-refresh"></i><span>重置</span></el-button>
        <el-button style="float: right; margin-right: 13px" @click="$router.push('/addUser')" type="danger"><i class="el-icon-refresh"></i><span>添加用户</span></el-button>
        <el-button @click="(index = !index), switchSort(index)" style="float: right; margin-right: 0px" type="primary"><i class="el-icon-loading"></i><span>{{ index ? "倒序" : "正序" }}</span></el-button>
    </div>

    <el-table :data="tableData" stripe>
        <el-table-column width="60" prop="id" label="编号"></el-table-column>
        <el-table-column prop="name" width="80" label="用户名"></el-table-column>
        <el-table-column prop="username" width="160" label="UUID"></el-table-column>
        <el-table-column prop="age" width="60" label="年龄"></el-table-column>
        <el-table-column prop="sex" width="60" label="性别"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="createtime" label="创建时间"></el-table-column>
        <el-table-column prop="updatetime" label="更新时间"></el-table-column>

        <el-table-column label="操作">
            <template v-slot="scope">
                <el-button @click="$router.push('/editUser?id=' + scope.row.id)" type="primary" size="mini">编辑</el-button>
                <el-popconfirm style="margin-left: 10px" title="您确定删除这行数据嘛？" @confirm="del(scope.row.id)">
                    <el-button type="danger" size="mini" slot="reference">删除</el-button>
                </el-popconfirm>
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
</div>
</template>

<script>
import router from "@/router";
import request from "@/utils/request";
//   import { load } from "mime";

export default {
    name: "User",

    data() {
        return {
            tableData: [],
            total: 0,
            params: {
                pageNum: 1,
                pagesize: 10,
                name: "",
                phone: "",
            },
            index: false,
        };
    },
    created() {
        this.load();
    },
    methods: {
        load() {
            request.get("/user/page", {
                params: this.params
            }).then((res) => {
                if ((res.code = "200")) {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }
            });
        },
        loadAsc() {
            request.get("/user/pageAsc", {
                params: this.params
            }).then((res) => {
                if ((res.code = "200")) {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                }
            });
        },
        del(id) {
            request.delete("/user/delete/" + id).then((res) => {
                if (res.code === "200") {
                    this.$notify.success("删除成功");
                    this.load();
                } else {
                    this.$notify.error("删除失败" + "\nres.msg");
                }
            });
        },
        reset() {
            this.params = {
                pageNum: 1,
                pagesize: 10,
                name: "",
                phone: "",
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
    },
};
</script>
