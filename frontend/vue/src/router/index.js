import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import SignUpView from '../views/SignUpView.vue';
import SignInView from '../views/SignInView.vue';
import AboutView from '../views/AboutView.vue';
import MainView from '../views/MainView.vue';
// Define your routes
const routes = [
    { path: '/', component: HomeView },
    { path: '/about', component: AboutView },
    { path: '/signUp', component: SignUpView },
    { path: '/signIn', component: SignInView },
    { path: '/main', component: MainView }
];

// Create the router instance
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

export default router;

/*
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/about',
            name: 'about',
            component: AboutView
        },
        {
            path: '/signUp',
            name: 'signUp',
            component: SignUpView
        },
        {
            path: '/signIn',
            name: 'signIn',
            component: SignInView
        },
        {
            path: '/home',
            name: 'home',
            component: HomeView
        }
    ]
})

export default router*/

