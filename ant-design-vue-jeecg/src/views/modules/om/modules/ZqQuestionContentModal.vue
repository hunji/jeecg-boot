<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{ disabled: disablesubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <zq-question-content-form ref="realForm" @ok="submitCallback" :readOnly="disableSubmit" normal></zq-question-content-form>
  </j-modal>
</template>

<script>

  import ZqQuestionContentForm from './ZqQuestionContentForm'
  export default {
    name: "ZqQuestionContentModal",
    components: {
      ZqQuestionContentForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>