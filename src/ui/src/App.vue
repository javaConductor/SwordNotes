<template>
  <div id="app">
    <Login username="guest" password="" authenticated="authenticated()"/>
  </div>
</template>

<script>
  import Login from './components/Login.vue'
  import backend from './backend';

  export default {
    name: 'App',
    components: {
      Login
    },
    methods: {
      authenticated() {
        return backend.authenticated();
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
