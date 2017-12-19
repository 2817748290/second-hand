<template>
	<div class="banner">
		<category-list></category-list>
		<slider></slider>
		<login></login>
	</div>
</template>

<script>
	import CategoryList from './CategoryList'
	import Slider from './Slider'
	import Login from './Login'

	export default {
	  name: 'banner',
	  components: {
		  'category-list': CategoryList,
	  	'slider': Slider,
	  	'login': Login
	  }
	}
</script>

<style scoped>
	@import '../assets/css/banner.css'
</style>