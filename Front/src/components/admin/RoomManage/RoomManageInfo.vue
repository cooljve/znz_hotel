<template>
  <div>
    <div>
      <el-row style="height: 480px">
        <el-col :span="8" v-for="room in currRooms.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                :key="room.rid" class="card">
          <el-card shadow="hover" :class="cardClass(room)">
            <div slot="header">
              <span class="cardTitle">{{room.roomNumber+room.type}}</span>
              <el-button
                size="medium"
                icon="el-icon-error"
                style="float: right;padding: 0 0 15px 0;color: black;"
                type="text"
                @click="deleteRoom(room)"
              ></el-button>
            </div>
            <div>
              <el-row>
                <el-col :span="10">{{'状态: '+room.status}}</el-col>
                <el-col :span="10"> {{'价格: '+room.price+'元/日'}}</el-col>
              </el-row>
              <el-row></el-row>
              <el-row>
                <el-button type="warning" @click="modifyRoomInfo(room.rid)" style="float: right" plain>详情</el-button>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-pagination layout="prev,pager,next,total" :page-size="pageSize" :total="total"
                     @current-change="changePage" background></el-pagination>
    </div>
    <div>
      <room-dialog
        :showDialog="showDialog"
        :room="needModifyRoom"
        :roomTypes="currRoomTypes"
        @closeDialog="closeDialog">
      </room-dialog>
    </div>
  </div>
</template>

<script>
  import RoomDialog from './RoomDialog'

  export default {
    name: "RoomManageInfo",
    components: {RoomDialog},
    props: {
      rooms: {
        type: Array,
        default: []
      },
      roomTypes: {
        type: Array,
        default: []
      },
    },
    data() {
      return {
        currentPage: 1,
        pageSize: 9,
        total: 0,
        currRooms: this.rooms,
        //     customer: {
        //       realName: '李浩',
        //       idCard: '100000199601010031',
        //       date: ['2019-02-28T16:00:00.000Z', '2019-03-08T15:59:59.000Z'],
        //       emailAddress: '123456@qq.com'
        //     }
        showDialog: false,
        roomIndex: 0,
        needModifyRoom: {},
        currRoomTypes:this.roomTypes,
      }
    },
    watch: {
      rooms: function (val) {
        this.updateValue(val);
      },
      roomTypes:function (val) {
        this.updateRoomTypes(val);
      }
    },
    methods: {
      updateValue: function (val) {
        this.currRooms = val;
        this.total = this.currRooms.length;
      },
      updateRoomTypes:function (val) {
        this.currRoomTypes=val;
      },
      modifyRoomInfo: function (rid) {
        for (let room of this.currRooms) {
          if (room.rid === rid) {
            this.needModifyRoom = room;
            break;
          }
        }
        this.showDialog = true;
      },
      closeDialog: function () {
        this.showDialog = false;
      },
      cardClass: function (value) {
        if (value.status === '空房干净') {
          return 'EC';
        }
        if (value.status === '空房待打扫') {
          return 'ED';
        }
        if (value.status === '住人已打扫') {
          return 'C';
        }
        return 'D';
      },
      deleteRoom: function (room) {
        this.$confirm('此操作将删除房间' + room.roomNumber + '，是否继续', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('room/deleteRoom', room.rid)
            .then(res => {
              if (!res.data.success || !res.data.data) {
                this.$message.error(res.data.message);
                return;
              }
              this.$message.success("删除成功");
              this.$router.go(0);
            }).catch(error => {
            this.$message.error(error);
            return;
          })
        }).catch(() => {})
      },
      changePage: function (page) {
        this.currentPage = page;
      },
    }
  }
</script>

<style scoped>
  .cardTitle {
    float: left;
  }

  .card {
    margin: 10px;
    width: 30%;
  }

  .ED {
    background-color: rgba(245, 108, 108, .1);
    border-color: rgba(245, 108, 108, .2);
    color: #f56c6c;
  }

  .EC {
    background-color: rgba(103, 194, 58, .1);
    border-color: rgba(103, 194, 58, .2);
    color: #67c23a;
  }

  .C {
    background-color: rgba(230, 162, 60, .1);
    border-color: rgba(230, 162, 60, .2);
    color: #e6a23c;
  }

  .D {
    background-color: rgba(144, 147, 153, .1);
    border-color: rgba(144, 147, 153, .2);
    color: black;
  }
</style>
