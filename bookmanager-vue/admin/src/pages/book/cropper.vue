<template>
	<div class="wrapper">
		<div class="model" style="width:100%;height:100%;" v-show="model" @click="model = false">
			<div class="model-show" style="width:100%;height:70%;">
				<img :src="modelSrc" alt="">
			</div>
			<div class="container" style="width:20%;margin-top:-10%;margin-left:44%" v-show="modelBtn" @click="modelBtn = false" >
				<div style="margin-left:-25%;margin-top:-2%" ><font size="2" color="#ffffff" face="微软雅黑">✱ 【确定】后将会上传图片为图书【封面】</font><br></div>
				<div style="margin-top:4%" >
					<button class="btn btn-info" @click="addDB" >确定</button>
					<button class="btn btn-success" style="margin-left:5%" >取消</button>
				</div>
				
			</div>
		</div>
		<div class="content">
	
			<div class="show-info">
				<h2 class="title">选择图书封面</h2>
				<div class="test">
					<vueCropper
						ref="cropper2"
						:img="example2.img"
						:outputSize="example2.size"
						:outputType="example2.outputType"
						:info="example2.info"
						:canScale="example2.canScale"
						:autoCrop="example2.autoCrop"
						:autoCropWidth="example2.width"
						:autoCropHeight="example2.height"
						:fixed="example2.fixed"
						:fixedNumber="example2.fixedNumber"
					></vueCropper>
				</div>
				<div style="margin-top:1%">
					<label class="btn btn-warning" for="upload2">选择图片</label>
					<input type="file" id="upload2" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg" @change="uploadImg($event, 2)">
					<button style="margin-left:1%" @click="finish2('base64')" class="btn btn-info">预览</button>
					<button style="margin-left:1%" @click="upload" class="btn btn-success">完成</button>
				</div>
				
			</div>
			
		</div>
	</div>
</template>

<script>
import vueCropper from './vue-cropper'
import axios from 'axios'


export default {
  data: function () {
    return {
			model: false,
			modelBtn: false,
			modelSrc: '',
		  	crap: false,
			imageU:'',
			coverImgUrl:'',
			headImgUrl:'',
			example2: {
				img: '',
				info: true,
				size: 1,
				outputType: 'jpeg',
				canScale: true,
				autoCrop: true,
				// 只有自动截图开启 宽度高度才生效
				width: 310,
				height: 430,
				fixed: true,
				fixedNumber: [31, 43]
			},
			downImg: '#',
			isShow: false
    }
  },
	methods: {
		sumitImageFile(base64Codes){
			var form=document.forms[0];
			
			var formData = new FormData();   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
			
			//convertBase64UrlToBlob函数是将base64编码转换为Blob
			formData.append("file",this.convertBase64UrlToBlob(base64Codes));  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
			
			
			axios.post("/api/book/upload",formData).then(response => {				
				this.coverImgUrl = response.data.result
				this.$refs.cropper2.clearCrop()
				this.$emit('transfer',this.coverImgUrl)
				this.$emit("isModelShow",this.isShow)
				this.$refs.cropper2.clearCrop()
			}).catch(function (error) {
			　　alert(error)
			});
     
		},

		 convertBase64UrlToBlob(urlData){
    
			var bytes=atob(urlData.split(',')[1]);           //去掉url的头，并转换为byte
			
			//处理异常,将ascii码小于0的转换为大于0
			var ab = new ArrayBuffer(bytes.length);
			var ia = new Uint8Array(ab);
			for (var i = 0; i < bytes.length; i++) {
				ia[i] = bytes.charCodeAt(i);
			}

			return new Blob( [ab] , {type : 'image/png'});
		},
		finish2 (type) {
			this.$refs.cropper2.getCropData((data) => {
				this.model = true
				this.modelBtn = false
				this.modelSrc = data
			})
		},
		upload(type){
			this.$refs.cropper2.getCropData((data) => {
				this.model = true
				this.modelBtn = true
				this.modelSrc = data
				// this.imageU = this.modelSrc	
			})	
		},
		addDB(type){
			this.$refs.cropper2.getCropData((data) => {
				this.sumitImageFile(data)
			})	
		},
		uploadImg (e, num) {
			this.modelSrc = ''
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
					this.example2.img = data
				}
			}
			// 转化为base64
			reader.readAsDataURL(file)
			// 转化为blob
			// reader.readAsArrayBuffer(file)
			this.$refs.cropper2.startCrop()
		},
		 down (type) {
			var aLink = document.createElement('a')
			aLink.download = 'demo'
			// 输出
			if (type === 'blob') {
				this.$refs.cropper2.getCropBlob((data) => {
					this.downImg = window.URL.createObjectURL(data)
					aLink.href = window.URL.createObjectURL(data)
					aLink.click()
					
				})
			} else {
				this.$refs.cropper2.getCropData((data) => {
					
					this.downImg = data
					aLink.href = data
					aLink.click()
				})
			}
			
		},
	},
	components: {
		vueCropper,
		
	},
	mounted () {
		
	}
}
</script>

<style scoped>
    @import '/static/bootstrap.css';

	.content {
		margin: auto;
		max-width: 80%;
	}

	.des {
		line-height: 30px;
	}

	code.language-html {
		padding: 10px 20px;
		margin: 10px 0px;
		display: block;
		background-color: #333;
		color: #fff;
		overflow-x: auto;
		font-family: Consolas, Monaco, Droid, Sans, Mono, Source, Code, Pro, Menlo, Lucida, Sans, Type, Writer, Ubuntu, Mono;
		border-radius: 5px;
		white-space: pre;
	}

	

	.show-info h2 {
		line-height: 50px;
	}

	/*.title, .title:hover, .title-focus, .title:visited {
		color: black;
	}*/

	.title {
		display: block;
		text-decoration: none;
		text-align: center;
		line-height: 1.5;
		margin: 20px 0px;
		background-image: -webkit-linear-gradient(left,#3498db,#f47920 10%,#d71345 20%,#f7acbc 30%,#ffd400 40%,#3498db 50%,#f47920 60%,#d71345 70%,#f7acbc 80%,#ffd400 90%,#3498db);
		color: transparent;
		-webkit-background-clip: text;
		background-size: 200% 100%;
		animation: slide 5s infinite linear;
		font-size: 40px;
	}

	.test {
	  height: 500px;
	}

	.model {
		position: fixed;
		z-index: 10;
		width: 100%;
		height: 100%;
		overflow: auto;
		top: 0;
		left: 0;
		background: rgba(0, 0, 0, 0.8);
	}

	.model-show {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100%;
	}

	.model img {
		user-select: none;
		background-position: 0px 0px, 10px 10px;
		background-size: 20px 20px;
    background-image: linear-gradient(45deg, #eee 25%, transparent 25%, transparent 75%, #eee 75%, #eee 100%),linear-gradient(45deg, #eee 25%, white 25%, white 75%, #eee 75%, #eee 100%);
	}

	.c-item {
		display: block;
		padding: 10px 0;
		user-select: none;
	}

	@keyframes slide {
    0%  {
      background-position: 0 0;
    }
    100% {
      background-position: -100% 0;
    }
  }

</style>
