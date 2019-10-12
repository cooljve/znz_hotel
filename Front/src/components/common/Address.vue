<template>
  <el-cascader
    :options="options"
    :props="props"
    clearable
    separator="-"
    v-model="selectedCity"
  ></el-cascader>
</template>

<script>
  import cities from '../../../Cities'
  export default {
    name: "Address",
    props:["value"],
    data() {
      return {
        options: [],
        props:{
          label:'text',
          value:'text'
        },
        selectedCity:this.value
      }
    },
    created() {
      // this.$axios.get('http://localhost:8000/api/cities').then(res => {
      //   this.options=res.data.data;
      // }, error => {
      //   this.$message.error("数据加载失败");
      // })
      this.options = cities;
    },
    watch:{
      selectedCity:function (val) {
        this.$emit('cityChanged',val);
      },
      value:function (val) {
        this.updateValue(val);
      }
    },
    methods:{
      updateValue:function (val) {
        this.selectedCity = val;
      }
    },
  }
</script>

<style scoped>

</style>
