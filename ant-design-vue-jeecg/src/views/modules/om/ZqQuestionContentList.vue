<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="提出人">
              <a-input placeholder="请输入提出人" v-model="queryParam.proposer"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="是否解决">
              <j-switch placeholder="请选择是否解决" :options="['1','0']" v-model="queryParam.solutionState"  query></j-switch>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="问题类型">
                <j-tree-select
                  ref="treeSelect"
                  placeholder="请输入问题类型"
                  v-model="queryParam.typeId"
                  dict="zq_question_type,type_name,id"
                  pidField="pid"
                  pidValue="0"
                  hasChildField="has_child">
                </j-tree-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="问题状态">
                <j-dict-select-tag v-model="queryParam.questionStatus" title="状态" dictCode="question_status" placeholder="请选择"/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('日常问题')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
          <a-menu-item key="2" @click="handleSubmit()">转为知识</a-menu-item>
          <a-menu-item key="3" v-has="'question:review'" @click="handleReview()">审核</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleSubmit(record.id)">转为知识</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleReview(record.id)" v-has="'question:review'">审核</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <zq-question-content-modal ref="modalForm" @ok="modalFormOk"></zq-question-content-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ZqQuestionContentModal from './modules/ZqQuestionContentModal'
  import JSwitch from '@/components/jeecg/JSwitch'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import { putAction } from '@/api/manage'

  export default {
    name: 'ZqQuestionContentList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JSwitch,
      ZqQuestionContentModal,
      JTreeSelect,
      JDictSelectTag
    },
    data () {
      return {
        description: '日常问题管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'提出人',
            align:"center",
            dataIndex: 'proposer'
          },
          {
            title:'简要描述',
            align:"center",
            dataIndex: 'brief'
          },
          {
            title:'问题类型',
            align:"center",
            dataIndex: 'typeId_dictText'
          },
          {
            title:'是否解决',
            align:"center",
            dataIndex: 'solutionState_dictText'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'questionStatus_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/om/zqQuestionContent/list",
          delete: "/om/zqQuestionContent/delete",
          deleteBatch: "/om/zqQuestionContent/deleteBatch",
          exportXlsUrl: "/om/zqQuestionContent/exportXls",
          importExcelUrl: "om/zqQuestionContent/importExcel",
          submitBatchUrl: "om/zqQuestionContent/submitBatch",
          sendBackUrl: "om/zqQuestionContent/sendBack",
          reviewhUrl: "om/zqQuestionContent/review",
        },
        dictOptions:{},
      }
    },
    created() {
      this.$set(this.dictOptions, 'solutionState', [{text:'是',value:'1'},{text:'否',value:'0'}])
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      // 转为知识--这里进行了优化，把批量和单个操作合一
      handleSubmit(id){
        if(!this.url.submitBatchUrl){
          this.$message.error("请设置url.submitBatchUrl!")
          return
        }
        let ids = id ? id : this.selectedRowKeys.join(',')
        var that = this;
        this.$confirm({
          title: "确认提交",
          content: "是否将选中知识转为知识?",
          onOk: function () {
            that.loading = true;
            putAction(that.url.submitBatchUrl, {ids: ids},'post').then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.loadData();
                that.onClearSelected();
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.loading = false;
            });
          }
        });
      },
      // 审核知识：有两种结果，通过和不通过
      handleReview(id){
        let ids = id ? id : this.selectedRowKeys.join(',')
        var that = this;
        this.$confirm({
          title: "审核",
          content: "是否审核通过?",
          closable: true,
          maskClosable:true,
          cancelText: '返回重填',
          okText: '审核通过',
          onOk: function () {
            that.loading = true;
            putAction(that.url.reviewhUrl, {ids: ids},'post').then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.loadData();
                that.onClearSelected();
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.loading = false;
            });
          },
          onCancel: function () {
            that.loading = true;
            putAction(that.url.sendBackUrl, {ids: ids},'post').then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.loadData();
                that.onClearSelected();
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.loading = false;
            });
          },
        });
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>