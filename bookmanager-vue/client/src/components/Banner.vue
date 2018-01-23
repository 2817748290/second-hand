<template>
	<div class="banner">
		<category-list></category-list>
		<slider></slider>
		<welcome></welcome>
	</div>
</template>

<script>
	import CategoryList from './CategoryList'
	import Slider from './Slider'
	import welcome from './Welcome'

	export default {
	  name: 'banner',
	  components: {
		  'category-list': CategoryList,
	  	'slider': Slider,
	  	'welcome': welcome
	  }
	}
</script>

<style scoped>
	@import '../assets/css/banner.css'
</style>