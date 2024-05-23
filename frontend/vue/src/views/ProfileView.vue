

<template>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">

  <div class="container">

    <div class="row">
      <div class="col-12">
        <div class="my-5">
          <h3>My Profile</h3>
          <hr>
        </div>
        <form class="file-upload">
          <div class="row mb-5 gx-5">
            <!-- Contact detail -->
            <div class="col-xxl-6 mb-5 mb-xxl-0">
              <div class="bg-secondary-soft px-4 py-5 rounded">
                <div class="row g-3">
                  <h4 class="mb-4 mt-0">Contact detail</h4>
                  <!-- First Name -->
                  <div class="col-md-6">
                    <label class="form-label">First Name *</label>
                    <input type="text" class="form-control" placeholder="" aria-label="First name" v-model="username" @input="validateUsername">
                  </div>
                  <!-- Email -->
                  <div class="col-md-6">
                    <label for="inputEmail4" class="form-label">Email *</label>
                    <input type="email" class="form-control" id="inputEmail4" value="example@homerealty.com" v-model="email" @input="validateEmail">
                  </div>

                </div> <!-- Row END -->
              </div>
            </div>
            <!-- Upload profile -->
            <div class="col-xxl-6">
              <div class="bg-secondary-soft px-4 py-5 rounded">
                <div class="row g-3">
                  <h4 class="mb-4 mt-0">Upload your profile photo</h4>
                  <div class="text-center">
                    <!-- Image upload -->
                    <div class="square position-relative display-2 mb-3">
                      <img src="../styles/noname.jpg" width="250px" height="250px" v-if="!urlPath">
                      <img :src="urlPath" width="250px" height="250px"  v-else>

                    </div>
                    <!-- Button -->

                    <input type="file" id="customFile" name="file" @change="uploadPhoto" hidden="">
                    <label class="btn btn-success-soft btn-block" for="customFile">Upload</label>

                    <button type="button" class="btn btn-danger-soft" @click="removePhoto">Remove</button>
                    <!-- Content -->
                    <p class="text-muted mt-3 mb-0"><span class="me-1">Note:</span>Minimum size 300px x 300px</p>
                  </div>
                </div>
              </div>
            </div>
          </div> <!-- Row END -->
          <!-- button -->
          <div class="gap-3 d-md-flex justify-content-md-end text-center">
            <button type="button" class="btn btn-danger btn-lg" @click="logout">Logout</button>
            <button type="button" class="btn btn-primary btn-lg" @click="updateProfile">Update profile</button>
          </div>
        </form> <!-- Form END -->
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import router from "../router/index.js";

