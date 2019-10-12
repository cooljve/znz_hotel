<template>
  <div class="block">
    <el-card shadow="always" v-loading="loading">
      <div slot="header">
        <span style="font-size: larger;">个人信息</span>
        <el-button style="float: right;" type="primary" @click="modifyAdmin">修改</el-button>
      </div>
      <div>
        <el-form :model="currAdmin" ref="currAdmin" :rules="currAdminRules" label-width="100px" style="width:300px;margin:auto">
          <el-form-item label="用户名" prop="aid">
            <el-input v-model="currAdmin.aid" style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="currAdmin.realName" style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="currAdmin.idCard" style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱地址" prop="emailAddress">
            <el-input v-model="currAdmin.emailAddress" style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱授权码" prop="emailAddress">
            <el-input type="password" v-model="currAdmin.emailPwd" style="width: 220px"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
  import global from '../common/GlobalVariable'

  export default {
    name: "Information",
    data() {
      return {
        currAdmin: {},
        currAdminRules: {
          aid: [{validator: global.validateUserName, trigger: 'blur'}],
          realName: [{validator: global.validateName, trigger: 'blur'}],
          idCard: [{validator: global.validateIdCard, trigger: 'blur'}],
          emailAddress: [{validator: global.validateEmail, trigger: 'blur'}],
        },
        loading: false,
      }
    },
    mounted() {
      this.initializeAdmin();
    },
    methods: {
      initializeAdmin: function () {
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.$axios.post('user/getAdminByAid', this.currAdmin.aid)
          .then(res=>{
            if(!res.data.success){
              this.$message.error(res.data.message);
            }
            this.currAdmin=res.data.data;
          }).catch(err=>{
          this.$message.error(err);
        })
      },
      modifyAdmin: function () {
        this.$refs['currAdmin'].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.$axios.post('user/modifyAdmin', {
              id: this.currAdmin.id,
              aid: this.currAdmin.aid,
              realName: this.currAdmin.realName,
              idCard: this.currAdmin.idCard,
              emailAddress: this.currAdmin.emailAddress,
              emailPwd: this.currAdmin.emailPwd,
            }).then((res) => {
              setTimeout(() => {
                this.loading = false;
              }, 2000);
              if (!res.data.data) {
                this.$message.error(res.data.message);
                return;
              }
              this.$message.success("修改成功");
              this.$router.go(0);
            }).catch(error => {
              this.$message.error(error);
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
