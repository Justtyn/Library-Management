import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import '@/assets/global.css'
import SlideVerify from 'vue-monoplasty-slide-verify';

Vue.config.productionTip = false
Vue.use(ElementUI, {size: 'small'});
Vue.use(SlideVerify);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
