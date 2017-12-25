<template>
	<el-row :gutter="20">
		<el-col :span="6" v-for="book in books">
  			<el-card>
				<img :src="'/public' + book.imageUrl" width="180px" height="240px" class="image" onerror='this.src="../../../static/default.png"' style="margin-left:15%">
				<div style="padding: 10px;margin-left:15%">
					<span><strong>图书名称：</strong>{{book.bookName}}</span><br>
					<span><strong>图书作者：</strong>{{book.author}}</span>
					<div class="bottom clearfix">
						<time class="time">{{book.createDate}}</time>
						<span><strong>图书状态：</strong>{{book.stateInfo.stateName}}</span><br>
						<el-button
							class="button"
							type = "primary"
							style="margin-left:8%;margin-top:2%;"
							@click="handlePass(book.bookId)"
						>通过</el-button>
						<el-button
							class="button"
							type = "danger"
							style="margin-left:2%;margin-top:2%;"
							@click="handleRefuse(book.bookId)"
						>否决</el-button>
					</div>
				</div>
			</el-card>		
		</el-col>
	</el-row>
</template>

<script>
	import util from '../../common/util'
	import NProgress from 'nprogress'
	import cropper from '../book/cropper'
	import { getInitBookList, deleteBook, addBook, updateBook, getBookInfoById, getTypeList } from '../../api/api';
	import $ from '../../../static/jquery.min.js'

	export default {
		 components:{
			cropper
		},
		data() {
			return {
				dialogVisible:false,     //模态框是否显示
    			addLoading: false,       //是否显示loading
				disabledChange: false,
				filters: {
					searchName: 'state_name',
					search:'借出审核中'
				},
				books: [],
				booktypes:[],
				total: 0,
				offset: 0,
				sort: '+book_id',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				//编辑界面数据
				editForm: {
					opState:0,
					id:0,  //0为添加表单 1为修改表单
					bookName:'',
					author:'',
					imageUrl:'',
					location:'',
					typeId:0,
					state:-1,
				},
				editLoading: false,
				btnEditText: '提 交',


			}
		},		
		mounted() {
			this.getBookList();
			this.getTypes();
		},
		methods: {

			isModelShow (...data) {
			this.isShow = data[0]
			if(!data[0]){
				$('#model-close').click()
			}
			console.log(data[0])
			},
			transfer (...data) {
			this.editForm.imageUrl = data[0]
			},
			// 初始化图书列表
			getBookList(){
				let para = {
					sort: this.sort,
					offset: this.offset,
					limit: this.limit,
					search: this.filters.search,
					searchName: this.filters.searchName,
				};
				this.listLoading = true;
				NProgress.start();
				getInitBookList(para).then((res) => {
					this.total = res.data.total;
					this.books = res.data.rows;
					this.listLoading = false;
					NProgress.done();
				});
			},

			//动态获取图书类型
			getTypes(){
				let para = {};
				this.listLoading = true;
				NProgress.start();
				getTypeList(para).then((res) => {
					this.listLoading = false;
					this.booktypes = res.data.result;
					console.log(this.booktypes)
					NProgress.done();
				});
			},

			//通过操作
			handlePass: function (value) {
				var _this = this;
				this.$confirm('确认要通过该记录吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					NProgress.start();
					let para = {
						bookId: value,
						state: 1
					};
					updateBook(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '操作成功',
							type: 'success'
						});
						_this.getBookList();
					});

				}).catch(() => {

				});
			},

			//否决操作 
			handleRefuse: function (value) {
				var _this = this;
				this.$confirm('确认要否决该记录吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					NProgress.start();
					let para = {
						bookId: value,
						state: 3
					};
					updateBook(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '操作成功',
							type: 'success'
						});
						_this.getBookList();
					});

				}).catch(() => {

				});
			},

		}
		
		
	}
</script>

<style scoped>
</style>
</script>