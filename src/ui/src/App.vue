<template>
  <div id="app">
    <v-container>
      <v-row>
        <v-col>
          <Login :username="username" :password="password" :authenticated="isAuthenticated" />
        </v-col>
      </v-row>
      <v-row v-if="isAuthenticated">
        <v-col>
          <Home :username="username"/>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>
  import Login from './components/Login.vue';
  import Home from './components/Home.vue';
  import pubsub from './pubsub';

  export default {
    name: 'App',
    data: function(){
      return {
        username: 'guest',
        password: '',
        isAuthenticated: false
      }
    },
    components: {
      Login, Home
    },
    created() {
      pubsub.$on("user.login", (auth) => {
        this.isAuthenticated = auth.authenticated;
        this.username = auth.username;
        this.password = auth.password;
      });
    },
  }
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
