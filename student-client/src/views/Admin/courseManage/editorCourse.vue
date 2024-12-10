<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="课程名称" prop="cname">
        <el-input v-model="ruleForm.cname" :value="ruleForm.cname"></el-input>
      </el-form-item>

      <el-form-item label="课程类别" prop="ctype_name">
        <el-select v-model="ruleForm.ctype_name">
          <el-option
              v-for="type in courseTypes"
              :key="type"
              :label="type"
              :value="type">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="课程日期" prop="courseDate">
        <el-date-picker
            v-model="courseDate"
            type="date"
            placeholder="选择日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="课程开展时间" prop="courseDate">
        <div style="display: flex; align-items: center;">
          <el-time-select
              placeholder="起始时间"
              v-model="startTime"
              :picker-options="{
        start: '08:00',
        step: '00:30',
        end: '22:00'
      }"
              style="flex-grow: 0;margin-right: 10px">
          </el-time-select>

          <span>——</span>

          <el-time-select
              placeholder="结束时间"
              v-model="endTime"
              :picker-options="{
        start: '08:00',
        step: '00:30',
        end: '22:00',
        minTime: startTime
      }"
              style="flex-grow: 0; margin-left: 10px;">
          </el-time-select>
        </div>
      </el-form-item>

      <el-form-item label="课程链接" prop="c_link">
        <el-input
            style="width: 60%"
            v-model.number="ruleForm.c_link"

        ></el-input>
      </el-form-item>



      <el-form-item label="课程简介" prop="c_description">
        <el-input
            type="textarea"
            v-model="ruleForm.c_description"
            :maxlength="200"
            show-word-limit
            rows="4"
            placeholder="请输入课程简介（最多200字）">
        </el-input>
      </el-form-item>



      <el-form-item label="课程状态" prop="c_status">

          <!-- 根据 c_status 的值来设置不同的标签样式 -->
          <el-tag
              v-if="ruleForm.c_status === 0"
              size="small"
              type="info"
              effect="light"
              style="font-size: 12px; color: gray;">
            待审核
          </el-tag>
          <el-tag
              v-else-if="ruleForm.c_status === 1"
              size="small"
              type="info"
              effect="light"
              style="font-size: 12px; color: orangered;">
            课程未开启
          </el-tag>
          <el-tag
              v-else-if="ruleForm.c_status === 2"
              size="small"
              type="success"
              effect="dark"
              style="font-size: 13px; color: green;">
            正在进行
          </el-tag>
          <el-tag
              v-else-if="ruleForm.c_status === 3"
              size="small"
              type="info"
              effect="light"
              style="font-size: 12px; color: gray;">
            课程结束
          </el-tag>
        <el-tag
            v-else-if="ruleForm.c_status === 4"
            size="small"
            type="info"
            effect="light"
            style="font-size: 13px; color: red;">
          已驳回
        </el-tag>

      </el-form-item>

      <el-form-item label="创建人" prop="c_author">
        <el-input
            style="width: 20%"
            v-model.number="ruleForm.c_author"
            :disabled="true"
        ></el-input>
      </el-form-item>



      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button v-show="isReview" @click="review">通过审核</el-button>
        <el-button v-show="isOverrule" @click="overrule">驳回申请</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import moment from "moment";

export default {
  data() {
    return {
      isReview:false,
      isOverrule:false,
      courseDate:'',
      startTime:'',
      endTime:'',
      reviewer:sessionStorage.getItem("name"),
      courseTypes: [], // 存储课程类型的数组
      selectedType: '', // 用于 v-model 绑定的选中值
      ruleForm: {
        cid: null,
        cname: null,
        ctype_name: null,
        c_author: null,
        c_startdate:this.courseDate + ' ' + this.startTime,
        c_enddate:this.courseDate + ' ' + this.endTime,
        c_status: null,
        c_reviewer: this.reviewer,
        c_description:'',
      },
      rules: {
        cname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],

      }
    };
  },
  created() {

    axios.post('http://localhost:10086/courseType/getAllCourseType',this.ruleForm)
        .then(resp => {
          this.courseTypes = resp.data;
          console.log(resp.data);
        })
        .catch(error => {
          console.error("请求失败:", error);
        });
    const that = this
    if (this.$route.query.cid === undefined) {
      this.ruleForm.cid = 1
    }
    else {
      this.ruleForm.cid = this.$route.query.cid
    }
    axios.get('http://localhost:10086/course/findById/' + this.ruleForm.cid).then(function (resp) {
      that.ruleForm = resp.data[0]
      that.courseDate = moment(that.ruleForm.c_enddate).format('YYYY-MM-DD');
      that.startTime = that.ruleForm.c_startdate.slice(-5);
      that.endTime = that.ruleForm.c_enddate.slice(-5);
      that.ruleForm.c_reviewer = that.reviewer;

      if(that.ruleForm.c_status === 0){
        that.isReview = true;
        that.isOverrule = true;
      }
      if(that.ruleForm.c_status === 1 || that.ruleForm.c_status === 2){
        that.isOverrule = true;
      }
      console.log(that.isReview)
      console.log(that.ruleForm)
    })
  },
  methods: {
    updateDates() {
      this.ruleForm.c_startdate = moment(this.courseDate).format('YYYY-MM-DD') + ' ' + this.startTime;
      this.ruleForm.c_enddate = moment(this.courseDate).format('YYYY-MM-DD') + ' ' + this.endTime;
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          console.log(this.ruleForm)
          axios.post("http://localhost:10086/course/updateCourse", this.ruleForm).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
            }
            else {
              that.$message.error('编辑失败，请检查数据库');
            }
            that.$router.push("/queryCourse")
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    review() {
      axios.post("http://localhost:10086/course/review",this.ruleForm).then(resp =>{
        console.log(resp)
        if (resp.data.success) {
          this.$message({
            showClose: true,
            message: '审核成功',
            type: 'success'
          });
          this.$router.push("/queryCourse")
        }
        else {
          this.$message.error('审核失败，请检查数据库');
        }

      })

    },
    overrule(){
      axios.post("http://localhost:10086/course/overrule",this.ruleForm).then(resp =>{
        console.log(resp)
        if (resp.data.success) {
          this.$message({
            showClose: true,
            message: '驳回成功',
            type: 'success'
          });
          this.$router.push("/queryCourse")
        }
        else {
          this.$message.error('审核失败，请检查数据库');
        }

      })
    }
  },

  watch:{
    startTime(newVal) {
      this.updateDates();
    },
    endTime(newVal) {
      this.updateDates();
    },
    courseDate(newVal) {
      this.updateDates();
    },
  },
}
</script>
