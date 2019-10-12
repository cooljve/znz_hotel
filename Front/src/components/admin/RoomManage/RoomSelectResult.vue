<template>
  <div>
    <el-card>
      <Headline>查询结果</Headline>
      <div>
        <el-table
          :data="tableRoomDatas.slice((currentPage-1)*pageSize,currentPage*pageSize)"
          border style="width: 100%"
          :default-sort="{prop:'roomNumber',order:'ascending'}"
          v-loading="tableLoading">
          <el-table-column label="房间号" width="150px" align="center" prop="roomNumber"></el-table-column>
          <el-table-column label="类型" width="200px" align="center" prop="type"></el-table-column>
          <el-table-column label="状态" width="150px" align="center" prop="status"></el-table-column>
          <el-table-column label="价格" sortable width="150px" align="center" prop="price"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                v-show="scope.row.status==='空房干净'"
                @click="openCheckInDialog(scope.$index,scope.row)"
                type="primary"
                size="small">入住
              </el-button>
              <el-button
                v-show="scope.row.status==='住人待打扫' ||scope.row.status==='住人已打扫' "
                @click="openModifyDialog(scope.$index,scope.row)"
                type="primary"
                size="small">续住
              </el-button>
              <el-button
                v-show="scope.row.status==='住人待打扫' ||scope.row.status==='住人已打扫' "
                @click="openCheckOutDialog(scope.$index,scope.row)"
                type="danger"
                size="small">退房
              </el-button>
              <el-button
                v-show="scope.row.status==='空房待打扫' ||scope.row.status==='住人待打扫' "
                @click="cleanRoom(scope.$index,scope.row)"
                type="success"
                size="small"
                style="margin: 0">打扫
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-pagination layout="prev,pager,next,total" :page-size="pageSize" :total="total"
                     @current-change="changePage" background></el-pagination>
    </el-card>
    <el-dialog title="入住" :visible.sync="showCheckInDialog"
               center :before-close="closeCheckInDialog">
      <el-form :model="checkInRoom.order" :rules="checkInRoomRules" ref="checkInRoom" label-width="80px">
        <el-form-item label="房间">{{checkInRoom.roomNumber+'-'+checkInRoom.type}}</el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="checkInRoom.order.realName" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="checkInRoom.order.idCard" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="emailAddress">
          <el-input v-model="checkInRoom.order.emailAddress" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="入住时间" prop="date">
          <el-date-picker
            v-model="checkInRoom.order.date"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['12:00:00', '12:00:00']"
            :picker-options="checkInPickerOptions"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="邮件通知" prop="inform">
          <el-checkbox v-model="checkInRoom.order.inform"></el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="checkIn">提交</el-button>
        <el-button @click="closeCheckInDialog">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="退房" :visible.sync="showCheckOutDialog"
               center :before-close="closeCheckOutDialog">
      <el-form :model="checkOutRoom.order" ref="checkOutRoom" label-width="80px">
        <el-form-item label="房间">{{checkOutRoom.roomNumber+'-'+checkOutRoom.type}}</el-form-item>
        <el-form-item label="评分">
          <el-rate
            v-model="checkOutRoom.order.rate"
            show-text>
          </el-rate>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="checkOut">提交</el-button>
        <el-button @click="closeCheckOutDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Headline from '../../common/Headline';
  import global from '../../common/GlobalVariable';

  export default {
    name: "RoomSelectResult",
    components: {Headline},
    props: {
      rooms: {
        type: Array,
        default: []
      },
      selectLoading: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        currentPage: 1,
        total: 0,
        pageSize: 5,
        tableLoading: this.selectLoading,
        tableRoomDatas: this.rooms,
        showCheckInDialog: false,
        showCheckOutDialog: false,
        checkInRoom: {
          rid: '',
          roomNumber: '',
          floorNum: '',
          type: '',
          status: '',
          price: 0,
          order: {
            oid: '',
            realName: '',
            idCard: '',
            date: ['', ''],
            emailAddress: '',
            inform: '',
            rate: 0,
            frontUserName: '',
          }
        },
        checkOutRoom: {
          rid: '',
          roomNumber: '',
          floorNum: '',
          type: '',
          status: '',
          price: 0,
          order: {
            oid: '',
            realName: '',
            idCard: '',
            date: ['', ''],
            emailAddress: '',
            inform: '',
            rate: 0,
            frontUserName: '',
          }
        },
        checkInRoomRules: {
          realName: [{validator: global.validateName, trigger: 'blur'}],
          idCard: [{validator: global.validateIdCard, trigger: 'blur'}],
          emailAddress: [{validator: global.validateEmail, trigger: 'blur'}],
        },
        modifyOrder: false,
        dates: [],
        checkInPickerOptions: this.pOptions(),
        orders: [],
      }
    },
    mounted: function () {
      this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
    },
    watch: {
      rooms: function (val) {
        this.total = this.rooms.length;
        this.updateRooms(val);
      },
      selectLoading: function (val) {
        this.updateLoading(val);
      }
    },
    methods: {
      updateRooms: function (val) {
        this.tableRoomDatas = val;
      },
      updateLoading: function (val) {
        this.tableLoading = val;
      },
      openCheckInDialog: function (index, row) {
        this.showCheckInDialog = true;
        this.modifyOrder = false;
        this.dates.length = 0;
        this.checkInRoom = row;
        this.disabledBookDate();
      },
      openCheckOutDialog: function (index, row) {
        this.showCheckOutDialog = true;
        this.checkOutRoom = row;
      },
      openModifyDialog: function (index, row) {
        this.showCheckInDialog = true;
        this.modifyOrder = true;
        this.checkInRoom = row;
      },
      checkOut: function () {
        this.checkOutRoom.order.frontUserName = this.currFrontUser.userName;
        this.$axios.post('order/checkOut', this.checkOutRoom)
          .then(res => {
            this.$message('需退费' + res.data.data + '元');
            setTimeout(() => {
              this.$router.go(0);
            }, 2000);
          })
          .catch(error => {
            this.$message.error(error);
            return;
          });
      },
      cleanRoom: function (index, row) {
        this.$axios.post('room/clean', row.rid)
          .then(res => {
            if (!res.data.success) {
              this.$message.error(res.data.message);
              return;
            }
            this.$router.go(0);
          })
          .catch(error => {
            this.$message.error(error);
            return;
          })
      },
      changePage: function (page) {
        this.currentPage = page;
      },
      closeCheckInDialog: function () {
        this.showCheckInDialog = false;
        this.$refs['checkInRoom'].resetFields();
      },
      closeCheckOutDialog: function () {
        this.showCheckOutDialog = false;
        this.$refs['checkOutRoom'].resetFields();
      },
      checkIn: function () {
        this.$refs['checkInRoom'].validate((valid) => {
          if (valid) {
            let url = this.modifyOrder ? 'order/modify' : 'order/checkIn';
            this.checkInRoom.order.frontUserName = this.currFrontUser.userName;
            this.$axios.post(url, this.checkInRoom)
              .then(res => {
                if (!res.data.success) {
                  this.$message.error(res.data.message);
                  return;
                }
                this.$router.go(0);
              }).catch(error => {
              this.$message.error(error);
              return;
            });
          } else {
            return false;
          }
        })
      },
      pOptions: function () {
        let self = this;
        return {
          disabledDate(time) {
            return self.dates.indexOf(time.getTime()) > -1||time.getTime()<Date.now()-24*3600*1000;
          }
        }
      },
      disabledBookDate: function () {
        this.$axios.post('room/getAllOrdersDate', this.checkInRoom.rid)
          .then(res => {
            this.dates = res.data.data;
          }).catch(err => {
        });
      }
    }
  }
</script>

<style scoped>

</style>
