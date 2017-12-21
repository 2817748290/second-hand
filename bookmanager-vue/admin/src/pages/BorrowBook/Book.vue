<template>
  <el-card>
		<img :src="editForm.avatarImage" width="180px" height="240px" class="image">
		<div style="padding: 14px;">
			<span>{{book.bookName}}</span>
			<div class="bottom clearfix">
				<time class="time">{{book.createDate}}</time>
				<p>该书无法借阅</p>
				<el-button 
					class="button"
					type = "primary"
				>借阅</el-button>
			</div>
		</div>
	</el-card>
</template>
<script>
	export default {
		// 组件名
		name: 'Book',
		// 从父组件传过来的属性
		props: ['book']
	}
</script>