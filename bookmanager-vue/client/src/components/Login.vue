<template>
  <div class="login">
    <h2>Hi 欢迎进入在线图书馆</h2>
    <p>登录在线图书馆，借阅你喜欢的书籍</p>
    <a class="login-btn" @click="login"> {{ loginState }} </a>
    <div class="login-ui" v-if="isLogined">
    	<header>
    		<h3>登录</h3>
    		<a href="javascript:;" @click="closeLogin">
				<img src="static/icons/close.png">
			</a>
    	</header>
    	<div class="form">
	    		<input type="text" name="username" placeholder="用户名/手机号/邮箱">
	    		<input type="password" name="password" placeholder="请输入密码">
	    		<a href="javascript:;">忘记密码?</a>
	    		<button class="login-btn" @click="submitLogin">登录</button>
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
	export default {
		name: 'login',
		data() {
			return {
				isLogined: false,
				loginState: '登录'
			}
		},
		methods: {
			login() {
				if (this.loginState == '登录') {
					this.isLogined = true;
					;
				}
			},
			closeLogin() {
				this.isLogined = false;
			},
			submitLogin() {
				this.isLogined = false;
				this.loginState = '已登录';
			}
		}
	}
</script>

<style scoped>
	@import '../assets/css/login.css'
</style>
