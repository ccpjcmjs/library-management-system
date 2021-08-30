import Vue from 'vue';
import Router from 'vue-router';
import VueResource from 'vue-resource';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import Login from "../components/Login";
import Index from "../components/Index";
import Reader from "../components/Reader";

Vue.use(ElementUI)
Vue.use(Router)
Vue.use(VueResource)
Vue.use(VueQuillEditor)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      meta: {
        isLogin: false
      }
    },
    {
      path: '/index',
      component: Index,
      meta: {
        isLogin: true
      }
    },
    {
      path: '/reader',
      component: Reader,
      meta: {
        isLogin: true
      }
    },
  ]
})
