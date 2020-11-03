import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import './styles/common.css'

axios.defaults.baseURL = process.env.VUE_APP_HOST
const app = createApp(App)
app.use(store).use(router).use(VueAxios, axios).mount('#app')
