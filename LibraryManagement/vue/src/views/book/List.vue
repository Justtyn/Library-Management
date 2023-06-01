<template>
  <div style="padding: 35px">
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
      <!-- 检索输入框 -->
      <el-input style="width: 140px" placeholder="请输入图书名称" v-model="params.id"></el-input>
      <el-input style="margin-left: 5px; width: 160px" placeholder="请输入图书标准码" v-model="params.name"></el-input>

      <el-button @click="load" style="margin-left: 5px" type="primary"><i class="el-icon-search"></i><span>搜索</span>
      </el-button>
      <el-button @click="reset" style="margin-left: 5px" type="warning"><i class="el-icon-refresh"></i><span>重置</span>
      </el-button>

      <el-button style="float: right; margin-right: 13px" @click="$router.push('/addCategory')" type="danger"><i
          class="el-icon-refresh"></i><span>添加图书</span></el-button>
      <el-button @click="(index = !index), switchSort(index)" style="float: right; margin-right: 0px" type="primary"><i
          class="el-icon-loading"></i><span>{{ index ? "倒序" : "正序" }}</span></el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" width="90" label="编号"></el-table-column>
      <el-table-column prop="description" width="100" label="图书名称"></el-table-column>
      <el-table-column prop="publish_date" width="100" label="出版日期"></el-table-column>
      <el-table-column prop="author" width="100" label="作者"></el-table-column>
      <el-table-column prop="press" width="100" label="出版社"></el-table-column>
      <el-table-column prop="category" width="100" label="分类"></el-table-column>
      <el-table-column prop="isbn" width="150" label="标准码"></el-table-column>
      <el-table-column prop="cover" width="150" label="封面"></el-table-column>
      <el-table-column prop="createtime" width="150" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" width="150" label="更新时间"></el-table-column>

      <el-table-column label="操作" width="250">
        <template v-slot="scope">
          <el-popconfirm title="您确定删除这行数据嘛？" @confirm="del(scope.row.id)">
            <el-button style="margin-left: 10px; float: right; " type="danger" size="mini" slot="reference">删除
            </el-button>
          </el-popconfirm>
          <el-button style=" float: right" @click="$router.push('/editCategory?id=' + scope.row.id)" type="primary"
                     size="mini">编辑
          </el-button>

        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 10px">
      <div class="block">
        <el-pagination current-page="params.pageNum" page-size="params.pageSize" layout="prev, pager, next"
                       @current-change="handelCurrentChange" :total="total">
        </el-pagination>
      </div>
    </div>

    <!-- 添加二级分类弹窗 -->
    <el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类备注" prop="remark">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import router from "@/router";
import request from "@/utils/request";
//   import { load } from "mime";
import Cookies from "js-cookie";

export default {
  name: "CategoryList",

  data() {
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
      tableData: [],
      dialogFormVisible: false,
      total: 0,
      form: {},
      pid: null,
      // 表单格式判断规则
      rules: {
        name: [{
          required: true,
          message: "请输入分类名称",
          trigger: "blur",
        },],
      },
      params: {
        pageNum: 1,
        pagesize: 10,
        name: "",
      },
      index: false,
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request
          .get("/category/page", {
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
          .get("/category/pageAsc", {
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
      request.delete("/category/delete/" + id).then((res) => {
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
      } else {
        this.load();
      }
    },
    handleAdd(row) {
      // 将当前行id 作为二级分类的pid
      this.pid = row.id;
      this.dialogFormVisible = true;
    },
    save() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          // 赋值
          this.form.pid = this.pid;
          request.post("/category/save", this.form).then((res) => {
            if (res.code === "200") {
              this.$notify.success("新增二级分类成功");
              this.dialogFormVisible = false;
              this.$refs["formRef"].resetFields();
              // this.$router.push("/categoryList");
              this.load();
              this.form = {};
            } else {
              this.$notify.error("新增二级分类失败" + res.msg);
            }
          });
        }
      });
    },
  },
};
</script>
