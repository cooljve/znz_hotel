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
              @change="displaySelectedReport">
            </el-date-picker>
            <el-button @click="exportTable" type="primary">导出表格</el-button>
          </div>
        </el-card>
        <br>
        <el-card shadow="always">
          <Headline>支出</Headline>
          <el-table
            :data="expendReport"
            border
            style="width: 100%;"
            show-summary
            v-loading="tableLoading">
            <el-table-column label="序号" prop="serialNum" sortable></el-table-column>
            <el-table-column label="账目名称" prop="name"></el-table-column>
            <el-table-column label="金额" prop="money" sortable></el-table-column>
          </el-table>
        </el-card>
        <br>
        <el-card shadow="always">
          <Headline>收入</Headline>
          <el-table
            :data="incomeReport"
            border
            style="width: 100%;"
            show-summary
            v-loading="tableLoading">
            <el-table-column label="序号" prop="serialNum" sortable></el-table-column>
            <el-table-column label="账目名称" prop="name"></el-table-column>
            <el-table-column label="金额" prop="money" sortable></el-table-column>
          </el-table>
          <div style="margin-top: 10px">{{'总利润：'+total+'元'}}</div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import Headline from '../common/Headline';

  export default {
    name: "daysReport",
    components: {Headline},
    data() {
      return {
        expendReport: [],
        incomeReport: [],
        date: '',
        tableLoading: false,
        pickerOptions: {
          disabledDate(time) {
            // return time.getTime() > Date.now();
          },
          shortcuts: [{
            text:'最近一周',
            onClick(picker){
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime()-60*60*24*1000*7);
              picker.$emit('pick', [start, end]);
            }
          },{
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
    mounted: function () {
      // this.initializeReport();
    },
    computed:{
      total(){
        let a=0;
        let b=0;
        this.expendReport.forEach(value => {
          a += value.money;
        });
        this.incomeReport.forEach(value => {
          b += value.money;
        });
        return b - a;
      }
    },
    methods: {
      initializeReport:function () {
        this.tableLoading = true;
        this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
        this.$axios.post('hotel/getReport',{
          aid: this.currAdmin.aid,
          startDate:this.date[0],
          endDate:this.date[1],
        }).then(res=>{
          if(!res.data.success){
            this.$message.error(res.data.message);
            return;
          }
          if (res.data.data != null) {
            this.expendReport=res.data.data.expendReport;
            this.incomeReport=res.data.data.incomeReport;
          }
        }).catch(err=>{
          this.$message.error(err);
        });
        setTimeout(() => {
          this.tableLoading = false;
        }, 2000);
      },
      displaySelectedReport: function () {
        this.initializeReport();
      },
      exportTable:function () {
        require.ensure([], () => {
          const { export_json_to_excel } = require('../../Excel/Export2Excel');
          const tHeader = ['序号', '名称', '金额'];
          // 上面设置Excel的表格第一行的标题
          const filterVal = ['serialNum', 'name', 'money'];
          // 上面的index、nickName、name是tableData里对象的属性
          let expend={serialNum:'支出',name:'', money: ''}
          let income={serialNum:'收入',name:'', money: ''}
          let tableData = [];
          tableData.push(expend);
          let eTable=tableData.concat(this.expendReport);
          eTable.push(income);
          let table=eTable.concat(this.incomeReport);
          const list = table;  //把data里的tableData存到list
          const data = this.formatJson(filterVal, list);
          export_json_to_excel(tHeader, data, this.date+'收支表');
        })
      },
      formatJson:function(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      }
    }
  }
</script>

<style scoped>

</style>
