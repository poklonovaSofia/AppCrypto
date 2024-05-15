<script setup>
import { onMounted } from 'vue';
import { RouterLink, RouterView } from 'vue-router';
import {gsap} from 'gsap';

var isOpen = false;

onMounted(() => {
  const menu = document.querySelector('.menu-btn');
  const topLeftSlider = document.querySelector('.top-left-slide');
  const bottomLeftSlider = document.querySelector('.bottom-left-slide');

  const topRightSlider = document.querySelector('.top-right-slide');
  const middleRightSlider = document.querySelector('.middle-right-slide');
  const bottomRightSlider = document.querySelector('.bottom-right-slide');

  const eksOneElement = document.querySelector('.eks-one');
  const eksTwo = document.querySelector('.eks-two');
  const eksThreeElement = document.querySelector('.eks-three');

  menu.addEventListener('click', () => {
    topLeftSlider.classList.toggle('top-left-slide-show');
    bottomLeftSlider.classList.toggle('bottom-left-slide-show');
    topRightSlider.classList.toggle('top-right-slide-show');
    middleRightSlider.classList.toggle('middle-right-slide-show');
    bottomRightSlider.classList.toggle('bottom-right-slide-show');
    eksTwo.classList.toggle('eks-two-fade');

    const tl = gsap.timeline();
    const tlEksThree = gsap.timeline();

    if (!isOpen) {
      tl.to(eksOneElement, {
        y: isOpen ? 0 : 9,
      }).to(eksOneElement, {
        rotate: isOpen ? 0 : 45
      });

      tlEksThree.to(eksThreeElement, {
        y: isOpen ? 0 : -9,
      }).to(eksThreeElement, {
        rotate: isOpen ? 0 : -45
      });
    } else {
      tl.to(eksOneElement, {
        rotate: isOpen ? 0 : 45,
      }).to(eksOneElement, {
        y: isOpen ? 0 : 9,
      });

      tlEksThree.to(eksThreeElement, {
        rotate: isOpen ? 0 : -45
      }).to(eksThreeElement, {
        y: isOpen ? 0 : -9
      });
    }

    isOpen = !isOpen;
  });

  gsap.from('.simple', {
    x: -100,
    duration: 1
  });
  gsap.from('.menu', {
    x: -100,
    duration: 1.2,
    delay: 0.3,
    opacity: 0
  });
  gsap.from('.navi', {
    y: -500,
    duration: 2,
    delay: 0.4,
    opacity: 0
  });
});
</script>

<template>
  <div>
    <div class="top-right-slide">
      <div class="inner-container-top">
        <RouterLink to="/home" class="nav-links">HOME</RouterLink>
      </div>
    </div>
    <div class="middle-right-slide">
      <div class="inner-container-middle">
        <RouterLink to="/about" class="nav-links">ABOUT</RouterLink>
      </div>
    </div>
    <div class="bottom-right-slide">
      <div class="inner-container-bottom">
        <RouterLink to="/signIn" class="nav-links">SIGN IN</RouterLink>
      </div>
    </div>
    <div class="top-left-slide">
      <div class="inner-container-left-top">
        <RouterLink to="/signUp" class="nav-links">SIGN UP</RouterLink>
      </div>
    </div>
    <div class="bottom-left-slide">
      <div class="inner-container-left-bottom">
        <RouterLink to="/signUp" class="nav-links">SIGN</RouterLink>
      </div>
    </div>
    <div class="menu-btn">
      <div class="eks-one"></div>
      <div class="eks-two"></div>
      <div class="eks-three"></div>
    </div>
    <div class="text"></div>
  </div>

  <div class="content">
    <RouterView />
  </div>


</template>

<style scoped>
@import '../styles/styles2.css';
</style>
