import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import './styles/common.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUserSecret)

axios.defaults.baseURL = process.env.VUE_APP_HOST
const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(store).use(router).use(VueAxios, axios).mount('#app')
