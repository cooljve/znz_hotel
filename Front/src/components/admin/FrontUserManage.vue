<template>
  <div class="block">
    <el-card shadow="always">
      <Headline>前台员工</Headline>
      <div style="float: right">
        <el-button icon="el-icon-plus" @click="showAddFrontUserDialog" type="text">添加前台员工</el-button>
      </div>
      <el-table :data="frontStaffTable" border v-loading="loading">
        <el-table-column label="用户名" width="200px" align="center" prop="userName"></el-table-column>
        <el-table-column label="姓名" width="100px" align="center" prop="realName"></el-table-column>
        <el-table-column label="身份证号" width="163px" align="center" prop="idCard"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="resetFrontStaffPwd(scope.$index,scope.row)"
                       type="primary" size="small">重置密码
            </el-button>
            <el-button @click="modifyFrontStaffInfo(scope.$index,scope.row)"
                       type="primary" size="small">修改
            </el-button>
            <el-button @click="deleteFrontStaff(scope.$index,scope.row)"
                       type="primary" size="small">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="新增前台" center :visible.sync="addUserDialog" :before-close="closeDialog">
        <el-form :model="userForm" ref="userForm" :rules="userFormRules" label-width="100px">
          <el-form-item label="用户名" prop="userName" v-if="!modifyUser">
            <el-input v-model="userForm.userName" style="width: 220px" placeholder="请输入用户名" clearable></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="userName" v-else>
            <el-input v-model="userForm.userName" disabled style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="userForm.realName" style="width: 220px" placeholder="请输入姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" prop="idCard">
            <el-input v-model="userForm.idCard" style="width: 220px" placeholder="请输入身份证号" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" v-if="!modifyUser">
            <el-input type="password" v-model="userForm.password" style="width: 220px" placeholder="请输入密码"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPwd" v-if="!modifyUser">
            <el-input type="password" v-model="userForm.checkPwd" style="width: 220px" placeholder="请再次输入密码"
                      clearable></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="addUser">保存</el-button>
          <el-button plain @click="closeDialog">取消</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import global from '../common/GlobalVariable'
  import Headline from '../common/Headline';
  import md5 from 'js-md5'

  export default {
    name: "FrontUserManage",
    components: {Headline},
    data() {
      var validatePwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'));
        } else {
          var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,12}$/;
          if (!reg.test(value)) {
            callback(new Error('密码应包含至少一个大写字母、小写字母和数字，且长度为6-12个字符'));
          } else {
            if (this.userForm.checkPwd !== '') {
              this.$refs.userForm.validateField('checkPwd');
            }
            callback();
          }
        }
      };
      var validatePwd2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.userForm.password) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      };
      return {
        addUserDialog: false,
        modifyUser: false,
        userForm: {
          userName: '', idCard: '', password: '', checkPwd: '', realName: '',
        },
        userFormRules: {
          userName: [{validator: global.validateUserName, trigger: 'blur'}],
          idCard: [{validator: global.validateIdCard, trigger: 'blur'}],
          password: [{validator: validatePwd, trigger: 'blur'}],
          checkPwd: [{validator: validatePwd2, trigger: 'blur'}],
          realName: [{validator: global.validateName, trigger: 'blur'}],
        },
        frontStaffTable: [],
        loading: false,
      }
    },
    mounted() {
      this.initializeAdmin();
    },
    methods: {
      initializeAdmin: function () {
        this.loading = true;
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.$axios.post('user/getFrontUsersByAid', this.currAdmin.aid)
          .then(res => {
            if (!res.data.success) {
              this.$message.error(res.data.message);
            }
            if (res.data.data != null) {
              this.frontStaffTable = res.data.data;
            }
            this.loading = false;
          }).catch(err => {
          this.$message.error(err);
        })
      },
      showAddFrontUserDialog: function () {
        this.addUserDialog = true;
        this.modifyUser = false;
      },
      closeDialog: function () {
        this.addUserDialog = false;
        this.userForm = {};
        this.$refs['userForm'].resetFields();
      },
      addUser() {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            this.loading = true;
            let url = '';
            let encryptedPwd = '';
            if (this.modifyUser) {
              url = 'user/modifyFrontStaff'
            } else {
              url = 'user/addFrontStaff'
              encryptedPwd = md5(this.userForm.password);
            }
            this.$axios.post(url, {
              aid: this.currAdmin.aid,
              userName: this.userForm.userName,
              idCard: this.userForm.idCard,
              password: encryptedPwd,
              realName: this.userForm.realName,
            }).then((res) => {
              if (res.data.data) {
                this.$message.success('操作成功！');
                this.$router.go(0);
              } else {
                this.$message.error(res.data.message);
              }
            }).catch((error) => {
              this.$message.error('操作失败，请重试！');
            });
            this.loading = false;
          } else {
            return false;
          }
        })
      },
      modifyFrontStaffInfo: function (index, row) {
        this.addUserDialog = true;
        this.modifyUser = true;
        let data = {
          userName: row.userName,
          idCard: row.idCard,
          realName: row.realName,
        };
        this.userForm = data;
      },
      deleteFrontStaff: function (index, row) {
        this.$confirm('确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true;
          this.$axios.post('user/deleteFrontUser', row.userName)
            .then(res => {
              if (!res.data.data) {
                this.$message.error("删除失败，请重试。")
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
          })
          this.loading = false;
        }).catch(() => {
        });
      },
      resetFrontStaffPwd: function (index, row) {
        this.loading = true;
        let pwd = md5('000000');
        this.$axios.post('user/resetFrontUserPwd', {
          aid: row.userName,
          newPwd: pwd
        })
          .then(res => {
            if (!res.data.data) {
              this.$message.error("重置失败，请重试。")
            } else {
              this.$message.success('操作成功！');
              this.$router.go(0);
            }
          }).catch(err => {
        })
        this.loading = false;
      },
    }
  }
</script>

<style scoped>

</style>
