<template>
  <div class="login" :style="note">
    <el-form ref="formItem" :model="formItem" :rules="ruleItem" label-width="80px" size="mini" style="padding-right: 20px" >
      <el-row>
        <el-col :xs="23" :sm="23" :md="23" :lg="23">
          <h2 style="font-size: 25px">{{ $t('lang.Login.login') }}</h2>
        </el-col>
      </el-row>
      <el-row>
        <el-col :xs="23" :sm="23" :md="23" :lg="23" style="padding-right: 20px">
          <el-form-item prop="account">
            <el-input prefix-icon="el-icon-user" type="text" v-model="formItem.account" :placeholder="$t('lang.Login.userTip')" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :xs="23" :sm="23" :md="23" :lg="23" style="padding-right: 20px">
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-unlock" type="password" v-model="formItem.password" :placeholder="$t('lang.Login.passTip')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :xs="16" :sm="16" :md="16" :lg="16">
          <el-form-item prop="code" >
            <el-input type="text" v-model="formItem.code" :placeholder="$t('lang.Login.verifiable')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8">
          <h2 id="verifyCode" @click="createCode()" style="margin-top: 0px;">{{verifyCode}}</h2>
        </el-col>
      </el-row>
      <el-row>
        <el-col :xs="24" :sm="24" :md="24" :lg="24">
          <el-button id="login_btn" shape="circle" type="primary" :loading="loading" @click.native="handleSubmit('formItem')">
            <span v-if="!loading">{{ $t('lang.Login.loginButto') }}</span>
            <span v-else>{{ $t('lang.Login.loginStatus') }}</span>
          </el-button>
        </el-col>
      </el-row>
      <el-row class="tip">
        <el-col :xs="10" :sm="10" :md="10" :lg="10">
          <el-button id="lang_btn" @click="changeLaguages()">{{ $t('lang.Login.language') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>


</template>

<script>
import { Message } from 'element-ui';

export default {
  name: 'Login',
  computed: {

  },
  data () {
    return {
      verifyCode: 'hello',
      formItem: {
        account: '',
        password: '',
        code: ''
      },
      ruleItem: {
        account: [{
          required: true,
          message: this.$t('lang.Login.userMessage'),
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: this.$t('lang.Login.passMessage'),
          trigger: 'blur'
        }, {
          type: 'string',
          min: 3,
          message: this.$t('lang.Login.passLenTip'),
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: this.$t('lang.Login.verifiableMessage'),
          trigger: 'blur'
        }]
      },
      loading: false,
      note: {
        backgroundImage: "url(" + require("../assets/login.png") + ")",
        backgroundRepeat: "no-repeat",
        weight: "100%",
        height: "100%",
      },
    }
  },
  mounted () {
    this.createCode()
  },
  methods: {
    handleSubmit (name) {
      let that = this
      this.loading = true
      this.$refs[name].validate((valid) => {
        if (valid) {
          if (that.verifyCode === that.formItem.code) {
            that.$http.post(that.GLOBAL.serverPath + '/excise/login',
              {
                account: that.formItem.account,
                password: that.formItem.password
              },
              {
                emulateJSON: true
              }
            ).then(function (res) {
              console.log(res.data)
              if (res.data.result === 'yes') {
                Message.success(this.$t('lang.Login.loginSuccess'))
                // this.$store.dispatch('setUser', true)
                window.sessionStorage.setItem('Flag', 'isLogin')
                window.sessionStorage.setItem('UserCondi', res.data.loginUser.condi)
                window.sessionStorage.setItem('userId', res.data.loginUser.rid)
                window.sessionStorage.setItem('account', res.data.loginUser.account)
                // window.sessionStorage.setItem('username', res.data.loginUser.name)
                // window.localStorage.setItem('userId', res.data.loginUser.rid)
                // window.localStorage.setItem('account', res.data.loginUser.account)
                // window.localStorage.setItem('username', res.data.loginUser.name)
                // window.localStorage.setItem('sex', res.data.loginUser.sex)
                // window.localStorage.setItem('condi', res.data.loginUser.condi)
                console.log('hahaha' + res.data.condi)
                if (res.data.condi === 0) {
                  // window.localStorage.setItem('token', 'login')
                  this.$router.replace({path: '/reader'})
                } else {
                  // window.localStorage.setItem('token', 'login')
                  this.$router.replace({path: '/index'})
                }
              } else {
                Message.error(this.$t('lang.Login.loginError'))
                this.loading = false
              }
            })
          } else {
            Message.error(this.$t('lang.Login.verifiableError'))
            this.loading = false
          }
        } else {
          this.loading = false
        }
      })
    },
    createCode () {
      var code = ''
      var codeLength = 4
      var random = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
      for (var i = 0; i < codeLength; i++) {
        var index = Math.floor(Math.random() * 36)
        code += random[index]
      }
      console.log(code)
      this.verifyCode = code
    },
    changeLaguages () {
      this.$confirm(this.$t('lang.confirm.content'), this.$t('lang.confirm.tip'), {
        confirmButtonText: this.$t('lang.confirm.ok'),
        cancelButtonText: this.$t('lang.confirm.cancel'),
        type: 'warning'
      })
      .then(() => {
        let lang = this.$i18n.locale === 'zh-CN' ? 'en-US' : 'zh-CN';
        this.$i18n.locale = lang;
        window.sessionStorage.setItem('lang',lang);
        location.reload()
      })
    },
  },
}
</script>

<style scoped>

.login{
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
}
h2{
  color: #2d8cf0;
  margin-bottom:40px;
  margin-left: 18px;
}
Form{
  position: absolute;
  top: 10%;
  left: 36.66%;
  margin: 0 auto;
  width:350px;
  border: 0px solid grey;
  padding:50px 0;
  border-radius: 10px;
  text-align: center;
  background: #00000060;
}
#login_btn{
  margin: 30px;
  padding: 10px;
  width:45%;
  border-radius: 10px;
}
.tip{
  margin-top:10px;
  color:darkgrey;
}
#verifyCode{
  position: absolute;
  top: 6px;
  right: 50px;
}
#lang_btn{
  position: absolute;
  right: -7px;
  top: 5px;
  border: 0px solid grey;
  background-color: transparent;
  color: #2d8cf0;
}
#lang_btn:hover{
  color: white;
}
</style>
