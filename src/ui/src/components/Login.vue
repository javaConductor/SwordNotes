<template>
  <v-container class="login">

    <v-row justify="space-around">

      <v-col lg="3" md="3">
        <v-text-field class="text-field" label="Username" v-model="username"/>
      </v-col>
      <v-col lg="1" md="4">
        <v-text-field class="text-field" label="Password" v-model="password"/>
      </v-col>
      <v-col lg="1" md="3">
        <v-btn @click="onLogin">Login</v-btn>
      </v-col>

    </v-row>

  </v-container>
</template>

<script>
  import backend from '../backend';

  export default {
    name: 'Login',
    data: function () {
      return {
        // username : "",
        // password: ""
      }
    },
    props: {
      msg: String,
      username: String,
      password: String,
      authenticated: Boolean
    },
    methods: {
      async onLogin() {
        const [u, p] = [this.$props.username, this.$props.password];
        console.log(`onLogin: user:[${u}]  pswd:[${p}]`);
        const [, err] = await backend.login(u, p);
        if (err) {
          alert(err);
        }
      },
      mounted: function () {
        console.log(`mounted: user:[${this.$props.username}]  pswd:[${this.$props.password}]`);
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .text-field {
    display: inline-block;
    margin: 0 10px;
    width: 100px;
    height: 20px;
  }

  a {
    color: #42b983;
  }
</style>
