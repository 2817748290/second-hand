<template>
	<div id="book">
		<div class="book-detail">
	        <img class="cover" v-bind:src="book.cover">
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
	export default {
		name: 'book',
		computed: {
			book() {
				let id = this.$route.params.id;
				let books = this.$store.getters.getAllBookList.filter((item, index, array) => {
					return (item.id == id);
				});
				return books[0];
			}
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