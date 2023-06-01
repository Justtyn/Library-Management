<template>
<div style="width: 90%; margin: 0px auto">
    <h1>图书管理系统首页</h1>
    <h2>
        管理员 : &ensp;<a href="" style="color: dodgerblue; text-decoration-line: none">{{ admin.username }}</a>
    </h2>
    <h2>
        手机号 : &ensp;<a href="" style="color: dodgerblue; text-decoration-line: none">{{ admin.phone }}</a>
    </h2>
    <h2>
        EMAIL : &ensp;<a href="" style="color: dodgerblue; text-decoration-line: none">{{ admin.email }}</a>
    </h2>
</div>
</template>

<script>
import Cookies from 'js-cookie'
import request from '@/utils/request';

export default {
    name: "HomeView",
    data() {
        return {
            admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
        }
    },
    created() {
        // this.load();
        // console.log(JSON.parse(Cookies.get('admin')));
    },
    methods: {
        load() {
            request.get("/admin/" + this.admin.id)
                .then((res) => {
                    if ((res.code = "200")) {
                        this.admin = res.data
                    }
                });
        }

    },
}
</script>

<style scoped></style>
