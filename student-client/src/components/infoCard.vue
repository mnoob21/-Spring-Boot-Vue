<template>
  <el-card>
    <el-descriptions title="用户信息" border>
      <el-descriptions-item label="用户 id">{{ id }}</el-descriptions-item>
      <el-descriptions-item label="用户名">{{ name }}</el-descriptions-item>
      <el-descriptions-item label="用户类型">
        <el-tag size="small">{{ type }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="性别">{{ sex }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ email }}</el-descriptions-item>
      <el-descriptions-item label="出生年月">{{ birthday }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ createTime }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script>
export default {
  name: "infoCard",

  data() {
    return {
      id:
        sessionStorage.getItem("type") === "teacher"
          ? sessionStorage.getItem("tid")
          : sessionStorage.getItem("sid"),
      name: sessionStorage.getItem("name"),
      type: sessionStorage.getItem("type"),
      sex: sessionStorage.getItem("sex"),
      phone: sessionStorage.getItem("phone"),
      email: sessionStorage.getItem("email"),
      birthday: sessionStorage.getItem("birthday"),
      createTime: sessionStorage.getItem("createTime"),
    };
  },
};
</script>

<style scoped></style>
