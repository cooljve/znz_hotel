<template>
  <div class="back">
    <el-container>
      <el-main>
        <el-card shadow="always" style="width: 600px;height: 540px;margin:auto;">
          <h1 style="margin-top: 10px">用户注册</h1>
          <el-form ref="newUserInfo" :model="newUserInfo" :rules="addUserRules" label-width="100px" status-icon>
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="newUserInfo.userName" placeholder="请输入用户名" clearable></el-input>
            </el-form-item>
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="newUserInfo.realName" placeholder="请输入姓名" clearable></el-input>
            </el-form-item>
            <el-form-item label="身份证号码" prop="idCard">
              <el-input v-model="newUserInfo.idCard" placeholder="请输入身份证号" clearable></el-input>
            </el-form-item>
            <el-form-item label="邮箱地址" prop="emailAddress">
              <el-input v-model="newUserInfo.emailAddress" placeholder="请输入邮箱地址" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="newUserInfo.password" placeholder="请输入密码" clearable></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPwd">
              <el-input type="password" v-model="newUserInfo.checkPwd" placeholder="请再次输入密码" clearable></el-input>
            </el-form-item>
            <!--<el-form-item label="用户类型" prop="type">-->
              <!--<el-select v-model="newUserInfo.type" placeholder="请选择">-->
                <!--<el-option label="住客" value="customer"></el-option>-->
                <!--<el-option label="酒店管理员" value="admin"></el-option>-->
              <!--</el-select>-->
            <!--</el-form-item>-->
            <el-form-item>
              <el-button @click="addUser" type="primary">提交</el-button>
              <el-button @click="goBack" type="primary" plain>返回登录</el-button>
              <el-button @click="resetForm('newUserInfo')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import TestHeader from '../common/TestHeader'
  import TestFooter from '../common/TestFooter'
  import global from '../common/GlobalVariable'
  import md5 from 'js-md5'

  export default {
    name: "AddUser",
    components: {TestHeader, TestFooter},
    data() {
      var validatePwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'));
        } else {
          var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,12}$/;
          if (!reg.test(value)) {
            callback(new Error('密码应包含至少一个大写字母、小写字母和数字，且长度为6-12个字符'));
          } else {
            if (this.newUserInfo.checkPwd !== '') {
              this.$refs.newUserInfo.validateField('checkPwd');
            }
            callback();
          }
        }
      };
      var validatePwd2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.newUserInfo.password) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      };
      return {
        newUserInfo: {
          userName: '', idCard: '', password: '', checkPwd: '', emailAddress: '', realName: '', type: 'admin',
        },
        addUserRules: {
          userName: [{validator: global.validateUserName, trigger: 'blur'}],
          idCard: [{validator: global.validateIdCard, trigger: 'blur'}],
          password: [{validator: validatePwd, trigger: 'blur'}],
          checkPwd: [{validator: validatePwd2, trigger: 'blur'}],
          emailAddress: [{validator: global.validateEmail, trigger: 'blur'}],
          realName: [{validator: global.validateName, trigger: 'blur'}],
          // type: [{required: true, message: '请选择用户类型', trigger: 'change'}],
        }
      }
    },
    methods: {
      addUser() {
        this.$refs['newUserInfo'].validate((valid) => {
          if (valid) {
            let encryptedPwd = md5(this.newUserInfo.password);
            this.$axios.post('user/register', {
              userName: this.newUserInfo.userName,
              idCard: this.newUserInfo.idCard,
              password: encryptedPwd,
              emailAddress: this.newUserInfo.emailAddress,
              realName: this.newUserInfo.realName,
              type: this.newUserInfo.type,
            }).then((res) => {
              if (res.data.data) {
                this.$message.success('注册成功，请登录！');
                this.$router.push('login');
              } else {
                this.$message.error(res.data.message);
              }
            }).catch((error) => {
              this.$message.error('注册失败，请重试！');
            })
          } else {
            return false;
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      goBack() {
        this.$router.push('/login');
      }
    }
  }
</script>


<style scoped>

  .back {
    background-image: url("../../assets/fengjing2.jpg");
    background-size: 100% 100%;
    height: 700px;
  }
</style>
