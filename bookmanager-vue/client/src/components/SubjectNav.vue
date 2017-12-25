<template>
  <div id="subject-nav">
    <div class="title">
      <h2> {{ subject }} </h2>
      <div class="more">
        <router-link :to="{name: 'Subjects', params: {subject: subject}}">
          查看更多&gt;&gt;
        </router-link>
      </div>
    </div>
    <div class="books">
      <div class="book" v-for="(book, index) in books" :key="index">
        <div>
          <router-link :to="{name: 'Book', params: {id: book.bookId}}">
            <img class="cover" v-bind:src="'public/'+book.imageUrl">
            <p class="name"> {{ book.bookName }} </p>
          </router-link>
        </div>
        <p>
          <span class="original-price" v-bind:class="{sale: isSale}"> {{ book.author }} </span>
          <!-- <span class="sale-price"> {{ book.author }} </span> -->
          <!-- <button class="buy" @click="addToCart(book)">预约</button> -->
        </p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'subject-nav',
    computed: {
      isSale() {
        if (this.books[0].salePrice) {
          return true;
        } else {
          return false;
        }
      }
    },
    props: [
      'subject',
      'books'    
    ],
    methods: {
      addToCart: function(book) {
        this.$store.dispatch('addToCart', book);
        alert("预约成功！");
      }
    },
    filters: {
      formatPrice(price) {
        return price + '元';
      }
    }
  }
</script>

<style scoped>
	@import '../assets/css/subject-nav.css'
</style>
