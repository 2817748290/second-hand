<template>
	<div class="slider">
		<div class="slider-container">
      <div class="wraper" v-bind:style="{left: wraperLeft + 'px'}">
        <div class="slide" v-for="slide in slides">
          <a href="javascript:;">
            <img v-bind:src="slide.img">
          </a>
        </div>
      </div>
      <div class="pagination">
        <span v-bind:class="{active: pagination.isActived}" v-for="(pagination, index) in paginations" @click="switchPagination(index)"></span>
      </div>
      <div class="button button-prev" @click="switchSlide(702)">
        <a href="javascript:;">&lt;</a>
      </div>
      <div class="button button-next" @click="switchSlide(-702)">
        <a href="javascript:;">&gt;</a>
      </div>
    </div>
	</div>
</template>

<script>
	import axios from 'axios'

	export default {
		name: 'slider',
		data () {
      return {
        wraperLeft: -702,
        slides: []
      }
    },
    computed: {
      paginations: function() {
        var pagis = [];
        var slideAmount = this.slides.length - 2;
        for (var i = 0; i < slideAmount; i++) {
          pagis.push({'isActived': false});
        }
        return pagis;
      }
    },
    mounted: function() {
      this.$nextTick(function() {
        this.initSlides('static/data/slides.json');
        this.autoPlay(3000);
      });
    },
    methods: {
      initSlides: function(url) {
        axios.get(url)
          .then(response=>{
            let data = response.data.slides;
            data.unshift(data[data.length-1]);
            data.push(data[1]);
            this.slides = data;
          })
          .catch(error=>{
            console.log(error);
          });
      },
      switchSlide: function(offset) {
        var newLeft = this.wraperLeft + offset;
        var slideIndex = 0;
        if (newLeft > -702) {
          this.wraperLeft = -2808;
          slideIndex = 3;
        } else if (newLeft < -2808) {
          this.wraperLeft = -702;
          slideIndex = 0;
        } else {
          this.wraperLeft = newLeft;
          slideIndex = newLeft/(-702) - 1;
        }
        this.switchPagination(slideIndex);
      },
      switchPagination: function(slideIndex) {
        let offset = (slideIndex + 1) * (-702);
        this.wraperLeft = offset;
        var pagis = this.paginations;
        for (var i = 0; i < pagis.length; i++) {
          pagis[i].isActived = false;
        }
        this.paginations[slideIndex].isActived = true;
      },
      autoPlay: function(interval) {
        window.setInterval(() => {
          this.switchSlide(-702);
        }, interval);
      }
    }
	}
</script>

<style scoped>
	@import '../assets/css/slider.css'
</style>