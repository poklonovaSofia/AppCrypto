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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
  <div class="container container4">
    <div class="wrapper">
      <div class="title"><span>Login Form</span></div>
      <form action="#" class="form2">
        <div class="row row2">
          <i class="fas fa-user"></i>
          <input type="email" name="loginemail" id="loginemail"  v-model="email" @input="validateEmail" required placeholder="Email">
        </div>
        <div class="row row2">
          <i class="fas fa-lock"></i>
          <input type="password" name="loginPassword" id="loginPassword" v-model="password" @input="validatePass" required placeholder="Password">
        </div>
        <div class="pass"><a href="#">Forgot password?</a></div>
        <div class="row row2 button">
          <input type="button" value="Login" @click="submitForm">

        </div>
        <RouterLink class="switch" to="/signUp">I'm New</RouterLink>
      </form>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap');
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins',sans-serif;
}

::selection{
  background: rgba(26,188,156,0.3);
}
.container4{
  max-width: 440px;
  padding: 0 20px;
  margin: 170px auto;
}
.wrapper{
  width: 100%;
  background: #fff;
  border-radius: 5px;
  box-shadow: 0px 4px 10px 1px rgba(0,0,0,0.1);
}
.wrapper .title{
  height: 90px;
  background: #16a085;
  border-radius: 5px 5px 0 0;
  color: #fff;
  font-size: 30px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
}
.wrapper .form2{
  padding: 30px 25px 25px 25px;
}
.wrapper .form2 .row2{
  height: 45px;
  margin-bottom: 15px;
  position: relative;
}
.wrapper .form2 .row2 input{
  height: 100%;
  width: 100%;
  outline: none;
  padding-left: 60px;
  border-radius: 5px;
  border: 1px solid lightgrey;
  font-size: 16px;
  transition: all 0.3s ease;
}
.form2 .row2 input:focus{
  border-color: #16a085;
  box-shadow: inset 0px 0px 2px 2px rgba(26,188,156,0.25);
}
.form2 .row2 input::placeholder{
  color: #999;
}
.wrapper form .row2 i{
  position: absolute;
  width: 47px;
  height: 100%;
  color: #fff;
  font-size: 18px;
  background: #16a085;
  border: 1px solid #16a085;
  border-radius: 5px 0 0 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.wrapper .form2 .pass{
  margin: -8px 0 20px 0;
}
.wrapper .form2 .pass a{
  color: #16a085;
  font-size: 17px;
  text-decoration: none;
}
.wrapper .form2 .pass a:hover{
  text-decoration: underline;
}
.wrapper .form2 .button input{
  color: #fff;
  font-size: 20px;
  font-weight: 500;
  padding-left: 0px;
  background: #16a085;
  border: 1px solid #16a085;
  cursor: pointer;
}
.form2 .button input:hover{
  background: #12876f;
}
.wrapper .form2 .signup-link{
  text-align: center;
  margin-top: 20px;
  font-size: 17px;
}
.wrapper .form2 .signup-link a{
  color: #16a085;
  text-decoration: none;
}
.form2 .signup-link a:hover{
  text-decoration: underline;
}


</style>