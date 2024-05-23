<script setup>
import { RouterLink, RouterView } from 'vue-router'
import axios from "axios";
import {ref} from "vue";
import router from '../router/index.js';
const email = ref('');
const isEmailValid = ref(false);

const validateEmail = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Регулярний вираз для перевірки електронної пошти
  isEmailValid.value = emailPattern.test(email.value);
};

const password = ref('');
const isPassValid = ref(false);
const validatePass = () => {
  const complexPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{6,}$/;
  isPassValid.value = complexPattern.test(password.value);
};
const submitForm = async (event)=>{
  validatePass();
  validateEmail();
  event.preventDefault();
  if (isPassValid.value && isEmailValid.value) {
    const userData = {
      email: email.value,
      password: password.value
    };
    try {
      const response = await axios.post('http://localhost:8080/api/signIn', userData);
      console.log('Response from server:', response.data);
      if(response.data === "Error") {
        isEmailValid.value = false;
        isPassValid.value = false;
      }
      else {
        if (response.data.accessToken) {
          localStorage.setItem('userToken', response.data.accessToken);
          console.log('Token saved to localStorage' + response.data.accessToken);
        } else {
          console.error('No accessToken found in the response');
        }
        await router.push("/main");
      }

    } catch (error) {
      console.error('Error:', error);
    }
  } else {
    console.log("Username not correct or email");
  }
};


</script>

<template>
  <div class="container">
    <section id="formHolder">
      <div class="row">
        <div class="col-sm-6 form">
          <div class="login form-peice ">
            <form class="login-form" action="#" method="post">
              <div class="form-group">
                <label for="loginemail">Email Adderss</label>
                <input type="email" name="loginemail" id="loginemail" v-model="email" @input="validateEmail" required>
              </div>
              <div class="form-group">
                <label for="loginPassword">Password</label>
                <input type="password" name="loginPassword" id="loginPassword" v-model="password" @input="validatePass" required>
              </div>
              <div class="CTA">
                <input type="button" value="Login" @click="submitForm">
                <RouterLink class="switch" to="/signUp">I'm New</RouterLink>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style lang="scss" scoped>
@import "../styles/styles.scss";

</style>