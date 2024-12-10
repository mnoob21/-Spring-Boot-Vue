<template>
  <div>
    <el-container>
      <el-main>
        <h1>当前年度开设的课程</h1>
        <el-card>
          <el-table
              :data="tableData"
              border
              stripe
              empty-text="你还没有开设课程"
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
                width="140">

              <template slot-scope="scope">


                <el-popconfirm
                    confirm-button-text='确定开启'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    title="确定开启课程？"
                    @confirm="openCourse(scope.row)"
                >
                  <el-button slot="reference" type="text" size="small">开启课程</el-button>
                </el-popconfirm>

                <el-popconfirm
                    confirm-button-text='确定终止'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    title="确定终止课程？"
                    @confirm="terminateCourse(scope.row)"
                >
                  <el-button slot="reference" type="text" size="small">终止课程</el-button>
                </el-popconfirm>

                <el-popconfirm
                    confirm-button-text='取消开设'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    title="确定取消开设？"
                    @confirm="deleteCT(scope.row)"
                >
                  <el-button slot="reference" type="text" size="small">取消开设</el-button>
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
      </el-main>
    </el-container>
  </div>
</template>

<script>
import moment from "moment";

export default {
  methods: {
    changePage(page) {
      page = page - 1
      const that = this
      let start = page * that.pageSize, end = that.pageSize * (page + 1)
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)
    },

    deleteCT(row){

      axios.get('http://localhost:10086/courseTeacher/delete/' + row.cid).then(resp =>{

        console.log(resp)
          if (resp.data.success) {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success'
            });
            window.location.reload()
          }
          else {
            this.$message({
              showClose: true,
              message: '删除出错，请查询数据库连接',
              type: 'error'
            });
          }
      })
    },

    openCourse(row){
      axios.get('http://localhost:10086/course/openCourse/' + row.cid).then(resp =>{
        if (resp.data.success) {
          this.$message({
            showClose: true,
            message: '开启成功',
            type: 'success'
          });
          this.$router.push("/offerCourse")
        }
        else {
          this.$message.error('开启失败!');
        }

      })
    },

    terminateCourse(row){
      axios.get('http://localhost:10086/course/terminateCourse/' + row.cid).then(resp =>{
        if (resp.data.success) {
          this.$message({
            showClose: true,
            message: '课程已终止!',
            type: 'success'
          });
          this.$router.push("/offerCourse")
        }
        else {
          this.$message.error('终止失败!');
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
      tid: null,
      term: null
    }
  },
  created() {
    this.tid = sessionStorage.getItem("tid");
    this.term = sessionStorage.getItem("currentTerm");

    console.log(this.term)

    const that = this
    axios.get('http://localhost:10086/courseTeacher/findMyCourse/' + this.tid + '/' + this.term).then(function (resp) {

      that.tmpList = resp.data
      that.total = resp.data.length
      let start = 0, end = that.pageSize
      let length = that.tmpList.length
      let ans = (end < length) ? end : length
      that.tableData = that.tmpList.slice(start, ans)

    })

  }

}
</script>
