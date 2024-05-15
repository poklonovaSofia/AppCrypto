
<template>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">



  <div class="container">
    <div class="wrapper">
      <div class="title"><span>Login Form</span></div>
      <form action="#">
        <div class="row">
          <i class="fas fa-user"></i>

          <input type="text" name="username" id="name" class="name" v-model="username" @input="validateName" placeholder="Username">

        </div>
        <div class="row">
          <i class="fas fa-user"></i>
          <input type="email" name="emailAddress" id="email" class="email" v-model="email" @input="validateEmail" placeholder="Email">

        </div>
        <div class="row">
          <i class="fas fa-lock"></i>
          <input type="password" name="password" id="password" class="pass" v-model="password" @input="validatePass" placeholder="Password">

        </div>
        <div class="row">
          <i class="fas fa-lock"></i>
          <input type="password" name="passwordCon" id="passwordCon" class="passConfirm" v-model="passwordConfirm" @input="validatePassConfirm" placeholder="Confirm Password">

        </div>
        <div class="pass"><a href="#">Forgot password?</a></div>
        <div class="row button">
          <input type="button" value="Signup Now" @click="submitForm">
        </div>
        <RouterLink class="switch" to="/signIn">I have an account</RouterLink>
      </form>
    </div>
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

<style scoped>


@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap');
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins',sans-serif;
}
body{
  background: #1abc9c;
  overflow: hidden;
}
::selection{
  background: rgba(26,188,156,0.3);
}
.container{
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
.wrapper form{
  padding: 30px 25px 25px 25px;
}
.wrapper form .row{
  height: 45px;
  margin-bottom: 15px;
  position: relative;
}
.wrapper form .row input{
  height: 100%;
  width: 100%;
  outline: none;
  padding-left: 60px;
  border-radius: 5px;
  border: 1px solid lightgrey;
  font-size: 16px;
  transition: all 0.3s ease;
}
form .row input:focus{
  border-color: #16a085;
  box-shadow: inset 0px 0px 2px 2px rgba(26,188,156,0.25);
}
form .row input::placeholder{
  color: #999;
}
.wrapper form .row i{
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
.wrapper form .pass{
  margin: -8px 0 20px 0;
}
.wrapper form .pass a{
  color: #16a085;
  font-size: 17px;
  text-decoration: none;
}
.wrapper form .pass a:hover{
  text-decoration: underline;
}
.wrapper form .button input{
  color: #fff;
  font-size: 20px;
  font-weight: 500;
  padding-left: 0px;
  background: #16a085;
  border: 1px solid #16a085;
  cursor: pointer;
}
form .button input:hover{
  background: #12876f;
}
.wrapper form .signup-link{
  text-align: center;
  margin-top: 20px;
  font-size: 17px;
}
.wrapper form .signup-link a{
  color: #16a085;
  text-decoration: none;
}
form .signup-link a:hover{
  text-decoration: underline;
}
</style>