<template>
	<section id="bookList">
		<div class="booklist">
			<div class="book" v-for="(book, index) in bookList">
					<div class="cover">
					<router-link :to="{name: 'Book', params: {id: book.bookId}}">
							<img class="cover" v-bind:src="'/public/'+book.imageUrl">
						</router-link>
					</div>
					<div class="book-info">
						<router-link :to="{name: 'Book', params: {id: book.bookId}}">
							<p class="name"> {{ book.bookName }} </p>
						</router-link>
						<p class="author"> {{ book.author }} </p>
						<p class="abstruct"> {{ book.abstruct || '这是一本书这是一本书这是一本书'}} </p>
						<div class="act">
							<span class="price"> ￥{{ book.price || 100 | formatPrice }} </span>
							<button class="buy" @click="addToCart(book)">借阅</button>
						</div>
					</div>
				</div>
		</div>
		<pagination :total="total" :current-page='current' @pageChange="pageChange"></pagination>
	</section>
</template>

<script>
  import pagination from './Pagination'

	export default {
		name: 'books',
		props: ['bookList','total'],
		data() {
			return {
				sortRule: 'ascending',
				//total: 150,     // 记录总条数
        		display: 10,   // 每页显示条数
        		current: 1   // 当前的页数
			}
		},
		mounted(){
			this.$nextTick(()=>{
				console.log(this.total+'dasd')
			})
		},
		methods: {
			addToCart: function(book) {
				let cartBooks = this.$store.getters.getCartBooks;
				let index = cartBooks.indexOf(book);
				if (index === -1) {
					this.$store.dispatch('addToCart', book);
				}
			},
			pageChange:function(currentPage){
				this.$emit('pageChange', currentPage);
				// ajax请求, 向后台发送 currentPage, 来获取对应的数据
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