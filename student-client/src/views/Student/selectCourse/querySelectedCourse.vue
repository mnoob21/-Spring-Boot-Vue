<template>
  <div>
    <el-card>
      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="cid"
            label="课号"
            width="70">
        </el-table-column>
        <el-table-column
            prop="cname"
            label="课程名称"
            width="180">
        </el-table-column>
        <el-table-column
            prop="tid"
            label="教师号"
            width="70">
        </el-table-column>
        <el-table-column
            prop="tname"
            label="教师名称"
            width="100">
        </el-table-column>

        <el-table-column
            prop="ctype_name"
            label="课程类别"
            width="140">
        </el-table-column>

        <el-table-column
            prop="c_startdate"
            label="课程开始时间"
            width="160">
        </el-table-column>

        <el-table-column
            prop="c_enddate"
            label="课程结束时间"
            width="160">
        </el-table-column>

        <el-table-column
            label="课程链接"
            width="160">
          <template slot-scope="scope">
            <!-- 只有当课程状态为2时，才显示课程链接 -->
            <a v-if="scope.row.c_status === 2" :href="scope.row.c_link" target="_blank">{{ scope.row.c_link }}</a>
            <!-- 如果课程状态不为2，可以显示一个占位符或者不显示任何内容 -->
            <span v-else></span>
          </template>
        </el-table-column>


        <el-table-column
            prop="c_status"
            label="课程状态"
            width="100"
            fixed="right">
          <template slot-scope="scope">
            <!-- 根据 c_status 的值来设置不同的标签样式 -->
            <el-tag
                v-if="scope.row.c_status === 0"
                size="small"
                type="info"
                effect="light"
                style="font-size: 12px; color: gray;">
              待审核
            </el-tag>
            <el-tag
                v-else-if="scope.row.c_status === 1"
                size="small"
                type="info"
                effect="light"
                style="font-size: 12px; color: orangered;">
              课程未开启
            </el-tag>
            <el-tag
                v-else-if="scope.row.c_status === 2"
                size="small"
                type="success"
                effect="dark"
                style="font-size: 13px; color: green;">
              正在进行
            </el-tag>
            <el-tag
                v-else-if="scope.row.c_status === 3"
                size="small"
                type="info"
                effect="light"
                style="font-size: 12px; color: gray;">
              课程结束
            </el-tag>
            <el-tag
                v-else-if="scope.row.c_status === 4"
                size="small"
                type="info"
                effect="light"
                style="font-size: 13px; color: red;">
              已驳回
            </el-tag>
          </template>
        </el-table-column>


        <el-table-column
            label="操作"
            width="100"
            fixed="right">
          <template slot-scope="scope">
            <el-popconfirm
                confirm-button-text='退课'
                cancel-button-text='取消'
                icon="el-icon-info"
                title="确定退课？"
                @confirm="deleteSCT(scope.row)"
            >
              <el-button slot="reference" type="text" size="small">退课</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="changePage"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  methods: {
    deleteSCT(row) {
      const cid = row.cid
      const tid = row.tid
      const sid = sessionStorage.getItem('sid')
      const term = sessionStorage.getItem('currentTerm')
      const sct = {
        cid: cid,
        tid: tid,
        sid: sid,
        term: term
      }

      const that = this
      axios.post('http://localhost:10086/SCT/deleteBySCT', sct).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '退课成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '退课失败，请联系管理员',
            type: 'error'
          });
        }
      })

    },
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },
  },
  data() {
    return {
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem('type')
    }
  },
  created() {
    const sid = sessionStorage.getItem('sid')
    const term = sessionStorage.getItem('currentTerm')
    const that = this
    axios.get('http://localhost:10086/SCT/findBySid/' + sid + '/' + term).then(function (resp) {
      that.tmpList = resp.data
      that.total = resp.data.length
      let start = 0, end = that.pageSize
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, end)
    })
  },

}
</script>
