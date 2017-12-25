<template>
	<div id="page-header">
		<h1>
			<a href="#" class="logo">
				<img src="../../static/icons/logo.png">
				<span>在线图书馆</span>
			</a>
		</h1>
		<div class="search">
			<input type="search" v-model="keyword" @input="searchBooks(keyword)"size="70" placeholder="书名、作者">
			<ul class="matching-books" v-show="isShow">
				<li v-for="book in matchingBooks" @click="hideMatchingBooks" >
					<router-link :to="{name: 'Book', params: {id: book.id}}">
						<img v-bind:src="book.cover">
						<div class="book-info">
							<p> {{ book.name }} </p>
							<p> {{ book.author }} </p>
						</div>
					</router-link>
				</li>
			</ul>
		</div>
		<div class="login">
			<span v-show="username">{{'您好,'+username}}</span>
    		<a class="login-btn" @click="loginInit"> {{ loginState }} </a>
		</div>
		<div class="login-ui" v-if="isLogined">
			<header>
				<h3>登录</h3>
				<a href="javascript:;" @click="closeLogin">
					<img src="static/icons/close.png">
				</a>
			</header>
			<div class="form">
					<input type="text" name="username" v-model="user.username" placeholder="用户名/手机号/邮箱">
					<input type="password" name="password" v-model="user.password" placeholder="请输入密码">
					<a href="javascript:;">忘记密码?</a>
					<button class="login-btn" @click="handleSubmit">登录</button>
			</div>
			<footer>
				<p>
					<a href="javascript:;">没有账号？免费注册 &gt;</a>
				</p>
			</footer>
    	</div>
	</div>
</template>

<script>
	import {requestLogin} from '../api/api.js'

	export default {
		name: 'page-header',
		data() {
			return {
				keyword: '',
				matchingBooks: [],
				isLogined: false,
				loginState: '登录',
				user:{
				},
				username:''
			}
		},
		computed: {
			isShow() {
				if (this.matchingBooks.length > 0) {
					return true;
				} else {
					return false;
				}
			}
		},
		mounted() {
			let user = JSON.parse(localStorage.getItem('user'));
			if(user!==null && user.userId!=='' && user.userId!==undefined){
				this.loginState = '退出'
			}else{
				this.loginState = '登录'
			}
		},
		methods: {
			searchBooks(keyword) {
				if (keyword == '' || keyword == undefined) {
					this.matchingBooks = [];
					return;
				}
				let lowerCase = keyword.toLowerCase();
				let allBooks = this.$store.getters.getAllBookList;
				this.matchingBooks = allBooks.filter((item, index, array)=>{
					return item.name.toLowerCase().includes(lowerCase) || item.author.toLowerCase().includes(lowerCase);
				});
			},
			closeLogin() {
				this.isLogined = false;
			},
			loginInit(){
				if(this.loginState==='退出'){
					this.username = ''
					localStorage.removeItem('user');
					this.loginState = '登录'
					return;
				}
				this.isLogined = true;
			},
			handleSubmit() {
				let param = {
					'username': this.user.username,
					'password': this.user.password
				}
				requestLogin(param).then((data)=>{
					console.log(data)
					if(data.status===1){
						this.username = this.user.username
						this.isLogined = false;
						localStorage.setItem('user',JSON.stringify({'userId':data.result,'username':this.user.username}))
						this.loginState = `退出`;
					}else{
						alert('帐号或密码错误!')
					}
				})
			}
		}
	}
</script>

<style scoped>
	@import '../assets/css/page-header.css';
	@import '../assets/css/login.css';
	#page-header .login{
		height: 100%;
		background: none;
	}
</style>
