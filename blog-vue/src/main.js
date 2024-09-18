// 导入Vue框架
import Vue from 'vue';
// 导入根组件App.vue
import App from './App.vue';
// 导入路由配置router
import router from './router';
// 导入状态管理store
import store from './store';
// 导入自定义的base.css样式文件
import './assets/css/base.css';
// 导入阿里icon的样式文件
import './assets/css/icon/iconfont.css';
// 导入typo.css样式文件
import "./assets/css/typo.css";
// 导入semantic-ui的样式文件   用node.js导入
import 'semantic-ui-css/semantic.min.css';
// 导入element-ui组件库        用node.js导入
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 导入日期时间格式化工具
import './util/dateTimeFormatUtils.js';
// 导入v-viewer图像浏览插件的样式文件  用node.js导入
import 'viewerjs/dist/viewer.css';
import Viewer from 'v-viewer';
// 导入自定义指令
import './util/directive';



// 控制台输出项目信息
console.log(
    '%c Vimio %c By Vimio %c https://github.com/vi-mio',
    'background:#35495e ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff',
    'background:#41b883 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #000',
    'background:transparent'
);

// 使用ElementUI组件库
Vue.use(Element);
// 使用v-viewer图像浏览插件
Vue.use(Viewer);

// Vue原型上挂载消息提示方法
Vue.prototype.msgSuccess = function (msg) {
    this.$message.success(msg);
};

Vue.prototype.msgError = function (msg) {
    this.$message.error(msg);
};

Vue.prototype.msgInfo = function (msg) {
    this.$message.info(msg);
};

// 定义一个立方函数
const cubic = value => Math.pow(value, 3);
// 定义一个缓动函数，实现在0.5秒内滚动至页面顶部，使用Element-ui中的回到顶部组件算法
const easeInOutCubic = value => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;

// Vue原型上挂载滚动至页面顶部的方法
Vue.prototype.scrollToTop = function () {
    const el = document.documentElement;
    const beginTime = Date.now();
    const beginValue = el.scrollTop;
    const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16));
    const frameFunc = () => {
        const progress = (Date.now() - beginTime) / 500;
        if (progress < 1) {
            el.scrollTop = beginValue * (1 - easeInOutCubic(progress));
            rAF(frameFunc);
        } else {
            el.scrollTop = 0;
        }
    };
    rAF(frameFunc);
};

// 关闭生产模式下的提示
Vue.config.productionTip = false;

// 创建Vue实例，挂载到id为'app'的DOM元素上
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
