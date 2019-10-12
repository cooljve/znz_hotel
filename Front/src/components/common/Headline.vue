<template>
  <div class="headline">
    <slot></slot>
    <hr style="border:none;border-top:3px solid #F1B709;width: 100px;margin: 0px" />
    <br>
  </div>
</template>

<script>
    export default {
      name: "Headline"
    }
</script>

<style scoped>
  .headline{
    clear: both;
    float: left;
    font-size: 20px;
    font-weight: bolder;
    margin: 0px;
  }
</style>
