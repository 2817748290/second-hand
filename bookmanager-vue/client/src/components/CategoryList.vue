<template>
  <div class="category-list">
    <h2>图书分类</h2>
    <ul>
      <li v-for="(category, index) in categoryList">
        <router-link :to="{name: 'Category', params:{category: category.category}}">
          {{ category.typeName }} 
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'category-list',
    computed: {
      categoryList() {
        return this.$store.getters.getCategoryList;
      }
    },
    mounted: function() {
      this.$nextTick(function() {
					this.$store.dispatch('setCategoryList', '/api/type/getTypeList');
      });
    }
  }
</script>

<style scoped>
  @import '../assets/css/category-list.css'
</style>
