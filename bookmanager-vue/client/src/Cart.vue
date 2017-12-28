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
            <div :ref="'timer'+index"></div>
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
  import {getSearchOrder,getBookInfoById,deleteOrder,updateBook,updateOrder} from './api/api.js'
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
        statusArr:[
          {status:0,value:'已关闭'},
          {status:1,value:'已关闭'},
          {status:2,value:'已关闭'},
          {status:3,value:'预约中'},
          {status:4,value:'已关闭'},
          {status:5,value:'预约审核中'},
          {status:6,value:'预约中'}
				]
      }
    },
    computed: {

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
          
            console.log(item.value)
          if(item.status === state){
            return item.value
          }
        }
      },
      formateReadyTime(index){
        for(let item of this.orderList){
          let timer = setInterval(() => {
            this.leftTimer(item.readyTime,`timer${index}`)
          },13);
        }

      },
      leftTimer(readyTime, index){
       // var index = ref.replace('timer','')
        var ref = `timer${index}`
        var EndTime= new Date(readyTime);
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
        if(m===0 && s===0){

          clearInterval(this.orderList[index].timer)
          let param = {
            'orderId' :this.orderList[index].orderId,
          }
          deleteOrder(param).then((res)=>{
            console.log('修改order结果')
            console.log(res.data)
          }).then(()=>{
            let param = {
              'bookId': this.orderList[index].bookId,
              'status': 0
            }
            updateBook(param).then(res=>{
              console.log('修改书籍结果')
              console.log(res.data)
            })
          })

        }
        console.log('拿到的')
        console.log(this.$refs[ref])
        this.$refs[ref][0].innerHTML= `${m}:${s}`
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
            //如果预约已经过期
            if(data[i].readyTime<new Date().getTime()){
              let param = {
                'orderId' :data[i].orderId,
              }
              deleteOrder(param).then((res)=>{
                console.log('修改order结果')
                console.log(res.data)
              }).then(()=>{
                let param = {
                  'bookId': this.orderList[index].bookId,
                  'status': 0
                }
                updateBook(param).then(res=>{
                  console.log('修改书籍结果')
                  console.log(res.data)
                })
              })
              return
            }
            // 如果预约已经关闭
            let orderStatus =  data[i].status
            if(orderStatus === 0 || orderStatus === 1 || orderStatus === 2 || orderStatus === 4){
              console.log("status")
              console.log(orderStatus)
              let param = {
                'orderId' :data[i].orderId,
              }
              deleteOrder(param).then((res)=>{
                console.log('修改order结果')
                console.log(res.data)
              }).then(()=>{
                let param = {
                  'bookId': this.orderList[index].bookId,
                  'status': 0
                }
                updateBook(param).then(res=>{
                  console.log('修改书籍结果')
                  console.log(res.data)
                })
              })
              return
            }
            this.orderList[i].timer = setInterval(() => {
              this.leftTimer(data[i].readyTime, i)
            },0);
            let bookId = data[i].bookId
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
                  this.getOrderList()
                }else{
                  alert('取消预约失败')
                }

              }).then(()=>{
                
                let param = {
                  'bookId': bookId,
                  'state' : 0
                }
                updateBook(param).then(res=>{
                  console.log('修改书籍结果')
                  console.log(res.data)
                  if(res.data.status === 1){
                    alert('取消预约成功')
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
