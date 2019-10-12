<template>
  <div class="block">
    <el-container style="height: 500px">
      <el-aside>
        <setting-menu></setting-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import SettingMenu from '../../components/admin/SettingMenu'
  export default {
    components: {SettingMenu},
    data() {
      return {
      }
    }
  };
</script>

<style scoped>
  .el-main{
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
</style>
