<template>
  <div id="app">
    <div class="container">
      <page-header></page-header>
      <nav class="page-nav">
        <ul>
          <li>
            <router-link :to="{name: 'Home'}" exact>
              <div>首页</div>
            </router-link>
          </li>
          <li>
            <router-link :to="{name: 'Subjects', params: {subject: '热门图书'}}">
              <div>主题</div>
            </router-link>
          </li>
          <li>
            <router-link :to="{name: 'Category', params:{category: '所有图书'}}">
              <div>分类</div>
            </router-link>
          </li>
          <li>
            <router-link :to="{name: 'Cart'}">
              <div>我的借阅</div>
            </router-link>
          </li>
        </ul>
      </nav>
      <router-view></router-view>
      <page-footer></page-footer>
    </div>
    <div id="back-top">
      <a href="#">
        <img src="static/icons/top.png">
      </a>
    </div>
    
  </div>
</template>

<script>
  import PageHeader from '@/components/PageHeader'
  import PageFooter from '@/components/PageFooter'

  export default {
    name: 'app',
    components: {
      'page-header': PageHeader,
      'page-footer': PageFooter
    }
  }
</script>

<style>
  @import "./assets/css/app.css"
</style>
