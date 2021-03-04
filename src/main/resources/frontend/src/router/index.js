import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/modules/auth'


Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'Auth',
    component: () => import('@/views/Auth.vue')
  },
  {
    path: '/orders',
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
    path: '/admin',
    name: 'Admin',
    meta: { auth: true },
    component: () => import('@/views/Admin.vue')
  }

];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {

  try {
    const requireAuth = to.matched.some(record => record.meta.auth)

    if (requireAuth && store.state.profile.user) {
      next('/login')
    } else {
      next()
    }
  } catch (e) {
    console.warn(e.message)
  }

})

export default router
