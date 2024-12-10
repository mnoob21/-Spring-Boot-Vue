<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="tid"
          label="教师编号"
          width="80">
      </el-table-column>

      <el-table-column
          prop="tname"
          label="教师昵称"
          width="100">
      </el-table-column>



      <el-table-column
          prop="treal_name"
          label="真实姓名"
          width="80">
      </el-table-column>

      <el-table-column
          prop="tid_number"
          label="身份证号"
          width="170">
      </el-table-column>

      <el-table-column
          prop="tsex"
          label="性别"
          width="70">
      </el-table-column>

      <el-table-column
          prop="tphone"
          label="电话"
          width="120">
      </el-table-column>

      <el-table-column
          prop="temail"
          label="邮箱"
          width="150">
      </el-table-column>

      <el-table-column
          prop="tbirthday"
          label="出生年月"
          width="100">
      </el-table-column>

      <el-table-column
          prop="create_time"
          label="创建时间"
          width="160">
      </el-table-column>

      <el-table-column
          prop="update_time"
          label="修改时间"
          width="160">
      </el-table-column>

      <el-table-column
          label="操作"
          width="100"
          fixed="right">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='删除'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="删除不可复原"
              @confirm="deleteTeacher(scope.row)"
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
    deleteTeacher(row) {
      if (row.tname === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可删除',
          type: 'error'
        });
        return
      }
      const that = this
      axios.get('http://localhost:10086/teacher/deleteById/' + row.tid).then(function (resp) {
        if (resp.data === true) {
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
      }).catch(function (e) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
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
      if (row.tname === 'admin') {
        this.$message({
          showClose: true,
          message: 'admin 不可编辑',
          type: 'error'
        });
        return
      }
      this.$router.push({
        path: '/editorTeacher',
        query: {
          tid: row.tid
        }
      })
    }
  },

  data() {
    return {
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
    }
  },
  props: {
    ruleForm: Object
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("组件监听 form")
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/teacher/findBySearch", newRuleForm).then(function (resp) {
          resp.data = resp.data.map(item => {
            var formattedDate = '';
            if (item.update_time) {
              formattedDate = moment(item.update_time).format('YYYY-MM-DD HH:mm:ss');
            } else {
              formattedDate = ''; // 或者你可以设置为 'N/A' 或者其它你想要的默认值
            }
            return{
              tid:item.tid,
              tname:item.tname,
              password:item.password,
              tsex:item.tsex,
              tphone:item.tphone,
              temail:item.temail,
              tbirthday:item.tbirthday,
              treal_name:item.treal_name,
              tid_number:item.tid_number,
              create_time:moment(item.create_time).format('YYYY-MM-DD HH:mm:ss'),
              update_time:formattedDate,
            }
          })
          console.log("查询结果:");
          console.log(newRuleForm)
          console.log(resp)
          that.tmpList = resp.data
          that.total = resp.data.length
          let start = 0, end = that.pageSize
          let length = that.tmpList.length
          let ans = (end < length) ? end : length
          that.tableData = that.tmpList.slice(start, end)
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>
