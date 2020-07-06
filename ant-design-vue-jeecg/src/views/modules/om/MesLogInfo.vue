<template>
  <a-card :bordered="false">
    <!--导航区域-->
    <div>
      <a-tabs defaultActiveKey="1" @change="callback">
        <a-tab-pane tab="操作日志" key="1">
          <!-- 查询区域 -->
          <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
              <a-row :gutter="24">
                <a-col :md="6" :sm="8">
                  <a-form-item label="模块名">
                    <a-input placeholder="请输入模块名称" v-model="queryParam.moduleId"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="8">
                  <a-form-item label="方法名">
                    <a-input placeholder="请输入方法名" v-model="queryParam.functionId"></a-input>
                  </a-form-item>
                </a-col>
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-col :md="6" :sm="24">
                    <a-button type="primary" @click="searchQuery">查询</a-button>
                    <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                    <a-switch @change="timeHandler" v-model="timerChecked" style="margin-left: 300px;" checkedChildren="开启刷新"
                      unCheckedChildren="关闭刷新" />
                  </a-col>
                </span>
              </a-row>
            </a-form>
          </div>

          <!--table区 -->
          <div>
            <a-table ref="table" size="middle" bordered rowKey="id" :columns="columns" :dataSource="dataSource"
              :pagination="ipagination" :loading="loading" @change="handleTableChange">
            </a-table>
          </div>
        </a-tab-pane>
        <a-tab-pane tab="操作异常日志" key="2">
          <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
              <a-row :gutter="24">
                <a-col :md="6" :sm="8">
                  <a-form-item label="模块名">
                    <a-input placeholder="请输入模块名称" v-model="queryParam.moduleId"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="8">
                  <a-form-item label="方法名">
                    <a-input placeholder="请输入方法名" v-model="queryParam.functionId"></a-input>
                  </a-form-item>
                </a-col>
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-col :md="6" :sm="24">
                    <a-button type="primary" @click="searchQuery">查询</a-button>
                    <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                    <a-switch @change="timeHandler" v-model="timerChecked" style="margin-left: 300px;" checkedChildren="开启刷新"
                      unCheckedChildren="关闭刷新" />
                  </a-col>
                </span>
              </a-row>
            </a-form>
          </div>

          <!--table区 -->
          <div>
            <a-table ref="table" size="middle" bordered rowKey="id" :columns="column2" :dataSource="dataSource"
              :pagination="ipagination" :loading="loading" @change="handleTableChange">
              <span slot="action" slot-scope="text, record">
                <a @click="shwoLogDetail(record.id)">查看</a>
              </span>
            </a-table>
          </div>
        </a-tab-pane>
        <a-tab-pane tab="错误日志" key="3">
          <!-- 查询区域 -->
          <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery3">
              <a-row :gutter="24">
                <a-col :md="6" :sm="8">
                  <a-form-item label="服务名">
                    <a-input placeholder="请输入服务名称" v-model="queryParam3.serviceName"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :md="6" :sm="8">
                  <a-form-item label="用户">
                    <a-input placeholder="请输入用户名" v-model="queryParam3.userName"></a-input>
                  </a-form-item>
                </a-col>
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-col :md="6" :sm="24">
                    <a-button type="primary" @click="searchQuery3">查询</a-button>
                    <a-button style="margin-left: 8px" @click="searchReset">重置</a-button>
                    <a-switch @change="timeHandler" v-model="timerChecked" style="margin-left: 300px;" checkedChildren="开启刷新"
                      unCheckedChildren="关闭刷新" />
                  </a-col>
                </span>
              </a-row>
            </a-form>
          </div>

          <!--table区 -->
          <div>
            <a-table ref="table" size="middle" bordered rowKey="id" :columns="columns3" :dataSource="dataSource"
              :pagination="ipagination" :loading="loading" @change="handleTableChange">
              <span slot="description" slot-scope="text">
                <j-ellipsis :value="text" :length="100" />
              </span>
            </a-table>
          </div>
        </a-tab-pane>
        <a-tab-pane tab="短信失败日志" key="4">短信失败日志</a-tab-pane>
      </a-tabs>
    </div>

    <MesLogXmlInfo v-if="modalVisiable" ref="modalForm"></MesLogXmlInfo>
  </a-card>
</template>

