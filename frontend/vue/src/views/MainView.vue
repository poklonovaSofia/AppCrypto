<template>
  <link href="https://fonts.googleapis.com/css?family=Droid+Sans+Mono" rel="stylesheet">


  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
  <!-- ваша розмітка тут -->

  <div class="container container2 d-flex">
    <div class="card">
      <div class="upper">
        <img src="../styles/back.jpg" class="img-fluid">
      </div>
      <div class="user text-center">
        <div class="profile">
          <img src="../styles/noname.jpg" class="rounded-circle" width="80">
        </div>
      </div>
      <div class="mt-5 text-center">
        <h4 class="mb-0">{{userData.username}}</h4>
        <span class="text-muted d-block mb-2">{{userData.email}}</span>
        <button v-if="!token" class="btn btn-primary btn-sm follow" @click="signIn">Sign In</button>
        <button v-else class="btn btn-danger btn-sm follow" @click="profileMode">Profile</button>
        <div class="d-flex justify-content-between align-items-center mt-4 px-4">
          <div class="stats">
            <h6 class="mb-0">Total {{coin.name}} Balance</h6>
            <span>{{usersBalance.balance}} =  {{countInDollars}} $</span>
          </div>

        </div>
      </div>
    </div>
    <div class="card card2">
      <div class="upper">
      </div>
      <div class="user text-center">
        <p> {{ tempBalance }}</p>
        <p> {{ coin.rate }} {{coin.name}}/hour</p>
        <p> {{ end_at }}</p>
      </div>
      <div class="mt-5 text-center">
        <button v-if="timePassed" class="btn btn-danger btn-sm follow" @click="claim">Claim</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import router from '../router/index.js'

export default {
  data() {
    return {
      token: '',
      userData: {
        id: "",
        username: "",
        email: ""
      },
      usersBalance: {
        user_id: "",
        coin_id: "",
        balance: ""
      },
      coin: {
        name: "",
        id: "",
        rate: ""
      },
      countInDollars: '',
      tempBalance: 0.0000,
      timePassed: false,
      end_at: "Filled"

    };
  },
  mounted() {
    this.token = localStorage.getItem('userToken') || '';
    this.fetchUserData();
  },
  methods: {
    async claim()
    {
      const req={
        tempBalance: this.tempBalance,
        duration: 1,
        user_id: this.userData.id,
        coin_id: this.coin.id
          }
      const response = await axios.post("http://localhost:8080/api/history/start/savings", req)
      if(response.data)
      {
        this.startWorker(response.data);
      }
    },
    startWorker(savings)
    {
      this.timePassed=false;
      if (typeof Worker !== 'undefined') {
        const worker = new Worker("@../workers/workerCalculateSave.js");
        worker.postMessage({initialValue: this.coin.rate, hours: 1, savings_id: savings.id});
        worker.onmessage = (event) => {
          const {data} = event;
          this.sum = data.sum;
          this.timePassed = data.timePassed;
        };
      }
      else console.log("Can't");
    },
    profileMode() {
     /* localStorage.removeItem('userToken');
      this.token = '';
      router.push("/signIn");*/


    },
    async fetchUserData() {
      if (this.token) {
        try {
          const response = await axios.get('http://localhost:8080/api/user', {
            headers: {
              Authorization: `Bearer ${this.token}`
            }
          });

          if (response.data === "token") {
            router.push("/signIn");
          } else {
            this.userData = response.data;
            const balanceResponse = await axios.get(`http://localhost:8080/api/user/${this.userData.id}/balance`, {
              headers: {
                Authorization: `Bearer ${this.token}`
              }
            });

            if (balanceResponse.data === 0) {
              this.usersBalance.balance = 0;
              this.coin.id = 0;
              this.coin.name = "sBP5";
            } else {
              this.usersBalance = balanceResponse.data;
              this.coin.id = balanceResponse.data.coin.id;
              this.coin.name = balanceResponse.data.coin.name;
            }

            const priceResponse = await axios.get(`http://localhost:8080/api/coin/${this.coin.id}/rate`, {
              headers: {
                Authorization: `Bearer ${this.token}`
              }
            });

            console.log(priceResponse.data.coin);
            this.coin.rate = priceResponse.data.rate;
            this.coin.id = priceResponse.data.coin.id;

            this.countInDollars = this.coin.rate * this.usersBalance.balance;
          }
        } catch (error) {
          console.error('Error fetching user data:', error);
        }

        try {
          const info ={
            coin_id: this.coin.id,
            user_id:this.userData.id
          }
          const response = await axios.post('http://localhost:8080/api/history/savings', info);
          console.log('Response from server:', response.data);

          if (response.data === null) {
            this.timePassed = true;
          } else {

            this.tempBalance = response.data.tempBalance;
            this.end_at = "Until " + response.data.end_at;
            this.timePassed=false;
            await this.startWorker(response.data);
          }
        } catch (error) {
          console.error('Error:', error);
        }
      } else {
        localStorage.removeItem('userToken');
        this.token = '';
        router.push("/signIn");
        console.error('User token not found');
      }
    }



  }
}
</script>




