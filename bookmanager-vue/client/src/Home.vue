<template>
	<div id="home">
		<banner></banner>
		<subject-nav v-bind:subject="commendSubject" :books="commendBookList"></subject-nav>
		<!-- <subject-nav v-bind:subject="discountSubject"></subject-nav> -->
		<subject-nav v-bind:subject="newSubject" :books="newBookList"></subject-nav>
	</div>
</template>

<script>
	import Banner from '@/components/Banner'
	import SubjectNav from '@/components/SubjectNav'
	import {getSearchBook,getBookList} from './api/api.js'

	export default {
		name: 'home',
		data () {
			return {
				commendSubject: '热门图书',
				discountSubject: '特价图书',
				newSubject: '新书上架',
				searchName: '',
				search:'',
				total: 0,
				offset: 0,
				sort: '+book_id',
				limit: 6,
				commendBookList: [],
				newBookList: []
			}
		},
		mounted: function() {
			this.$nextTick(function() {
				this.initBooksData();
				let param = {
					searchName: '',
					search: '',
					offset: this.offset,
					sort: this.sort,
					limit: this.limit
				}
				getSearchBook(param).then((res)=>{
					console.log(res.data)
					this.commendBookList = res.data.rows
				})
				this.sort = '-book_date'
				let param2 = {
					searchName: '',
					search: '',
					offset: this.offset,
					sort: this.sort,
					limit: this.limit
				}
				getSearchBook(param2).then((res)=>{
					console.log("newBookList:")
					this.newBookList = res.data.rows
					console.log(this.newBookList)
				})
			});
		},
		methods: {
			initBooksData() {
				this.$store.dispatch('setCategoryList', '/api/type/getTypeList');
				this.$store.dispatch('setDiscountBooks', 'static/data/books/discount-books.json');
				this.$store.dispatch('setNewBooks', 'static/data/books/new-books.json');
				this.$store.dispatch('setAllBooks', '/api/book/getBookList');
			}
		},
		components: {
			'banner': Banner,
			'subject-nav': SubjectNav
		},
	}
</script>

<style>

</style>
