import Vue from 'vue'
import App from './App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.css'

import AsyncComputed from 'vue-async-computed'
import JsonEditor from 'vue-json-edit'


Vue.use(AsyncComputed);
Vue.use(JsonEditor);
Vue.use(Vuetify);

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
