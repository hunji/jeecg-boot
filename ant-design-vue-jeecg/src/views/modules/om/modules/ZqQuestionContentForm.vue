<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="提出人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['proposer']" placeholder="请输入提出人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="简要描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['brief']" rows="4" placeholder="请输入简要描述"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="详情" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-editor v-decorator="['content',{trigger:'input'}]"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="解决方案" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['solution']" rows="4" placeholder="请输入解决方案"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否解决" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch :options="['1','0']" v-decorator="['solutionState']" ></j-switch>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="问题类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-tree-select
                ref="treeSelect"
                placeholder="请选择父级节点"
                v-decorator="['typeId']"
                dict="zq_question_type,type_name,id"
                pidField="pid"
                pidValue="0"
                hasChildField="has_child">
              </j-tree-select>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JEditor from '@/components/jeecg/JEditor'
  import JSwitch from '@/components/jeecg/JSwitch'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'

  export default {
    name: 'ZqQuestionContentForm',
    components: {
      JFormContainer,
      JEditor,
      JSwitch,
      JTreeSelect
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          typeId: {
            rules: [
              { required: true, message: '请输入问题类型!'},
            ]
          },
        },
        url: {
          add: "/om/zqQuestionContent/add",
          edit: "/om/zqQuestionContent/edit",
          queryById: "/om/zqQuestionContent/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'proposer','brief','content','solution','solutionState','typeId'))
        })
      },

      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'proposer','brief','content','solution','solutionState','typeId'))
      },
    }
  }
</script>