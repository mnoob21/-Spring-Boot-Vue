<template>
  <div>
    <el-container>
      <el-header>
        <div style="text-align: center; font-size: 25px; font-weight: bolder">
          <i class="el-icon-s-home" style="margin-right: 25px"></i>
          艺趣生活馆后台系统
        </div>
      </el-header>

      <el-main>
        <el-card class="login-module">
          <div slot="header" class="clearfix">
            <span
              style="
                text-align: center;
                font-size: 20px;
                font-family: 'Microsoft YaHei';
              "
            >
              <p>
                <i
                  class="el-icon-office-building"
                  style="margin-right: 18px"
                ></i
                >注册
              </p>
            </span>
          </div>
          <div>
            <el-form
              :model="ruleForm"
              :rules="rules"
              ref="ruleForm"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item label="用户名" prop="nickname">
                <el-input
                  v-model.number="ruleForm.nickname"
                  placeholder="请输入用户名"
                  prefix-icon="el-icon-user-solid"
                ></el-input>
              </el-form-item>

              <el-form-item label="用户密码" prop="password">
                <el-input
                  v-model="ruleForm.password"
                  placeholder="请输入密码"
                  show-password
                  prefix-icon="el-icon-ice-cream-round"
                ></el-input>
              </el-form-item>

              <el-form-item label="真实姓名" prop="realname" v-if="isTeacher">
                <el-input
                    v-model="ruleForm.realname"
                    placeholder="请输入真实姓名"
                    prefix-icon="el-icon-s-custom"
                ></el-input>
              </el-form-item>

              <el-form-item label="身份证号码" prop="idnumber" v-if="isTeacher">
                <el-input
                    v-model="ruleForm.idnumber"
                    placeholder="请输入身份证号码"
                    prefix-icon="el-icon-postcard"
                ></el-input>
              </el-form-item>

              <el-form-item label="用户性别" prop="sex">
                <el-select v-model="ruleForm.sex" clearable placeholder="" style="width: 60px;float: right">
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

              <el-form-item label="用户类型" prop="type">
                <el-radio-group v-model="ruleForm.type">
                  <el-radio label="user" value="user">普通用户</el-radio>
                  <el-radio label="teacher" value="teacher">教师</el-radio>
<!--                  <el-radio label="admin" value="admin">管理员</el-radio>-->
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-link href="/login" target="_blank" style="float: left" :underline="false">返回登录</el-link>
                <el-button type="primary" @click="submitForm('ruleForm')"
                  >注册</el-button
                >
                <el-button @click="resetForm('ruleForm')">重置</el-button>
<!--                <el-button @click="test('ruleForm')">test</el-button>-->
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      smsCode:'',
      countdown: 0, // 倒计时秒数
      isTeacher:false,
      options:[{
        value: '男',
        label: '男',
      },
      {
        value: '女',
        label: '女',
      }],
      ruleForm: {
        id: null,
        nickname: '',
        password: '',
        realname: '',
        idnumber: '',
        sex: '',
        phone: '',
        email: '',
        birthday: '',
        type: '',
        captcha:'',
      },

      rules: {
        nickname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [{ required: true, message: "请输入密码！", trigger: "blur" }],
        idnumber: [
          { required: true, message: '请输入身份证号码', trigger: 'blur' },
          { pattern: /^(?:\d{15}|\d{18}|\d{17}X|\d{17}x)$/, message: '身份证号码格式不正确', trigger: 'blur' }],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        type: [{ required: true, message: "请选择！", trigger: "change" }],
        captcha:[
          { required: true, message: "请输入验证码!", trigger:"blur"},
        ]
      },
    };
  },

  //点击事件
  watch: {
    //当选择老师时弹出真实姓名和身份证号输入框
    'ruleForm.type'(newVal) {
      if (newVal === 'teacher') {
        this.isTeacher = true;
      } else {
        this.isTeacher = false;
      }
    }
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
          if(this.ruleForm.type === "user"){
            let form = {
              "id": this.ruleForm.id,
              "nickName": this.ruleForm.nickname,
              "passWord": this.ruleForm.password,
              "sex": this.ruleForm.sex,
              "phone": this.ruleForm.phone,
              "email": this.ruleForm.email,
              "birthday": this.ruleForm.birthday
            }
            axios.post("http://localhost:10086/user/register",form).then(resp =>{
              if(String(this.smsCode) === String(this.ruleForm.captcha)){
                if(resp.data.success){
                  this.$message({
                    showClose: true,
                    message: resp.data.msg,
                    type: "success",
                  });
                  this.$router.push("/login");
                }else{
                  this.$message({
                    showClose: true,
                    message: resp.data.msg,
                    type: "error",
                  });
                }
              }else{
                this.$message({
                  showClose: true,
                  message: "验证码错误！",
                  type: "error",
                });
              }

            }).catch(error => {
              this.$message({
                showClose: true,
                message: "注册失败：" + error.response.data.msg,
                type: "error",
              });
            });
          }
          if(this.ruleForm.type === "teacher"){
            let form = {
              "id": this.ruleForm.id,
              "nickName": this.ruleForm.nickname,
              "passWord": this.ruleForm.password,
              "realName": this.ruleForm.realname,
              "idNumber": this.ruleForm.idnumber,
              "sex": this.ruleForm.sex,
              "phone": this.ruleForm.phone,
              "email": this.ruleForm.email,
              "birthday": this.ruleForm.birthday
            }
            axios.post("http://localhost:10086/teacher/register",form).then(resp =>{
              if(String(this.smsCode) === String(this.ruleForm.captcha)){
                if(resp.data.success){
                  this.$message({
                    showClose: true,
                    message: resp.data.msg,
                    type: "success",
                  });
                  this.$router.push("/login");
                }else{
                  this.$message({
                    showClose: true,
                    message: resp.data.msg,
                    type: "error",
                  });
                }
              }else{
                this.$message({
                  showClose: true,
                  message: "验证码错误！",
                  type: "error",
                });
              }

            }).catch(error => {
              this.$message({
                showClose: true,
                message: "注册失败：" + error.response.data.msg,
                type: "error",
              });
            });
          }

        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    test(forName) {
      console.log(this.ruleForm);
    },
  },
};
</script>

<style>
.login-module {
  /*width: 380px;*/
  /*height: 325px;*/
  margin-top: 60px;
  /*border: none;*/
  position: absolute;
  right: 500px;
  text-align: center;
  width: 30%;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}
</style>
