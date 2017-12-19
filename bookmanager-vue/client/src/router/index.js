import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/Home'
import Category from '@/Category'
import Cart from '@/Cart'
import Book from '@/Book'
import Subjects from '@/Subjects'

Vue.use(Router)

export default new Router({
  base: __dirname,
  linkActiveClass: 'active',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/category/:category',
      name: 'Category',
      component: Category
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart
    },
    {
      path: '/book/:id',
      name: 'Book',
      component: Book
    },
    {
      path: '/subjects/:subject',
      name: 'Subjects',
      component: Subjects
    }
  ]
})
