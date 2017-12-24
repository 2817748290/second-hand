<template>
  <div class="category-list">
    <h2>图书分类</h2>
    <ul>
      <li v-for="(item, index) in categoryList">
        <a @click="getSearchBook(item.typeName)">
          {{ item.typeName }} 
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
  import {getTypeList} from '../api/api.js'
  export default {
    name: 'category-list',
    data(){
      return {
        categoryList: []
      }
    },
    computed: {

    },
    mounted: function() {
      this.$nextTick(function() {
        console.log("mounted")
        getTypeList().then(res=>{
          console.log(res.data)
          this.categoryList = res.data.result
        });
      });
    }
  }
</script>

<style scoped>
  @import '../assets/css/category-list.css'
</style>
