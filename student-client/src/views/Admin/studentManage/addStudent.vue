<template>
  <div>
    <el-form style="width: 50%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户昵称" prop="nickname">
        <el-input
            v-model="ruleForm.nickname"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user-solid"
        ></el-input>
      </el-form-item>

      <el-form-item label="初始密码" prop="password">
        <el-input
            v-model="ruleForm.password"
            placeholder="请输入密码"
            show-password
            prefix-icon="el-icon-ice-cream-round"
        ></el-input>
      </el-form-item>

      <el-form-item label="用户性别" prop="sex">
        <el-select v-model="ruleForm.sex" clearable placeholder="" style="width: 60px;float: left">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="用户手机号" prop="phone">
        <el-input
            v-model="ruleForm.phone"
            placeholder="请输入手机号"
            prefix-icon="el-icon-phone"
        ></el-input>
      </el-form-item>

      <el-form-item label="验证码" prop="captcha">
        <el-input
            v-model="ruleForm.captcha"
            placeholder="六位验证码"
            prefix-icon="el-icon-key"
            style="width: 65%; display: inline-block;"
        ></el-input>
        <el-button
            :type="countdown ? 'info' : 'primary'"
            :disabled="countdown > 0"
            @click="sendCaptcha"
            style="width: 33%; display: inline-block; vertical-align: top;"
        >
          {{ countdown ? `${countdown}秒后重发` : '发送验证码' }}
        </el-button>
      </el-form-item>

      <el-form-item label="用户邮箱" prop="email">
        <el-input
            v-model="ruleForm.email"
            placeholder="请输入邮箱"
            prefix-icon="el-icon-s-promotion"
        ></el-input>
      </el-form-item>

      <el-form-item label="用户出生年月" prop="birthday">
        <el-date-picker
            v-model="ruleForm.birthday"
            type="date"
            placeholder="选择日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>

      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      smsCode:'',
      countdown: 0, // 倒计时秒数
      options:[{
        value: '男',
        label: '男',
      },
        {
          value: '女',
          label: '女',
        }],
      ruleForm: {
        nickname: '',
        password: '',
        sex: '',
        phone: '',
        email: '',
        birthday: '',
        captcha:'',
      },
      rules: {
        nickname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        captcha:[
          { required: true, message: "请输入验证码!", trigger:"blur"},
        ]
      }
    };
  },
  methods: {
    sendCaptcha() {
      if (this.countdown === 0) {
        // 发送验证码的逻辑...
        this.countdown = 60; // 设置倒计时为60秒
        let intervalId = setInterval(() => {
          this.countdown -= 1;
          if (this.countdown === 0) {
            clearInterval(intervalId); // 倒计时结束，清除计时器
          }
        }, 1000);
      }

      console.log(this.ruleForm.phone)
      axios.post("http://localhost:10086/user/sendSMS",this.ruleForm).then(resp =>{
        console.log(resp.data)
        this.smsCode = resp.data
        this.$message({
          showClose: true,
          message: "验证码已发送!",
          type: "success",
        });
      })


    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 通过前端校验
          const that = this
          console.log(this.ruleForm)
          let form = {
            "id": this.ruleForm.id,
            "nickName": this.ruleForm.nickname,
            "passWord": this.ruleForm.password,
            "sex": this.ruleForm.sex,
            "phone": this.ruleForm.phone,
            "email": this.ruleForm.email,
            "birthday": this.ruleForm.birthday
          }
          axios.post("http://localhost:10086/user/register", form).then(function (resp) {
            if(String(that.smsCode) === String(that.ruleForm.captcha)){
              if (resp.data.success) {
                that.$message({
                  showClose: true,
                  message: '插入成功',
                  type: 'success'
                });
                that.$router.push("/studentList")
              } else {
                that.$message.error('插入失败，该手机号已存在');
              }
            }else{
              that.$message({
                showClose: true,
                message: "验证码错误！",
                type: "error",
              });
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
