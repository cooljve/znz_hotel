<template>
  <div>
    <testheader></testheader>
    <hr/>
    <div>
      这是首页！
    </div>
    <hr/>
    <testfooter></testfooter>
  </div>
</template>



<script>
  import testheader from '../common/TestHeader'
  import testfooter from '../common/TestFooter'

  export default {
    name: 'Index',
    components:{testheader,testfooter}
  }
</script>
