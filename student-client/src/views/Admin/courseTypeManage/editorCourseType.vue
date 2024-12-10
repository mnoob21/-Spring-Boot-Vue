<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="课程类别名" prop="ctype_name">
        <el-input v-model="ruleForm.ctype_name" :value="ruleForm.ctype_name"></el-input>
      </el-form-item>
      <el-form-item label="创建人" prop="ctype_author">
        <el-input :disabled="true" v-model.number="ruleForm.ctype_author" :value="ruleForm.ctype_author"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="test">test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      ruleForm: {
        ctype_id: null,
        ctype_name: null,
        ctype_author: null
      },
      rules: {
        ctype_name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],

      }
    };
  },
  created() {
    const that = this
    if (this.$route.query.ctype_id === undefined) {
      this.ruleForm.ctype_id = 1
    }
    else {
      this.ruleForm.ctype_id = this.$route.query.ctype_id
    }
    axios.get('http://localhost:10086/courseType/findById/' + this.ruleForm.ctype_id).then(function (resp) {
      console.log(resp)
      that.ruleForm.ctype_name = resp.data.ctype_name;
      that.ruleForm.ctype_author = resp.data.ctype_author;
      console.log(that.ruleForm)
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          console.log(this.ruleForm)
          axios.post("http://localhost:10086/courseType/updateCourseType", this.ruleForm).then(function (resp) {
            if (resp.data.success) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
              that.$router.push("/queryCourseType")
            }
            else {
              that.$message.error('编辑失败，请检查数据库');
            }
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    test() {
      console.log(this.ruleForm)
    }
  }
}
</script>
