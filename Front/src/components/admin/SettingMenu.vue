<template>
  <div>
    <el-col class="grid-content">
      <el-menu
        @select="handleSelect"
        router
        :default-active="$route.path"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/admin/setting/info">
          <i class="el-icon-info"></i>
          <span>个人信息</span>
        </el-menu-item>
        <el-menu-item index="/admin/setting/frontUser">
          <i class="el-icon-question"></i>
          <span slot="title">前台员工管理</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </div>
</template>

<script>
  export default {
    methods: {
      handleSelect(key,keyPath){

      }
    }
  }
</script>

<style scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .el-menu{
    height: 500px;
  }
  .el-menu-item{
    font-size: 20px;
  }
</style>
