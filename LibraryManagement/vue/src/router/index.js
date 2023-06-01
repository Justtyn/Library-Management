import Vue from "vue";
import VueRouter from "vue-router";
import Layout from "../views/Layout.vue";
import Cookies from "js-cookie";

Vue.use(VueRouter);

const routes = [
  // Login
  {
    path: "/login",
    name: "Login",
    component: () => import("../../src/views/login/Login.vue"),
  },
  // Register
  {
    path: "/register",
    name: "Register",
    component: () => import("../../src/views/login/Register.vue"),
  },
  // 主页
  {
    path: "/",
    name: "Layout",
    component: Layout,
    redirect: "/home",
    children: [
      // 主页
      {
        path: "home",
        name: "Home",
        component: () => import("../views/home/HomeView.vue"),
      },
      // User
      {
        path: "userList",
        name: "UserList",
        component: () => import("../../src/views/user/User.vue"),
      },
      {
        path: "addUser",
        name: "AddUser",
        component: () => import("../../src/views/user/AddUser.vue"),
      },
      {
        path: "editUser",
        name: "EditUser",
        component: () => import("../../src/views/user/EditUser.vue"),
      },
      // Admin
      {
        path: "adminList",
        name: "AdminList",
        component: () => import("../../src/views/admin/List.vue"),
      },
      {
        path: "addAdmin",
        name: "AddAdmin",
        component: () => import("../../src/views/admin/Add.vue"),
      },
      {
        path: "editAdmin",
        name: "EditAdmin",
        component: () => import("../../src/views/admin/Edit.vue"),
      },
      // Category
      {
        path: "categoryList",
        name: "CategoryList",
        component: () => import("../../src/views/category/List.vue"),
      },
      {
        path: "addCategory",
        name: "AddCategory",
        component: () => import("../../src/views/category/Add.vue"),
      },
      {
        path: "editCategory",
        name: "EditCategory",
        component: () => import("../../src/views/category/Edit.vue"),
      },
    ],
  },
  {
    path: "*",
    name: "404",
    component: () => import("@/views/404.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// 路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === "/login" || to.path === "/register") next();
  const admin = Cookies.get("admin");

  //  强制退回登陆页面
  if (!admin && to.path !== "/login" && to.path !== "/register") {
    next("/login");
    setTimeout("console.clear()", 300);
    return;
  }
  // 当访问 /home 时 并且 cookie 存在 admin 数据 则放行
  next();
});

export default router;
