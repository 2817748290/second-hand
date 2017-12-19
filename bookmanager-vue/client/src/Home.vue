<template>
	<div id="home">
		<banner></banner>
		<subject-nav v-bind:subject="hotSubject" v-bind:books="hotBooksPart"></subject-nav>
		<subject-nav v-bind:subject="discountSubject" v-bind:books="discountBooksPart"></subject-nav>
		<subject-nav v-bind:subject="newSubject" v-bind:books="newBooksPart"></subject-nav>
	</div>
</template>

<script>
	import Banner from '@/components/Banner'
	import SubjectNav from '@/components/SubjectNav'

	export default {
		name: 'home',
		data () {
			return {
				hotSubject: '热门图书',
				discountSubject: '特价图书',
				newSubject: '新书上架'
			}
		},
		computed: {
			hotBooksPart () {
				var hotBooks = this.$store.getters.getHotBooks;
				return hotBooks.slice(0, 6);
			},
			discountBooksPart () {
				var discountBooks = this.$store.getters.getDiscountBooks;
				return discountBooks.slice(0, 6);
			},
			newBooksPart () {
				var newBooks = this.$store.getters.getNewBooks;
				return newBooks.slice(0, 6);
			}
		},
		components: {
			'banner': Banner,
			'subject-nav': SubjectNav 
		},
		mounted: function() {
			this.$nextTick(function() {
				this.initBooksData();
			});
		},
		methods: {
			initBooksData() {
				this.$store.dispatch('setCategoryList', 'static/data/book-category.json');
				this.$store.dispatch('setHotBooks', 'static/data/books/hot-books.json');
				this.$store.dispatch('setDiscountBooks', 'static/data/books/discount-books.json');
				this.$store.dispatch('setNewBooks', 'static/data/books/new-books.json');
				this.$store.dispatch('setAllBooks', 'static/data/books/all-books.json');
			}
		}
	}
</script>

<style>
	
</style>