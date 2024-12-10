<template>
  <el-container>
    <el-main>
      <el-card>
        <el-form style="width: 50%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户昵称" prop="name">
            <el-input
                v-model.name="ruleForm.name"
                :value="ruleForm.name"
                prefix-icon="el-icon-user-solid"
            ></el-input>
          </el-form-item>

          <el-form-item label="旧密码" prop="oldpass">
            <el-input
                :disabled="true"
                v-model.name="ruleForm.oldpass"
                :value="ruleForm.oldpass"
                autocomplete="off"
                prefix-icon="el-icon-ice-cream-round"
            ></el-input>
          </el-form-item>

          <el-form-item label="新密码" prop="pass">
            <el-input
                type="password"
                v-model="ruleForm.pass"
                autocomplete="off"
                prefix-icon="el-icon-ice-cream-round"
            ></el-input>
          </el-form-item>

          <el-form-item label="确认密码" prop="checkPass">
            <el-input
                type="password"
                v-model="ruleForm.checkPass"
                autocomplete="off"
                prefix-icon="el-icon-ice-cream-round"
            ></el-input>
          </el-form-item>

          <el-form-item label="更改性别" prop="sex">
            <el-select v-model.name="ruleForm.sex" clearable placeholder="" style="width: 60px;float: left">
              <el-option
                  v-for="item in options"
                  :value="item.value"
                  :key="item.value"
                  :label="item.label"
                  >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="更改手机号" prop="phone">
            <el-input
                :value="ruleForm.phone"
                v-model.name="ruleForm.phone"
                placeholder="请输入手机号"
                prefix-icon="el-icon-phone"
            ></el-input>
          </el-form-item>

          <el-form-item label="更改邮箱" prop="email">
            <el-input
                :value="ruleForm.email"
                v-model.name="ruleForm.email"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-s-promotion"
            ></el-input>
          </el-form-item>

          <el-form-item label="更改出生年月" prop="birthday">
            <el-date-picker
                :value="ruleForm.birthday"
                v-model.name="ruleForm.birthday"
                type="date"
                placeholder="选择日期">
            </el-date-picker>
          </el-form-item>


          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>
<script>
import moment from "moment";

export default {
  data() {

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      options:[{
        value: '男',
        label: '男',
      },
        {
          value: '女',
          label: '女',
        }],
      showPassword:true,
      ruleForm: {
        pass: '',
        checkPass: '',
        name: sessionStorage.getItem('name'),
        oldpass:sessionStorage.getItem('password'),
        sex:sessionStorage.getItem('sex'),
        phone:sessionStorage.getItem('phone'),
        email:sessionStorage.getItem('email'),
        birthday:sessionStorage.getItem('birthday'),
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        name: [
          { require: true, message: '名字不能为空', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this
          sessionStorage.setItem('name', that.ruleForm.name)

          const type = sessionStorage.getItem('type')
          let form = null
          let ss = null
          if (type === 'student') {
            ss = 'Student'
            form = {
              sid: sessionStorage.getItem('sid'),
              sname: that.ruleForm.name,
              password: that.ruleForm.pass,
              ssex:that.ruleForm.sex,
              sphone:that.ruleForm.phone,
              semail:that.ruleForm.email,
              sbirthday:moment(that.ruleForm.birthday).format('YYYY-MM-DD'),

            }
          }
          else {
            ss = 'Teacher'
            form = {
              tid: sessionStorage.getItem('tid'),
              tname: that.ruleForm.name,
              password: that.ruleForm.pass,
              tsex:that.ruleForm.sex,
              tphone:that.ruleForm.phone,
              temail:that.ruleForm.email,
              tbirthday:moment(that.ruleForm.birthday).format('YYYY-MM-DD'),
            }
          }

          axios.post('http://localhost:10086/' + type + '/update' + ss, form).then(function(resp){

            console.log(resp.data)
            if (resp.data != null) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
              console.log(resp.data);

              that.ruleForm.oldpass = resp.data.password;
              sessionStorage.setItem('password', that.ruleForm.pass)

              if(type === 'student'){
                that.ruleForm.sex = resp.data.ssex;
                that.ruleForm.phone = resp.data.sphone;
                that.ruleForm.email = resp.data.semail;
                that.ruleForm.birthday = resp.data.sbirthday;
                sessionStorage.setItem('sex', that.ruleForm.sex)
                sessionStorage.setItem('phone', that.ruleForm.phone)
                sessionStorage.setItem('email', that.ruleForm.email)
                sessionStorage.setItem('birthday', moment(that.ruleForm.birthday).format('YYYY-MM-DD'))
              }else{
                that.ruleForm.sex = resp.data.tsex;
                that.ruleForm.phone = resp.data.tphone;
                that.ruleForm.email = resp.data.temail;
                that.ruleForm.birthday = resp.data.tbirthday;
                sessionStorage.setItem('sex', that.ruleForm.sex)
                sessionStorage.setItem('phone', that.ruleForm.phone)
                sessionStorage.setItem('email', that.ruleForm.email)
                sessionStorage.setItem('birthday',moment(that.ruleForm.birthday).format('YYYY-MM-DD'))
              }

              console.log(that.ruleForm)
            }
            else {
              that.$message.error('编辑失败，联系管理员');
            }
            that.$router.push("/" + type + 'Home')
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
