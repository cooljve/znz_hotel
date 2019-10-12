<template>
  <div class="block">
    <el-container>
      <el-main>
        <el-card shadow="always">
          <Headline>房间类型</Headline>
          <div style="float: right">
            <el-button icon="el-icon-plus" @click="openDialog('type')" type="text">添加</el-button>
          </div>
          <el-table :data="roomTypeTable" border v-loading="reportLoading">
            <el-table-column label="序号" width="150px" align="center" prop="serialNum"></el-table-column>
            <el-table-column label="房间类型" width="400px" align="center" prop="roomType"></el-table-column>
            <el-table-column label="床位数" width="150px" align="center" prop="bedCount"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button @click="modifyRoomTypeInfo(scope.$index,scope.row)"
                           type="primary" size="small">修改
                </el-button>
                <el-button @click="deleteRoomType(scope.$index,scope.row)"
                           type="primary" size="small">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-dialog title="新增项" :visible.sync="showRoomTypeDialog" center @close="closeDialog('type')">
            <el-form :model="roomTypeForm" ref="roomTypeForm" :rules="roomTypeFormRules" label-width="80px">
              <el-form-item label="房间类型" prop="roomType">
                <el-input v-model="roomTypeForm.roomType" style="width: 220px" clearable></el-input>
              </el-form-item>
              <el-form-item label="床位数" prop="bedCount">
                <el-input v-model.number="roomTypeForm.bedCount" style="width: 220px" clearable></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer">
              <el-button type="primary" @click="addRoomType">保存</el-button>
              <el-button plain @click="closeDialog('type')">取消</el-button>
            </div>
          </el-dialog>

        </el-card>
        <br>
        <el-card shadow="always">
          <Headline>房间成本</Headline>
          <div style="float: right">
            <el-button icon="el-icon-plus" @click="openDialog('cost')" type="text">其他</el-button>
          </div>
          <el-table :data="roomCostTable" border v-loading="reportLoading">
            <el-table-column label="序号" width="150px" align="center" prop="serialNum"></el-table-column>
            <el-table-column label="描述" width="400px" align="center" prop="desc"></el-table-column>
            <el-table-column label="类型" width="150px" align="center" prop="out"></el-table-column>
            <el-table-column label="金额" width="150px" align="center" prop="cost"></el-table-column>
            <el-table-column label="时间" sortable width="150px" align="center" prop="date">
              <template slot-scope="scope">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px">{{ scope.row.date }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button @click="modifyOtherInfo(scope.$index,scope.row)"
                           type="primary" size="small">修改
                </el-button>
                <el-button @click="deleteTableItem(scope.$index,scope.row)"
                           type="primary" size="small">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-dialog title="新增项" :visible.sync="showCostDialog" center @close="closeDialog('cost')">
            <el-form :model="otherForm" ref="otherForm" :rules="otherFormRules" label-width="80px">
              <el-form-item label="描述" prop="desc">
                <el-input v-model="otherForm.desc" style="width: 220px" clearable></el-input>
              </el-form-item>
              <el-form-item label="类型" prop="out">
                <el-select v-model="otherForm.out" placeholder="请选择">
                  <el-option label="收入" value="收入"></el-option>
                  <el-option label="支出" value="支出"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="费用" prop="cost">
                <el-input v-model.number="otherForm.cost" style="width: 275px" clearable>
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
              <el-form-item label="日期" prop="date">
                <el-date-picker
                  v-model="otherForm.date"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </el-form>
            <div slot="footer">
              <el-button type="primary" @click="addCostItem">添加</el-button>
              <el-button plain @click="closeDialog('cost')">取消</el-button>
            </div>
          </el-dialog>

        </el-card>
        <br>
        <el-card shadow="always">
          <Headline>员工</Headline>
          <div style="float: right">
            <el-button icon="el-icon-plus" @click="openDialog('addStaff')" type="text">添加员工</el-button>
          </div>
          <el-table :data="staffTable" border v-loading="reportLoading">
            <el-table-column label="序号" width="150px" align="center" prop="serialNum"></el-table-column>
            <el-table-column label="职业" width="200px" align="center" prop="work"></el-table-column>
            <el-table-column label="人数" width="150px" align="center" prop="count"></el-table-column>
            <el-table-column label="工资(元/每人每月)" sortable width="200px" align="center" prop="salary"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button @click="modifyStaffInfo(scope.$index,scope.row)"
                           type="primary" size="small">修改
                </el-button>
                <el-button @click="deleteStaff(scope.$index,scope.row)"
                           type="primary" size="small">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-dialog title="添加员工" :visible.sync="showAddStaffDialog" center @close="closeDialog('addStaff')">
            <el-form :model="staffForm" ref="staffForm" :rules="staffFormRules" label-width="80px">
              <el-form-item label="职业" prop="work">
                <el-input v-model="staffForm.work" style="width: 220px" clearable></el-input>
              </el-form-item>
              <el-form-item label="人数" prop="count">
                <el-input-number v-model="staffForm.count" :min="1"></el-input-number>
              </el-form-item>
              <el-form-item label="工资" prop="salary">
                <el-input v-model.number="staffForm.salary" style="width: 275px" clearable>
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
            </el-form>
            <div slot="footer">
              <el-button type="primary" @click="addStaff">确认</el-button>
              <el-button plain @click="closeDialog('addStaff')">取消</el-button>
            </div>
          </el-dialog>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import Headline from '../common/Headline';
  import global from '../common/GlobalVariable';

  export default {
    name: "ReportSet",
    components: {Headline},
    data() {
      return {
        reportLoading: false,
        staffForm: {
          sid: '',
          work: '',
          count: '',
          salary: ''
        },
        staffFormRules: {
          work: [{required: true, message: '该项不能为空', trigger: 'blur'}],
          salary: [{validator: global.validatePrice, trigger: 'blur'}]
        },
        staffTable: [],
        roomTypeTable: [],
        showAddStaffDialog: false,
        roomCostTable: [],
        showCostDialog: false,
        otherForm: {
          desc: '',
          cost: '',
          date: '',
          out: '',
        },
        otherFormRules: {
          desc: [{required: true, message: '该项不能为空'}],
          cost: [{validator: global.validatePrice}],
        },
        hid: '',
        modifyStaff: false,
        modifyRoomType: false,
        modifyOtherCost: false,
        showRoomTypeDialog: false,
        roomTypeForm: {
          rtid: '',
          roomType: '',
          bedCount: ''
        },
        roomTypeFormRules: {
          roomType: [{required: true, message: '该项不能为空'}],
          bedCount: [{validator: global.validatePrice}],
        }
      }
    },
    mounted() {
      this.initializeReportSet();
    },
    methods: {
      initializeReportSet: function () {
        this.reportLoading = true;
        this.otherForm.date = new Date();
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.$axios.post('hotel/getReportSetByAid', this.currAdmin.aid)
          .then(res => {
            if (!res.data.success) {
              this.$message.error(res.data.message);
              return;
            }
            if (res.data.data != null) {
              this.hid = res.data.data.hid;
              this.staffTable = res.data.data.staffDtos;
              this.roomTypeTable = res.data.data.roomTypeDtos;
              this.roomCostTable = res.data.data.otherCostDtos;
            }
            this.reportLoading = false;
          }).catch(err => {
          this.$message.error(err);
          return;
        });
      },
      addStaff: function () {
        this.$refs['staffForm'].validate((valid) => {
          if (valid) {
            let url = this.modifyStaff ? 'staff/modify' : 'staff/add';
            this.$axios.post(url, {
              hid: this.hid,
              sid: this.staffForm.sid,
              work: this.staffForm.work,
              count: this.staffForm.count,
              salary: this.staffForm.salary
            }).then(res => {
              if (!res.data.data) {
                this.$message.error("操作错误，请重新尝试");
              } else {
                this.staffForm = {};
                this.$router.go(0);
              }
            }).catch(err => {
              this.$message.error(err);
            });
            this.$message('添加');
            this.closeDialog('addStaff');
          } else {
            return false;
          }
        });
      },
      addRoomType: function () {
        this.$refs['roomTypeForm'].validate((valid) => {
          if (valid) {
            let url = this.modifyRoomType ? 'roomType/modify' : 'roomType/add';
            this.$axios.post(url, {
              hid: this.hid,
              rtid: this.roomTypeForm.rtid,
              roomType: this.roomTypeForm.roomType,
              bedCount: this.roomTypeForm.bedCount,
            }).then(res => {
              if (!res.data.data) {
                this.$message.error("操作错误，请重新尝试");
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
              this.$message.error(err);
            });
            this.$message('添加');
            this.closeDialog('type');
          } else {
            return false;
          }
        });
      },
      modifyRoomTypeInfo: function (index, row) {
        this.showRoomTypeDialog = true;
        this.modifyRoomType = true;
        let data = {
          rtid: row.rtid,
          roomType: row.roomType,
          bedCount: row.bedCount
        };
        this.roomTypeForm = data;
      },
      modifyStaffInfo: function (index, row) {
        this.showAddStaffDialog = true;
        this.modifyStaff = true;
        let data = {
          sid: row.sid,
          work: row.work,
          count: row.count,
          salary: row.salary
        };
        this.staffForm = data;
      },
      openDialog: function (dialog) {
        if (dialog === 'cost') {
          this.showCostDialog = true;
          this.modifyOtherCost = false;
        } else if (dialog === 'addStaff') {
          this.showAddStaffDialog = true;
          this.modifyStaff = false;
        } else if (dialog === 'type') {
          this.showRoomTypeDialog = true;
          this.modifyRoomType = false
        }
      },
      closeDialog: function (dialog) {
        if (dialog === 'cost') {
          this.showCostDialog = false;
          this.otherForm = {};
          this.$refs['otherForm'].resetFields();
        } else if (dialog === 'addStaff') {
          this.showAddStaffDialog = false;
          this.staffForm = {};
          this.$refs['staffForm'].resetFields();
        } else if (dialog === 'type') {
          this.showRoomTypeDialog = false;
          this.roomTypeForm = {};
          this.$refs['roomTypeForm'].resetFields();
        }
      },
      addCostItem: function () {
        this.$refs['otherForm'].validate((valid) => {
          if (valid) {
            let url = this.modifyOtherCost ? 'otherCost/modify' : 'otherCost/add';
            this.$axios.post(url, {
              hid: this.hid,
              ocid: this.otherForm.ocid,
              desc: this.otherForm.desc,
              date: this.otherForm.date,
              cost: this.otherForm.cost,
              out: this.otherForm.out,
            }).then(res => {
              if (!res.data.data) {
                this.$message.error("操作错误，请重新尝试");
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
              this.$message.error(err);
            });
            this.closeDialog('cost');
          } else {
            this.$message('错误');
            return false;
          }
        });
      },
      modifyOtherInfo: function (index, row) {
        this.showCostDialog = true;
        this.modifyOtherCost = true;
        let data = {
          desc: row.desc,
          cost: row.cost,
          date: row.date,
          out: row.out,
        };
        this.otherForm = data;
      },
      deleteStaff: function (index, row) {
        this.$confirm('确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('staff/delete', row.sid)
            .then(res => {
              if (!res.data.data) {
                this.$message.error("删除失败，请重试。")
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
          })
        }).catch(() => {
        });
      },
      deleteRoomType: function (index, row) {
        this.$confirm('确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('roomType/delete', row.rtid)
            .then(res => {
              if (!res.data.data) {
                this.$message.error("删除失败，请重试。")
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
          })
        }).catch(() => {
        });
      },
      deleteTableItem: function (index, row) {
        this.$confirm('确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('otherCost/delete', row.ocid)
            .then(res => {
              if (!res.data.data) {
                this.$message.error("删除失败，请重试。")
              } else {
                this.$router.go(0);
              }
            }).catch(err => {
          })
        }).catch(() => {
        });
      }
    }
  }
</script>

<style scoped>

</style>
