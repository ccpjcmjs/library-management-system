<template>
  <div class="container" style="position: relative">
    <!--  查询用户表单  -->
    <el-form ref="formInline" :model="formInline" :rules="ruleInline" size="small" inline>
      <el-form-item prop="account">
        <!--  搜索框  -->
        <el-input type="text" v-model="formInline.account" placeholder="学号">
          <!--  搜索栏图标 -->
          <el-icon class="el-icon-user" slot="prepend"></el-icon>
        </el-input>
      </el-form-item>
      <!-- 查找按钮  -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit('formInline')">查找</el-button>
      </el-form-item>
      <!-- 添加用户按钮 -->
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">新添用户</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="data6" border style="position: absolute; " border :header-cell-style="{background:'#F5F7FA',color:'#606266'}">
      <el-table-column prop="account" label="学号"  />
      <el-table-column prop="name" label="姓名"  />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="time" label="录入时间" />
      <el-table-column prop="condi" label="身份" :formatter="formatCondi" />
      <el-table-column fixed="right" width="150" label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index)" type="primary" size="mini">编辑</el-button>
          <el-button type="danger" size="mini" @click="remove(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 一页显示十条数据-->
    <el-pagination @current-change="changePage" :total="total" :page-size="10" background layout="prev, pager, next, total " hide-on-single-page  style="position: absolute; top: 590px; left: 530px"></el-pagination>

    <el-dialog title="新添用户" :visible.sync="dialogVisible" width="30%">
      <el-form ref="formItem3" :model="formItem3" :rules="ruleItem3" label-width="80px" size="mini">

        <el-form-item label="学号" prop="account">
          <el-input v-model="formItem3.account" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formItem3.name" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formItem3.password" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="formItem3.sex">
            <el-radio v-model="radio" label="男">男</el-radio>
            <el-radio v-model="radio" label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份" prop="condi">
          <el-select v-model="formItem3.condi" placeholder="">
            <el-option value="0" label="学生">学生</el-option>
            <el-option value="1" label="图书管理员">图书管理员</el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closedUserForm" size="mini">取消</el-button>
        <el-button type="primary" @click="addUserForm" size="mini">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑用户" :visible.sync="editFormVisible" width="30%">
        <el-form ref="formItem4" :model="formItem4" :rules="ruleItem4" label-width="80px" size="mini">
          <el-form-item label="学号" prop="account">
            <el-input v-model="formItem4.account" placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="formItem4.name" placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="formItem4.password" placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="formItem4.sex">
              <el-radio v-model="radio" label="男">男</el-radio>
              <el-radio v-model="radio" label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="身份" prop="condi">
            <el-select v-model="formItem4.condi" placeholder="">
              <el-option value="0" label="学生">学生</el-option>
              <el-option value="1" label="图书管理员">图书管理员</el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="editFormVisible = false" size="mini">取消</el-button>
          <el-button type="primary" @click="update(formItem4)" size="mini">确定</el-button>
        </span>
    </el-dialog>
  </div>
</template>
<script type="es6">
import { Message } from 'element-ui';
  export default {
    name: 'UserManage',
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
          account: ''
        },
        formItem2: {
          account: '',
          name: '',
          sex: '男',
          condi: '0'
        },
        formItem3: {
          account: '',
          name: '',
          password: '',
          sex: '男',
          condi: '0'
        },
        formItem4: {
          rid: '',
          account: '',
          name: '',
          password: '',
          sex: '男',
          condi: '0'
        },
        ruleItem2: {
          account: [{
            required: true,
            message: '请填写账号！',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '请填写学生姓名',
            trigger: 'blur'
          }]
        },
        ruleItem3: {
          account: [{
            required: true,
            message: '请填写账号！',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '请填写学生姓名',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请填写密码',
            trigger: 'blur'
          }]
        },
        ruleItem4: {
          account: [{
            required: true,
            message: '请填写账号！',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '请填写学生姓名',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请填写密码',
            trigger: 'blur'
          }]
        },
        data6: [],
      }
    },
    mounted(){
      this.request(1);
    },
    methods: {
      handleSubmit(account) {
        this.request(1)
      },
      remove (index) {
        this.$http.post(this.GLOBAL.serverPath + '/excise/removeReader',
          {
            account: this.data6[index].account,
          },
          {
            emulateJSON: true
          }
        ).then(function (res) {
          Message.success('删除成功')
          this.request(1)
        }).catch((e) =>{
          Message.error('网络有误！')
        })
      },
      update(formItem4){
        this.$http.post(this.GLOBAL.serverPath + '/excise/updateReader',
          {
            rid: formItem4.rid,
            account: formItem4.account,
            name: formItem4.name,
            password: formItem4.password,
            sex: formItem4.sex,
            condi: formItem4.condi,
          },
          {
            emulateJSON: true
          }
          ).then(function (res) {
          if(res.data.status=='ok'){
            Message.success('修改成功')
            this.editFormVisible = false
            this.request(1)
          }else{
            Message.error('已存在该学号的用户')
          }
        }).catch((e) => {
          Message.error('网络有误！')
        })
      },
      request (currentPage){
        let that = this
        this.$http.post(that.GLOBAL.serverPath + '/excise/getAllReaders',
          {
            account: that.formInline.account,
            currentPage: currentPage
          },
          {
            emulateJSON: true
          }
        ).then(function (res) {
          console.log(res.data.pageInfo)
          that.total = res.data.pageInfo.total
          that.data6 = res.data.readers
        }).catch((e) => {
          Message.error('网络有误！')
        })
      },
      changePage: function(currentPage){
        this.request(currentPage)
      },
      addUserForm () {
        let that = this
        this.$refs.formItem3.validate(async (valid) => {
          if (valid) {
            that.$http.post(that.GLOBAL.serverPath + '/excise/addReader',
              {
                account: that.formItem3.account,
                name: that.formItem3.name,
                password: that.formItem3.password,
                sex: that.formItem3.sex,
                condi: that.formItem3.condi,
              },
              {
                emulateJSON: true
              }
            ).then(function (res) {
              console.log(res.data.status)
              if(res.data.status=='ok'){
                Message.success('新增成功')
                that.formInline.account=''
                that.dialogVisible = false
                this.$refs.formItem3.resetFields();
                that.request(1)
              }else{
                Message.error('已存在该学号的用户')
              }

            }).catch((e) => {
              Message.error('网络有误！')
            })
          }
        })
      },
      closedUserForm(){
        this.dialogVisible = false
        this.$refs.formItem3.resetFields();
      },
      formatCondi: function (row, colum ,cellValue) {
                      if (cellValue === 0){
                        return '学生';
                      }else {
                        return '图书管理员';
                      }
      },
      handleEdit(index){
        this.editFormVisible = true;
        this.formItem4 = this.data6[index];
        console(this.formItem4);
        if (this.data6[index].condi === 0){
          this.formItem4.condi = '0';
        }else {
          this.formItem4.condi = '1';
        }
      }
    }
  }
</script>
<style>
.el-table__body tr,
  .el-table__body td {
    padding: 0;
    height: 48px;
  }
</style>

