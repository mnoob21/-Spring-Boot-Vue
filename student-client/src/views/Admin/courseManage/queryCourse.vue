<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item label="课程号" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="课程名" prop="cname">
              <el-input v-model="ruleForm.cname"></el-input>
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

            <el-form-item label="课程状态" prop="cstatus">
              <el-select v-model="ruleForm.cstatus">
                <el-option
                    v-for="type in courseStatus"
                    :key="type"
                    :label="type"
                    :value="type">
                </el-option>
              </el-select>
            </el-form-item>



            <el-form-item label="模糊查询" prop="fuzzy">
              <el-switch v-model="ruleForm.fuzzy"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseList from "@/views/Admin/courseManage/courseList";
export default {
  components: {CourseList},
  data() {
    return {
      ruleForm: {
        cid: null,
        cname: null,
        ctype_name:null,
        fuzzy: true,
        cstatus:'',
      },
      courseTypes: [], // 存储课程类型的数组
      courseStatus:[
          "待审核","课程未开启","正在进行","已结束","已驳回"
      ],
      selectedType: '', // 用于 v-model 绑定的选中值
      rules: {
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
