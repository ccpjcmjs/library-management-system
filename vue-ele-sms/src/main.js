// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import Global from "./components/util/Global";
import reset from './assets/css/reset.css';
import VueI18n from 'vue-i18n';
import Vuex from './vuex/index'
import { Message } from 'element-ui';

Vue.prototype.GLOBAL = Global
Vue.config.productionTip = false
Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: window.sessionStorage.getItem('lang') || 'zh-CN',
  fallbackLocale: 'zh-CN',
  silentFallbackWarn: true,
  // 语言标识, 通过切换locale的值来实现语言切换,this.$i18n.locale
  messages: {
    'zh-CN': require('./i18n/zh'),   // 中文语言包
    'en-US': require('./i18n/en')    // 英文语言包
  }
})

// 路由拦截器
router.beforeEach((to, from, next) => {
  let getFlag = window.sessionStorage.getItem('Flag')
  // console.log('Flag= ' + getFlag)
  if (getFlag === 'isLogin') { /* 如果存在Flag并且为isLogin意味着用户登录，这时修改vux内state下isLogin的状态 */
    Vuex.state.isLogin = true
    // console.log('isLogin2: '+to.meta.isLogin)
    next();
    if (!to.meta.isLogin) { /* 如果在有登录状态的情况下前往不需要权限的路由路径，则判定为退出登录，进行提示并跳转登录页 */
      next({
        path: '/'
      })
    }
  } else {
    // console.log('isLogin1: '+to.meta.isLogin)
    if (to.meta.isLogin) { /* 如果没有登录状态且要去往需要权限的路径时跳转登录页并进行提示 */
      next({
        path: '/#/Login'
      })
      Message.info('请先登录')
    } else {
      next()
    }
  }
})

router.afterEach(route => {
  window.scroll(0, 0)
})

router.afterEach(route => {
  window.scroll(0, 0)
})

// 路由拦截器
// router.beforeEach((to, from, next) => {
//
// })

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  components: { App },
  template: '<App/>',
  data: {
    Bus: new Vue()
  },
})
