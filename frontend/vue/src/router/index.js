import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import SignUpView from '../views/SignUpView.vue';
import SignInView from '../views/SignInView.vue';
import AboutView from '../views/AboutView.vue';
import MainView from '../views/MainView.vue';
import ProfileView from '../views/ProfileView.vue';
// Define your routes
const routes = [
    { path: '/', component: HomeView },
    { path: '/about', component: AboutView },
    { path: '/signUp', component: SignUpView },
    { path: '/signIn', component: SignInView },
    { path: '/main', component: MainView },
    { path: '/profile', component: ProfileView }
];

// Create the router instance
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

export default router;



