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
        <li class="col-book-info">编辑</li>
      </ul>
      <ul class="items">
        <li class="item-book" v-show="book.state!==3 && book.state!==6&&　book.state!==5" v-for="(book,index) in bookList" :key="index">
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
            <a v-show="book.state===1" style="text-decoration:underline;cursor:pointer;" @click="handleReturn(book.bookId)">还书</a>
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
  import {getSearchOrder,getBookInfoById,deleteOrder,updateBook,updateOrder} from './api/api.js'
  export default {
    name: 'cart',
    data () {
      return {
        title: '还书',
        isCheckedAll: true,
        totalCount: 0,
        totalPrices: 0,
        user:{},
				offset: 0,
				sort: '-create_date',
        limit: 10,
        orderList: [],
        bookList: [],
        statusArr:[
          {status:0,value:'已还'},
          {status:1,value:'待还'},
          {status:2,value:'还书审核中'},
          {status:3,value:'预约中'},
          {status:4,value:'还书审核中'},
          {status:5,value:'预约审核中'},
          {status:6,value:'借出审核中'}
		   ]
      }
    },
    mounted () {
      this.$nextTick(() => {
        this.user = JSON.parse(localStorage.getItem('user'));
        if(this.user===null || this.user.userId==='' || this.user.userId===undefined){
          alert("您还未登录,请先登录!")
          this.$router.push({path: '/'})
        }else{
          this.getOrderList()
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

          for(let i in data){

            let bookId = data[i].bookId
            let formData = new FormData()
            formData.append("bookId",bookId)
            getBookInfoById(formData).then(res=>{
              this.bookList.push(res.data.result)
            })
          }

        })
      },

      handleReturn(bookId){
        if(confirm('您确定要还书吗?')){
          for(let item of this.orderList){
            if(item.bookId === bookId){
              let param = {
                'orderId': item.orderId,
                'status': 2
              }
              updateOrder(param).then(res=>{
                
                if(res.data.status === 1){
                  this.getOrderList()
                }else{
                  alert('还书失败')
                }

              }).then(()=>{
                
                let param = {
                  'bookId': bookId,
                  'state' : 4
                }
                updateBook(param).then(res=>{
                  console.log('修改书籍结果')
                  console.log(res.data)
                  if(res.data.status === 1){
                    alert('还书成功')
                  }
                })

              })
            }
          }
        }
      }
    }

  }
</script>

<style scoped>
	@import './assets/css/cart.css'
</style>
