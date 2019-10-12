<template>
  <el-container>
    <el-main>
      <room-select-criteria
        :selectLoading="selectLoading"
        :types="roomTypes"
        @changeLoading="changeLoading($event)"
        @getSpecialRooms="getSpecialRooms($event)">
      </room-select-criteria>
      <br>
      <room-select-result
        :rooms="rooms"
        :selectLoading="selectLoading">
      </room-select-result>
    </el-main>
  </el-container>
</template>

<script>
  import RoomSelectCriteria from './RoomSelectCriteria'
  import RoomSelectResult from './RoomSelectResult'
  import global from '../../common/GlobalVariable'

  export default {
    name: "RoomCheckIn",
    components: {RoomSelectCriteria, RoomSelectResult},
    data() {
      return {
        rooms: [],
        selectLoading: false,
        roomTypes: [],
      }
    },
    mounted() {
      this.initialize();
    },
    methods: {
      initialize: function () {
        this.currFrontUser = JSON.parse(localStorage.getItem('currFrontUser'));
        this.$axios.post('room/getAllRooms', this.currFrontUser.aid)
          .then(res => {
            this.rooms = res.data.data.roomDtoList;
            if (res.data.data.roomTypeDtos.length === 0) {
              this.roomTypes = global.roomTypes;
            } else {
              this.roomTypes = res.data.data.roomTypeDtos;
            }
          }).catch(error => {
        });
      },
      changeLoading: function (val) {
        if (!val) {
          setTimeout(() => {
            this.selectLoading = val;
          }, 1000)
        } else {
          this.selectLoading = val;
        }
      },
      getSpecialRooms: function (selectedRooms) {
        this.rooms = selectedRooms;
      }
    }
  }
</script>

<style scoped>

</style>
