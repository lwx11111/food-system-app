<!-- 图片上传 -->
<template>
	<u-upload
		:fileList="fileList1"
		:maxCount="1"
		@afterRead="afterRead"
		@delete="deletePic"
		name="1"
		multiple
	></u-upload>
</template>

<script>
	export default {
		data() {
			return {
				// 必须是1 不带这个1还不行
				fileList1: [],
			}
		},
		props:{
			
			/**
			 * 回传时区别数组
			 * 如果多个数组，传key1 指定是哪个数组 key2知道下标
			 * 单个数组 只传key2即可
			 * 单张图片不用传
			 */
			key1:{
				required:false,
				type: String
			},
			key2:{
				required:false,
				type: Number
			}
		},
		methods:{
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				console.log(event);
				// this.fileList.push(event.file)
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: 'http://localhost:8081/sysoss/uploadOSS', // 仅为示例，非真实的接口地址
						filePath: url,
						name: 'file',
						formData: {
							user: 'test'
						},
						complete: (res) => {
							let data = JSON.parse(res.data)
							console.log(this.key1);
							console.log(this.key2);
							let url = "http://8.130.120.92:9000/appsys/" + data.data.storageFileName
							this.$emit('getUrl', url,this.key1,this.key2 )
						},
						success: (res) => {
							console.log(res);
							setTimeout(() => {
								resolve(res.data.data)
							}, 1000)
						},
						
					});
				})
			},
		}

	}
</script>
