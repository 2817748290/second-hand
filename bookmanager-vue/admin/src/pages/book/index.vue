<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.search" placeholder="请输入查询内容"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="books" highlight-current-row v-loading="listLoading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="bookName" label="书名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="author" label="作者" width="100" sortable>
				</el-table-column>
				<el-table-column prop="location" label="图书位置" width="120" sortable>
				</el-table-column>
				<el-table-column prop="type.typeName" label="图书类型" width="120" sortable>
				</el-table-column>
				<el-table-column prop="stateInfo.stateName" label="状态" min-width="100" sortable>
				</el-table-column>
				<el-table-column prop="isbn" label="图书编码" min-width="150" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" width="200">
				<span>
					<el-button  size="small" @click="">查看</el-button>
					<el-button size="small" @click="handleEdit(row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(row)">删除</el-button>
				</span>
				</el-table-column>
				</el-table>
		</template>

<!--分页-->
<el-col :span="24" class="toolbar" style="padding-bottom:10px;">
<el-pagination layout="prev, pager, next" @current-change="" :page-size="20" :total="total" style="float:right;">
</el-pagination>
</el-col>

<!-- 编辑界面-->
<el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
	<el-form :model="editForm" label-width="80px" ref="editForm">
		<el-form-item label="书名" prop="bookName">
			<el-input v-model="editForm.bookName" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="作者" prop="author">
			<el-input v-model="editForm.author" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="图书位置" prop="location">
			<el-input v-model="editForm.location" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="图书类型" prop="type">
			<el-input v-model="editForm.type" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="状态" prop="state">
			<el-input v-model="editForm.state" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="图书编码" prop="isbn">
			<el-input v-model="editForm.isbn" auto-complete="off"></el-input>
		</el-form-item>
		<!-- <el-form-item label="借书人" prop="user.username">
			<el-input v-model="editForm.user.username" auto-complete="off"></el-input>
		</el-form-item> -->
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click.native="editFormVisible = false">取 消</el-button>
		<el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
	</div>
</el-dialog>
</section>
</template>

<script>
	import util from '../../common/util'
	import NProgress from 'nprogress'
	import { getInitBookList, deleteBook, addBook, updateBook, getBookInfoById} from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					searchName: 'book_name',
					search:''
				},
				books: [],
				total: 0,
				offset: 0,
				sort: '+book_id',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				//编辑界面数据
				editForm: {
					id:0,  //0为添加表单 1为修改表单
					// username: '',
					// group: 0,
					// userState:0,
					// points: '',
					// email: ''
				},
				editLoading: false,
				btnEditText: '提 交',
				

			}
		},		
		mounted() {
			this.getBookList();
		},
		methods: {
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

			//删除操作 
			handleDel: function (row) {
				var _this = this;
				this.$confirm('确认删除该记录吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					NProgress.start();
					let para = new FormData();
					para.append('bookId',row.bookId);
					deleteBook(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '删除成功',
							type: 'success'
						});
						_this.getBookList();
					});

				}).catch(() => {

				});
			},

			//编辑 or 新增
			editSubmit: function () {
				var _this = this;

				_this.$refs.editForm.validate((valid) => {
					if (valid) {

						_this.$confirm('确认提交吗？', '提示', {}).then(() => {
							_this.editLoading = true;
							NProgress.start();
							_this.btnEditText = '提交中';

							if (_this.editForm.id == 0) {
								//新增
								let para = {
									bookName: _this.editForm.bookName,
									author: _this.editForm.author,
									imageUrl: _this.editForm.imageUrl,
									location: _this.editForm.location,
									isbn: _this.editForm.isbn,
									typeId: _this.editForm.typeId,
									userId: _this.editForm.userId,
									state: _this.editForm.state
								};
								addBook(para).then((res) => {
									console.log(res.data)
									_this.editLoading = false;
									NProgress.done();
									_this.btnEditText = '提 交';
									_this.$notify({
										title: '成功',
										message: '提交成功',
										type: 'success'
									});
									_this.editFormVisible = false;
									_this.getBookList();
								});
							} else {
								//编辑
								console.log(_this.editForm)
								let para = {
									bookId: _this.editForm.id,
									bookName: _this.editForm.bookName,
									author: _this.editForm.author,
									imageUrl: _this.editForm.imageUrl,
									location: _this.editForm.location,
									isbn: _this.editForm.isbn,
									typeId: _this.editForm.typeId,
									userId: _this.editForm.userId,
									state: _this.editForm.state
								};
								updateBook(para).then((res) => {
									_this.editLoading = false;
									NProgress.done();
									_this.btnEditText = '提 交';
									_this.$notify({
										title: '成功',
										message: '提交成功',
										type: 'success'
									});
									_this.editFormVisible = false;
									_this.getBookList();
								});

							}

						});

					}
				});

			},
			//显示新增界面
			handleAdd: function () {
				var _this = this;

				this.editFormVisible = true;
				this.editFormTtile = '新增';
				this.editForm.id = 0;
				this.editForm.bookName = '';
				// this.editForm.nickname = '';
				// this.editForm.password = '';
				// this.editForm.group = '';
				// this.editForm.points = '';
				// this.editForm.email = '';
			},
			//显示编辑界面
			handleEdit: function (row) {
				console.log(row)
				this.editFormVisible = true;
				this.editFormTtile = '编辑';
				this.editForm.id = row.bookId;
				this.editForm.bookName = row.bookName;
				this.editForm.author = row.author;
				// this.editForm.password = row.password;
				// this.editForm.group = row.group;
				// this.editForm.points = row.points;
				// this.editForm.email = row.email;
			}
		}
		
		
	}
</script>

<style scoped>
</style>