<template>
	<div id="subjects">
		<nav id="subjects-nav">
			<ul>
				<li v-for="(subject,index) in subjects" :key="index">
					<router-link :to="{name: 'Subjects', params: {subject: subject}}">
						{{ subject }}
					</router-link>
				</li>
			</ul>
		</nav>
		<section id="subjects-books">
			<h2> {{ title }} </h2>
			<books :bookList="bookList" :total="total" @pageChange="pageChange"></books>
		</section>
	</div>
</template>

<script>
	import Books from './components/Books'
	import {getSearchBook,getBookList} from './api/api.js'

	export default {
		name: 'subjects',
		components: {
			'books': Books
		},
		data () {
			return {
				title: '',
	 			subjects: ['热门图书','新书上架'],
				// subjects: ['热门图书','特价图书','新书上架'],
				searchName: '',
				search:'',
				total: 0,
				offset: 0,
				sort: '+book_id',
				limit: 10,
				bookList: []
			}
		},
		watch: {
			'$route': 'changeSubject'
		},
		mounted(){
			this.$nextTick(() => {
				this.changeSubject()
			});
		},
		methods: {
			changeSubject(){
				var subject = this.$route.params.subject; 
								console.log(subject+'dasdasdas')

				switch(subject) {
					case '热门图书':
						console.log('情况1')
						this.title = '热门图书'
						this.sort = '-book_id'
						var param = {
							searchName: '',
							search: '',
							offset: this.offset,
							sort: this.sort,
							limit: this.limit
						}
						getSearchBook(param).then((res)=>{
							this.bookList = res.data.rows
							this.total = res.data.total
						})
						return 
					case '新书上架':
					console.log('情况2')
						this.title = '新书上架';
						this.sort = '-book_date'
						var param = {
							searchName: '',
							search: '',
							offset: this.offset,
							sort: this.sort,
							limit: this.limit
						}
						getSearchBook(param).then((res)=>{
							this.bookList = res.data.rows
							this.total = res.data.total
						})
						return
				}
			},
			/*
				分页组件传递过来的页数 
			 */
			pageChange (currentPage){
				this.offset = (currentPage-1)*this.limit
				this.getSearchBookList(this.search)
			},
			getSearchBookList (typeName,index) {
				this.search = ''
				let param = {
					searchName: this.searchName,
					search: this.search,
					offset: this.offset,
					sort: this.sort,
					limit: this.limit
				}
				getSearchBook(param).then((res)=>{
					console.log(res.data.rows)
					this.bookList = res.data.rows
					this.total = res.data.total
				})
			}
		}
	}
</script>

<style scoped>
	@import './assets/css/subjects.css'
</style>