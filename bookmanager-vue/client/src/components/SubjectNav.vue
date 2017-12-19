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
      <div class="book" v-for="(book, index) in books">
        <div>
          <router-link :to="{name: 'Book', params: {id: book.id}}">
            <img class="cover" v-bind:src="book.cover">
            <p class="name"> {{ book.name }} </p>
          </router-link>
        </div>
        <p>
          <span class="original-price" v-bind:class="{sale: isSale}"> {{ book.price | formatPrice }} </span>
          <span class="sale-price"> {{ book.salePrice }} </span>
          <button class="buy" @click="addToCart(book)">购买</button>
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
