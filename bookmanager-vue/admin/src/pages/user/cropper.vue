<template>
   <div class="wrapper">
      <div class="model" style="width:100%;height:100%;" v-show="model" @click="model = false">
        <div class="model-show" style="width:100%;height:70%;">
          <img :src="modelSrc" alt="">
        </div>
        <div style="text-align:center;">
          <font size="2" color="#ffffff" face="微软雅黑">✱ 【确定】后将会上传图片为用户【头像】</font><br>
          <button class="btn btn-success" @click="confirm">确认</button>
          <button class="btn btn-info">取消</button>
        </div>
      </div>
      <div class="content">
         <div class="show-info">
            <div class="test test1">
               <vueCropper
                  ref="cropper"
                  :img="option.img"
                  :outputSize="option.size"
                  :outputType="option.outputType"
                  :info="option.info"
                  :canScale="option.canScale"
                  :autoCrop="option.autoCrop"
                  :autoCropWidth="option.width"
                  :autoCropHeight="option.height"
                  :fixed="option.fixed"
                  :fixedNumber="option.fixedNumber"
                  :original = "option.original"
               ></vueCropper>
            </div>
            <div class="test-button">
               <label class="btn" for="uploads">上传</label>
               <input type="file" id="uploads" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg" @change="uploadImg($event, 2)">
               <button @click="finish('base64')" class="btn">完成</button>
            </div>
            <div class="show-preview" :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden', 'margin': '5px'}">
               <div :style="previews.div">
                  <img :src="previews.url" :style="previews.img">
               </div>
            </div>
         </div>
      </div>
   </div>            
</template>
<script>
	import vueCropper from './vue-cropper'

	export default {
    props: ['img'],
		data() {
			return {
				model: false,
				modelSrc: '',
				crap: false,
				previews: {},
				option: {
          img: '',
          info: true,
          size: 1,
          outputType: 'jpeg',
          canScale: true,
          autoCrop: true,
          // 只有自动截图开启 宽度高度才生效
          width: 200,
          height: 200,
          fixed: true,
          original: false
				},
				downImg: '#',
				filters: {
					searchName: 'nickname',
					search:'a',
					groupValue: ''
				},
        isModelShow: false
      }
		},	
		components: {
			vueCropper
		},
		methods: {
      		startCrop () {
            this.crop = true
            // console.log('开始截图')
          },
         changeScale (num) {
            num = num || 1
            this.$refs.cropper.changeScale(num)
         },
        clearCrop () {
          this.$refs.cropper.clearCrop()
        },
         finish (type) {
            // 输出
            // var test = window.open('about:blank')
            // test.document.body.innerHTML = '图片生成中..'
            if (type === 'blob') {
               this.$refs.cropper.getCropBlob((data) => {
                  var img = window.URL.createObjectURL(data)
                  this.model = true
                  this.modelSrc = img
               })
            } else {
               this.$refs.cropper.getCropData((data) => {
                  this.model = true
                  this.modelSrc = data
               })
            }
         },
         // 实时预览函数
         realTime (data) {
            this.previews = data
         },

         finish2 (type) {
            this.$refs.cropper2.getCropData((data) => {
               this.model = true
               this.modelSrc = data
            })
         },
         finish3 (type) {
            this.$refs.cropper3.getCropData((data) => {
               this.model = true
               this.modelSrc = data
            })
         },
         down (type) {
            // event.preventDefault()
            var aLink = document.createElement('a')
            aLink.download = 'demo'
            // 输出
            if (type === 'blob') {
               this.$refs.cropper.getCropBlob((data) => {
                  this.downImg = window.URL.createObjectURL(data)
                  aLink.href = window.URL.createObjectURL(data)
                  aLink.click()
               })
            } else {
               this.$refs.cropper.getCropData((data) => {
                  this.downImg = data
                  aLink.href = data
                  aLink.click()
               })
            }
         },
         uploadImg (e, num) {
            //上传图片
            // this.option.img
            var file = e.target.files[0]
            if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
               alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
               return false
            }
            var reader = new FileReader()
            reader.onload = (e) => {
               let data
               if (typeof e.target.result === 'object') {
                  // 把Array Buffer转化为blob 如果是base64不需要
                  data = window.URL.createObjectURL(new Blob([e.target.result]))
               } else {
                  data = e.target.result
               }
               if (num === 1) {
                  this.option.img = data
               } else if (num === 2) {
                  this.option.img = data
               }
            }
            // 转化为base64
            // reader.readAsDataURL(file)
            // 转化为blob
            reader.readAsArrayBuffer(file)
            this.$refs.cropper.changeScale(200)
            this.startCrop()
         },
         realTime (data) {
            this.previews = data
         },
         confirm(){
            this.model = false
            this.$emit('transfer',this.option.img)
            this.$emit("isModelShow",this.isModelShow)
         }
      }
	}
</script>

<style scoped>
	.content {
		margin: auto;
		max-width: 1200px;
		margin-bottom: 0px;
	}
	.show-info{
		margin-bottom: 5px;
	}

</style>
