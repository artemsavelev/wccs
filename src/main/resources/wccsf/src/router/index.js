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
    name: 'List',
    component: () => import('../views/OrdersList.vue')
  },
  {
    path: '/create',
    name: 'Form',
    component: () => import('../views/OrderForm.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue')
  },
  // {
  //   path: '/estimate',
  //   name: 'Estimate',
  //   component: () => import('../views/Estimate.vue')
  // },
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
