
<template>
  <div class="container">
    <section id="formHolder">
      <div class="row">
        <!-- Form Box -->
        <div class="col-sm-6 form">
          <!-- Signup Form -->
          <div class="signup form-peice">
            <form class="signup-form" >
              <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" name="username" id="name" class="name" v-model="username" @input="validateName">
                <span class="error" v-if="!(isNameValid.value)">Not correct</span>
              </div>
              <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" name="emailAddress" id="email" class="email" v-model="email" @input="validateEmail">
                <span class="error" v-if="!(isEmailValid.value)">Please enter a valid email address</span>
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" class="pass" v-model="password" @input="validatePass">
                <span class="error" v-if="!(isPassValid.value)"></span>
              </div>
              <div class="form-group">
                <label for="passwordCon">Confirm Password</label>
                <input type="password" name="passwordCon" id="passwordCon" class="passConfirm" v-model="passwordConfirm" @input="validatePassConfirm">
                <span class="error" v-if="!(isPassConfValid.value)"></span>
              </div>
              <div class="CTA">
                <input type="button" value="Signup Now" @click="submitForm">
                <RouterLink class="switch" to="/signIn">I have an account</RouterLink>
              </div>

            </form>

          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script setup>
import { RouterLink, RouterView } from 'vue-router'
import {ref, onMounted} from 'vue';
import axios from 'axios';
import router from "../router/index.js"
const username = ref('');
const isNameValid = ref(false);

const validateName = () => {
  isNameValid.value = username.value.trim().length >= 5;
};
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
const passwordConfirm = ref('');
const isPassConfValid = ref(false);
const validatePassConfirm = () => {
  isPassConfValid.value = password.value === passwordConfirm.value;
};


const submitForm = async (event) => {

  event.preventDefault();
  if (isNameValid.value && isEmailValid.value && isPassValid.value && isPassConfValid.value) {
    const userData = {
      username: username.value,
      email: email.value,
      password: password.value
    };
    try {
      const response = await axios.post('http://localhost:8080/api/signUp', userData);
      console.log('Response from server:', response.data);
      if(response.data === "Email")
        isEmailValid.value=false;
      else if(response.data === "Password")
        isPassValid.value = false;
      else if(response.data === "Username")
        isNameValid.value=false;
      else if(response.data === "Error"){}
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

<style lang="scss" scoped>
@import "../styles/styles.scss";


</style>