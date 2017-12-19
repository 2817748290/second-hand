<template>
  <div id="cart">
    <h2> {{ title }} </h2>
    <div class="cart-books">
      <ul class="item-bar">
        <li class="col-select-btn"><input @click="switchCheckedAll" v-model="isCheckedAll" type="checkbox">全选</li>
        <li class="col-book-info">图书</li>
        <li class="col-price">价格</li>
        <li class="col-edit">编辑</li>
      </ul>
      <ul class="items">
        <li class="item-book" v-for="book in cartBooks">
          <div class="col-select-btn">
            <input @click="switchChecked(book)" v-model="book.isChecked" type="checkbox">
          </div>
          <div class="col-book-info">
            <router-link :to="{name: 'Book', params: {id: book.id}}">
              <img class="cover" v-bind:src="book.cover">
            </router-link>
            <div>
              <router-link :to="{name: 'Book', params: {id: book.id}}">
                <p class="name"> {{ book.name }} </p>
              </router-link>
              <p class="author" v-text="book.author"></p>
            </div>
          </div>
          <p class="col-price">￥{{ book.price }}</p>
          <div class="col-edit">
            <img @click="deleteCartBook(book)" src="static/icons/delete.png" alt="删除">
          </div>
        </li>
      </ul>
      <div class="check-out">
        <p>共 <span class="count">{{ totalCount }}</span> 本，应付金额：<span class="prices">￥{{ totalPrices }}</span> </p>
        <a @click="checkout" href="javascript:;">去结算</a>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'cart',
    data () {
      return {
        title: '购物车状态',
        isCheckedAll: true,
        totalCount: 0,
        totalPrices: 0
      }
    },
    mounted: function() {
      this.$nextTick(function() {
        this.countQuantityAndPrice();
      });
    },
    computed: {
      cartBooks: function() {
        var carts = this.$store.getters.getCartBooks;
        if (carts.length > 0) {
          carts.forEach((item, index, array) => {
            if (typeof item.isChecked == 'undefined') {
              this.$set(item, 'isChecked', true);
            }
          });
        } else {
          this.isCheckedAll = false;
        }
        return carts;
      }
    },
    methods: {
      /** switchCheckedAll() --> 定义全选按钮切换的功能
       *  选中全选按钮 --> 所有项都被选中
       *  取消选中全选按钮 --> 所有项都不被选中
       */
      switchCheckedAll() {
        if (this.isCheckedAll) {
          this.cartBooks.forEach((item, index, array) => {
            item.isChecked = true;
          });
        } else {
          this.cartBooks.forEach((item, index, array) => {
            item.isChecked = false;
          });
        }
      },
      switchChecked(book) {
        // 只要任意一项未被选中，都让全选按钮处于未选中状态
        if (!book.isChecked) {
          this.isCheckedAll = false;
        }
      },
      // countQuantityAndPrice(): 计算选中的图书数量和总价
      countQuantityAndPrice: function() {
        this.totalPrices = 0;
        var checkedBooks = this.cartBooks.filter((item, index, array) => {
          return item.isChecked;
        });
        this.totalCount = checkedBooks.length;
        checkedBooks.forEach((item, index, array) => {
          this.totalPrices += item.price;
        });
      },
      /** deleteCartBook()：从 store 的 cart 中删除点击的项
       *  index --> 要删除的项在 cartBooks 中的索引
       */
      deleteCartBook: function(book) {
        this.$store.dispatch('deleteCartBook', book);
      },
      // checkout(): 结算，并清空购物车
      checkout: function() {
        this.$store.dispatch('clearCart');
        alert('下单成功！');
      }
    },
    watch: {
      cartBooks: {
        handler: function() {
          let isChecked = this.cartBooks.every((item, index, array) => {
            return item.isChecked;
          });
          if (isChecked) {
            this.isCheckedAll = true;
          }
          if (this.cartBooks.length == 0) {
            this.isCheckedAll = false;
          }

          // 每当 cartBooks 发生变化时，重新计算图书数量和价格
          this.countQuantityAndPrice();
        },
        // 只有将 deep 设为 true，才能监听到 books.isChecked 属性变化
        deep: true
      }
    }
  }
</script>

<style scoped>
	@import './assets/css/cart.css'
</style>
