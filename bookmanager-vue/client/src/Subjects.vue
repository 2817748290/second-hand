<template>
	<div id="subjects">
		<nav id="subjects-nav">
			<ul>
				<li v-for="subject in subjects">
					<router-link :to="{name: 'Subjects', params: {subject: subject}}">
						{{ subject }}
					</router-link>
				</li>
			</ul>
		</nav>
		<section id="subjects-books">
			<h2> {{ title }} </h2>
			<books v-bind:books="books"></books>
		</section>
	</div>
</template>

<script>
	import Books from './components/Books'

	export default {
		name: 'subjects',
		components: {
			'books': Books
		},
		data () {
			return {
				title: '热门图书',
				subjects: ['热门图书','特价图书','新书上架']
			}
		},
		computed: {
			books () {
				var subject = this.$route.params.subject; 
				switch(subject) {
					case '热门图书':
						this.title = '热门图书';
						let hotBooks = this.$store.getters.getHotBooks;
						if (hotBooks.length === 0) {
							this.$store.dispatch('setHotBooks', 'static/data/books/hot-books.json');
						}
						return hotBooks;
					case '特价图书':
						this.title = '特价图书';
						let discountBooks = this.$store.getters.getDiscountBooks;
						if (discountBooks.length === 0) {
							this.$store.dispatch('setDiscountBooks', 'static/data/books/discount-books.json');
						}
						return discountBooks;
					case '新书上架':
						this.title = '新书上架';
						let newBooks = this.$store.getters.getNewBooks;
						if (newBooks.length === 0) {
							this.$store.dispatch('setNewBooks', 'static/data/books/new-books.json');
						}
						return newBooks;
				}
			}
		}
	}
</script>

<style scoped>
	@import './assets/css/subjects.css'
</style>