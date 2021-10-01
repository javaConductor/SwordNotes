<template>
  <div id="app">
    <Login :username="username" :password="password" :authenticated="isAuthenticated" />
  </div>
</template>

<script>
  import Login from './components/Login.vue'
  import backend from './backend';
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
      Login
    },
    created() {
      pubsub.$on("user.login", (auth) => {
        this.isAuthenticated = auth.authenticated;
        this.username = auth.username;
        this.password = auth.password;
      });
    },
    watch: {
      authenticated: function(isAuthenticated, wasAuthenticated) { // watch it
        console.log('watch: authenticated changed from ', isAuthenticated, ' to ', wasAuthenticated);
        this.isAuthenticated = isAuthenticated;
      }
    },
    methods: {
      authenticated() {
        const a = !!backend.authenticated();
        console.log(`App:authenticated() => ${a}`);
        return a;
      }
    }
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
