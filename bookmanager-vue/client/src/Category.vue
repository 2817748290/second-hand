<template>
	<div id="category">
		<div class="category-bar">
			<dl>
				<dd :class="{'active':activeArr[0]}">
					<a @click="getSearchBookList('',0)">
						<span>所有图书</span>
					</a>
				</dd>
				<dd :class="{'active':activeArr[index+1]}" v-for="(item,index) in categoryList">
					<a @click="getSearchBookList(item.typeName,index)">
						<span class="category-name"> {{ item.typeName }} </span>
					</a>
				</dd>
			</dl>
		</div>
		<section id="books">
			<books :total="this.total" :bookList="this.bookList" @pageChange="pageChange"></books>
		</section>
	</div>
</template>

<script>
	import Books from './components/Books'
	import {getSearchBook,getBookList,getTypeList} from './api/api.js'
	export default {
		name: 'category',
		components: {
			'books': Books,
		},
		data () {
			return {
				searchName: 'type_name',
				search:'',
				bookList: [],
				total: 0,
				offset: 0,
				sort: '+book_id',
				limit: 10,
				categoryList: [],
				activeArr: []
			}
		},
		mounted(){
			this.$nextTick(()=>{
				this.getCategoryList();
				this.getSearchBookList('',0);
			})
		},
		methods: {
			/*
				分页组件传递过来的页数 
			 */
			pageChange (currentPage){
				this.offset = (currentPage-1)*this.limit
				this.getSearchBookList(this.search)
			},
			getCategoryList() {
				getTypeList().then(res=>{
					this.categoryList = res.data.result
					this.activeArr.push(false)//所有图书
					for(let item of this.categoryList){
						this.activeArr.push(false)
					}
				});
			},
			getSearchBookList (typeName,index) {
				//类别部分点击背景颜色控制
				for(let i in this.activeArr){
					this.activeArr[i] = false
				}
				typeName==='' ? this.activeArr[index] = true 
				:this.activeArr[index+1] = true
				this.search = typeName
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
	@import './assets/css/category.css'
</style>