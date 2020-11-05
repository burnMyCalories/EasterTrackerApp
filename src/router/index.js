import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Welcome',
    component: () => import('../views/Welcome.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/addProfile',
    name: 'AddProfile',
    component: () => import('../views/AddProfile.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    children: [
      {
        path: 'myProfile',
        name: 'MyProfile',
        component: () => import('../views/MyProfile.vue')
      },
      {
        path: 'circle',
        name: 'Circle',
        component: () => import('../views/Circle.vue')
      },
      {
        path: 'hideEgg',
        name: 'hideEgg',
        component: () => import('../components/HideEgg.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
