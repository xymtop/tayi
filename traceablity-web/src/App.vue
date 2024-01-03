<template>
  <router-view></router-view>
</template>
<style>
@import "./styles/index.css";

#app {
  height: 100%;
}
body {
  height: 100%;
}
html {
  height: 100%;
}
@media print {
  body * {
    visibility: hidden;
  }
  #myDiv, #myDiv * {
    visibility: visible;
  }
  #myDiv {
    display: block;
    width: auto; /* 可以设置为auto，或者用具体数值指定大小 */
    height: auto; /* 同上，可以是具体的数值 */
    margin: 0;
    padding: 0;
    position: absolute;
    left: 0;
    top: 0;
  }
}
</style>
