<template>
  <div class="block">
    <el-container v-if="showTab">
      <el-aside>
        <el-row>
          <label style="float: left;color: #fff;font-size: 18px">房态</label>
        </el-row>
        <el-row>
          <el-col :span="spanNum">
            <el-badge :value="roomCount.total">
              <el-tag style="color: #fff">全部</el-tag>
            </el-badge>
          </el-col>
          <el-col :span="spanNum"></el-col>
        </el-row>
        <el-row>
          <el-col :span="spanNum">
            <el-badge :value="roomCount.emptyDirty">
              <el-tag type="danger">空脏</el-tag>
            </el-badge>
          </el-col>
          <el-col :span="spanNum">
            <el-badge :value="roomCount.emptyClean">
              <el-tag type="success">空净</el-tag>
            </el-badge>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="spanNum">
            <el-badge :value="roomCount.dirty">
              <el-tag type="info">住脏</el-tag>
            </el-badge>
          </el-col>
          <el-col :span="spanNum">
            <el-badge :value="roomCount.clean">
              <el-tag type="warning">住净</el-tag>
            </el-badge>
          </el-col>
        </el-row>
        <br>
        <br>
        <br>
        <el-row>
          <el-button icon="el-icon-circle-plus" type="primary" @click="showAddDialog = true">添加</el-button>
          <el-button icon="el-icon-circle-plus" type="primary" @click="multipleAddRooms">批量添加</el-button>
        </el-row>
      </el-aside>
      <el-main>
        <room-manage-info :rooms="rooms" :roomTypes="roomTypes"></room-manage-info>
      </el-main>
    </el-container>
    <el-container v-else>
      <h1>请先添加酒店。</h1>
    </el-container>
    <el-dialog title="添加房间" :visible.sync="showAddDialog" :before-close="resetForm" center>
      <el-form :model="newRoom" label-width="100px" ref="newRoom" :rules="newRoomRules">
        <el-form-item label="楼层" prop="floorNum">
          <el-input v-model.number="newRoom.floorNum" style="width: 220px" clearable></el-input>
        </el-form-item>
        <el-form-item label="房间数量" v-if="multipleAdd" prop="count">
          <el-input-number v-model="newRoom.count" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="newRoom.type">
            <el-option v-for="type in roomTypes" :key="type.roomType" :label="type.roomType" :value="type.roomType">
              <span style="float: left">{{ type.roomType }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{'床位数'+ type.bedCount }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="newRoom.status">
            <el-option label="空房干净" value="EC"></el-option>
            <el-option label="空房待打扫" value="ED"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input
            v-model.number="newRoom.price"
            style="width: 295px"
            clearable
          >
            <template slot="append">元/晚</template>
          </el-input>
        </el-form-item>
        <el-form-item label="房间配置" prop="configuration">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选
          </el-checkbox>
          <el-checkbox-group v-model="newRoom.configuration" @change="handleCheckedChange">
            <el-checkbox v-for="config in configs" :label="config" :key="config"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="addRoom">添加</el-button>
        <el-button plain @click="resetForm">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import RoomManageInfo from './RoomManageInfo'
  import global from '../../common/GlobalVariable'

  export default {
    name: "RoomManage",
    components: {RoomManageInfo},
    data() {
      return {
        roomCount: {},
        spanNum: 9,
        showAddDialog: false,
        newRoom: {
          floorNum: '',
          status: '',
          type: '',
          price: 0,
          configuration: ['wifi', '空调'],
          count: 1,
        },
        roomTypes: [],
        newRoomRules: {
          floorNum: [{validator: global.validatePrice, trigger: 'blur'}],
          type: [{required: true, message: '请选择房间类型', trigger: 'change'}],
          status: [{required: true, message: '请选择状态', trigger: 'change'}],
          price: [{validator: global.validatePrice, trigger: 'blur'}],
        },
        configs: global.configOptions,
        checkAll: false,
        isIndeterminate: true,
        multipleAdd: false,
        currAdmin: {},
        showTab: false,
        rooms: [],
      }
    },
    mounted() {
      this.initializeRooms();
    },
    methods: {
      addRoom: function () {
        this.$refs['newRoom'].validate((valid) => {
          if (valid) {
            if (this.multipleAdd) {
              this.$axios.post('room/addRoomBatch', {
                aid: this.currAdmin.aid,
                floorNum: this.newRoom.floorNum,
                status: this.newRoom.status,
                type: this.newRoom.type,
                price: this.newRoom.price,
                configuration: this.newRoom.configuration,
                count: this.newRoom.count,
              })
                .then(res => {
                  if (!res.data.success) {
                    this.$message.error(res.data.message);
                    return;
                  }
                  localStorage.setItem('currAdmin', JSON.stringify(res.data.data));
                  this.resetForm();
                  this.$router.go(0);
                })
                .catch(error => {
                  this.$message.error(error);
                })
            } else {
              this.$message('添加');
              this.$axios.post('room/addRoom', {
                aid: this.currAdmin.aid,
                floorNum: this.newRoom.floorNum,
                status: this.newRoom.status,
                type: this.newRoom.type,
                price: this.newRoom.price,
                configuration: this.newRoom.configuration,
                count: this.newRoom.count,
              })
                .then(res => {
                  if (!res.data.success) {
                    this.$message.error(res.data.message);
                    return;
                  }
                  localStorage.setItem('currAdmin', JSON.stringify(res.data.data));
                  this.resetForm();
                  this.$router.go(0);
                })
                .catch(error => {
                  this.$message.error(error);
                })
            }
          } else {
            this.$message('错误');
            return false;
          }
          this.multipleAdd = false;
        })
      },
      multipleAddRooms: function () {
        this.showAddDialog = true;
        this.multipleAdd = true;
      },
      resetForm: function () {
        this.$refs['newRoom'].resetFields();
        this.showAddDialog = false;
        this.multipleAdd = false;
      },
      handleCheckAllChange: function (val) {
        this.newRoom.configuration = val ? global.configOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedChange: function (value) {
        let count = value.length;
        this.checkAll = count === this.configs.length;
        this.isIndeterminate = count > 0 && count < this.configs.length;
      },
      initializeRooms: function () {
        const loading = this.$loading({
          lock: true,
          text: '拼命加载中',
          spinner: 'el-icon-loading',
          background: 'rgba(0,0,0,0.7)'
        });
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        if (this.currAdmin.haveHotel) {
          this.showTab = true;
          this.$axios.post('room/getAllRooms', this.currAdmin.aid)
            .then(res => {
              this.roomCount = res.data.data.roomCount;
              this.rooms = res.data.data.roomDtoList;
              if (res.data.data.roomTypeDtos.length === 0) {
                this.roomTypes = global.roomTypes;
              } else {
                this.roomTypes = res.data.data.roomTypeDtos;
              }
            }).catch(error => {
          });
        } else {
          this.showTab = false;
        }
        setTimeout(() => {
          loading.close();
        }, 2000)
      }
    }
  }
</script>

<style scoped>
  .el-aside {
    background-color: #545c64;
    height: 500px;
  }

  .el-row {
    margin: 10px;
  }

  .el-tag {
    font-size: 16px;
    font-weight: bolder;
  }

  .el-form-item {
    margin-bottom: 20px;
  }
</style>
