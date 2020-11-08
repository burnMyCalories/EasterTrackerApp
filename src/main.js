/*
 * Copyright (c) 2020.  Easter Egg Mapp App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: Nov. 2020
 * Usage: The entry point of this App
 */

import { createApp } from 'vue'
import App from './App.vue'
// import './registerServiceWorker'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import './styles/common.css'
import '@/styles/font.scss'

axios.defaults.baseURL = process.env.VUE_APP_HOST
const app = createApp(App)
app.use(store).use(router).use(VueAxios, axios).mount('#app')
