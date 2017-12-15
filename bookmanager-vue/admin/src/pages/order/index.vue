<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.search" placeholder="借阅记录号"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.username" placeholder="借阅者用户名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.bookName" placeholder="借阅书籍名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getOrderList">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="orderList" highlight-current-row v-loading="listLoading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="orderId" label="借阅记录编号" width="150" sortable>
				</el-table-column>
				<el-table-column prop="borrower.username" label="用户名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="borrower.nickname" label="昵称" width="120" sortable>
				</el-table-column>
				<el-table-column prop="book.bookName" label="书籍名" width="120">
				</el-table-column>
				<el-table-column prop="status" label="借阅状态" width="120" :formatter="statusFormatter" sortable>
				</el-table-column>
				<el-table-column prop="createDate" label="借书日期" min-width="120" :formatter="dateFormatter" sortable>
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
		<el-form-item label="借阅状态">
			<el-radio-group v-model="editForm.status">
				<el-radio class="radio" :label="0">未还</el-radio>
				<el-radio class="radio" :label="1">已还</el-radio>				
			</el-radio-group>
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
	import moment from 'moment'
	import { getOrderList, getOrderListPage,updateOrder,deleteOrder  } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					//第一个记录
					searchName: 'order_id',
					search:'',
					username: '',
					bookName: ''
				},
				orderList: [],
				total: 0,
				offset: 0,
				sort: '+create_date',
				limit: 20,
				listLoading: false,
				editFormVisible: false,//编辑界面显是否显示
				editFormTtile: '编辑',//编辑界面标题
				//编辑界面数据
				editForm: {
					id:0,  //0为添加表单 1为修改表单
					status: '',
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
				statusArr:[
					{status:0,value:'未还'},
					{status:1,value:'已还'}				
				]

			}
		},		
		mounted() {
			this.getOrderList();
		},
		methods: {
			handleCurrentChange(val) {
				this.offset = (val-1)*20;
				this.getOrderList();
			},
			//获取用户列表
			getOrderList() {
				let para = {
					sort: this.sort,
					offset: this.offset,
					limit: this.limit,
					search: this.filters.search,
					searchName: this.filters.searchName,
					usernameValue: this.filters.username,
					bookNameValue: this.filters.bookName
				};
				this.listLoading = true;
				NProgress.start();
				getOrderListPage(para).then((res) => {
					//this.total = res.data.total;
					console.log('total:'+this.total)
					this.orderList = res.data.rows;
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
					console.log("id:"+row.orderId)
					para.append('orderId',row.orderId);
					deleteOrder(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						if(res.data.status===1){
							_this.$notify({
								title: '成功',
								message: '删除成功',
								type: 'success'
							});
						}else{
							_this.$notify({
								title: '成功',
								message: '删除失败',
								type: 'success'
							});
						}
						_this.editFormVisible = false;
						_this.getOrderList();
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
								addOrder(para).then((res) => {
									_this.listLoading = false;
									NProgress.done();
									if(res.data.status===1){
										_this.$notify({
											title: '成功',
											message: '添加成功',
											type: 'success'
										});
									}else{
										_this.$notify({
											title: '成功',
											message: '添加失败',
											type: 'success'
										});
									}
									_this.editFormVisible = false;
									_this.getOrderList();
								});
							} else {
								//编辑
								let para = {
									orderId: _this.editForm.id,
									status: _this.editForm.status
								};
								updateOrder(para).then((res) => {
									_this.listLoading = false;
									NProgress.done();
									if(res.data.status===1){
										_this.$notify({
											title: '成功',
											message: '修改成功',
											type: 'success'
										});
									}else{
										_this.$notify({
											title: '成功',
											message: '修改失败',
											type: 'success'
										});
									}
									_this.editFormVisible = false;
									_this.getOrderList();
								});

							}

						});

					}
				});

			},
			//显示编辑界面
			handleEdit: function (row) {
				this.editFormVisible = true;
				this.editFormTtile = '编辑';
				this.editForm.id = row.orderId;
				this.editForm.status = row.status;
			},
			dateFormatter(row,column){
            var date = row['createDate'];  
				if (date == undefined) {  
					return "";  
				}  
				return moment(date).format("YYYY-MM-DD HH:mm:ss");  
			},
			statusFormatter(row, column) {
				var s = row['status'];
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