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
      <!-- 添加书籍按钮 -->
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">添加书籍</el-button>
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
      <el-table-column fixed="right" width="245" label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index)" type="primary" size="mini">编辑</el-button>
          <el-button @click="numHandleEdit(scope.$index)" type="primary" size="mini">添加副本</el-button>
          <el-button type="danger" size="mini" @click="remove(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 一页显示十条数据-->
    <el-pagination @current-change="changePage" :total="total" :page-size="10" background layout="prev, pager, next, total " hide-on-single-page  style="position: absolute; top: 590px; left: 530px"></el-pagination>

    <el-dialog title="新添书籍" :visible.sync="dialogVisible" width="800">
      <el-form ref="formItem2" :model="formItem2" :rules="ruleItem2" label-width="80px" size="mini">

        <el-form-item label="书名" prop="title">
          <el-input v-model="formItem2.title" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="formItem2.author" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="formItem2.publisher" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="publishtime">
          <el-date-picker
            v-model="formItem2.publishtime"
            type="date"
            placeholder="选择日期"
            style="position: absolute; left: 1px"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="描述" prop="descri">
<!--          <el-input v-model="formItem2.descri" placeholder=""></el-input>-->
          <quill-editor v-model="formItem2.descri" ref="VueQuillEditor"
                        :content="content"
                        :options="editorOption"
                        @change="onEditorChange($event)">
          </quill-editor>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closedAlbumsForm" size="mini">取消</el-button>
        <el-button type="primary" @click="addAlbumsForm" size="mini">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑书籍" :visible.sync="editFormVisible" width="800">
      <el-form ref="formItem3" :model="formItem3" :rules="ruleItem3" label-width="80px" size="mini">

        <el-form-item label="书名" prop="title">
          <el-input v-model="formItem3.title" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="formItem3.author" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="formItem3.publisher" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="publishtime">
          <el-date-picker
            v-model="formItem3.publishtime"
            type="date"
            placeholder="选择日期"
            style="position: absolute; left: 1px"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="描述" prop="descri">
          <!--          <el-input v-model="formItem2.descri" placeholder=""></el-input>-->
          <quill-editor v-model="formItem3.descri" ref="VueQuillEditor"
                        :content="content"
                        :options="editorOption"
                        @change="onEditorChange($event)">
          </quill-editor>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
          <el-button @click="editFormVisible = false" size="mini">取消</el-button>
          <el-button type="primary" @click="update(formItem3)" size="mini">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="添加书籍副本" :visible.sync="numFormVisible" width="30%">
      <el-form ref="formItem4" :model="formItem4" :rules="ruleItem4" label-width="80px" size="mini">
        <el-form-item label="编号" prop="num">
          <el-input v-model="formItem4.num" placeholder=""></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="numFormVisible = false" size="mini">取消</el-button>
          <el-button type="primary" @click="addAlbumsNum()" size="mini">确定</el-button>
      </span>
    </el-dialog>
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
      formItem3: {
        title: '',
        author: '',
        publisher: '',
        publishtime: '',
        descri: ''
      },
      formItem4: {
        num: ''
      },
      ruleItem2: {
        title: [{
          required: true,
          message: '请填写书名！',
          trigger: 'blur'
        }],
        author: [{
          required: true,
          message: '请填书籍作者',
          trigger: 'blur'
        }],
        publisher: [{
          required: true,
          message: '请填出版社',
          trigger: 'blur'
        }],
        publishtime: [{
          required: true,
          message: '请填写出版时间'
        }],
        descri: [{
          required: true,
          message: '请填书籍描述',
          trigger: 'blur'
        }]
      },
      ruleItem3: {
        title: [{
          required: true,
          message: '请填写书名！',
          trigger: 'blur'
        }],
        author: [{
          required: true,
          message: '请填书籍作者',
          trigger: 'blur'
        }],
        publisher: [{
          required: true,
          message: '请填出版社',
          trigger: 'blur'
        }],
        publishtime: [{
          required: true,
          message: '请填写出版时间'
        }],
        descri: [{
          required: true,
          message: '请填书籍描述',
          trigger: 'blur'
        }]
      },
      ruleItem4: {
        num: [{
          required: true,
          message: '请填写副本编号',
          trigger: 'blur'
        }],
      },
      data6: [],
    }
  },
  mounted(){
    this.request(1);
  },
  methods: {
    handleSubmit(title) {
      this.request(1)
    },
    remove (index) {
      this.$http.post(this.GLOBAL.serverPath + '/excise/removeAlbum',
        {
          aid: this.data6[index].aid,
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
    update(formItem3){
      console.log(formItem3)
      this.$http.post(this.GLOBAL.serverPath + '/excise/updateAlbum',
        {
          aid: formItem3.aid,
          title: formItem3.title,
          author: formItem3.author,
          publisher: formItem3.publisher,
          publishtime: formItem3.publishtime,
          descri: formItem3.descri,
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
          Message.error('修改失败')
        }
      }).catch((e) => {
        Message.error('网络有误！')
      })
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
        that.data6 = res.data.albums
      }).catch((e) => {
        Message.error('网络有误！')
      })
    },
    changePage: function(currentPage){
      this.request(currentPage)
    },
    addAlbumsForm () {
      let that = this
      this.$refs.formItem2.validate(async (valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/excise/addAlbum',
            {
              title: that.formItem2.title,
              author: that.formItem2.author,
              publisher: that.formItem2.publisher,
              publishtime: that.formItem2.publishtime+'',
              descri: that.formItem2.descri,
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            console.log(res.data.status)
            if(res.data.status=='ok'){
              Message.success('添加成功')
              that.formInline.account=''
              // that.formItem2.title=''
              // that.formItem2.author=''
              // that.formItem2.publisher=''
              // that.formItem2.publishtime=''
              // that.formItem2.descri=''
              that.dialogVisible = false
              this.$refs.formItem2.resetFields();
              that.request(1)
            }else {
              Message.info('添加失败')
            }
          }).catch((e) => {
            Message.error('网络有误！')
          })
        }
      })
    },
    addAlbumsNum(){
      let that = this
      this.$refs.formItem4.validate(async (valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/excise/addSubAlbum',
            {
              aid: that.currIndex,
              number: that.formItem4.num
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            console.log(res.data.status)
            if(res.data.status=='ok'){
              Message.success('添加成功')
              that.formItem4.num = '';
              that.numFormVisible = false;
              that.request(1)
            }else {
              Message.info('添加失败，添加编号已存在，请重新输入！')
            }
          }).catch((e) => {
            Message.error('网络有误！')
          })
        }
      })
    },
    closedAlbumsForm(){
      this.dialogVisible = false
      this.$refs.formItem2.resetFields();
    },
    handleEdit(index){
      this.editFormVisible = true;
      this.formItem3 = this.data6[index];
      // console(this.formItem3);
    },
    numHandleEdit(index){
      this.numFormVisible = true;
      // this.formItem4.num = this.data6[index].num
      this.currIndex = this.data6[index].aid
    },
    onEditorChange({editor,html,text}){
      // 富文本编辑器，文本改变时，设置字段值
      console.log(editor,html,text)
      this.content = html
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
</style>

