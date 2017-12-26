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
		<div class="login-ui" v-if="isModalShow">
			<header>
				<h3>{{btnText}}</h3>
				<a href="javascript:;" @click="closeLogin">
					<img src="static/icons/close.png">
				</a>
			</header>
			<div class="form">
					<input type="text" name="username" v-model="user.username" placeholder="用户名">
					<input type="password" name="password" v-model="user.password" placeholder="请输入密码">
					<input v-show="!isLoginState" type="password" name="repassword" v-model="user.repassword" placeholder="请再次输入密码">
					<input v-show="!isLoginState" type="text" name="nickname" v-model="user.nickname" placeholder="昵称">
					<input v-show="!isLoginState" type="text" name="email" v-model="user.email" placeholder="邮箱">
					<a href="javascript:;">忘记密码?</a>
					<button class="login-btn" @click="handleSubmit">{{btnText}}</button>
			</div>
			<footer>
				<p>
					<a href="javascript:;" @click="modalInit">{{changeText}} &gt;</a>
				</p>
			</footer>
    	</div>
	</div>
</template>

<script>
	import {requestLogin,addUser} from '../api/api.js'

	export default {
		name: 'page-header',
		data() {
			return {
				keyword: '',
				matchingBooks: [],
				isModalShow: false,
				isLoginState: true,
				loginState: '登录',
				user:{
				},
				username:'',
				changeText: '没有账号？免费注册',
				btnText: '登录'
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
				this.username = user.username
				this.loginState = '退出'
			}else{
				this.loginState = '登录'
			}
		},
		methods: {
			modalInit(){
				if(this.isLoginState){
					this.isLoginState = false
					this.changeText = '已有帐号,前往登录'
					this.btnText = '注册'
				}else{
					this.isLoginState = true
					this.changeText = '没有账号？免费注册'
					this.btnText = '登录'
				}
			},
			closeLogin() {
				this.isModalShow = false;
			},
			loginInit(){
				if(this.loginState==='退出'){
					this.username = ''
					localStorage.removeItem('user');
					this.loginState = '登录'
					return;
				}
				this.isModalShow = true;
			},
			handleSubmit() {
				if(this.btnText === '登录'){

					let param = {
						'username': this.user.username,
						'password': this.user.password
					}
					requestLogin(param).then((data)=>{
						if(data.status===1){
							this.username = this.user.username
							this.isModalShow = false;
							localStorage.setItem('user',JSON.stringify({'userId':data.result,'username':this.user.username}))
							this.loginState = `退出`;
						}else{
							alert('帐号或密码错误!')
						}
					})

				}else if(this.btnText === '注册'){

					let param = new FormData()
					param.append('username', this.user.username)
					param.append('password', this.user.password)
					param.append('nickname', this.user.nickname)
					param.append('email', this.user.email)
					addUser(param).then(res=>{
						console.log('注册信息')
						if(res.data.status===1){
							alert('注册成功')
							this.isLoginState = true
							this.changeText = '没有账号？免费注册'
							this.btnText = '登录'
						}else{
							alert('注册失败')
						}
					})

				}
				
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
