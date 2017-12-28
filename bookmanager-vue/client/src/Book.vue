<template>
	<div id="book">
		<div class="book-detail">
	        <img class="cover" v-bind:src="'/public/'+book.imageUrl">
	        <div class="book-info">
	          <h3 class="name">
	          	{{ book.bookName }}
	          </h3>
	          <p class="author">
	          	<span>作者：</span>
	          	<span> {{ book.author }} </span>
	          </p>
	          <p class="abstruct">
	          	<span>简介：</span>
	          	<span> {{ book.abstruct || '这是一本书这是一本书这是这是一本书这是一本书一本书这是一本书'}} </span>
	          </p>
				 <p class="abstruct">
	          	<span>位置：</span>
	          	<span> {{ book.location}} </span>
	          </p>
	          <div class="act">
	          	<p class="price">
			        	<span>状态：</span>
			        	<span> {{formatStatus(book.state)}} </span>
			        </p>
					 
	            <button class="buy" @click="handleAdd(book)">预约</button>
	          </div>
	        </div>
        </div>
	</div>
</template>

<script>
	import {getBookInfoById,addOrder,getUserById,updateUser,updateBook} from './api/api.js'
	export default {
		name: 'book',
		data () {
			return {
				'book': {},
				'bookId': '',
				'user': {},
				statusArr:[
					{status:0,value:'可借'},
					{status:1,value:'不可借'},
					{status:2,value:'不可借'},
					{status:3,value:'不可借'},
					{status:4,value:'不可借'},
					{status:5,value:'不可借'},
					{status:6,value:'不可借'}
				]
			}
		},
		mounted(){
			this.user = JSON.parse(localStorage.getItem('user'));
			console.log(this.user)
			this.bookId = this.$route.params.id;
			let param = new FormData()
			param.append("bookId",this.bookId)
			getBookInfoById(param).then((res)=>{
				this.book = res.data.result
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
			handleAdd (book) {
				//验证是否登录
				if(this.user===null || this.user.userId==='' || this.user.userId===undefined){
					alert("您还未登录,请先登录!")
					return
				}
				//此书可借
				if(this.book.state===0){

					let param = new FormData()
					console.log("userId:"+this.user.userId)
					param.append('userId',this.user.userId)
					getUserById(param).then(res=>{
						this.user = res.data.result

						if(this.user.points <= 10){

							alert('抱歉,您的积分不够,暂时无法预约书籍！')

						}else{

							let param = {
								'bookId': book.bookId,
								'status': 5
							}
							addOrder(param).then(res => {
								let readyTime = res.data.result
								alert('预约成功,请在24小时之内到图书馆借书处借书,否则预约将自动取消!')
								
							}).then(()=>{
								this.user.points -= 10
								let param = new FormData()
								param.append('userId', this.user.userId)
								param.append('points', this.user.points)
								updateUser(param).then((res) => {
									console.log('修改结果：')
									console.log(res.data)
								})
							}).then(()=>{
								let param = {
									'bookId': book.bookId,
									'state': 5
								}
								updateBook(param).then(res=>{
									console.log('修改书籍结果:')
									console.log(res.data)
								})
							})

						}
					})

				} else {
					alert("此书已经借出!")
				}
			}
				
		}
	}
</script>

<style scoped>
	@import './assets/css/book.css'
</style>
