<script setup>
import { ref } from 'vue';
import axios from 'axios';

// Використання ref для створення реактивних змінних
const user = ref(null);

// Метод для завантаження даних про відвідування сторінки /about
const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/about');
    console.log('Response from server:', response.data);

    if (response.data === "Successs") {
      console.log("Data received successfully!");
    } else {
      console.warn("Unexpected response:", response.data);
    }
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

// Метод для завантаження даних про користувача за ID
const fetchUser = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/users/1');
    user.value = response.data;
  } catch (error) {
    console.error('There was an error fetching the user:', error);
  }
};


</script>
<template>

  <button @click="fetchData">Fetch Data</button>
  <div>
    <button @click="fetchUser">Get User</button>
    <div v-if="user">
      <p>ID: {{ user.id }}</p>
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>
  </div>

</template>

<style>
</style>