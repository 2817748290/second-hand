<template>
	<div id="page-header">
		<h1>
			<a href="#" class="logo">
				<img src="../../static/icons/logo.png">
				<span>在线图书馆</span>
			</a>
		</h1>
		<div class="search">
			<input type="search" v-model="keyword" @input="searchBooks(keyword)"size="70" placeholder="书名、作者">
			<ul class="matching-books" v-show="isShow">
				<li v-for="book in matchingBooks" @click="hideMatchingBooks">
					<router-link :to="{name: 'Book', params: {id: book.id}}">
						<img v-bind:src="book.cover">
						<div class="book-info">
							<p> {{ book.name }} </p>
							<p> {{ book.author }} </p>
						</div>
					</router-link>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'page-header',
		data() {
			return {
				keyword: '',
				matchingBooks: []
			}
		},
		computed: {
			isShow() {
				if (this.matchingBooks.length > 0) {
					return true;
				} else {
					return false;
				}
			}
		},
		methods: {
			searchBooks(keyword) {
				if (keyword == '' || keyword == undefined) {
					this.matchingBooks = [];
					return;
				}
				let lowerCase = keyword.toLowerCase();
				let allBooks = this.$store.getters.getAllBookList;
				this.matchingBooks = allBooks.filter((item, index, array)=>{
					return item.name.toLowerCase().includes(lowerCase) || item.author.toLowerCase().includes(lowerCase);
				});
			},
			hideMatchingBooks() {
				this.matchingBooks = [];
			}	
		}
	}
</script>

<style scoped>
	@import '../assets/css/page-header.css'
</style>