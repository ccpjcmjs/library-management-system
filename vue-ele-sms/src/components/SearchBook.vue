<template>
  <div class="container" style="position: relative">
    <!--  查询用户表单  -->
    <el-form ref="formInline" :model="formInline" :rules="ruleInline" size="small" inline>
      <el-form-item prop="account">
        <!--  搜索框  -->
        <el-input type="text" v-model="formInline.title" placeholder="书名">
          <!--  搜索栏图标 -->
          <el-icon class="el-icon-search" slot="prepend"></el-icon>
        </el-input>
      </el-form-item>
      <!-- 查找按钮  -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit('formInline')">查找</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="data6"
              border
              max-height="530"
              style="position: absolute;"
              :header-cell-style="{background:'#F5F7FA',color:'#606266'}"
    >
      <el-table-column prop="aid" label="编号" />
      <el-table-column prop="title" label="书名"  />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" />
      <el-table-column prop="publishtime" label="出版时间" />
      <el-table-column prop="num" label="可借副本" />
      <el-table-column fixed="right" width="150" label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="show(scope.$index)" type="primary" size="mini">查看</el-button>
          <el-button type="danger" size="mini" @click="borrow(scope.$index)" >借阅</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 一页显示十条数据-->
    <el-pagination @current-change="changePage" :total="total" :page-size="10" background layout="prev, pager, next, total " hide-on-single-page  style="position: absolute; top: 590px; left: 530px"></el-pagination>

  </div>
</template>
<script type="es6">
import { Message } from 'element-ui';
export default {
  name: 'BookManage',
  data () {
    return {
      dialogVisible: false,
      editFormVisible: false,
      numFormVisible: false,
      status: false,
      currIndex: 0,//最近被点击添加编号副本的图书编号
      total: '',
      currentPage: 1,
      editorOption: {},
      formInline: {
        title: ''
      },
      formItem2: {
        title: '',
        author: '',
        publisher: '',
        publishtime: '',
        descri: ''
      },
      data6: [],
      data7: [],
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit(title) {
      this.request(1)
    },
    borrow (index) {
      if(this.data6[index].count != 0){
        this.$http.post(this.GLOBAL.serverPath + '/excise/borrow',
          {
            aid: this.data6[index].aid,
            rid: window.sessionStorage.getItem('userId'),
            raccount: window.sessionStorage.getItem('account')
          },
          {
            emulateJSON: true
          }
        ).then(function (res) {
          Message.success('借阅成功,注意归还日期！')
          // this.data6[index].count = this.data6[index].count-1
          this.request(1)
        }).catch((e) =>{
          Message.error('网络有误！')
        })
      }else {
        Message.error('该副本已被借完，请借阅其它书籍!')
      }
    },
    request (currentPage){
      let that = this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getAllAlbums',
        {
          title: that.formInline.title,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data.pageInfo)
        that.total = res.data.pageInfo.total
        that.data6=[]
        that.data7=res.data.albums
        that.data7.forEach((e) => {
          let obj={}
          obj.aid = e.aid
          obj.title = e.title
          obj.author = e.author
          obj.publisher = e.publisher
          obj.publishtime = e.publishtime
          obj.num = e.num
          obj.descri = e.descri
          var count=0
          var s=''
          e.subalbums.forEach((item)=>{
            if( item.condi==0 ){
              count++
              s=s+item.number+','
            }
          })
          if(count == 0){
            obj.unBorrowed = true
          }else{
            obj.unBorrowed = false
          }
          obj.count = count
          obj.suba = s
          that.data6.push(obj)
        })
      }).catch((e) => {
        Message.error('网络有误！')
      })
    },
    changePage: function(currentPage){
      this.request(currentPage)
    },
    show (index) {
      this.$alert(`书名：${this.data6[index].title}<br>作者：${this.data6[index].author}<br>出版社：${this.data6[index].publisher}<br>出版时间：${this.data6[index].publishtime}<br>副本数量：${this.data6[index].num}<br>可借数量：${this.data6[index].count}<br>可借副本编号：<span style="color:#ff0000;">${this.data6[index].suba}</span><br>介绍：${this.data6[index].descri}`,{
        iconClass: 'el-icon-info',
        width: '400',
        title: '书籍信息',
        lockScroll: false,
        closeOnPressEscape: true,
        dangerouslyUseHTMLString: true
      })
    },
  }
}
</script>
<style>
.el-table__body tr,
.el-table__body td {
  padding: 0;
  height: 48px;
}
.el-message-box{
  width: 1100px;
  position: relative;
  top: 20px;
  overflow: scroll;
  height: 100%;
}
.el-message-box__status{
  position: absolute;
  top: 10px;
}
</style>

