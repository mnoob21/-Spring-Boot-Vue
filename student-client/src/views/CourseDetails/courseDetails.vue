<template>
  <div class="course-details-wrapper">
    <!-- 美化的返回按钮 -->
    <el-button
        type="text"
        class="back-button"
        icon="el-icon-arrow-left"
        @click="goBack"
    >
      返回
    </el-button>

    <el-card class="course-card-custom">
      <div slot="header" class="course-header">
        <span class="course-header-text">课程详情</span>
      </div>
      <el-row>
        <el-col :span="8">
          <img :src="course.coverImage" alt="封面图片" class="cover-image-styled" @error="handleImageError">
        </el-col>
        <el-col :span="16">
          <div class="course-info-container">
            <h2 class="course-title-styled">{{ course.name }}</h2>
            <p>课程类别：<span class="teacher-name-styled">{{ course.courseType }}</span></p>
            <p>教师：<span class="teacher-name-styled">{{ course.teacherName }}</span></p>
            <p>时间：<span class="course-time-styled">{{ course.time }}</span></p>
          </div>
        </el-col>
      </el-row>
      <div class="course-description-wrapper">
        <h3 class="course-intro-header-styled">课程介绍</h3>
        <p class="course-description-text-styled">{{ course.description }}</p>
      </div>
    </el-card>

    <el-card class="comment-card-styled">
      <div slot="header" class="comment-header">
        <span class="comment-header-text-styled">评论区</span>
      </div>
      <!-- 评论列表需要一个滚动的容器 -->
      <div class="comments-container">
        <div v-for="comment in comments" :key="comment.id" class="comment-item-styled">
          <p class="comment-content-styled">{{ comment.content }}</p>
          <p class="comment-meta-styled">—— {{ comment.author }} <span class="comment-time-styled">({{ comment.time }})</span></p>
          <!-- 只有当用户是admin时显示删除按钮 -->
          <el-button
              v-if="userType === 'admin'"
              type="danger"
              icon="el-icon-delete"
              @click="deleteComment(comment.id)"
              size="small"
          >删除</el-button>
        </div>
      </div>
      <!-- 评论输入框 -->
      <div class="comment-input-wrapper">
        <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入评论内容"
            v-model="newComment.content"
            class="comment-input-styled"
            :maxlength="200"
            show-word-limit>
        </el-input>
        <el-button :loading="submitting" :disabled="!newComment.content" type="primary" @click="submitComment" class="submit-button-styled">发表评论</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userType:sessionStorage.getItem("type"),
      course: {
        courseType: sessionStorage.getItem("courseType"),
        coverImage: 'https://via.placeholder.com/600',
        name: sessionStorage.getItem("courseName"),
        teacherName: sessionStorage.getItem("courseAuthor"),
        time: sessionStorage.getItem("courseTime"),
        description: sessionStorage.getItem("courseDescription"),
        cid:sessionStorage.getItem("cid"),
      },
      comments: [],
      newComment: {
        commentId:'',
        content: '',
        author: sessionStorage.getItem("name"),
        time: `${new Date().getFullYear()}-${(new Date().getMonth() + 1).toString().padStart(2, '0')}-${new Date().getDate().toString().padStart(2, '0')}`,

      },
      submitting: false
    };
  },
  methods: {

    goBack() {
      this.$router.go(-1);
    },
    handleImageError(event) {
      event.target.src = 'https://via.placeholder.com/600';
    },
    submitComment() {
      this.submitting = true;
      let form = {
        "ccomment_content":this.newComment.content,
        "ccomment_author":this.newComment.author,
        "ccomment_time":this.newComment.time,
        "c_id":this.course.cid,
        "ccomment_id":'',
      }
      setTimeout(() => {
        axios.post('http://localhost:10086/courseComments/insertComment',form).then(resp =>{
          console.log(resp)
          if(resp.data.success){
            this.$message({
              showClose: true,
              message: "评论成功!",
              type: 'success'
            });
            this.findCommentsById();
            this.submitting = false;
          }

        })

      }, 1000);
    },

    findCommentsById(){
      axios.get('http://localhost:10086/courseComments/findByCourseId/' + this.course.cid).then(resp=>{
        const commentsData = resp.data;
        console.log(commentsData)
        this.comments = commentsData.map(comment => ({
          id: comment.ccomment_id,
          content: comment.ccomment_content,
          author: comment.ccomment_author,
          time: comment.ccomment_time
        }));
      })
    },


    deleteComment(commentId) {
      console.log(commentId)
      if (this.userType === 'admin') {
        this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          axios.get('http://localhost:10086/courseComments/deleteComment/'+ commentId)
              .then(response => {
                if (response.data.success) {
                  this.$message({
                    type: 'success',
                    message: '评论删除成功!'
                  });
                  // 从数组中移除该评论
                  this.comments = this.comments.filter(comment => comment.id !== commentId);
                } else {
                  this.$message.error('删除失败');
                }
              })
              .catch(error => {
                console.error('删除评论失败:', error);
                this.$message.error('删除失败');
              });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },


  },



  created() {
    console.log(this.userType);
    this.findCommentsById();
  }
};
</script>

<style scoped>
.back-button {
  font-size: 16px;
  color: #409eff;
  border: none;
  background-color: transparent;
  cursor: pointer;
  outline: none;
  padding: 10px;
  margin-bottom: 10px;
  margin-left: 10px;
  transition: color 0.3s;
}

.back-button:hover {
  color: #66b1ff;
}

.back-button .el-icon-arrow-left {
  margin-right: 5px;
}

.course-details-wrapper {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #fafafa;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.course-card-custom {
  margin-bottom: 25px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.course-header {
  padding: 12px 24px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #e8eaec;
}

.cover-image-styled {
  width: 100%;
  height: auto;
  border-radius: 10px;
}

.course-info-container {
  margin-top: 25px;
}

.course-title-styled {
  font-size: 26px;
  font-weight: bold;
  color: #333;
}

.teacher-name-styled, .course-time-styled {
  font-weight: bold;
  color: #555;
}

.course-description-wrapper {
  padding: 25px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.course-intro-header-styled {
  font-size: 20px;
  color: #444;
}

.course-description-text-styled {
  font-size: 15px;
  color: #666;
}

.comment-card-styled {
  margin-top: 25px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.comment-header {
  padding: 12px 24px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #e8eaec;
}

.comment-item-styled {
  padding: 20px 25px;
  border-bottom: 1px solid #ebeef5;
}

.comment-item-styled:last-child {
  border-bottom: none;
}

.comment-content-styled {
  font-size: 15px;
  color: #333;
}

.comment-meta-styled {
  color: #999;
  font-size: 13px;
}

.comment-time-styled {
  margin-left: 10px;
}

.comment-input-styled {
  margin-top: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  padding: 14px;
  width: 100%;
  box-sizing: border-box;

}

.submit-button-styled {
  margin-top: 15px;
  width: 100%;
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
  border-radius: 8px;
  font-size: 18px;
  padding: 14px;
}

.submit-button-styled:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.comments-container {
  max-height: 400px; /* 或者你希望的最大高度 */
  overflow-y: auto; /* 允许垂直滚动 */
  margin-bottom: 20px; /* 根据需要调整 */
}

.comment-input-wrapper {
  position: sticky;
  bottom: 0;
  background-color: #fff; /* 确保背景颜色与页面背景一致 */
  padding: 10px;
  border-top: 1px solid #ebeef5; /* 分隔线 */
  z-index: 100;
}
</style>
