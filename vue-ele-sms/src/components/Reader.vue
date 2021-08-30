<template>
  <div class="layout">
    <el-row type="flex">
      <!-- 左侧边栏     -->
      <!--      <el-col span="5" class="layout-menu-left">-->
      <el-menu default-active="1-1"
               class="el-menu-vertical-demo"
               @open="handleOpen"
               @close="handleClose"
               background-color="#464c5b"
               text-color="#fff"
               :collapse="collapse"
               theme="dark" width="auto"
               :open-names="['1']">
        <div class="layout-logo-left">
          <img class="logoimg" src="../assets/img/logo.png" alt="">
        </div>
        <el-submenu index="1" style="text-align: left">
          <template slot="title">
            <i class="el-icon-reading"></i>
            <span slot="title">书籍管理</span>
          </template>
          <el-menu-item index="1-1" @click.native="lookBook">
            <i class="el-icon-tickets"></i>
            <span @click="">查寻书籍</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="3" style="text-align: left">
          <template slot="title">
            <i class="el-icon-coin"></i>
            <span slot="title">借阅管理</span>
          </template>
          <el-menu-item index="3-1" @click.native="borrowedRecord">
            <i class="el-icon-tickets"></i>
            <span @click="">借阅记录</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
      <!--      </el-col>-->

      <el-col :span="23">
        <!--  头部 -->
        <el-menu class="layout-header" mode="horizontal" text-color="#fff" background-color="#464c5b" active-text-color="#fff">
          <el-button class="buttonimg">
            <img class="showimg" :src="collapse?imgsq:imgshow" @click="toggle(collapse)">
          </el-button>
          <el-avatar class="portrait" icon="el-icon-user-solid"></el-avatar>
          <el-submenu index="2" class="submenu">
            <template slot="title" style="">{{parseInt(UserCondi)==0?'用户':'管理员'}}</template>
            <el-menu-item index="2-1">设置</el-menu-item>
            <el-menu-item @click="" index="2-2">个人中心</el-menu-item>
            <el-menu-item @click="exit()" index="2-3">退出</el-menu-item>
          </el-submenu>
        </el-menu>
        <!--  导航  -->
        <div class="layout-breadcrumb">
          <el-breadcrumb>
            <el-breadcrumb-item href="#">{{one_nav}}</el-breadcrumb-item>
            <el-breadcrumb-item href="#">{{two_nav}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{three_nav}}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <!--  -->
        <div class="layout-content">
          <div class="layout-content-main">
            <template id="userManage"></template>
            <component :is="currentView"></component>
          </div>
        </div>
        <!--  底部版权标记  -->
        <div class="layout-copy">
          2021-2022 &copy; 图书馆管理
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import searchBook from '../components/SearchBook.vue'
import userBorrowingRecord from "./userBorrowingRecord";
import Button from 'element-ui/lib/button'
export default {
  name: 'Index',
  data () {
    return {
      // isCollapse: true,
      collapse: false,
      imgshow: require('../assets/img/show.png'),
      imgsq: require('../assets/img/sq.png'),
      msg: 'haha',
      one_nav: '主页',
      two_nav: '书籍管理',
      three_nav: '查询书籍',
      currentView: 'searchBook',
      UserCondi: window.sessionStorage.getItem('UserCondi')
    }
  },
  methods: {
    lookBook () {
      this.currentView = 'searchBook'
      this.one_nav = '主页'
      this.two_nav = '书籍管理'
      this.three_nav = '查看书籍'
      this.currentView = 'searchBook'
    },
    borrowedRecord () {
      this.currentView = 'userBorrowingRecord'
      this.one_nav = '主页'
      this.two_nav = '借阅管理'
      this.three_nav = '借阅记录'
      this.currentView = 'userBorrowingRecord'
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    toggle(showtype) {
      this.collapse = !showtype
      this.$root.Bus.$emit('toggle',this.collapse)
    },
    exit() {
          setTimeout(() => {
            window.sessionStorage.removeItem('Flag')
            window.sessionStorage.removeItem('UserCondi')
            window.sessionStorage.removeItem('username')
            window.sessionStorage.removeItem('userId')
            window.sessionStorage.removeItem('account')
            this.$router.replace({ path: '/' })
            this.$message({
              type: 'success',
              message: '已退出登录!'
            })
          }, 100)
    },
  },
  components: {
    Button,
    searchBook: searchBook,
    userBorrowingRecord: userBorrowingRecord,
  }
}
</script>
<style scoped>
.layout{
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  margin-top:-60px;
}
.layout-breadcrumb{
  padding: 10px 15px 0;
  text-align:left;
}
.layout-content{
  min-height: 635px;
  /*height: 980px;*/
  margin: 15px;
  /*overflow: hidden;*/
  background: #fff;
  border-radius: 4px;
}
.layout-content-main{
  padding: 10px;
}
.layout-copy{
  text-align: center;
  padding: 10px 0 20px;
  color: #9ea7b4;
}
.layout-menu-left{
  background: #464c5b;
}
.layout-header{
  height: 60px;
  padding-top: 3px;
  background: #464c5b;
  box-shadow: 0 1px 1px rgba(0,0,0,.1);
  position: relative;
}
.layout-logo-left{
  /*width: 90%;*/
  /*height: 80px;*/
  /*!*background: #5b6270;*!*/
  /*border-radius: 3px;*/
  /*margin: 10px auto;*/
  height: 40px;
  line-height: 40px;
  color: #9d9d9d;
  font-size: 20px;
  text-align: center;
  padding: 20px 0px;
}
Button{
  color:white;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 20%;
  /*min-height: 30px;*/
  min-height: 800px;
}
.packup{
  position: absolute;
  left: 13%;
}
.buttonimg {
  height: 60px;
  background-color: transparent;
  border: none;
}
.showimg {
  width: 26px;
  height: 26px;
  position: absolute;
  bottom: 15px;
  left: 10px;
}
.portrait{
  width: 35px;
  height: 35px;
  position: absolute;
  bottom: 13px;
  right: 110px;
}
.logoimg {
  height: 40px;
}
.submenu {
  float: right;
}
</style>
