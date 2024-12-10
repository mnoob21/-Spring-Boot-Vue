<template>
  <div>
    <el-form style="width: 50%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户昵称" prop="sname">
        <el-input v-model="ruleForm.sname" :value="ruleForm.sname"></el-input>
      </el-form-item>




      <el-form-item label="性别" prop="ssex" >
        <el-input style="width: 60px;float: left"
            v-model="ruleForm.ssex"
            :disabled="true"
            :value="ruleForm.ssex">
        </el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="sphone">
        <el-input
            v-model="ruleForm.sphone"
            :disabled="true"
            :value="ruleForm.sphone">
        </el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="semail">
        <el-input
            v-model="ruleForm.semail"
            :disabled="true"
            :value="ruleForm.semail">
        </el-input>
      </el-form-item>

      <el-form-item label="出生年月" prop="sbirthday">
        <el-input
            v-model="ruleForm.sbirthday"
            :disabled="true"
            :value="ruleForm.sbirthday">
        </el-input>
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
        sid: null,
        sname: "",
        password: "",
        ssex: "",
        sphone: "",
        semail: "",
        sbirthday: "",
      },
      rules: {
        sname: [
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
    if (this.$route.query.sid === undefined) {
      this.ruleForm.sid = 1
    }
    else {
      this.ruleForm.sid = this.$route.query.sid
    }
    axios.get('http://localhost:10086/student/findById/' + this.ruleForm.sid).then(function (resp) {
      that.ruleForm = resp.data
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          console.log(this.ruleForm)
          axios.post("http://localhost:10086/student/updateStudent", this.ruleForm).then(function (resp) {
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
            that.$router.push("/studentList")
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
