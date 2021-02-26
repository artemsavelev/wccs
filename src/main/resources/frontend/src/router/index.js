import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'Auth',
    component: () => import('../views/Auth.vue')
  },
  {
    path: '/',
    name: 'OrdersList',
    component: () => import('../views/OrdersList.vue')
  },
  {
    path: '/create',
    name: 'OrderForm',
    component: () => import('../components/OrderForm.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue')
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../views/Settings.vue')
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/Admin.vue')
  }

];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
