<template>
  <el-dialog
    title="房间详情"
    :visible.sync="showDialog"
    :before-close="closeDialog"
    center
  >
    <el-form :model="room" label-width="80px" style="padding: 0">
      <el-form-item label="房间号">
        <el-input v-model="room.roomNumber" style="width: 220px"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="room.type">
          <el-option v-for="type in types" :key="type.roomType" :label="type.roomType" :value="type.roomType">
            <span style="float: left">{{ type.roomType }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{'床位数'+ type.bedCount }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="room.status">
          <el-option v-if="!room.order.realName" label="空房干净" value="EC"></el-option>
          <el-option v-if="!room.order.realName" label="空房待打扫" value="ED"></el-option>
          <el-option v-if="room.order.realName" label="有人已打扫" value="C"></el-option>
          <el-option v-if="room.order.realName" label="有人待打扫" value="D"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="room.order.realName" label="住客信息">
        <el-collapse>
          <el-collapse-item :title="room.order.realName" style="font-size: 14px">
            <div>
              <div>身份证号：{{room.order.idCard}}</div>
              <div>邮箱地址：{{room.order.emailAddress}}</div>
              <div>
                <span>时间区间 </span>
                <el-date-picker
                  v-model="room.order.date"
                  type="daterange"
                  readonly
                >
                </el-date-picker>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="room.price" style="width: 295px">
          <template slot="append">元/晚</template>
        </el-input>
      </el-form-item>
      <el-form-item label="房间配置">
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选
        </el-checkbox>
        <el-checkbox-group v-model="room.configuration" @change="handleCheckedChange">
          <el-checkbox v-for="config in configs" :label="config" :key="config"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="modifyRoom">修改</el-button>
      <el-button plain @click="closeDialog">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import global from '../../common/GlobalVariable'

  export default {
    name: "RoomDialog",
    props: ['showDialog', 'room', 'roomTypes'],
    data() {
      return {
        configs: global.configOptions,
        checkAll: false,
        isIndeterminate: true,
        types: this.roomTypes,
      }
    },
    watch: {
      roomTypes: function (val) {
        this.updateRoomTypes(val);
      }
    },
    methods: {
      updateRoomTypes: function (val) {
        this.types = val;
      },
      closeDialog: function () {
        this.$emit("closeDialog");
      },
      handleCheckAllChange: function (val) {
        this.room.configuration = val ? global.configOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedChange: function (value) {
        let count = value.length;
        this.checkAll = count === this.configs.length;
        this.isIndeterminate = count > 0 && count < this.configs.length;
      },
      modifyRoom: function () {
        this.$axios.post('room/modifyRoom', this.room)
          .then(res => {
            if (!res.data.success || !res.data.data) {
              this.$message.error(res.data.message);
              return;
            }
            this.$message.success("修改成功");
            this.closeDialog();
            this.$router.go(0);
          })
          .catch(error => {
            this.$message.error(error);
            return;
          })
      }
    }
  }
</script>

<style scoped>
  .el-form-item {
    margin-bottom: 10px;
  }

</style>
