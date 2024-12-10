<template>
  <div>
    <el-form style="width: 50%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="教师昵称" prop="tname">
        <el-input v-model="ruleForm.tname" :value="ruleForm.tname"></el-input>
      </el-form-item>

     
      <el-form-item label="真实姓名" prop="treal_name">
        <el-input :disabled="true" v-model="ruleForm.treal_name" :value="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="身份证号码" prop="tid_number">
        <el-input :disabled="true" v-model="ruleForm.tid_number" :value="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="tsex">
        <el-input :disabled="true" style="width: 60px;float: left" v-model="ruleForm.tsex" :value="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="tphone">
        <el-input :disabled="true" v-model="ruleForm.tphone" :value="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="temail">
        <el-input :disabled="true" v-model="ruleForm.temail" :value="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="出生年月" prop="tbirthday">
        <el-input :disabled="true" v-model="ruleForm.tbirthday" :value="ruleForm.password"></el-input>
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
        tid: null,
        tname: "",
        password: "",
        treal_name: "",
        tid_number: "",
        tsex: "",
        tphone: "",
        temail: "",
        tbirthday: "",
      },
      rules: {
        tname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
      }
    };
  },
  created() {
    const that = this
    if (this.$route.query.tid === undefined) {
      this.ruleForm.tid = 6
    }
    else {
      this.ruleForm.tid = this.$route.query.tid
    }
    axios.get('http://localhost:10086/teacher/findById/' + this.ruleForm.tid).then(function (resp) {
      that.ruleForm = resp.data
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          if (that.ruleForm.tname === 'admin') {
            that.$message({
              showClose: true,
              message: 'admin 不可编辑',
              type: 'error'
            });
            this.$router.push('/queryTeacher')
            return
          }
          console.log(this.ruleForm)
          axios.post("http://localhost:10086/teacher/updateTeacher", this.ruleForm).then(function (resp) {
            if (resp.data != null) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
            }
            else {
              that.$message.error('编辑失败，请检查数据库');
            }
            that.$router.push("/queryTeacher")
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
