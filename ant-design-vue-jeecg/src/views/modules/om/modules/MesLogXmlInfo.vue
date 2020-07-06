<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    @ok="handleOk"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="异常方法" hasFeedback>
          <span>{{ dataForm.exceptionfun }}</span>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="message信息" hasFeedback>
          <span>{{ dataForm.messagexml }}</span>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="异常栈" hasFeedback>
          <span>{{ dataForm.exceptionxml }}</span>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="传入数据结构" hasFeedback>
          <span>{{ dataForm.inxml }}</span>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="传出数据结构" hasFeedback>
          <span>{{ dataForm.outxml }}</span>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { getAction } from '@/api/manage'

export default {
  name: 'JeecgDemoModal',
  data() {
    return {
      title: '异常详细信息',
      visible: false,
      dataForm: {},
      confirmLoading: false,
      params: {},
      labelCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 5
        }
      },
      wrapperCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 16
        }
      },
      url: {
        detail: '/om/mesLog/xmlInfo'
      }
    }
  },
  created() {},
  methods: {
    init(id) {
      this.visible = true
      this.dataForm.id = id
      this.confirmLoading = true
      this.params.id = id
      this.$nextTick(() => {
        getAction(this.url.detail, this.params).then(res => {
          if (res.success) {
            this.dataForm = res.result
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          console.log(this.dataForm)
          this.confirmLoading = false
        })
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
    handleOk() {
      this.close()
    }
  }
}
</script>

<style scoped>
</style>
