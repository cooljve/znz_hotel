<template>
  <el-card shadow="always">
    <Headline>房间查询</Headline>
    <div style="clear: both">
      <el-form :inline="true" ref="roomSelectForm" :model="roomSelectForm" :rules="selectRoomRules" label-width="80px">
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model.number="roomSelectForm.roomNumber" placeholder="请输入准确房间号" clearable></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="roomSelectForm.type" clearable multiple>
            <el-option v-for="type in roomTypes" :key="type.roomType" :label="type.roomType" :value="type.roomType">
              <span style="float: left">{{ type.roomType }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{'床位数'+ type.bedCount }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="roomSelectForm.status" clearable multiple>
            <el-option v-for="status in roomStatus" :key="status.value" :label="status.label"
                       :value="status.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格区间" style="clear: left">
          <el-col :span="11">
            <el-form-item prop="minPrice" style="margin-right: 0px">
              <el-input v-model.number="roomSelectForm.minPrice" placeholder="最低价" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="maxPrice" style="margin-right: 0px">
              <el-input v-model.number="roomSelectForm.maxPrice" placeholder="最高价" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item style="float: right;margin-right: 100px">
          <el-button type="primary" @click="selectRoom">查询</el-button>
          <el-button @click="resetForm('roomSelectForm')">重置</el-button>
          <el-button type="primary" @click="showBookDialog=true">预订房间</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog title="预订" :visible.sync="showBookDialog" center :before-close="closeBookDialog">
      <el-form :model="bookForm.order" :rules="bookFormRules" ref="bookForm" label-width="80px" style="height: 300px">
        <el-form-item label="房间类型">
          <el-select v-model="bookForm.type" clearable>
            <el-option v-for="type in roomTypes" :key="type.roomType" :label="type.roomType" :value="type.roomType">
              <span style="float: left">{{ type.roomType }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{'床位数'+ type.bedCount }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="bookForm.order.realName" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="bookForm.order.idCard" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="emailAddress">
          <el-input v-model="bookForm.order.emailAddress" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="入住时间" prop="date">
          <el-date-picker
            v-model="bookForm.order.date"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['12:00:00', '12:00:00']"
            :picker-options="checkInPickerOptions"
          ></el-date-picker>
        </el-form-item>
        <!--<el-form-item label="邮件通知" prop="inform">-->
          <!--<el-checkbox v-model="bookForm.order.inform"></el-checkbox>-->
        <!--</el-form-item>-->
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="bookRoom">提交</el-button>
        <el-button @click="closeBookDialog">取消</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  import Headline from '../../common/Headline'
  import global from '../../common/GlobalVariable'

  export default {
    name: "RoomSelectCriteria",
    components: {Headline},
    props: {
      selectLoading: {
        type: Boolean,
        default: false
      },
      types: {
        type: Array,
        default: []
      }
    },
    data() {
      const checkNumber = (rule, value, callback) => {
        if (!Number.isInteger(value)) {
          callback(new Error('必须为数字'))
        } else if (value < 0) {
          callback(new Error('必须大于0'))
        }
        callback();
      };
      var checkMaxPrice = (rule, value, callback) => {
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('必须为数字'));
          } else {
            if (value < this.roomSelectForm.minPrice) {
              callback(new Error('该值应大于等于最小值'))
            }
            callback();
          }
        }, 1000);
      };
      return {
        roomTypes: this.types,
        roomStatus: global.roomStatus,
        roomSelectForm: {
          roomNumber: '',
          type: '',
          status: '',
          minPrice: 0,
          maxPrice: 0,
        },
        selectRoomRules: {
          minPrice: [{validator: checkNumber}],
          maxPrice: [{validator: checkMaxPrice}],
        },
        bookForm: {
          type: '',
          order: {
            oid: '',
            realName: '',
            idCard: '',
            date: ['', ''],
            emailAddress: '',
            inform: true,
            rate: 0,
            frontUserName: '',
          }
        },
        bookFormRules: {
          realName: [{validator: global.validateName, trigger: 'blur'}],
          idCard: [{validator: global.validateIdCard, trigger: 'blur'}],
          emailAddress: [{validator: global.validateEmail, trigger: 'blur'}],
        },
        showBookDialog: false,
        checkInPickerOptions:{
          disabledDate(time){
            return time.getTime() < Date.now();
          }
        },
      }
    },
    watch: {
      types: function (val) {
        this.updateTypes(val);
      }
    },
    mounted: function () {
      this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
    },
    methods: {
      updateTypes: function (val) {
        this.roomTypes = val;
      },
      resetForm: function (name) {
        this.$refs[name].resetFields();
      },
      selectRoom: function () {
        this.$refs['roomSelectForm'].validate((valid) => {
          if (valid) {
            this.$emit('changeLoading', true);
            this.$axios.post('room/select', this.roomSelectForm)
              .then(res => {
                this.$emit('getSpecialRooms', res.data.data);
                this.$emit('changeLoading', false);
              }).catch(error => {
              this.$message.error(error);
              return;
            })
            this.$message('开始查询');
          } else {
            return false;
          }
        })
      },
      bookRoom: function () {
        this.$refs['bookForm'].validate((valid) => {
          if (valid) {
            this.bookForm.order.frontUserName = this.currFrontUser.userName;
            this.$axios.post('room/book', this.bookForm)
              .then(res => {
                if (res.data.data === 0) {
                  this.$message.warning('抱歉，没有空房');
                  return;
                }
                this.$message.success('成功预订房间' + res.data.data + ',请准时入住');
                setTimeout(() => {
                  this.$router.go(0);
                },2000);
              }).catch(error => {
              this.$message.error(error);
              return;
            });
          } else {
            return false;
          }
        })
      },
      closeBookDialog: function () {
        this.showBookDialog = false;
        this.$refs['bookForm'].resetFields();
      },
    }
  }
</script>

<style scoped>

  .el-form-item {
    float: left;
    margin-right: 80px;
  }

</style>
