<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-select v-model="filters.searchName" placeholder="请选择">
					<el-option
					v-for="item in options"
					:key="item.value"
					:label="item.label"
					:value="item.value">
					</el-option>
				</el-select>
				<el-form-item>
					<el-input v-model="filters.search" placeholder="请输入查询内容"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getBookList">查询</el-button>
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
				<el-table-column prop="bookName" label="书名" width="180" sortable>
				</el-table-column>
				<el-table-column prop="author" label="作者" width="120" sortable>
				</el-table-column>
				<el-table-column prop="location" label="图书位置" width="150" sortable>
				</el-table-column>
				<el-table-column prop="type.typeName" label="图书类型" width="150" sortable>
				</el-table-column>
				<el-table-column prop="stateInfo.stateName" label="状态" width="120" sortable>
				</el-table-column>
				<el-table-column prop="isbn" label="图书编码" width="180" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" min-width="200">
				<span>
					<el-button size="small" @click="handleLook(row)">查看</el-button>
					<el-button size="small" @click="handleEdit(row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(row)">删除</el-button>
				</span>
				</el-table-column>
				</el-table>
		</template>

<!--分页-->
<el-col :span="24" class="toolbar" style="padding-bottom:10px;">
<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
</el-pagination>
</el-col>

 <!-- 图片上传 -->

<!-- 编辑界面-->
<el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
	<el-form :model="editForm" label-width="80px" ref="editForm">
		<el-form-item label="图书名称" prop="bookName">
			<el-input v-model="editForm.bookName" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="图书作者" prop="author">
			<el-input v-model="editForm.author" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="图书位置" prop="location">
			<el-input v-model="editForm.location" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="图书类型" prop="typeId">
			<el-select v-model="editForm.typeId" placeholder="请选择" v-bind:disabled="disabledChange">
				<el-option
				v-for="booktype in booktypes"
				:key="booktype.typeId"
				:label="booktype.typeName"
				:value="booktype.typeId">
				</el-option>
			</el-select>
		</el-form-item>
		<el-form-item label="图书封面" prop="imageUrl">
			<img v-bind:src="'/public' + this.editForm.imageUrl"  onerror='this.src="../../../static/default.png"'><br>
  			<el-button type="primary" @click="dialogVisible = true" style="margin-top:1%" >选择图书封面</el-button>
		</el-form-item>
		<el-form-item label="图书状态">
			<el-radio-group v-model="editForm.state" v-bind:disabled="disabledChange">
				<el-radio :label="0">可借</el-radio>
				<el-radio :label="1">已借出</el-radio>
				<el-radio :label="2">已销毁</el-radio>
			</el-radio-group>
		</el-form-item>

		<el-form-item label="图书编码" prop="isbn">
			<el-input v-model="editForm.isbn" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click.native="editFormVisible = false">取 消</el-button>
		<el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
	</div>
</el-dialog>

<el-dialog
	title="选择图书封面"
	:visible.sync="dialogVisible"
	width="1000"
	>
	<cropper @transfer="transfer" @isModelShow="isModelShow" style="margin: 1% auto auto auto"></cropper>
	<span slot="footer" class="dialog-footer">
		<el-button id="model-close" type="primary" @click="dialogVisible = false" style="display:none">确 定</el-button>
	</span>
</el-dialog>
</section>
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
					searchName: '',
					search:''
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
				options: [{
					value: 'book_name',
					label: '图书名称'
					}, {
					value: 'author',
					label: '作者姓名'
					}, {
					value: 'location',
					label: '图书位置'
					}, {
					value: 'type_Id',
					label: '图书类型'
					}, {
					value: 'isbn',
					label: '国际标准书号(ISBN)'
					}, {
					value: 'state',
					label: '图书状态'
					}],

			}
		},		
		mounted() {
			this.getBookList();
			this.getTypes();
		},
		methods: {

			opendialog:function(){    //代开模态框
			this.dialogVisible = false
			},

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
					console.log(this.books)
					this.listLoading = false;
					NProgress.done();
				});
			},

			//换页
			handleCurrentChange(val) {
				this.offset = (val-1)*20;
				this.getBookList();
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

							if (_this.editForm.opState == 0) {
								//新增
								let para = {
									bookName: _this.editForm.bookName,
									author: _this.editForm.author,
									imageUrl: _this.editForm.imageUrl,
									location: _this.editForm.location,
									isbn: _this.editForm.isbn,
									typeId: _this.editForm.typeId,
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
							} else if (_this.editForm.opState == 1){
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

							} else if (_this.editForm.opState == 2){
								//查看
								_this.editFormVisible = false;
								_this.getBookList();
							}

						});

					}
				});

			},
			//显示新增界面
			handleAdd: function () {
				var _this = this;
				this.disabledChange = false;
				this.editFormVisible = true;
				this.editFormTtile = '新增';
				this.editForm.opState = 0;
				this.editForm.id = 0;
				this.editForm.bookName = '';
				this.editForm.author = '';
				this.editForm.imageUrl = '';
				this.editForm.location = '';
				this.editForm.isbn = '';
				this.editForm.typeId = '';
				this.editForm.state = 0;
			},
			//显示编辑界面
			handleEdit: function (row) {
				console.log(row)
				this.disabledChange = false;
				this.editFormVisible = true;
				this.editFormTtile = '编辑';
				this.editForm.opState = 1;
				this.editForm.id = row.bookId;
				this.editForm.bookName = row.bookName;
				this.editForm.author = row.author;
				this.editForm.imageUrl = row.imageUrl;
				this.editForm.location = row.location;
				this.editForm.isbn = row.isbn;
				this.editForm.typeId = row.typeId;
				this.editForm.state = row.state;
			},
			//显示查看界面
			handleLook: function (row) {
				console.log(row)
				this.disabledChange = true;
				this.editFormVisible = true;
				this.editFormTtile = '查看';
				this.editForm.opState = 2;
				this.editForm.id = row.bookId;
				this.editForm.bookName = row.bookName;
				this.editForm.author = row.author;
				this.editForm.imageUrl = row.imageUrl;
				this.editForm.location = row.location;
				this.editForm.isbn = row.isbn;
				this.editForm.typeId = row.typeId;
				this.editForm.state = row.state;
				this.btnEditText = '确 定'
			}
		}
		
		
	}
</script>

<style scoped>
</style>