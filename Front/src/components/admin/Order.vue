<template>
  <div class="block">
    <el-container>
      <el-main>
        <el-card shadow="always">
          <div style="float: left;margin-bottom: 10px">
            时间选择
            <el-date-picker
              v-model="date"
              type="daterange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              unlink-panels
              :picker-options="pickerOptions"
              :default-time="['00:00:00', '23:59:59']"
              @change="displaySelectedOrder">
            </el-date-picker>
          </div>
        </el-card>
        <br>
        <el-card shadow="always">
          <el-table
            :data="orderTable.slice((currentPage-1)*pageSize,currentPage*pageSize)"
            :default-sort="{prop:'roomNumber',order:'ascending'}"
            border
            v-loading="orderLoading">
            <el-table-column label="序号" width="50px" align="center" prop="serialNum"></el-table-column>
            <el-table-column label="房间" width="55px" align="center" prop="roomNumber"></el-table-column>
            <el-table-column label="姓名" width="80px" align="center" prop="realName"></el-table-column>
            <el-table-column label="身份证号" width="163px" align="center" prop="idCard"></el-table-column>
            <el-table-column label="邮箱地址" width="158px" align="center" prop="emailAddress"></el-table-column>
            <el-table-column label="状态" width="80px" align="center" prop="status"
                             :filters="[{text:'离店',value:'离店'},{text:'入住',value:'入住'},{text:'预订',value:'预订'}]"
                             :filter-method="filterOrderStatus"
            ></el-table-column>
            <el-table-column label="消费" width="85px" align="center" prop="cost"></el-table-column>
            <el-table-column label="开始时间" width="250px" align="center" prop="startDate">
              <template slot-scope="scope">
                <el-date-picker
                  v-model="scope.row.startDate"
                  type="datetime"
                  format="yyyy 年 MM 月 dd 日 HH点"
                  disabled>
                </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="结束时间" width="250px" align="center" prop="endDate">
              <template slot-scope="scope">
                <el-date-picker
                  v-model="scope.row.endDate"
                  type="datetime"
                  format="yyyy 年 MM 月 dd 日 HH点"
                  disabled>
                </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="邮件通知" width="50px" align="center">
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.inform" disabled></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="操作员" width="100px" align="center" prop="frontUserName"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  v-show="(scope.row.status==='预订' ||scope.row.status==='入住') && scope.row.inform "
                  @click="sendEmail(scope.$index,scope.row)"
                  type="primary" size="small">发送邮件
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination layout="prev,pager,next,total" :page-size="pageSize" :total="total"
                         @current-change="changePage" background></el-pagination>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Order",
    data() {
      return {
        currentPage: 1,
        total: 0,
        pageSize: 10,
        orderTable: [],
        orderLoading: false,
        date: '',
        pickerOptions: {
          disabledDate(time) {
            // return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 60 * 60 * 24 * 1000 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 60 * 60 * 24 * 1000 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 60 * 60 * 24 * 1000 * 365);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
      }
    },
    mounted() {
      this.initializeOrder();
    },
    methods: {
      initializeOrder: function () {
        this.orderLoading = true;
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
        let userName = '';
        if (this.currAdmin != null) {
          userName = this.currAdmin.aid;
        } else {
          userName = this.currFrontUser.aid;
        }
        this.$axios.post('order/getAllOrders', userName)
          .then(res => {
            if (res.data.data.length != 0) {
              this.orderTable = res.data.data;
              this.total = res.data.data.length;
              for (let val of this.orderTable) {
                val.startDate = val.date[0];
                val.endDate = val.date[1];
              }
            }
            this.orderLoading = false;
          }).catch(error => {
        });
      },
      displaySelectedOrder: function () {
        this.orderLoading = true;
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
        let userName = '';
        if (this.currAdmin != null) {
          userName = this.currAdmin.aid;
        } else {
          userName = this.currFrontUser.aid;
        }
        this.$axios.post('order/getOrdersByDate', {
          aid: userName,
          startDate: this.date[0],
          endDate: this.date[1],
        }).then(res => {
          if (res.data.data.length != 0) {
            this.orderTable = res.data.data;
            this.total = res.data.data.length;
            for (let val of this.orderTable) {
              val.startDate = val.date[0];
              val.endDate = val.date[1];
            }
          }
          this.orderLoading = false;
        }).catch(err => {
          this.$message.error(err);
        });
        setTimeout(() => {
          this.orderLoading = false;
        }, 2000);
      },
      changePage: function (page) {
        this.currentPage = page;
      },
      sendEmail: function (index, row) {
        this.$axios.post('order/sendEmailToCus', row)
          .then(res => {
            if (!res.data.success) {
              this.$message.error(res.data.data);
            }
            this.$message.success("邮件已发送！");
          }).catch(err => {
          this.$message.error(err);
        })
      },
      filterOrderStatus: function (value, row) {
        return row.status === value
      }
    },
  }
</script>

<style scoped>

</style>
