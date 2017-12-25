import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import NProgress from 'nprogress'//页面顶部进度条
import 'nprogress/nprogress.css'
import axios from 'axios'


import Login from './pages/Login.vue'
import Home from './pages/Home.vue'
import Main from './pages/Main.vue'
import Table from './pages/nav1/Table.vue'
import Form from './pages/nav1/Form.vue'
import user from './pages/nav1/user.vue'
import Page4 from './pages/nav2/Page4.vue'
import Page5 from './pages/nav2/Page5.vue'
import Page6 from './pages/nav3/Page6.vue'
import echarts from './pages/charts/echarts.vue'
import User from './pages/user/index.vue'
import Book from './pages/book/index.vue'
import Order from './pages/order/index.vue'
import BorrowBook from './pages/BorrowBook/index.vue'
import ReturnBook from './pages/ReturnBook/index.vue'
import AppointmentBook from './pages/AppointmentBook/index.vue'
import Rights from './pages/Rights/index.vue'
import vueCropper from './components/vueCropper.vue'

// start mock
// import Mock from './mock';
// Mock.bootstrap();

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

NProgress.configure({ showSpinner: false });

const routes = [
  {
    path: '/login',
    component: Login,
    hidden: true//不显示在导航中
  },
  {
    path: '/',
    component: Home,
    name: '用户管理',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-message',//图标样式class
    children: [
      { path: '/user', component: User, name: '用户管理' }
    ]
  },
  {
    path: '/',
    component: Home,
    name: '书籍管理',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-document',//图标样式class
    children: [
      { path: '/book', component: Book, name: '书籍管理' }
    ]
  },{
    path: '/',
    component: Home,
    name: '借阅记录管理',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-search',//图标样式class
    children: [
      { path: '/order', component: Order, name: '借阅记录管理' }
    ]
  },{
    path: '/',
    component: Home,
    name: '预约审核',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-edit',//图标样式class
    children: [
      { path: '/appointment', component: AppointmentBook, name: '预约审核' }
    ]
  },{
    path: '/',
    component: Home,
    name: '借书审核',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-edit',//图标样式class
    children: [
      { path: '/borrow', component: BorrowBook, name: '借书审核' }
    ]
  },
  {
    path: '/',
    component: Home,
    name: '还书审核',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-view',//图标样式class
    children: [
      { path: '/return', component: ReturnBook, name: '还书审核' }
    ]
  },{
    path: '/',
    component: Home,
    name: '申述审核',
    leaf: true,//只有一个节点    
    iconCls: 'el-icon-message',//图标样式class
    children: [
      { path: '/rights', component: Rights, name: '申述审核' }
    ]
  },
  //{ path: '/main', component: Main },
  // {
  //   path: '/',
  //   component: Home,
  //   name: '导航一',
  //   iconCls: 'el-icon-message',//图标样式class
  //   children: [
  //     //{ path: '/main', component: Main },
  //     { path: '/table', component: Table, name: 'Table' },
  //     { path: '/form', component: Form, name: 'Form' },
  //     { path: '/user', component: user, name: '列表' },
  //   ]
  // },
  // {
  //   path: '/',
  //   component: Home,
  //   name: '导航二',
  //   iconCls: 'fa fa-id-card-o',
  //   children: [
  //     { path: '/page4', component: Page4, name: '页面4' },
  //     { path: '/page5', component: Page5, name: '页面5' }
  //   ]
  // },
  // {
  //   path: '/',
  //   component: Home,
  //   name: '',
  //   iconCls: 'fa fa-address-card',
  //   leaf: true,//只有一个节点
  //   children: [
  //     { path: '/page6', component: Page6, name: '导航三' }
  //   ]
  // },
  // {
  //   path: '/',
  //   component: Home,
  //   name: 'Charts',
  //   iconCls: 'fa fa-bar-chart',
  //   children: [
  //     { path: '/echarts', component: echarts, name: 'echarts' }
  //   ]
  // }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  NProgress.start();
  next()
})

router.afterEach(transition => {
  NProgress.done();
});

new Vue({
  el: '#app',
  template: '<App/>',
  router,
  store,
  components: { App }
  //render: h => h(Login)
}).$mount('#app')

//router.replace('/login')

