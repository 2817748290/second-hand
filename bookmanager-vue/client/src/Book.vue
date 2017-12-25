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
	          	<span> {{ book.abstruct || '这是一本书'}} </span>
	          </p>
	          <div class="act">
	          	<p class="price">
			        	<span>价格：</span>
			        	<span> ￥{{ book.price || 100}} </span>
			        </p>
	            <button class="buy" @click="handleAdd(book)">预约</button>
	          </div>
	        </div>
        </div>
	</div>
</template>

<script>
	import {getBookInfoById,addOrder,getUserById,updateUser} from './api/api.js'
	export default {
		name: 'book',
		data () {
			return {
				'book': {},
				'bookId': '',
				'user': {},
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

			handleAdd (book) {
				//验证是否登录
				if(this.user===null || this.user.userId==='' || this.user.userId===undefined){
					alert("您还未登录,请先登录!")
					return
				}

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
							'status': 0
						}
						addOrder(param).then(res => {
							let readyTime = res.data.result
							alert('预约成功,请在15钟之内到图书馆借书处借书,否则预约将自动取消!')
							let timer = setInterval(() => {
								if(new Date().getTime() >= readyTime){
									console.log("预约成功了")
									clearInterval(timer)
								}
							},13)
						}).then(()=>{
							this.user.points -= 10
							let param = new FormData()
							param.append('userId', this.user.userId)
							param.append('points', this.user.points)
							updateUser(param).then((res) => {
								console.log('修改结果：')
								console.log(res.data)
							})
						})

					}
				})


      		}
		}
	}
</script>

<style scoped>
	@import './assets/css/book.css'
</style>
