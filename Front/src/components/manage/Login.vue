<template>
  <el-container style="height: 700px;">
    <el-main>
      <el-card shadow="always" style="width: 450px;height: 330px;margin:auto;">
        <div>
          <h1 style="margin-top: 10px">登录</h1>
          <el-form ref="loginInfo" :rules="loginRules" :model="loginInfo" style="margin: 20px" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input type="text" v-model="loginInfo.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="loginInfo.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="用户类型" prop="type">
              <el-select v-model="loginInfo.type" placeholder="请选择">
                <el-option label="前台" value="frontUser"></el-option>
                <el-option label="酒店管理员" value="admin"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button @click="login" type="primary">登录</el-button>
              <el-button @click="register" type="primary" plain>注册</el-button>
              <el-button @click="resetForm('loginInfo')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
  import TestHeader from '../common/TestHeader'
  import TestFooter from '../common/TestFooter'
  import md5 from 'js-md5'

  export default {
    name: 'Login',
    components: {TestHeader, TestFooter},
    data() {
      return {
        loginInfo: {username: '', password: '', type: 'admin'},
        responseResult: [],
        loginRules: {
          username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
          type: [{required: true, message: '请选择用户类型', trigger: 'change'}],
        },
      }
    },
    methods: {
      login() {
        this.$refs['loginInfo'].validate((valid) => {
          if (valid) {
            let password = md5(this.loginInfo.password);
            this.$axios
              .post('/user/login', {
                username: this.loginInfo.username,
                password: password,
                type: this.loginInfo.type
              })
              .then(res => {
                if (!res.data.success) {
                  this.$message.error(res.data.message);
                  return;
                }
                let data = res.data;
                localStorage.setItem('token', data.token);
                console.log('login', data.data);
                if (this.loginInfo.type === 'admin') {
                  localStorage.setItem('name', data.data.aid);
                  localStorage.setItem('currAdmin', JSON.stringify(data.data));
                  this.$router.push({name: 'admin'});
                } else {
                  localStorage.setItem('name', data.data.aid);
                  localStorage.setItem('currFrontUser', JSON.stringify(data.data));
                  this.$router.push({name: 'frontUser'});
                }
              })
              .catch(failResponse => {
                this.$message.error(failResponse);
              })
          } else {
            return false;
          }
        })
      },
      register() {
        this.$router.push({path: '/register'})
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>
<style scoped>

  .el-main {
    background: #ffc940bd url("../../assets/fengjing.jpg");
    background-size:100% 100%;
  }

</style>
