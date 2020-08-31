<template>
  <div style="background-color: #ececec; padding: 20px;">
    <a-card title="功能列表">
      <a-card-grid @click="changeView('ProcessStorageTransfer')">
        智能物流----储位转移
      </a-card-grid>
      <a-card-grid @click="changeView('ProcessStorageCorrection')" >
        储位修正
      </a-card-grid>
      <a-card-grid>
        功能3
      </a-card-grid>
      <a-card-grid >
        功能4
      </a-card-grid>
      <a-card-grid >
        功能5
      </a-card-grid>
      <a-card-grid >
        功能222
      </a-card-grid>
      <a-card-grid >
        功能6
      </a-card-grid>
      <a-card-grid>
        功能7
      </a-card-grid>
    </a-card>
  <p>
   <component :is="currentView">
   </component>
  </p>
  </div>
</template>

<script>
  import ProcessStorageTransfer from './modules/ProcessStorageTransfer'
  import ProcessStorageCorrection from './modules/ProcessStorageCorrection'
export default {
  components: {
    ProcessStorageTransfer,
    ProcessStorageCorrection
  },
  data () {
    return {
      currentView: ''
    }
  },
  methods: {
    changeView(data){
      this.currentView = data
    }
 }
}
</script>
<style scoped>
  .ant-card-grid{
    width:22%;text-align:center;margin:10px;
  }
</style>