<style scoped>

.container2{
  min-height: 100vh;
  background-color: #c5c3c3;
  font-family: "Segoe Print", sans-serif;
  font-weight: 300;
  width: 100%;
}

.card{

  width: 420px;
  border: none;
  border-radius: 15px;
  padding: 8px;
  background-color: #fff;
  position: relative;
  height: 470px;
  margin:20px;
}
.card2{
  width:800px;
}
.upper{

  height: 200px;

}

.upper img{

  width: 100%;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;

}

.user{
  position: relative;
}

.profile img{


  height: 80px;
  width: 80px;
  margin-top:2px;


}

.profile{

  position: absolute;
  top:-50px;
  left: 38%;
  height: 90px;
  width: 90px;
  border:3px solid #fff;

  border-radius: 50%;

}

.follow{

  border-radius: 15px;
  padding-left: 20px;
  padding-right: 20px;
  height: 35px;
}

.stats span{

  font-size: 23px;
}
* {
  box-sizing: border-box;
  transition: 0.5s cubic-bezier(0.5, 0, 0.2, 1);
}

.abs-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.container3 {
  width: 80px;
  height: 50px;
  perspective: 5000px;
  transform-style: preserve-3d;
}
.container3.flipped {
  transform: translate(-50%, -50%) rotateY(180deg);
}
.container3.complete .icon-background {
  fill: #21cd92;
  stroke: #21cd92;
}
.container3.complete .error {
  opacity: 0;
  transition: 0.3s ease;
}
.container3.error .icon-background {
  fill: #f44336;
  stroke: #f44336;
}
.container3.error .check {
  opacity: 0;
  transition: 0.3s ease;
}

.front,
.back {
  backface-visibility: hidden;
}
.front {
  z-index: 2;
  transform: rotateY(0deg);
}
.back {
  transform: rotateY(180deg);
}

.rad-progress,
.alt-state {
  height: 80px;
  width: 50px;
  border-radius: 100%;
}
.rad-progress {
  transform: translate(-50%, -50%) rotateZ(-90deg);
}
.progress-background {
  stroke: rgba(0, 0, 0, 0.08);
  stroke-width: 8;
  fill: none;
}
.progress-bar {
  stroke: #2195cd;
  stroke-width: 8;
  fill: none;
}
.progress-label {
  font-family: 'Droid Sans Mono';
  font-size: 14px;
}

.icon {
  height: 30px;
  width: 50px;
  transition: 0.3s 0.3s ease;
}
.icon.check {
  background: url('https://skytango.com/wp-content/uploads/2016/11/White-Checkmark-icon.png') center no-repeat;
  background-size: 130%;
}
.icon.error {
  background: url('http://aspiredm.com/wp-content/themes/Aspire2016/library/images/menu-cross.png') center no-repeat;
  background-size: 90%;
}

</style>