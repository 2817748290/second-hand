<template>
	<div id="category">
		<div class="category-bar">
			<dl>
				<dt>
					<router-link :to="{name: 'Category', params: {category: '所有图书'}}">
						<span>所有图书</span>
					</router-link>
				</dt>
				<dd v-for="category in categoryList">
					<router-link :to="{name: 'Category', params: {category: category.category}}">
						<span class="category-name"> {{ category.category }} </span>
					</router-link>
				</dd>
			</dl>
		</div>
		<section id="books">
			<books v-bind:books="books"></books>
		</section>
	</div>
</template>

<script>
	import Books from './components/Books'

	export default {
		name: 'category',
		components: {
			'books': Books
		},
		computed: {
			categoryList() {
				let categories = this.$store.getters.getCategoryList;
				if (categories.length === 0) {
					this.$store.dispatch('setCategoryList', 'static/data/book-category.json');
				}
				return categories;
			},
			books () {
				var category = this.$route.params.category;
				var allBooks = this.$store.getters.getAllBookList;
				if (category === '所有图书') {
					return allBooks;
				} else {
					let books = allBooks.filter((item, index, array)=>{
						return (item.category === category);
					});
					return books;
				}
				
			}
		}
	}
</script>

<style scoped>
	@import './assets/css/category.css'
</style>