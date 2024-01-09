<template>
  <NabCar style="font-size: 21px"/>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color:#18b5a6;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

body{
  background-color: #393d49;
}
</style>
<script setup lang="ts">
import NabCar from "@/views/NabCar.vue";
</script>