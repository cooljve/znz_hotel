<template>

</template>

<script>
  const validatePwd = (rule, value, callback) => {
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
  const validatePwd2 = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('请再次输入密码'));
    } else if (value !== this.newUserInfo.password) {
      callback(new Error('两次输入密码不一致'));
    } else {
      callback();
    }
  };
  const validateName = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('姓名不能为空'))
    } else {
      var reg = /^[\u4E00-\u9FA5]{2,5}$/
      if (!reg.test(value)) {
        callback(new Error('请输入正确中文姓名'))
      }
    }
    callback();
  };
  const validateIdCard = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('身份证号不能为空'))
    } else {
      var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!reg.test(value)) {
        callback(new Error('请输入正确的身份证号码'));
      }
      callback();
    }
  };
  const validateEmail = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('邮箱地址不能为空'))
    } else {
      var reg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
      if (!reg.test(value)) {
        callback(new Error('邮箱格式不正确'))
      }
      callback()
    }
  };
  const validatePrice = (rule, value, callback) => {
    if (!value) {
      callback(new Error('该项不能为空'))
    } else {
      if (!Number.isInteger(value)) {
        callback(new Error('必须为数字'))
      } else if (value < 0) {
        callback(new Error('必须大于0'))
      }
      callback();
    }
  };
  const validateUserName = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('用户名不能为空'))
    }else{
      var reg = /^[a-zA-Z0-9_\u4E00-\u9FA5]{6,12}$/;
      if (!reg.test(value)) {
        callback(new Error('用户名只能含大小写字母、数字、下划线和中文字符，长度为6-12位'))
      }
    }
    callback();
  };

  const configOptions = ['wifi', '空调', '有窗', '独卫', '麻将桌', '早餐'];
  const roomTypes = [
    {roomType: '大床房', bedCount: 2},
    {roomType: '标准间', bedCount: 2},
    {roomType: '三人房', bedCount: 3},
    {roomType: '套房（两人）', bedCount: 2}];
  const roomStatus = [
    {label: '空房干净', value: 'EC'},
    {label: '空房待打扫', value: 'ED'},
    {label: '住人已打扫', value: 'C'},
    {label: '住人待打扫', value: 'D'}];
  export default {
    name: "GlobalVariable",
    validatePwd,
    validatePwd2,
    validateUserName,
    validateName,
    validateIdCard,
    validateEmail,
    validatePrice,
    configOptions,
    roomTypes,
    roomStatus,
    data(){
      return{
        newRoomTypes:[],
      }
    },
    mounted(){
      this.initializeRoomType();
    },
    methods:{
      initializeRoomType:function () {

      },
    }
  }
</script>

<style scoped>

</style>