export default {
  data(){
    return{
      urlPath: '',
      oldUrlPath: '',
      userData: {
        id: "",
        username: "",
        email: ""
      },
      username: "",
      isNameValid: true,
      email: "",
      isEmailValid: true,
      formData: null,
      isRemovePhoto: false

  };
  },
  mounted(){
    this.token = localStorage.getItem('userToken') || '';
    this.fetchUserData();
  },
  methods: {
    validateUsername(){
      this.isNameValid = this.username.trim().length >= 5;
    },
    validateEmail () {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Регулярний вираз для перевірки електронної пошти
      this.isEmailValid = emailPattern.test(this.email);
    },
    async uploadPhoto(event)
    {

      const file = event.target.files[0];
      if (file && file.type.startsWith('image/')) {
        this.formData = new FormData();
        this.formData.append('file', file);
        this.formData.append('userId', this.userData.id);

        try {
          const response = await fetch('http://localhost:8080/api/files/temp', {
            method: 'POST',
            body: this.formData
          });
          if (response.ok) {
            const fileUrl = await response.text();
            this.urlPath = fileUrl;
            console.log('File URL:', fileUrl);
          } else {
            console.error('Upload failed:', response.statusText);
          }
        } catch (error) {
          console.error('Error:', error);
        }
      } else {
        alert('Please upload a valid image file.');
      }
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
            await router.push("/signIn");
          } else {
            this.userData = response.data;
            this.username = this.userData.username;
            this.email = this.userData.email;

            await this.fetchAvatarUrl();
          }
        } catch (error) {
          console.error('Error:', error);
        }
      }
    },
    async fetchAvatarUrl()
    {
      try {
        const response = await axios.get(`http://localhost:8080/api/files/${this.userData.id}`, {
          headers: {
            Authorization: `Bearer ${this.token}`
          }
        });

        if (response.data ) {
          const fileUrl = await response.data.urlPath;
          this.urlPath = fileUrl;
          this.oldUrlPath  =fileUrl;
          console.log('File URL:', this.oldUrlPath);
        } else {
          console.error('No avatar URL found');
        }
      } catch (error) {
        console.error('Error fetching avatar URL:', error);
      }
    },
    async logout()
    {
      localStorage.removeItem('userToken');
      this.token = '';
      await router.push("/signIn");
    },
    async updateProfile()
    {
      this.validateEmail();
      this.validateUsername();
      if(this.isNameValid && this.isEmailValid)
      {
        if(this.formData!==null) {

          try {
            const response = await fetch('http://localhost:8080/api/files', {
              method: 'POST',
              body: this.formData
            });
            if (response.ok) {
              const fileUrl = await response.text();
              this.urlPath = fileUrl;
              this.oldUrlPath = fileUrl;
              await router.push("/main");
              console.log('File URL:', fileUrl);
            } else {
              console.error('Upload failed:', response.statusText);
            }
          } catch (error) {
            console.error('Error:', error);
          }
        }
        try {
          this.userData.username = this.username;
          this.userData.email = this.email;
          await axios.post('http://localhost:8080/api/update', this.userData);


        }catch(error)
        {
          console.error('Error:', error);
        }
        if(this.isRemovePhoto)
        {
          try {
            const id= this.userData.id;
            await axios.post('http://localhost:8080/api/files/full', id, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            console.log(this.oldUrlPath);
            await axios.post('http://localhost:8080/api/files/delete', this.oldUrlPath, {
              headers: {
                'Content-Type': 'application/json'
              }
            });


          }catch(error)
          {
            console.error('Error:', error);
          }


        }
        await router.push("/main");
      }

    },
    async removePhoto()
    {
      if(this.urlPath !== '')
      {
        if(this.urlPath===this.oldUrlPath) {
          this.isRemovePhoto = true;
        }
        else {
          await axios.post('http://localhost:8080/api/files/delete', this.urlPath);
        }
        this.urlPath = '';
        this.formData = null;

      }
    }
  }



}
</script>
<style scoped>

.bg-secondary-soft {
  background-color: rgba(208, 212, 217, 0.1) !important;
}
.rounded {
  border-radius: 5px !important;
}
.py-5 {
  padding-top: 3rem !important;
  padding-bottom: 3rem !important;
}
.px-4 {
  padding-right: 1.5rem !important;
  padding-left: 1.5rem !important;
}
.file-upload{
  margin-top: 100px;
}
img
{
  background-size: 50px;
}
.file-upload .square {
  height: 250px;
  width: 250px;
  margin: auto;
  vertical-align: middle;
  border: 1px solid #e5dfe4;
  background-color: #fff;
  border-radius: 5px;
}
.text-secondary {
  --bs-text-opacity: 1;
  color: rgba(208, 212, 217, 0.5) !important;
}
.btn-success-soft {
  color: #28a745;
  background-color: rgba(40, 167, 69, 0.1);
}
.btn-danger-soft {
  color: #dc3545;
  background-color: rgba(220, 53, 69, 0.1);
}
.form-control {
  display: block;
  width: 100%;
  padding: 0.5rem 1rem;
  font-size: 0.9375rem;
  font-weight: 400;
  line-height: 1.6;
  color: #29292e;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #e5dfe4;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 5px;
  -webkit-transition: border-color 0.15s ease-in-out, -webkit-box-shadow 0.15s ease-in-out;
  transition: border-color 0.15s ease-in-out, -webkit-box-shadow 0.15s ease-in-out;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out, -webkit-box-shadow 0.15s ease-in-out;
}
</style>