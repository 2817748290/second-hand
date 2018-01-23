<template>
	<section id="books">
		<div class="booklist">
			<div class="book" v-for="(book, index) in bookList" :key="index">
					<div class="cover">
					<router-link :to="{name: 'Book', params: {id: book.bookId}}">
							<img class="cover" v-bind:src="'/public/'+book.imageUrl">
						</router-link>
					</div>
					<div class="book-info">
						<router-link :to="{name: 'Book', params: {id: book.bookId}}">
							<p class="name"> {{ book.bookName }} </p>
						</router-link>
						<p class="author">作者: {{ book.author }} </p>
						<p class="abstruct"> 简介: {{ book.abstruct || '这是一本书这是一本书这是一本书'}} </p>
						<div class="act">
							<!-- <span class="price"> 状态{{book.state}} </span> -->
							<!-- <button class="buy" @click="handleAdd(book)">预约</button> -->
						</div>
					</div>
				</div>
		</div>
		<pagination :total="total" :current-page='current' @pageChange="pageChange"></pagination>
	</section>
</template>

<script>
	import pagination from './Pagination'
	import {getBookInfoById,addOrder,getUserById,updateUser} from '../api/api.js'

	export default {
		name: 'books',
		props: ['bookList','total'],
		data() {
			return {
				'sortRule': 'ascending',
				//total: 150,     // 记录总条数
        		'display': 10,   // 每页显示条数
				'current': 1,   // 当前的页数
				'book': {},
				'bookId': '',
				'user': {},
			}
		},
		updated(){
				console.log(this.bookList)
				console.log(this.total+'dasd')
		},
		mounted(){
			this.user = JSON.parse(localStorage.getItem('user'));
		},
		methods: {
			pageChange:function(currentPage){
				this.$emit('pageChange', currentPage);
				// ajax请求, 向后台发送 currentPage, 来获取对应的数据
			},
			handleAdd (book) {
				//验证是否登录
				if(this.user===null || this.user.userId==='' || this.user.userId===undefined){
					alert("您还未登录,请先登录!")
					return
				}

				let param = new FormData()
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
							console.log(readyTime)
							alert('预约成功,请在2分钟之内到图书馆借书处借书,否则预约将自动取消!')
							// let timer = setInterval(() => {
							// 	if(new Date().getTime() >= readyTime){
							// 		console.log("预约成功了")
							// 		clearInterval(timer)
							// 	}
							// },13)
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
		},
		filters: {
			formatPrice(price) {
				return price + '元';
			}
		},
		components: {
			'pagination': pagination,
		}
	}
</script>

<style scoped>
	@import '../assets/css/books.css'
</style>
