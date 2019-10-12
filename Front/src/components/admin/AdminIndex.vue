<template>
  <div>
    <el-container>
      <el-header style="padding: 0px">
        <home-tabs></home-tabs>
      </el-header>
      <el-main style="padding: 10px 0 10px 0;background-color: #D3DCE6">
        <router-view></router-view>
      </el-main>
      <el-footer style="background-color:#545c64"></el-footer>
    </el-container>
  </div>
</template>

<script>
  import HomeTabs from '../../components/admin/HomeTabs'
  import TestHeader from '../../components/common/TestHeader'

  export default {
    name: "AdminIndex",
    components: {HomeTabs, TestHeader}
  }
</script>

<style scoped>

</style>