<script>
  import {
    getAction
  } from '@/api/manage'
  import JEllipsis from '@/components/jeecg/JEllipsis'
  import MesLogXmlInfo from './modules/MesLogXmlInfo.vue'
  export default {
    name: 'MesLogInfo',
    components: {
      MesLogXmlInfo,
      JEllipsis
    },
    data() {
      return {
        description: '新造MES日志管理页面',
        url: {
          list: '/om/mesLog/list',
          errorList: '/om/mesLog/errorlist'
        },
        timer: '',
        timerChecked: false,
        /* 数据源 */
        dataSource: [],
        /* 分页参数 */
        ipagination: {
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
        /* table加载状态 */
        loading: false,
        // 查询条件
        logType: '1',
        queryParam: {
          moduleId: '',
          functionId: ''
        },
        queryParam3: {
          serviceName: '',
          userName: ''
        },
        modalVisiable: false,
        // 不同类型下的cloumn
        columns: [{
            title: '模块名',
            align: 'center',
            dataIndex: 'moduleid'
          },
          {
            title: '方法名',
            align: 'center',
            dataIndex: 'functionid'
          },
          {
            title: '计算机名',
            align: 'center',
            dataIndex: 'computername'
          },
          {
            title: '当前用户',
            align: 'center',
            dataIndex: 'curuserno'
          },
          {
            title: '记录时间',
            align: 'center',
            dataIndex: 'createdate'
          },
          {
            title: '返回结果',
            align: 'center',
            dataIndex: 'result'
          },
          {
            title: 'KeyValue',
            align: 'center',
            dataIndex: 'keyvalue'
          }
        ],

        column2: [{
            title: '模块名',
            align: 'center',
            dataIndex: 'moduleid'
          },
          {
            title: '方法名',
            align: 'center',
            dataIndex: 'functionid'
          },
          {
            title: '计算机名',
            align: 'center',
            dataIndex: 'computername'
          },
          {
            title: '当前用户',
            align: 'center',
            dataIndex: 'curuserno'
          },
          {
            title: '记录时间',
            align: 'center',
            dataIndex: 'createdate'
          },
          {
            title: '返回结果',
            align: 'center',
            dataIndex: 'result'
          },
          {
            title: '操作类型',
            align: 'center',
            dataIndex: 'action',
            scopedSlots: {
              customRender: 'action'
            }
          }
        ],

        columns3: [{
            title: '服务名称',
            align: 'center',
            dataIndex: 'servicename'
          },
          {
            title: '字段',
            align: 'left',
            dataIndex: 'keyfield'
          },
          {
            title: '值',
            align: 'left',
            dataIndex: 'keyvalue'
          }, {
            title: '描述',
            align: 'left',
            dataIndex: 'description',
            scopedSlots: {
              customRender: 'description'
            }
          }, {
            title: '用户',
            align: 'center',
            dataIndex: 'userid'
          }, {
            title: '记录时间',
            align: 'center',
            dataIndex: 'recorddate'
          }
        ]
      }
    },
    created() {
      this.loadData(1)
    },
    beforeDestroy() {
      clearInterval(this.timer);
    },
    methods: {
      callback(key) {
        this.logType = key;
        if (key == 1 || key == 2) {
          this.loadData(1)
        } else if (key == 3) {
          this.loadData3(1)
        } else if (key == 4) {

        }

        // 切换tab时停止计时器工作
        clearInterval(this.timer);
        this.timerChecked = false;
      },
      loadData(arg) {
        if (!this.url.list) {
          this.$message.error('请设置url.list属性!')
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1
        }
        var params = this.queryParam //查询条件
        params.logType = this.logType
        params.pageNo = this.ipagination.current
        params.pageSize = this.ipagination.pageSize
        this.loading = true
        getAction(this.url.list, params).then(res => {
          if (res.success) {
            this.dataSource = res.result.records
            this.ipagination.total = res.result.total
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      },
      // 加载error信息
      loadData3(arg) {
        if (!this.url.errorList) {
          this.$message.error('请设置url.errorList属性!')
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1
        }
        var params = this.queryParam3 //查询条件
        params.pageNo = this.ipagination.current
        params.pageSize = this.ipagination.pageSize
        this.loading = true
        getAction(this.url.errorList, params).then(res => {
          if (res.success) {
            this.dataSource = res.result.records
            this.ipagination.total = res.result.total
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      },
      searchQuery() {
        this.loadData(1)
      },
      searchQuery3() {
        this.loadData3(1)
      },
      searchReset() {
        this.queryParam.moduleId = ''
        this.queryParam.functionId = ''
      },
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field
          this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
        }
        this.ipagination = pagination
        if (this.logType == 1 || this.logType == 2) {
          this.loadData()
        } else if (this.logType == 3) {
          this.loadData3()
        }

      },
      shwoLogDetail(id) {
        this.modalVisiable = true
        this.$nextTick(() => {
          this.$refs.modalForm.init(id)
        })
      },
      timeHandler(flag) {
        if (flag) {
          this.timer = setInterval(() => {
            if (this.logType == 1 || this.logType == 2) {
              this.loadData(1);
            } else if (this.logType == 3) {
              this.loadData3(1);
            }
          }, 3000)
        } else {
          clearInterval(this.timer);
        }
      }
    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less'
</style>
