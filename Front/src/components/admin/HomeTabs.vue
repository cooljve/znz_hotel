<template>
  <div>
    <div>
      <el-menu
        router
        :default-active="$route.children"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/admin/home" v-if="isAdmin">主页</el-menu-item>
        <el-submenu index="room">
          <template slot="title">客房管理</template>
          <el-menu-item v-if="!isAdmin" index="/frontUser/room/checkIn">入住退房</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/admin/room/status">房态</el-menu-item>
        </el-submenu>
        <el-menu-item index="/order">订单查看</el-menu-item>
        <el-submenu index="report" v-if="isAdmin">
          <template slot="title">财务管理</template>
          <el-menu-item index="/admin/report/set">数值设置</el-menu-item>
          <el-menu-item index="/admin/report/days">收支表</el-menu-item>
        </el-submenu>
        <el-tooltip v-if="isAdmin" class="item" effect="dark" content="设置" placement="bottom">
          <el-menu-item index="/admin/setting"><i class="el-icon-setting"></i></el-menu-item>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="退出登录" placement="bottom">
          <el-button class="button" icon="el-icon-close" @click="quit"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="修改密码" placement="bottom">
          <el-button class="button" icon="el-icon-edit-outline" @click="openChgPwdWin"></el-button>
        </el-tooltip>
      </el-menu>
    </div>
    <el-dialog title='修改密码' :visible.sync="chgPwdWinShow" @close="closeChgPwdWin">
      <el-form :model="changePasswordForm" ref="changePasswordForm" :rules="pwdRules" label-position="right"
               label-width="120px" status-icon>
        <el-form-item label="原密码" prop="oldPwd">
          <el-input placeholder="请输入原密码" type="password" v-model="changePasswordForm.oldPwd" clearable></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input placeholder="请输入新密码" type="password" v-model="changePasswordForm.password" clearable></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPwd">
          <el-input placeholder="请再次输入新密码" type="password" v-model="changePasswordForm.checkPwd"
                    clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="changePwd">确认</el-button>
        <el-button @click="closeChgPwdWin">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import md5 from 'js-md5'

  export default {
    data() {
      var validatePwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'));
        } else {
          var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,12}$/;
          if (!reg.test(value)) {
            callback(new Error('密码应包含至少一个大写字母、小写字母和数字，且长度为6-12个字符'));
          } else {
            if (this.changePasswordForm.checkPwd !== '') {
              this.$refs.changePasswordForm.validateField('checkPwd');
            }
            callback();
          }
        }
      };
      var validatePwd2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.changePasswordForm.password) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      };
      return {
        chgPwdWinShow: false,
        changePasswordForm: {
          oldPwd: '',
          password: '',
          checkPwd: '',
        },
        pwdRules: {
          oldPwd: [
            {required: true, message: '该项不能为空', trigger: 'blur'},
          ],
          password: [
            {validator: validatePwd, trigger: 'blur'}
          ],
          checkPwd: [
            {validator: validatePwd2, trigger: 'change'},
          ],
        },
        activeIndex: '1',
        currAdmin: {},
        isAdmin:false,
      };
    },
    mounted() {
      this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
      this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
      if(this.currAdmin!=null){
        this.isAdmin=true;
      }else{
        this.isAdmin=false;
      }
      console.log('currFrontUser', this.currFrontUser);
      console.log('currAdmin', this.currAdmin);
    },
    methods: {
      handleSelect(key, keyPath) {
        this.$router.push(key);
      },
      openChgPwdWin() {
        this.changePasswordForm = {};
        this.chgPwdWinShow = true;
      },
      closeChgPwdWin() {
        this.chgPwdWinShow = false;
        this.$refs.changePasswordForm.resetFields();
      },
      quit() {
        localStorage.removeItem('name');
        localStorage.removeItem('currAdmin');
        localStorage.removeItem('currFrontUser');
        this.$router.push('/login');
      },
      changePwd() {
        this.$refs['changePasswordForm'].validate((valid) => {
          if (valid) {
            let oldPwd=md5(this.changePasswordForm.oldPwd);
            let newPwd=md5(this.changePasswordForm.password);
            let url = '';
            let username = '';
            if (this.isAdmin) {
              url='/user/adminChangePwd';
              username=this.currAdmin.aid;
            }else {
              url = 'user/frontUserChangePwd';
              username = this.currFrontUser.userName;
            }
            this.$axios.post(url, {
              aid: username,
              oldPwd: oldPwd,
              newPwd: newPwd
            }).then(res => {
              if (!res.data.success) {
                this.$message.error(res.data.message);
                return;
              }
              this.$message.success('修改成功');
              this.closeChgPwdWin();
            });
          } else {
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .button {
    background-color: #545c64;
    color: #909399;
    border: 0px;
    margin-top: 10px;
    float: right;
  }
</style>
