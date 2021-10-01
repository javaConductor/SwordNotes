<template>
  <v-container class="login">

    <v-row justify="space-around" v-if="!authenticated">

      <v-col lg="3" md="5">
        <v-text-field class="text-field" label="Username" v-model="username"/>
      </v-col>
      <v-col lg="3" md="5">
        <v-text-field type="password" class="text-field" label="Password" v-model="password"/>
      </v-col>
      <v-col lg="1" md="1">
        <v-btn @click="onLogin">Login</v-btn>
      </v-col>


    </v-row>

    <v-row justify="space-around" v-if="authenticated">

      <v-col lg="3" md="3">
        Welcome {{username}}!
      </v-col>
      <v-col lg="1" md="4">
        <v-btn @click="onLogout">Logout</v-btn>
      </v-col>

    </v-row>

  </v-container>
</template>

<script>
  import backend from '../backend';
  import pubsub from '../pubsub';

  export default {
    name: 'Login',
    data: function () {
      return {
        accessToken: "",
        refreshToken: ""
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
        pubsub.$emit("user.login", {authenticated:true, username: this.username, password: this.password});
      },

      async onLogout() {
        console.log(`onLogout`);
        const err = await backend.logout();
        if (err) {
          alert(err);
        }
        pubsub.$emit("user.login", {authenticated:false, username: this.username, password: this.password});
      },
    },
    mounted: function () {
      console.log(`mounted: user:[${this.$props.username}]  pswd:[${this.$props.password}] auth:[${this.$props.authenticated}]`);
    },
    computed: {

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
</style>
