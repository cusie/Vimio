<template>
  <div>
    <div class="ui top attached segment m-padded-lr-big">
      <h2 class="m-text-500" style="text-align: center">该项目功能正在开发中...</h2>
      <img class="img-t" src="https://cdn.jsdmirror.com/gh/vi-mio/resources/Images/22&33.png">
    </div>
    <div class="ui card">

      </div>
    <!--评论-->
<!--    <div class="ui bottom teal attached segment threaded comments">
      <CommentList :page="1" :blogId="null" v-if="about.commentEnabled==='true'"/>
      <h3 class="ui header" v-else>评论已关闭</h3>
    </div>-->
  </div>
</template>
<script>
import {getOhter} from "@/api/other";
import CommentList from "@/components/comment/CommentList";

export default {
  name: "Other",
  components: {CommentList},
  data() {
    return {
      about: {
        title: '',
        musicId: '',
        content: '',
        commentEnabled: 'false'
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      getOhter().then(res => {
        if (res.code === 200) {
          this.about = res.data
        } else {
          this.msgError(res.msg)
        }
      }).catch(() => {
        this.msgError("请求失败")
      })
    }
  }
}
</script>

<style>
.content ul li {
  letter-spacing: 1px !important;
}
.img-t {
  width: 100%; /* 将图片宽度缩小为原来的 50% */

  height: auto; /* 高度自动调整以保持比例 */
}
</style>
