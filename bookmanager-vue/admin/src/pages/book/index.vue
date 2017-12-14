<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.search" placeholder="昵称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="">新增</el-button>
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
				<el-table-column prop="type" label="图书类型" width="120" sortable>
				</el-table-column>
				<el-table-column prop="state" label="状态" min-width="100" sortable>
				</el-table-column>
				<el-table-column prop="isbn" label="图书编码" min-width="150" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" width="200">
				<span>
					<el-button  size="small" @click="">查看</el-button>
					<el-button size="small" @click="">编辑</el-button>
					<el-button type="danger" size="small" @click="">删除</el-button>
				</span>
				</el-table-column>
				</el-table>
		</template>

<!--分页-->
<el-col :span="24" class="toolbar" style="padding-bottom:10px;">
<el-pagination layout="prev, pager, next" @current-change="" :page-size="20" :total="total" style="float:right;">
</el-pagination>
</el-col>

<!--编辑界面-->
<!-- <el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
	<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
		<el-form-item label="用户名" prop="username">
			<el-input v-model="editForm.username" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="昵称" prop="nickname">
			<el-input v-model="editForm.nickname" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="密码" prop="password">
			<el-input type="password" v-model="editForm.password" auto-complete="off"></el-input>
		</el-form-item>
		<el-form-item label="用户组">
			<el-radio-group v-model="editForm.group">
				<el-radio class="radio" :label="0">超级管理员</el-radio>
				<el-radio class="radio" :label="1">普通管理员</el-radio>
				<el-radio class="radio" :label="2">普通用户</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="用户状态">
			<el-radio-group v-model="editForm.userState">
				<el-radio class="radio" :label="0">离线</el-radio>
				<el-radio class="radio" :label="1">在线</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="积分">
			<el-input-number v-model="editForm.points" :min="0" :max="200"></el-input-number>
		</el-form-item>
		<el-form-item label="邮箱">
			<el-input v-model="editForm.email"></el-input>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click.native="editFormVisible = false">取 消</el-button>
		<el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
	</div>
</el-dialog> -->
</section>
</template>

<script>
	import util from '../../common/util'
	import NProgress from 'nprogress'
	import { getInitBookList} from '../../api/api';

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
				sort: 'book_id',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				//编辑界面数据
				editForm: {
					id:0,  //0为添加表单 1为修改表单
					username: '',
					group: 0,
					userState:0,
					points: '',
					email: ''
				},
				editLoading: false,
				btnEditText: '提 交',
				

			}
		},		
		mounted() {
			this.getBookList();
		},
		methods: {
			
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
			}
		}
		
	}
</script>

<style scoped>
</style>