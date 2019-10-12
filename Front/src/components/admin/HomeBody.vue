<template>
  <div class="block">
    <el-container>
      <el-aside width="300px">
        <el-button @click="addHotelDialog=true">{{suffixText}}酒店</el-button>
      </el-aside>
      <el-main style="background-color: #D3DCE6">
        <el-card shadow="always" style="width: 500px;float:left;margin-right: 50px;margin-top: 15px;">
          <div style="font-size: 14px">
            <span class="item">{{'名称：'+hotelForm.hotelName}}</span>
            <br>
            <span class="item">{{'地址：'+hotelForm.city+','+hotelForm.location}}</span>
            <br>
            <span class="item">{{'详情：'+hotelForm.details}}</span>
            <br>
            <span class="item">{{'评分：'+hotelForm.grade}}</span>
            <br>
          </div>
        </el-card>
        <!--访问后台接口-->
        <el-upload
          :action="abc"
          list-type="picture-card"
          :on-preview="previewPic"
          :on-remove="removePic"
          :before-upload="checkFileTypeSize"
          style="float: left;clear: right;margin: 10px;"
        >
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip" style="clear: both;margin-left: 80px;">
            只能上传jpg/png文件，且不超过1MB
          </div>
        </el-upload>
        <el-dialog :visible.sync="imgDialog">
          <img width="100%" :src="picUrl">
        </el-dialog>
        <el-carousel style="height: 500px;clear: both;" trigger="click" :interval="3000">
          <el-carousel-item style="height: 500px" v-for="item in items" :key="item.pid">
            <img :src="'api/hotel/showPic?id='+item.pid">
          </el-carousel-item>
        </el-carousel>
      </el-main>
    </el-container>
    <el-dialog title="酒店信息" center :visible.sync="addHotelDialog" :before-close="closeDialog">
      <el-form :model="hotelForm" ref="hotelForm" label-width="80px">
        <el-form-item label="名称" required prop="hotelName">
          <el-input v-model="hotelForm.hotelName" clearable style="width: 220px;"></el-input>
        </el-form-item>
        <el-form-item label="省市" required prop="city">
          <Address v-model="hotelForm.city" @cityChanged="cityChanged($event)"></Address>
        </el-form-item>
        <el-form-item label="地点" required prop="location">
          <el-input v-model="hotelForm.location" clearable placeholder="例：岳麓区-麓山南路932号"></el-input>
        </el-form-item>
        <el-form-item label="详细描述" prop="details">
          <el-input v-model="hotelForm.details" type="textarea" :autosize="{ minRows: 3, maxRows: 6}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="addHotel">保存</el-button>
        <el-button plain @click="closeDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Address from '../common/Address'

  export default {
    name: "HomeBody",
    components: {Address},
    data() {
      return {
        items: [],
        addHotelDialog: false,
        hotelForm: {
          hotelName: '',
          city: [],
          location: '',
          details: '',
          imgLocate: [],
          grade: 0
        },
        suffixText: '添加',
        spanNum: 12,
        imgDialog: false,
        picUrl: '',
        currAdmin: {},
      }
    },
    mounted() {
      this.currAdmin = JSON.parse(localStorage.getItem('currAdmin'));
      if (!this.currAdmin.haveHotel) {
        this.suffixText = '添加'
      } else {
        this.suffixText = '修改';
      }
      this.initializeHotel();
    },
    methods: {
      initializeHotel: function () {
        this.$axios.post('hotel/getHotelByAid', this.currAdmin.aid)
          .then(res => {
            if (!res.data.success) {
              this.$message.error(res.data.message);
              return;
            }
            this.items = res.data.data.photos;
            if (res.data.data != null) {
              this.hotelForm = res.data.data;
              this.hotelForm.city = res.data.data.city.split('-');
            }
          }).catch(err => {
          this.$message.error(err);
          return;
        });
      },
      addHotel: function () {
        this.$refs['hotelForm'].validate((valid) => {
          let url = this.suffixText === '修改' ? 'hotel/modify' : 'hotel/add';
          if (valid) {
            this.$axios.post(url, {
              aid: this.currAdmin.aid,
              hotelName: this.hotelForm.hotelName,
              city: this.hotelForm.city,
              location: this.hotelForm.location,
              details: this.hotelForm.details,
            }).then(res => {
              if (!res.data) {
                this.$message.error('错误');
                return;
              }
              this.currAdmin.haveHotel = true;
              localStorage.setItem('currAdmin', JSON.stringify(this.currAdmin));
              this.closeDialog();
              this.$router.go(0);
            })
              .catch(error => {
                this.$message.error(error);
                return;
              })
          } else {
            return false;
          }
        })
      },
      closeDialog: function () {
        this.addHotelDialog = false;
        this.hotelForm = {};
        this.$refs['hotelForm'].resetFields();
      },
      previewPic: function (file) {
        this.picUrl = file.url;
        this.imgDialog = true;
      },
      removePic: function (file, fileList) {
        console.log(file, fileList);
      },
      checkFileTypeSize: function (file) {
        let jpg = file.name.split('.')[1] === 'jpg';
        let png = file.name.split('.')[1] === 'png';
        let fileSize = file.size / 1024 < 1024;
        if (!jpg && !png) {
          this.$message('上传图片只能是jpg或png');
          return false;
        }
        if (!fileSize) {
          this.$message('图片大小不能超过1MB');
          return false;
        }
        if (jpg || png && fileSize) {
          let formData = new FormData();
          formData.append('aid', this.currAdmin.aid);
          formData.append('file', file);
          this.$axios.post('hotel/uploadPic', formData)
            .then(res => {
              if (!res.data.success) {
                this.$message.error(res.data.message);
              }
              this.$router.go(0);
            }).catch(err => {
          });
          return true;
        }
        return jpg || png && fileSize;
      },
      cityChanged: function (city) {
        this.hotelForm.city = city;
      },
    }
  }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 30px;
    opacity: 0.8;
    line-height: 300px;
    margin: 0;

  }

  .item {
    float: left;
    clear: both;
  }

  .el-carousel__item:nth-child(2n) {
    opacity: 0.8;
    background-color: #2c3e50;
    background-size: 100% 100%;
  }

  .el-carousel__item:nth-child(2n+1) {
    opacity: 0.8;
    background-color: aliceblue;
    background-size: 100% 100%;
  }

  .el-aside {
    background-color: gray;
    color: #333;
    text-align: center;
    line-height: 200px;;
  }

  .el-row {
    margin: 10px;
  }
</style>
