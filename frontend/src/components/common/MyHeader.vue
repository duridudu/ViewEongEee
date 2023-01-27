<template>
  <div class="Header">
      <router-link to="/">
            <img
              alt="logo"
              src="@/assets/image/logo.png"
              class="Header-logo"
            />
      </router-link>
      <nav>
        <a><button ><v-icon size="large">mdi-bell-ring</v-icon></button></a>
        <a><button><v-icon size="large">mdi-account</v-icon></button></a>
        <a><button><v-icon size="large">mdi-menu</v-icon></button></a>
        <!-- <Link to="/login">
          <PersonOutline
           fontSize="large"
          ></PersonOutline>
        </Link> -->
      </nav>
    </div>
</template>

<script>
export default {
  name: "MyHeader",
};
</script>

<style scoped>
.Header {
  /* position: fixed; */
  left: 0;
  top: 0;
  width: 100%;
  height: 100px;
  background-color: white;
  display: inline-block;
}
.HeaderMain {
  width: 100%;
  height: 100px;
  background-color: white;
}

.Header-logo {
  margin-top: 20px;
  margin-left: 200px;
  height: 80px;
}

nav {
  margin-top: 40px;
  margin-right: 200px;
  display: inline-block;
  float: right;
}

.logo-mascot {
  width: 8%;
}

.logo-text {
  width: 18%;
}

img {
  display: inline-block;
}

span {
  align-items: right;
}

a {
  align-items: right;
  margin-right: 2rem;
  color: rgb(49, 49, 49);
}

.search-bar{
  display: flex;
  justify-content: center;
  align-items: center;
}
.search-field {
  text-align: center;
  width: 50%;
}

</style>
