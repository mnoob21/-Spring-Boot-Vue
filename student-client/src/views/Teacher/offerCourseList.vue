<template>
  <div>
    <el-table
        :data="tableData"
        border
        stripe
        empty-text="你还没有已通过申请的课程哟~"
        style="width: 100%">
      <el-table-column
          fixed
          prop="cid"
          label="课程号"
          width="70">
      </el-table-column>

      <el-table-column
          prop="cname"
          label="课程名"
          width="150">
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
          prop="c_link"
          label="课程链接"
          width="160">
      </el-table-column>


      <el-table-column
          prop="c_status"
          label="课程状态"
          width="100">
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
              confirm-button-text='选择'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确定开设？"
              @confirm="offer(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">开设</el-button>
          </el-popconfirm>
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
          <el-button @click="courseDetails(scope.row)" type="text" size="small">查看课程</el-button>
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
    courseDetails(row){
      console.log(row)
      sessionStorage.setItem("courseName",row.cname);
      sessionStorage.setItem("courseType",row.ctype_name);
      sessionStorage.setItem("courseAuthor",row.c_author);
      sessionStorage.setItem("courseTime",row.c_startdate + '——' + row.c_enddate);
      sessionStorage.setItem("courseDescription",row.c_description);
      sessionStorage.setItem("cid",row.cid);



      this.$router.push("/courseDetails")
    },
    select(row) {
      console.log(row)
    },
    deleteTeacher(row) {
      const that = this
      axios.get('http://localhost:10086/course/deleteById/' + row.cid).then(function (resp) {
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
      })
    },
    offer(row) {
      const tid = sessionStorage.getItem("tid")
      const cid = row.cid
      const term = sessionStorage.getItem("currentTerm")
      const that = this

      if(row.c_status === 0 || row.c_status === 3){
        that.$message({
          showClose: true,
          message: '课程未审核或已结束 无法开设!',
          type: 'error'
        });
      }else if(row.c_status === 4){
        that.$message({
          showClose: true,
          message: '课程审核未通过 无法开设!',
          type: 'error'
        });
      } else{
        axios.get('http://localhost:10086/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {

          if (resp.data.success) {
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
              message: '请勿重复开课!',
              type: 'error'
            });
          }
        })

      }



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
        path: '/teacherEditorCourse',
        query: {
          cid: row.cid
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
      pageSize: 10,
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
        console.log("组件监听 form")
        console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/course/findBySearch", newRuleForm).then(function (resp) {

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
