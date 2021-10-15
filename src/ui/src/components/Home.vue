<template>
  <v-container class="home">

    <v-row justify="space-around">
      <v-col lg="3" md="5">
        Sword Explorer Notes
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
  export default {
    name: 'Home',

    data: function () {
      return {
      }
    },

    props: {
      username: String,
    },

    mounted: function () {
      console.log(`mounted: user: [${this.$props.username}]`);
    },
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
