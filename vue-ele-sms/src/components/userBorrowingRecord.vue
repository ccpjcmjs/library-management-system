<template>
  <div class="container" style="position: relative">
    <!--  查询用户表单  -->
    <el-form ref="formInline" :model="formInline" :rules="ruleInline" size="small" inline>
      <el-form-item prop="raccount">
        <!--  搜索框  -->
        <el-input type="text" v-model="formInline.raccount" placeholder="借阅者学号">
          <!--  搜索栏图标 -->
          <el-icon class="el-icon-user" slot="prepend"></el-icon>
        </el-input>
      </el-form-item>
      <!-- 查找按钮  -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit('formInline')">查找</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="data6" border style="position: absolute; " border :header-cell-style="{background:'#F5F7FA',color:'#606266'}">
      <el-table-column prop="bid" label="编号"  />
      <el-table-column prop="title" label="书名"  />
      <el-table-column prop="number" label="副本编号" />
      <el-table-column prop="raccount" label="借阅者学号" />
      <el-table-column prop="rname" label="借阅者姓名" />
      <el-table-column prop="time" label="借阅时间" />
      <el-table-column prop="backtime" label="应归还时间" />
      <el-table-column prop="condi" label="是否超期" />
      <el-table-column fixed="right" width="180" label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="show(scope.$index)" type="primary" size="mini">查看</el-button>
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
  name: 'userBorrowingRecord',
  data () {
    return {
      dialogVisible: false,
      editFormVisible: false,
      radio: '男',
      total: '',
      currentPage: 1,
      condi: '',
      modal1: false,
      formInline: {
        raccount: ''
      },
      data6: [],
      data7: [],  //存放后台数据
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit(account) {
      this.request(1)
    },
    request (currentPage){
      let that = this
      this.$http.post(that.GLOBAL.serverPath + '/excise/getAllBorrowRecords',
        {
          raccount: that.formInline.raccount,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        // console.log('data: '+res.data)
        that.total = res.data.pageInfo.total
        that.data6=[]
        that.data7 = res.data.borrowrecords
        that.data7.forEach((e) =>{
          let obj = {}
          obj.bid = e.bid
          obj.title = e.album.title
          obj.number = e.subalbum.number
          obj.raccount = e.raccount
          obj.rname = e.reader.name
          obj.time = e.time
          obj.backtime = e.backtime
          obj.sid = e.sid
          let time = new Date().getTime();
          // console.log(time)
          if(time < e.inttime){
            obj.condi = '未超期'
          }else{
            let delayDay = Math.ceil((time - e.inttime)/86400000)
            obj.condi = '超期'+delayDay+'天'
          }
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
      this.$alert(`书名：${this.data6[index].title}<br>书籍编号：${this.data6[index].number}<br>借阅者学号：${this.data6[index].raccount}<br>借阅者姓名：${this.data6[index].rname}<br>借阅时间：${this.data6[index].time}<br>应归还时间：${this.data6[index].backtime}<br>状态：${this.data6[index].condi}`,{
        iconClass: 'el-icon-info',
        title: '书籍信息',
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
.el-message-box__content{
  font-size: 12px;
}
.el-message-box__status{
  position: absolute;
  top: 10px;
}
</style>

