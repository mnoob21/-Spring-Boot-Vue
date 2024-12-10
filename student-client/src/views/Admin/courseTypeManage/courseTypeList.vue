<template>
  <div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          fixed
          prop="ctype_id"
          label="课程类别号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="ctype_name"
          label="课程类别名"
          width="150">
      </el-table-column>
      <el-table-column
          prop="ctype_author"
          label="创建人"
          width="150">
      </el-table-column>
      <el-table-column
          prop="create_time"
          label="创建时间"
          width="170">
      </el-table-column>
      <el-table-column
          prop="update_time"
          label="修改时间"
          width="170">
      </el-table-column>
      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='删除'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="删除不可复原"
              @confirm="deleteCourseType(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">编辑</el-button>
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
  </div>
</template>

<script>
import moment from "moment";

export default {
  methods: {
    select(row) {
      console.log(row)
    },
    deleteCourseType(row) {
      const that = this
      axios.get('http://localhost:10086/courseType/deleteById/' + row.ctype_id).then(function (resp) {
        console.log(resp)
        if (resp.data.success) {
          that.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
          });
           window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '删除出错，请查询数据库连接',
            type: 'error'
          });
        }
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      })
    },
    offer(row) {
      const tid = sessionStorage.getItem("tid")
      const cid = row.cid
      const term = sessionStorage.getItem("currentTerm")

      const that = this
      axios.get('http://localhost:10086/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '开设成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '开设失败，请联系管理员',
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
    editor(row) {
      this.$router.push({
        path: '/editorCourseType',
        query: {
          ctype_id: row.ctype_id
        }
      })
    }
  },
  created() {
    console.log(this.type)
  },
  data() {
    return {
      tableData: null,
      pageSize: 5,
      total: null,
      tmpList: null,
      type: sessionStorage.getItem("type"),
    }
  },
  props: {
    ruleForm: Object,
    isActive: Boolean
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        // console.log("组件监听 form")
        // console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/courseType/findBySearch", newRuleForm).then(function (resp) {

          resp.data = resp.data.map(item => {
            var formattedDate = '';
            if (item.update_time) {
              formattedDate = moment(item.update_time).format('YYYY-MM-DD HH:mm:ss');
            } else {
              formattedDate = ''; // 或者你可以设置为 'N/A' 或者其它你想要的默认值
            }
            return{
              ctype_id:item.ctype_id,
              ctype_name:item.ctype_name,
              ctype_author:item.ctype_author,
              create_time:moment(item.create_time).format('YYYY-MM-DD HH:mm:ss'),
              update_time:formattedDate,
            }
          })
          console.log("查询结果:");
          console.log(resp)
          that.tmpList = resp.data
          that.total = resp.data.length
          let start = 0, end = that.pageSize
          let length = that.tmpList.length
          let ans = (end < length) ? end : length
          that.tableData = that.tmpList.slice(start, ans)
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>
