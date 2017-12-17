<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.search" placeholder="昵称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="users" highlight-current-row v-loading="listLoading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="username" label="用户名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="nickname" label="昵称" width="100" sortable>
				</el-table-column>
				<el-table-column prop="points" label="积分" width="100" sortable>
				</el-table-column>
				<el-table-column prop="group" label="用户组" width="120" :formatter="groupFormatter"  >
				</el-table-column>
				<el-table-column prop="userState" label="状态" min-width="180" :formatter="statusFormatter" sortable>
				</el-table-column>
				<el-table-column prop="email" label="邮箱" min-width="180" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" width="150">
				<span>
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

<!--编辑界面-->
<el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
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
				<el-radio class="radio" :label="0">审核中</el-radio>
				<el-radio class="radio" :label="1">通过</el-radio>				
				<el-radio class="radio" :label="2">封禁</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="积分">
			<el-input-number v-model="editForm.points" :min="0" :max="200"></el-input-number>
		</el-form-item>
		<!-- <el-form-item label="生日">
			<el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
		</el-form-item> -->
		<el-form-item label="邮箱">
			<el-input v-model="editForm.email"></el-input>
		</el-form-item>
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
	import { getUserListPage, removeUser, editUser, addUser } from '../../api/api';


	export default {
		data() {
			return {
				filters: {
					searchName: 'nickname',
					search:'a'
				},
				users: [],
				total: 0,
				offset: 0,
				sort: '+user_id',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				//编辑界面数据
				editForm: {
					id:0,  //0为添加表单 1为修改表单
					username: '',
					nickname: '',
					password: '',
					group: 0,
					userState: 0,
					points: '',
					email: ''
				},
				editLoading: false,
				btnEditText: '提 交',
				editFormRules: {
					username: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
					],
					nickname: [
						{ required: true, message: '请输入昵称', trigger: 'blur' }
					],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					]
				},
				groups: [
					{group:0,value:'超级管理员'},
					{group:1,value:'普通管理员'},
					{group:2,value:'普通用户'}
				],
				statusArr:[
					{status:0,value:'审核中'},
					{status:1,value:'通过'},
					{status:2,value:'封禁'}
				]

			}
		},		
		mounted() {
			this.getUsers();
		},
		methods: {
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},
			handleCurrentChange(val) {
			
				this.offset = (val-1)*20;
				this.getUsers();
			},
			//获取用户列表
			getUsers() {
				let para = {
					sort: this.sort,
					offset: this.offset,
					limit: this.limit,
					search: this.filters.search,
					searchName: this.filters.searchName,
				};
				this.listLoading = true;
				NProgress.start();
				getUserListPage(para).then((res) => {
					this.total = res.data.total;
					console.log('total:'+this.total)
					this.users = res.data.rows;
					this.listLoading = false;
					NProgress.done();
				});
			},
			//删除
			handleDel: function (row) {
				var _this = this;
				this.$confirm('确认删除该记录吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					NProgress.start();
					let para = new FormData();
					para.append('userId',row.userId);
					removeUser(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '删除成功',
							type: 'success'
						});
						_this.getUsers();
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
									username: _this.editForm.username,
									nickname: _this.editForm.nickname,
									password: _this.editForm.password,
									group: _this.editForm.group,
									userState: _this.editForm.userState,
									points: _this.editForm.points,
									email: _this.editForm.email
								}
								addUser(para).then((res) => {
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
									_this.getUsers();
								});
							} else {
								//编辑
								console.log(_this.editForm)
								let para = {
									userId: _this.editForm.id,
									username: _this.editForm.username,
									nickname: _this.editForm.nickname,
									password: _this.editForm.password,
									group: _this.editForm.group,
									userState: _this.editForm.userState,
									points: _this.editForm.points,
									email: _this.editForm.email
								};
								editUser(para).then((res) => {
									_this.editLoading = false;
									NProgress.done();
									_this.btnEditText = '提 交';
									_this.$notify({
										title: '成功',
										message: '提交成功',
										type: 'success'
									});
									_this.editFormVisible = false;
									_this.getUsers();
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
				this.editForm.username = '';
				this.editForm.nickname = '';
				this.editForm.password = '';
				this.editForm.group = 0;
				this.editForm.userState = 0;
				this.editForm.points = '';
				this.editForm.email = '';
			},
			//显示编辑界面
			handleEdit: function (row) {
				console.log(row)
				this.editFormVisible = true;
				this.editFormTtile = '编辑';
				this.editForm.id = row.userId;
				this.editForm.username = row.username;
				this.editForm.nickname = row.nickname;
				this.editForm.password = row.password;
				this.editForm.group = row.group;
				this.editForm.userState = row.userState;
				this.editForm.points = row.points;
				this.editForm.email = row.email;
			},
			groupFormatter(row, column) {
				var g = row['group'];
				for(let item of this.groups){
					if(g===item.group){
						return item.value
					}
				}
			},
			statusFormatter(row, column) {
				var s = row['userState'];
				for(let item of this.statusArr){
					if(s===item.status){
						return item.value
					}
				}
			}
		}
		
	}
</script>

<style scoped>
</style>