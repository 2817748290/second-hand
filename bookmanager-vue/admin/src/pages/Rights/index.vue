<template>
	<el-row :gutter="20">
		<el-col :span="24" v-for="right in rights">
  			<el-card>
				<div style="padding: 10px;margin-left:40%">
					<span style="margin-right:55px"><strong>申述标题：</strong></span>{{right.rightTitle}}<br>
					<span style="margin-right:55px"><strong>申述类型：</strong></span>{{typeFormatter(right.rightTypeId)}}</span><br>
					<span style="margin-right:55px"><strong>申述内容：</strong></span>{{right.rightContent}}</span><br>
					<span style="margin-right:70px"><strong>申述人：</strong></span>{{right.order.borrower.nickname}}</span><br>
					<span style="margin-right:55px"><strong>申述日期：</strong></span>{{dateFormatter(right.rightDate)}}</span><br>
					<div class="bottom clearfix">
						<span  style="margin-right:55px"><strong>申述状态：</strong></span>{{stateFormatter(right.rightStateId)}}<br>
						<el-button 
							class="button"
							type = "primary"
							style="margin-left:3%;margin-top:2%;"
							@click="handlePass(right.rightId)"
						>通过</el-button>
						<el-button 
							class="button"
							type = "danger"
							style="margin-left:2%;margin-top:2%;"
							@click="handleRefuse(right.rightId)"
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
	import moment from 'moment'
	import {getInitRights, deleteRight, addRight, updateRight, getRightInfoById } from '../../api/api';

	export default {
		 components:{
			
		},
		data() {
			return {
				dialogVisible:false,     //模态框是否显示
    			addLoading: false,       //是否显示loading
				disabledChange: false,
				filters: {
					searchName: 'right_title',
					search:''
				},
				rights: [],
				total: 0,
				offset: 0,
				sort: '+right_id',
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
			this.getRights();
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
			// 初始化列表
			getRights(){
				let para = {
					sort: this.sort,
					offset: this.offset,
					limit: this.limit,
					search: this.filters.search,
					searchName: this.filters.searchName,
				};
				this.listLoading = true;
				NProgress.start();
				getInitRights(para).then((res) => {
					this.total = res.data.total;
					this.rights = res.data.rows;
					this.listLoading = false;
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
						rightId: value,
						rightStateId: 1
					};
					updateRight(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '操作成功',
							type: 'success'
						});
						_this.getRights();
					});

				}).catch(() => {

				});
			},

			//否决操作 
			handleRefuse: function (value) {
				var _this = this;
				this.$confirm('确认要通过该记录吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					NProgress.start();
					let para = {
						rightId: value,
						rightStateId: 2
					};
					updateRight(para).then((res) => {
						_this.listLoading = false;
						NProgress.done();
						_this.$notify({
							title: '成功',
							message: '操作成功',
							type: 'success'
						});
						_this.getRights();
					});

				}).catch(() => {

				});
			},

			dateFormatter(value){
				
            	return moment(value).format("YYYY-MM-DD HH:mm:ss");  
			},

			typeFormatter(value){
				if(value==0){
					return "还书异常";
				}else if(value==1){
					return "借阅记录异常";
				}else {
					return "其他异常";
				}
			},

			stateFormatter(value){
            	if(value==0){
					return "审核中";
				}else if(value==1){
					return "通过";
				}else if(value==2){
					return "否决";
				}
			},

			
		}
		
		
	}
</script>

<style scoped>
</style>
</script>