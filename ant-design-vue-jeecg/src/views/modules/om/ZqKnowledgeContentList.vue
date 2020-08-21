<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="审核状态">
              <j-dict-select-tag v-model="queryParam.reviewState" title="审核状态" dictCode="approval_status" placeholder="请选择"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="问题类型">
                <j-tree-select
                  ref="treeSelect"
                  placeholder="请输入问题类型"
                  v-model="queryParam.typeId"
                  dict="zq_knowledge_type,type_name,id"
                  pidField="pid"
                  pidValue="0"
                  hasChildField="has_child">
                </j-tree-select>
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
      <a-button type="primary" icon="download" @click="handleExportXls('知识内容信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
          <a-menu-item key="2" @click="handleReview()" v-has="'question:review'">审核</a-menu-item>
          <a-menu-item key="3" @click="handleBack()" v-has="'question:review'">撤销</a-menu-item>
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
                <a @click="handleReview(record.id)" v-has="'question:review'">审核</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleBack(record.id)" v-has="'question:review'">撤销</a>
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

    <zqKnowledgeContent-modal ref="modalForm" @ok="modalFormOk"></zqKnowledgeContent-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ZqKnowledgeContentModal from './modules/ZqKnowledgeContentModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import { putAction } from '@/api/manage'

  export default {
    name: "ZqKnowledgeContentList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ZqKnowledgeContentModal,
      JTreeSelect,
      JDictSelectTag
    },
    data () {
      return {
        description: '知识内容信息管理页面',
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
            title:'标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title:'类型',
            align:"center",
            dataIndex: 'typeId_dictText'
          },
          {
            title:'简要描述',
            align:"center",
            dataIndex: 'brief'
          },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'reviewState_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/om/zqKnowledgeContent/list",
          delete: "/om/zqKnowledgeContent/delete",
          deleteBatch: "/om/zqKnowledgeContent/deleteBatch",
          exportXlsUrl: "/om/zqKnowledgeContent/exportXls",
          importExcelUrl: "om/zqKnowledgeContent/importExcel",
          reviewUrl: "om/zqKnowledgeContent/review",
          backUrl: "om/zqKnowledgeContent/sendBack",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      // 审核
      handleReview(id){
        let ids = id ? id : this.selectedRowKeys.join(',')
        var that = this;
        this.$confirm({
          title: "确认提交",
          content: "是否确认审核通过?",
          onOk: () =>{
            this.loading = true;
            putAction(this.url.reviewUrl, {ids: ids},'post').then((res) => {
              if (res.success) {
                this.$message.success(res.message);
                this.loadData();
                this.onClearSelected();
              } else {
                this.$message.warning(res.message);
              }
            }).finally(() => {
              this.loading = false;
            });
          }
        });
      },
      // 撤销审核
      handleBack(id){
        let ids = id ? id : this.selectedRowKeys.join(',')
        this.$confirm({
          title: "确认提交",
          content: "是否撤销审核?",
          onOk: ()=>{
            this.loading = true;
            putAction(this.url.backUrl, {ids: ids},'post').then((res) => {
              if (res.success) {
                this.$message.success(res.message);
                this.loadData();
                this.onClearSelected();
              } else {
                this.$message.warning(res.message);
              }
            }).finally(() => {
              this.loading = false;
            });
          }
        });
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>