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

            <el-form-item label="模糊查询" prop="fuzzy">
              <el-switch v-model="ruleForm.fuzzy"></el-switch>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <offer-course-list :rule-form="ruleForm"></offer-course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import OfferCourseList from "@/views/Teacher/offerCourseList";
export default {
  components: {OfferCourseList},
  data() {
    return {
      courseTypes: [], // 存储课程类型的数组
      selectedType: '', // 用于 v-model 绑定的选中值
      ruleForm: {
        cid: null,
        cname: null,
        fuzzy: true,
        c_author:sessionStorage.getItem("name"),
      },
      rules: {
        cid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        cname: [
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
