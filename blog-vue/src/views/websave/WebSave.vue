<template>
  <div>
    <div class="ui top attached segment" style="text-align: center">
      <h2 class="m-text-500">网站收集</h2>
    </div>
    <section v-for="(category, index) in categories" :key="index">
      <div class="ui teal attached segment">
        <h3>{{ category.title }}</h3>
      </div>
      <div class="ui attached segment card-container">
        <div class="ui link three doubling cards">
          <a
              :href="web.website"
              target="_blank"
              rel="external nofollow noopener"
              class="card"
              :style="randomRGB()"
              v-for="(web, webIndex) in category.data"
              :key="webIndex"
              @click="addViews(web.webname)"
          >
            <div class="image">
              <img :src="web.avatar" onerror="this.src = '/img/error.png'" />
            </div>
            <div class="content">
              <div class="header">{{ web.webname }}</div>
              <div class="description">{{ web.description }}</div>
            </div>
          </a>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { getWebData, addViewsByWebname } from "@/api/websites";

export default {
  name: "WebSave",
  data() {
    return {
      categories: [
        { title: "学习网站:", data: [] },
        { title: "科学上网:", data: [] },
        { title: "我常用的网站:", data: [] },
        { title: "新世界的大门:", data: [] },
      ],
      bgColor: [
        "#1abc9c","#2ecc71","#3498db",
        "#9b59b6","#34495e","#f1c40f",
        "#e67e22","#e74c3c","#ee5a24",
        "#9980fa","#8c7ae6","#f79f1f",
      ],
    };
  },
  created() {
    this.fetchWebData();
  },
  methods: {
    async fetchWebData() {
      try {
        const res = await getWebData();
        if (res.code === 200) {
          this.categories[0].data = res.data.essential.sort((a, b) => a.webname.localeCompare(b.webname));
          this.categories[1].data = res.data.scienceweb.sort((a, b) => a.webname.localeCompare(b.webname));
          this.categories[2].data = res.data.frequentlyuse.sort((a, b) => a.webname.localeCompare(b.webname));
          this.categories[3].data = res.data.other.sort((a, b) => a.webname.localeCompare(b.webname));
        } else {
          this.showError(res.msg);
        }
      } catch (error) {
        this.showError("请求失败");
        logError(error);
      }
    },
    addViews(webname) {
      addViewsByWebname(webname);
    },
    randomRGB() {
      const index = Math.floor(Math.random() * this.bgColor.length);
      return {backgroundColor: this.bgColor[index]};
    },
    showError(message) {
      console.error(message);
      // 可以在这里将错误信息显示在页面上的某个位置
    },
  },
};
</script>

<style scoped>
.card .image {
  width: 70px !important;
  margin: 10px auto 0px;
  background: unset !important;
}

.card .image img {
  border-radius: 100% !important;
  width: 70px !important;
  height: 70px !important;
}

.card .content {
  text-align: center;
  padding: 10px 14px !important;
  border-top: 0 !important;
}

.card .content * {
  color: #fff !important;
}

.card .content .header {
  font-size: 16px !important;
}

.card .content .description {
  font-size: 12px !important;
}

.card .content .description {
  margin-top: 5px !important;
  margin-bottom: 7px;
}

</style>
