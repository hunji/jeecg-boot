<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="12" :lg="12" :md="12" :sm="12">
            <a-form-item label="地牛编号">
              <span>55</span>
            </a-form-item>
          </a-col>
           <a-col :xl="12" :lg="12" :md="12" :sm="12">
            <a-form-item label="当前设备">
              <span>55</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="储位号：">
              <a-input placeholder="储位号：" v-model="queryParam.locator_no"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="车轮架号：">
              <a-input placeholder="车轮架号：" v-model="queryParam.wheel_frame_no"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <!-- 操作按钮区域 -->
            <div class="table-operator">
              <a-button type="primary" icon="search" @click="loadData">车轮查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a-dropdown v-if="selectedRowKeys.length > 0">
                <a-menu slot="overlay">
                  <a-menu-item key="1">移入</a-menu-item>
                  <a-menu-item key="2">移出</a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px">
                  转移操作
                  <a-icon type="down" />
                </a-button>
              </a-dropdown>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)"
          >下载</a-button>
        </template>
      </a-table>
    </div>
  </a-card>
</template>
<script>
import axios from 'axios'
export default {
  name: 'ProcessStorageTransfer',
  data() {
    return {
      queryParam: {
        work_function: 1,
        locator_no: '',
        wheel_frame_no: ''
      },
      /* 数据源 */
      dataSource:[],
      /* 分页参数 */
      ipagination:{
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + "-" + range[1] + " 共" + total + "条"
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      /* 筛选参数 */
      filters: {},
      /* table加载状态 */
      loading:false,
      /* table选中keys*/
      selectedRowKeys: [],
      /* table选中records*/
      selectionRows: [],
      disableMixinCreated: true,
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '车轮架号',
          align: 'center',
          dataIndex: 'title',
        },
        {
          title: '车轮号',
          align: 'center',
          dataIndex: 'typeId_dictText',
        },
        {
          title: '系统记录储位',
          align: 'center',
          dataIndex: 'brief',
        },
        {
          title: '当前感应储位',
          align: 'center',
          dataIndex: 'reviewState_dictText',
        },
        {
          title: '感应储位别名',
          dataIndex: 'action',
          align: 'center',
        },
      ],
      url: {
        getInfo: window._CONFIG['mesOPURL']
      },
    }
  },
  methods: {
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys;
      this.selectionRows = selectionRows;
    },
    onClearSelected() {
      this.selectedRowKeys = [];
      this.selectionRows = [];
    },
    searchReset() {
      this.queryParam = {}
    },
    loadData(arg) {
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.queryParam;//查询条件
      this.loading = true;
      
      axios.post(this.url.getInfo, {
        uri: 'KMI.erp_wheel_get_zqrail_json',
        content: JSON.stringify(params)
      }).then((res) => {
        console.log(res)
        this.loading = false;
      });
    }
  },
}
</script>