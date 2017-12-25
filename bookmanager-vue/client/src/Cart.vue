<template>
  <div id="cart">
    <h2> {{ title }} </h2>
    <div class="cart-books">
      <ul class="item-bar">
        <li class="col-select-btn"><input v-model="isCheckedAll" type="checkbox">全选</li>
        <li class="col-book-info">图片</li>
        <li class="col-book-info">书名</li>
        <li class="col-book-info">作者</li>
        <li class="col-book-info">状态</li>
        <li class="col-book-info">剩余时间</li>
        <li class="col-book-info">编辑</li>
      </ul>
      <ul class="items">
        <li class="item-book" v-for="(book,index) in bookList" :key="index">
          <div class="col-select-btn">
            <input v-model="book.isChecked" type="checkbox">
          </div>
          <div class="col-book-info">
            <router-link :to="{name: 'Book', params: {id: book.id}}">
              <img class="cover" v-bind:src="'/public/'+book.imageUrl">
            </router-link>
          </div>
          <div class="col-book-info">
              <router-link :to="{name: 'Book', params: {id: book.id}}">
                <p class="name"> {{ book.bookName }} </p>
              </router-link>
          </div>
          <div class="col-book-info">
            <p class="author" v-text="book.author"></p>
          </div>
          <div class="col-book-info">
            <p class="author">{{formatStatus(book.state)}}</p>
          </div>
          <div class="col-book-info">
            <div :ref="'timer'+index">{{formateReadyTime(book,index)}}</div>
          </div>
          <div class="col-book-info">
            <img @click="handleDelete(book.bookId)" src="static/icons/delete.png" alt="删除">
          </div>
        </li>
      </ul>
      <!-- <div class="check-out">
        <p>共 <span class="count">{{ totalCount }}</span> 本，应付金额：<span class="prices">￥{{ totalPrices }}</span> </p>
        <a href="javascript:;">去结算</a>
      </div> -->
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import {getSearchOrder,getBookInfoById,deleteOrder} from './api/api.js'
  export default {
    name: 'cart',
    data () {
      return {
        title: '预约状态',
        isCheckedAll: true,
        totalCount: 0,
        totalPrices: 0,
        user:{},
				offset: 0,
				sort: '-create_date',
        limit: 10,
        orderList: [],
        bookList: [],
        leftTime: 0,
        statusArr:[
					{status:-1,value:'已关闭'},
          {status:0,value:'预约中'},
          {status:1,value:'已借'},
          {status:2,value:'还书审核中'},
          {status:3,value:'预约审核中'},
          {status:4,value:'借出审核中'}
				]
      }
    },
    computed: {

    },
    mounted: function() {
      this.$nextTick(function() {
        this.user = JSON.parse(localStorage.getItem('user'));
        if(this.user===null || this.user.userId==='' || this.user.userId===undefined){
          alert("您还未登录,请先登录!")
          this.$router.push({path: '/'})
        }else{
          this.getOrderList()
          console.log('dsads')
          console.log(this.$refs.timer0)
                    console.log(this.$refs.timer2)

        }
      })
    },
    methods: {
      formatStatus(state) {
        for(let item of this.statusArr){
          if(item.status === state){
            return item.value
          }
        }
      },
      formateReadyTime(book){
        for(let item of this.orderList){
          if(item.bookId = book.bookId){
            console.log(item)
            return (()=>{
              // setInterval(() => {
              //   this.leftTimer(item.readyTime)
              // },0);
            })()
          }
        }

      },
      leftTimer(time, ref){
        console.log(time)
        var EndTime= new Date(time);
        var NowTime = new Date();
        var t =EndTime.getTime() - NowTime.getTime();
        var d=0;
        var h=0;
        var m=0;
        var s=0;
        if(t>=0){
          d=Math.floor(t/1000/60/60/24);
          h=Math.floor(t/1000/60/60%24);
          m=Math.floor(t/1000/60%60);
          s=Math.floor(t/1000%60);
        }
        this.$refs.ref = `${m}:${s}`
        // console.log(this.leftTime)
      },
      getOrderList(){
        this.bookList=[]
        this.orderList=[]
        let param = {
          usernameValue: this.user.username,
          bookNameValue: '',
					offset: this.offset,
					sort: this.sort,
					limit: this.limit
        }
        getSearchOrder(param).then((res)=>{
          this.orderList = res.data.rows
          return this.orderList
        }).then((data)=>{
          for(let item of data){
            let bookId = item.bookId
            let formData = new FormData()
            formData.append("bookId",bookId)
            getBookInfoById(formData).then(res=>{
              this.bookList.push(res.data.result)
            })
          }
        })
      },
      handleDelete(bookId){
        if(confirm('您确定要取消预约吗?')){
          for(let item of this.orderList){
            if(item.bookId === bookId){
              let formData = new FormData()
              formData.append('orderId', item.orderId)
              deleteOrder(formData).then(res=>{
                if(res.data.status === 1){
                  alert('取消预约成功')
                  this.orderList = []
                  this.getOrderList()
                }else{
                  alert('取消预约失败')
                }
              })
            }
          }
        }
      }
    }
    // computed: {
    //   cartBooks: function() {
    //     var carts = this.$store.getters.getCartBooks;
    //     if (carts.length > 0) {
    //       carts.forEach((item, index, array) => {
    //         if (typeof item.isChecked == 'undefined') {
    //           this.$set(item, 'isChecked', true);
    //         }
    //       });
    //     } else {
    //       this.isCheckedAll = false;
    //     }
    //     return carts;
    //   }
    // },
    // methods: {
    //   /** switchCheckedAll() --> 定义全选按钮切换的功能
    //    *  选中全选按钮 --> 所有项都被选中
    //    *  取消选中全选按钮 --> 所有项都不被选中
    //    */
    //   switchCheckedAll() {
    //     if (this.isCheckedAll) {
    //       this.cartBooks.forEach((item, index, array) => {
    //         item.isChecked = true;
    //       });
    //     } else {
    //       this.cartBooks.forEach((item, index, array) => {
    //         item.isChecked = false;
    //       });
    //     }
    //   },
    //   switchChecked(book) {
    //     // 只要任意一项未被选中，都让全选按钮处于未选中状态
    //     if (!book.isChecked) {
    //       this.isCheckedAll = false;
    //     }
    //   },
    //   // countQuantityAndPrice(): 计算选中的图书数量和总价
    //   countQuantityAndPrice: function() {
    //     this.totalPrices = 0;
    //     var checkedBooks = this.cartBooks.filter((item, index, array) => {
    //       return item.isChecked;
    //     });
    //     this.totalCount = checkedBooks.length;
    //     checkedBooks.forEach((item, index, array) => {
    //       this.totalPrices += item.price;
    //     });
    //   },
    //   /** deleteCartBook()：从 store 的 cart 中删除点击的项
    //    *  index --> 要删除的项在 cartBooks 中的索引
    //    */
    //   deleteCartBook: function(book) {
    //     this.$store.dispatch('deleteCartBook', book);
    //   },
    //   // checkout(): 结算，并清空购物车
    //   checkout: function() {
    //     this.$store.dispatch('clearCart');
    //     alert('下单成功！');
    //   }
    // },
    // watch: {
    //   cartBooks: {
    //     handler: function() {
    //       let isChecked = this.cartBooks.every((item, index, array) => {
    //         return item.isChecked;
    //       });
    //       if (isChecked) {
    //         this.isCheckedAll = true;
    //       }
    //       if (this.cartBooks.length == 0) {
    //         this.isCheckedAll = false;
    //       }

    //       // 每当 cartBooks 发生变化时，重新计算图书数量和价格
    //       this.countQuantityAndPrice();
    //     },
    //     // 只有将 deep 设为 true，才能监听到 books.isChecked 属性变化
    //     deep: true
    //   }
    // }
  }
</script>

<style scoped>
	@import './assets/css/cart.css'
</style>
