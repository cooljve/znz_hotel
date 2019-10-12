// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//引用axios，并把基础url设置为后台服务api地址
import axios from "axios";
//生成Excel所需js文件
import Blob from "./Excel/Blob";
import Export2Excel from "./Excel/Export2Excel";

axios.defaults.baseURL='http://localhost:8080/api'
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8'
//将API方法绑定到全局
Vue.prototype.$axios=axios
//阻止vue启动时生成生产提示
Vue.config.productionTip = false

Vue.use(ElementUI);
/* eslint-disable no-new */

router.beforeEach((to,from,next)=>{
  if(to.matched.some(record=>record.meta.requireLogin)){// 判断该路由是否需要登录权限
    if (localStorage.name) {// 判断当前登录用户状态是否存在；存在则直接跳转
      next();
    }else{
      //防止无线循环
      if (to.name === 'login') {
        next();
        return;
      }
      next({
        path:'/',
        query:{redirect:to.fullPath}//表示把当前路由信息传递过去方便登录后跳转回来；
      });
    }
  }else {
    next();//若点击的是不需要验证的页面,则进行正常的路由跳转
  }
})

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
