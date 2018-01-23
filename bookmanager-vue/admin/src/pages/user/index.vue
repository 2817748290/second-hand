<template>

	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-select v-model="filters.groupValue" placeholder="请选择">
					<el-option
					v-for="item in groups"
					:key="item.group"
					:label="item.value"
					:value="item.group">
					</el-option>
				</el-select>
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
				<el-table-column prop="group" label="用户组" width="120" :formatter="groupFormatter" sortable>
				</el-table-column>
				<el-table-column prop="userState" label="状态" width="100" :formatter="statusFormatter" sortable>
				</el-table-column>
				<el-table-column prop="email" label="邮箱" width="180" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" min-width="180">
				<span>
					<el-button size="small" @click="handleQuery(row)">查看</el-button>
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
			<el-input v-model="editForm.username" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="昵称" prop="nickname">
			<el-input v-model="editForm.nickname" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="密码" prop="password">
			<el-input type="password" v-model="editForm.password" auto-complete="off" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
		<el-form-item label="头像" prop="avatarImage" v-bind:disabled="disabledChange">
			<img v-show="editForm.avatarImage" :src="editForm.avatarImage"  width="200px" height="200px"><br>
  			<el-button type="primary" v-show="btnChooseShow" @click="choose" style="margin-top:1%">选择用户头像</el-button>
		</el-form-item>
		<el-form-item label="用户组">
			<el-radio-group v-model="editForm.group" v-bind:disabled="disabledChange">
				<el-radio :label="0">超级管理员</el-radio>
				<el-radio :label="1">普通管理员</el-radio>
				<el-radio :label="2">普通用户</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="用户状态">
			<el-radio-group v-model="editForm.userState" v-bind:disabled="disabledChange">
				<el-radio :label="0">审核中</el-radio>
				<el-radio :label="1">通过</el-radio>				
				<el-radio :label="2">封禁</el-radio>
			</el-radio-group>
		</el-form-item>
		
		<el-form-item label="积分"> 
			<el-input-number v-model="editForm.points" :min="0" :max="200"  v-bind:disabled="disabledChange"></el-input-number>
		</el-form-item>
		<el-form-item label="邮箱" prop="email">
			<el-input v-model="editForm.email" v-bind:disabled="disabledChange"></el-input>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click.native="editFormVisible = false">取 消</el-button>
		<el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
	</div>
</el-dialog>
<el-dialog
	title="选择用户头像"
	:visible.sync="dialogVisible"
	width="1000"
	>
	<cropper ref="c" :img="this.editForm.avatarImage" @transfer="transfer" @isModelShow="isModelShow"></cropper>
	<span slot="footer" class="dialog-footer">
		<el-button id="model-close" type="primary" @click="dialogVisible = false" style="display:none">确 定</el-button>
	</span>
</el-dialog>
</section>
</template>

