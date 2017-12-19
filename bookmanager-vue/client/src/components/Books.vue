<template>
	<section id="books">
		<div class="booklist">
			<div class="book" v-for="(book, index) in books">
		        <div class="cover">
			    	<router-link :to="{name: 'Book', params: {id: book.id}}">
		            <img class="cover" v-bind:src="book.cover">
			      	</router-link>
		        </div>
		        <div class="book-info">
		          <router-link :to="{name: 'Book', params: {id: book.id}}">
		            <p class="name"> {{ book.name }} </p>
		          </router-link>
		          <p class="author"> {{ book.author }} </p>
		          <p class="abstruct"> {{ book.abstruct }} </p>
		          <div class="act">
		            <span class="price"> ￥{{ book.price | formatPrice }} </span>
		            <button class="buy" @click="addToCart(book)">购买</button>
		          </div>
		        </div>
		      </div>
		</div>
	</section>
</template>

<script>
	export default {
		name: 'books',
		data() {
			return {
				sortRule: 'ascending'
			}
		},
		props: [
			'books'
		],
		methods: {
			addToCart: function(book) {
				let cartBooks = this.$store.getters.getCartBooks;
				let index = cartBooks.indexOf(book);
				if (index === -1) {
					this.$store.dispatch('addToCart', book);
				}
	        }
		},
		filters: {
			formatPrice(price) {
				return price + '元';
			}
		}
	}
</script>

<style scoped>
	@import '../assets/css/books.css'
</style>