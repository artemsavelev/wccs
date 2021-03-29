import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'


Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'Auth',
    component: () => import('@/views/Auth.vue')
  },
  {
    path: '/',
    name: 'OrdersList',
    meta: { auth: true },
    component: () => import('@/views/OrdersList.vue')
  },
  {
    path: '/create',
    name: 'OrderForm',
    meta: { auth: true },
    component: () => import('@/components/OrderForm.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    meta: { auth: true },
    component: () => import('@/views/Profile.vue')
  },
  {
    path: '/settings',
    name: 'Settings',
    meta: { auth: true },
    component: () => import('@/views/Settings.vue')
  },
  {
    path: '/admin/add-user',
    name: 'AddUser',
    meta: { auth: true },
    component: () => import('@/components/AddUser.vue')
  },
  {
    path: '/admin/users',
    name: 'Users',
    meta: { auth: true },
    component: () => import('@/components/Users.vue')
  },
  {
    path: '/admin/add-component',
    name: 'AddComponent',
    meta: { auth: true },
    component: () => import('@/components/AddComponent.vue')
  },
  {
    path: '/admin/components',
    name: 'ListComponents',
    meta: { auth: true },
    component: () => import('@/components/ListComponents.vue')
  }

];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {

  const requireAuth = to.matched.some(record => record.meta.auth)

  if (requireAuth && !store.getters.profile) {
    next('/login')
  } else {
    next()
  }




})

export default router
