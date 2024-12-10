<template>
  <div>
    <el-form style="width: 50%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="课程类别名" prop="typename">
        <el-input v-model="ruleForm.typename"></el-input>
      </el-form-item>
      <el-form-item label="创建人" prop="author" >
        <el-input style="width: 20%"
            v-model.number="ruleForm.author"
            :disabled="true"
        ></el-input>
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
        typename: null,
        author: sessionStorage.getItem('name'),
      },
      rules: {
        typename: [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],

      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          // console.log(this.ruleForm)
          let form = {
            "ctype_id": this.ruleForm.id,
            "ctype_name": this.ruleForm.typename,
            "ctype_author": this.ruleForm.author,

          }

          axios.post("http://localhost:10086/courseType/add", form).then(function (resp) {
            console.log(resp)
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '添加成功',
                type: 'success'
              });
              that.$router.push("/queryCourseType")
            }
            else {
              that.$message.error('添加失败!该课程类别已存在');
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
