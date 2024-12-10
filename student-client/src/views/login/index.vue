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
                >登陆
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
<!--              <el-form-item label="用户 id" prop="id">-->
<!--                <el-input-->
<!--                  v-model.number="ruleForm.id"-->
<!--                  prefix-icon="el-icon-lollipop"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->

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

              <el-form-item :label="temp" prop="phone">
                <el-input
                    v-model.number="ruleForm.phone"
                    :placeholder="'请输入'+temp"
                    prefix-icon="el-icon-phone"
                ></el-input>
              </el-form-item>


              <el-form-item label="验证码" prop="captcha" v-if="isAdmin">
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


              <el-form-item label="用户类型" prop="type">
                <el-radio-group v-model="ruleForm.type">
                  <el-radio label="student" value="student">普通用户</el-radio>
                  <el-radio label="teacher" value="teacher">教师</el-radio>
                  <el-radio label="admin" value="admin">管理员</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-link href="/register" target="_blank" style="float: left" :underline="false">注册</el-link>
                <el-button type="primary" @click="submitForm('ruleForm')"
                  >登陆</el-button
                >
                <el-button @click="resetForm('ruleForm')">重置</el-button>

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
      temp:'手机号',
      smsCode:'',
      countdown: 0, // 倒计时秒数
      isAdmin:true,
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
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        type: [{ required: true, message: "请选择", trigger: "change" }],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确", trigger: "blur" }
        ],
        nickname: [
          { required: true, message: "请输入用户名！", trigger: "blur" },
        ],
        captcha:[
          { required: true, message: "请输入验证码!", trigger:"blur"},
        ]
      },
    };
  },

  watch: {
    //当选择老师时弹出真实姓名和身份证号输入框
    'ruleForm.type'(newVal) {
      if (newVal === 'admin') {
        this.isAdmin = false;
        this.temp = '身份标识码'
      } else {
        this.isAdmin = true;
        this.temp = '手机号'
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
      const that = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let check = false;
          let name = null;

          axios
              .get("http://localhost:10086/info/getCurrentTerm")
              .then(function (resp) {
                sessionStorage.setItem("currentTerm", resp.data);
              });

          axios
              .get("http://localhost:10086/info/getForbidCourseSelection")
              .then(function (resp) {
                sessionStorage.setItem("ForbidCourseSelection", resp.data);
              });

          if (
              that.ruleForm.type === "teacher"
          ) {
            let form = {
              tid: that.ruleForm.id,
              password: that.ruleForm.password,
              tname: that.ruleForm.nickname,
              phone: that.ruleForm.phone,

            };
            console.log(form);
            axios
                .post("http://localhost:10086/teacher/login", form)
                .then(function (resp) {
                  console.log("教师登陆验证信息：" + resp.data);
                  check = resp.data;
                  if(String(that.smsCode) === String(that.ruleForm.captcha)){
                    if (check === true) {
                      axios
                          .get(
                              "http://localhost:10086/teacher/findByPhone/" +
                              that.ruleForm.phone
                          )
                          .then(function (resp) {
                            console.log("登陆页正在获取用户信息" + resp.data);
                            name = resp.data.tname;
                            let createtime = resp.data.create_time.substring(0, 10);

                            sessionStorage.setItem("token", "true");
                            sessionStorage.setItem("type", that.ruleForm.type);
                            sessionStorage.setItem("name", name);
                            sessionStorage.setItem("tid", resp.data.tid);
                            sessionStorage.setItem("sex", resp.data.tsex);
                            sessionStorage.setItem("phone", resp.data.tphone);
                            sessionStorage.setItem("email", resp.data.temail);
                            sessionStorage.setItem("birthday", resp.data.tbirthday);
                            sessionStorage.setItem("createTime", createtime);
                            sessionStorage.setItem("password", that.ruleForm.password);

                            console.log(
                                "name: " +
                                name +
                                " " +
                                that.ruleForm.type +
                                " " +
                                resp.data.tid
                            );
                            if (resp.data) {
                              that.$message({
                                showClose: true,
                                message: "登陆成功，欢迎 " + name + "!",
                                type: "success",
                              });
                              that.$router.push("/teacher");
                            } else {
                              that.$message({
                                showClose: true,
                                message: "admin 登陆失败，检查登陆类型",
                                type: "error",
                              });
                            }
                          });
                    } else {
                      that.$message({
                        showClose: true,
                        message: "登陆失败，检查账号密码",
                        type: "error",
                      });
                    }
                  }else{
                    that.$message({
                      showClose: true,
                      message: "验证码错误!请输入正确的验证码",
                      type: "error",
                    })
                  }

                });
          } else if (that.ruleForm.type === "student") {
            let form = {
              sid: that.ruleForm.id,
              password: that.ruleForm.password,
              phone: that.ruleForm.phone,
              sname: that.ruleForm.nickname,

            };
            axios
                .post("http://localhost:10086/student/login", form)
                .then(function (resp) {
                  console.log("学生登陆验证信息：" + resp.data);
                  check = resp.data;

                  if(String(that.smsCode) === String(that.ruleForm.captcha)){
                    if (check === true) {
                      axios
                          .get(
                              "http://localhost:10086/student/findByPhone/" +
                              that.ruleForm.phone
                          )
                          .then(function (resp) {
                            console.log("登陆页正在获取用户信息" + resp.data);
                            console.log(resp.data);
                            name = resp.data.sname;
                            let createtime = resp.data.create_time.substring(0, 10);

                            sessionStorage.setItem("token", "true");
                            sessionStorage.setItem("type", that.ruleForm.type);
                            sessionStorage.setItem("name", name);
                            sessionStorage.setItem("sid", resp.data.sid);
                            sessionStorage.setItem("sex", resp.data.ssex);
                            sessionStorage.setItem("phone", resp.data.sphone);
                            sessionStorage.setItem("email", resp.data.semail);
                            sessionStorage.setItem("birthday", resp.data.sbirthday);
                            sessionStorage.setItem("createTime", createtime);
                            sessionStorage.setItem("password", that.ruleForm.password);

                            that.$message({
                              showClose: true,
                              message: "登陆成功，欢迎 " + name + "!",
                              type: "success",
                            });

                            console.log("正在跳转：" + "/" + that.ruleForm.type);

                            // 3. 路由跳转
                            that.$router.push({
                              path: "/" + that.ruleForm.type,
                              query: {},
                            });
                          });
                    } else {
                      that.$message({
                        showClose: true,
                        message: "账号密码错误，请联系管理员",
                        type: "error",
                      });
                    }
                  }else{
                    that.$message({
                      showClose: true,
                      message: "验证码错误!请输入正确的验证码",
                      type: "error",
                    })
                  }

                });
          } else {
            let form = {
              aid: that.ruleForm.id,
              password: that.ruleForm.password,
              aphone: that.ruleForm.phone,
              aname: that.ruleForm.nickname,
            };
            axios
                .post("http://localhost:10086/admin/login", form)
                .then(resp => {
                  name = resp.data.aname
                  if(resp.data != ''){


                    sessionStorage.setItem("token", "true");
                    sessionStorage.setItem("type", that.ruleForm.type);
                    sessionStorage.setItem("name", name);
                    sessionStorage.setItem("aid", resp.data.aid);
                    sessionStorage.setItem("phone", resp.data.aphone);
                    sessionStorage.setItem("password", resp.data.password);
                    that.$message({
                      showClose: true,
                      message: "登录成功！欢迎管理员 " + name,
                      type: "success",
                    });
                    that.$router.push("/admin");
                  }else{
                    that.$message({
                      showClose: true,
                      message: "管理员登录失败！！",
                      type: "error",
                    });
                  }
                  console.log(resp)
                })


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
