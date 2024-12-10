<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">


            <el-form-item label="课程号" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="课程名" prop="cname">
              <el-input v-model.number="ruleForm.cname"></el-input>
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
            <el-form-item label="课程模糊查询">
              <el-switch v-model="ruleForm.cFuzzy"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <select-course-list :rule-form="ruleForm"></select-course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import SelectCourseList from "@/views/Student/selectCourse/selectCourseList";
export default {
  components: {SelectCourseList},
  data() {
    return {
      ruleForm: {
        tid: null,
        cid: null,
        cname: null,
        tname: null,
        tFuzzy: true,
        cFuzzy: true
      },
      courseTypes: [], // 存储课程类型的数组
      rules: {
        tid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        cid: [
          { type: 'number', message: '必须是数字类型' }
        ],
      }
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
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
  }
}
</script>
