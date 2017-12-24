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
	            <button class="buy" @click="addToCart(book)">借阅</button>
	          </div>
	        </div>
        </div>
	</div>
</template>

<script>
	import {getBookInfoById} from './api/api.js'
	export default {
		name: 'book',
		data () {
			return {
				'book': '',
				'bookId': ''
			}
		},
		mounted(){
			this.bookId = this.$route.params.id;
			console.log(this.$route)
			console.log(this.bookId+"bookId")
			let param = new FormData()
			param.append("bookId",this.bookId)
			getBookInfoById(param).then((res)=>{
				this.book = res.data.result
			})
		},
		methods: {
			addToCart: function(book) {
				this.$store.dispatch('addToCart', book);
      }
		}
	}
</script>

<style scoped>
	@import './assets/css/book.css'
</style>