<script>
	import util from '../../common/util'
	import NProgress from 'nprogress'
	import { getUserListPage, removeUser, updateUser, addUser } from '../../api/api';
	import cropper from './cropper'

	export default {
		data() {
			return {
				model: false,
				modelSrc: '',
				crap: false,
				downImg: '#',
				filters: {
					searchName: 'nickname',
					search:'',
					groupValue: ''
				},
				users: [],
				total: 0,
				offset: 0,
				sort: '+user_id',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				disabledChange: false,
				//编辑界面数据
				editForm: {
					id:0,  //0为添加表单 1为修改表单
					username: '',
					nickname: '',
					password: '',
					avatarImage: '',
					group: 0,
					userState: 0,
					points: '',
					email: '',
					isCropperShow: false
				},
				editLoading: false,
				btnEditText: '提 交',
				dialogVisible: false,
				btnChooseShow: false,
				editFormRules: {
					username: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
					],
					nickname: [
						{ required: true, message: '请输入昵称', trigger: 'blur' }
					],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur' }
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
				],
				options: [{
					value: 'nickname',
					label: '昵称'
					}, {
					value: 'username',
					label: '账号'
					}, {
					value: 'points',
					label: '积分'
					}],

			}
		},	
		components: {
			cropper
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
					groupValue: this.filters.groupValue
				};
				this.listLoading = true;
				NProgress.start();
				getUserListPage(para).then((res) => {
					this.total = res.data.total;
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
								if(_this.editForm.avatarImage===''){

									_this.$message.error('您没有选择头像哦!');
									_this.editLoading = false;
									_this.btnEditText = '提 交';
								}else{
									
									let para = new FormData();
									para.append('username', _this.editForm.username);
									para.append('nickname', _this.editForm.nickname);
									para.append('password', _this.editForm.password);
									para.append('group', _this.editForm.group);
									para.append('userState', _this.editForm.userState);
									para.append('points', _this.editForm.points);
									para.append('email', _this.editForm.email);
									this.$refs.c.$refs.cropper.getCropBlob((data) => {
										// do something
										para.append('avatarImage',data);
										console.log(data)  
										addUser(para).then((res) => {
											console.log(res.data)
											_this.editLoading = false;
											NProgress.done();
											_this.btnEditText = '提 交';
											if(res.data.status === 1){
												_this.$notify({
													title: '成功',
													message: '添加成功',
													type: 'success'
												});
											}else{
												_this.$notify({
													title: '失败',
													message: '添加失败',
													type: 'error'
												});
											}
											_this.editFormVisible = false;
											_this.getUsers();
										})
									});
								}
								
							} else {
								//编辑
								if(_this.editForm.avatarImage===''){
									_this.$message.error('您没有选择头像哦!');
									_this.editLoading = false;
									_this.btnEditText = '提 交';
								}else{
									
									let para = new FormData();
									para.append('userId', _this.editForm.id);
									para.append('username', _this.editForm.username);
									para.append('nickname', _this.editForm.nickname);
									para.append('password', _this.editForm.password);
									para.append('group', _this.editForm.group);
									para.append('userState', _this.editForm.userState);
									para.append('points', _this.editForm.points);
									para.append('email', _this.editForm.email);
									console.log('this.$refs.c：')
									console.log(this.$refs.c)
									console.log(this.$refs.c2)
									//判断cropper是否选择过图片
									// todo:重复代码需要优化
									if(this.$refs.c===undefined){
										para.append('avatarImage',this.editForm.avatarImage);
										updateUser(para).then((res) => {
											console.log(res.data)
											_this.editLoading = false;
											NProgress.done();
											_this.btnEditText = '提 交';
											if(res.data.status === 1){
												_this.$notify({
													title: '成功',
													message: '修改成功',
													type: 'success'
												});
												_this.getUsers();
											}else{
												_this.$notify({
													title: '失败',
													message: '修改失败',
													type: 'error'
												});
											}
											_this.editFormVisible = false;
										})

									}else{

										this.$refs.c.$refs.cropper.getCropBlob((data) => {
											para.append('avatarImage',data);
											//console.log('data1111111:')
											updateUser(para).then((res) => {
												console.log(res.data)
												_this.editLoading = false;
												NProgress.done();
												_this.btnEditText = '提 交';
												if(res.data.status === 1){
													_this.$notify({
														title: '成功',
														message: '修改成功',
														type: 'success'
													});
													_this.getUsers();
												}else{
													_this.$notify({
														title: '失败',
														message: '修改失败',
														type: 'error'
													});
												}
												_this.editFormVisible = false;
											})
										});
									}
									
								}
							}

						});

					}
				});

			},
			//显示查看界面
			handleQuery: function (row) {
				this.disabledChange = true;
				this.editFormVisible = true;
				this.editFormTtile = '查看';
				this.editForm.id = row.userId;
				this.editForm.username = row.username;
				this.editForm.nickname = row.nickname;
				this.editForm.password = row.password;
				this.editForm.group = row.group;
				this.editForm.userState = row.userState;
				this.editForm.points = row.points;
				this.editForm.email = row.email;
				this.editForm.isCropperShow = true;
				this.editForm.avatarImage = `public/${row.avatarImage}`;
				this.btnEditText = '确 定'
				this.btnChooseShow = false
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
				this.editForm.avatarImage = '';
				this.editForm.group = 0;
				this.editForm.userState = 0;
				this.editForm.points = '';
				this.editForm.email = '';
				this.editForm.isCropperShow = false;
				this.editForm.img = ''
				this.btnChooseShow = true
			},
			//显示编辑界面
			handleEdit: function (row) {
				this.disabledChange = false;
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
				this.editForm.isCropperShow = true;
				this.editForm.avatarImage = `public/${row.avatarImage}`;
				this.btnChooseShow = true
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
			},
			handlePreview(){}
			,
			handleRemove(){},
			handleExceed(){},
			choose(){
				this.dialogVisible = true
				if(this.$refs.c !== undefined){
					console.log(this.$refs.c)
					console.log("dhudashdjas")
					this.$refs.c.$refs.cropper.clearCrop()
				}
			},
			transfer (...data) {
				this.editForm.avatarImage = data[0]
			},
			isModelShow (...data) {
				this.dialogVisible = data[0]
			}
		}
	}
</script>

<style scoped>
	.content {
		margin: auto;
		max-width: 1200px;
		margin-bottom: 0px;
	}
	.show-info{
		margin-bottom: 5px;
	}
</style>