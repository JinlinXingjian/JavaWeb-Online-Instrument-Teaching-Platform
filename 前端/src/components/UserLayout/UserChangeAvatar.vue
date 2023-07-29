<template>
  <el-card class="box-card">
    <el-row>
      <el-col :span="6">
        <el-upload 
        class="avatar-uploader" 
        :show-file-list="false" 
        :action="uploadUrl" 
        :on-success="handleAvatarSuccess" 
        :before-upload="beforeAvatarUpload" 
        :headers="{ Authorization: token }">
          <img v-if="imageUrl" :src="imageUrl"  />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>

      </el-col>
      <el-col :span="18">
        <el-text class="mx-1">请上传小于3M的文件</el-text>
      </el-col>
    </el-row>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import type { UploadProps } from 'element-plus';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const username = userStore.username;
const imageUrl = ref('');
// 定义上传url和token
const uploadUrl = `http://localhost/upload/avatar/${username}.jpg`;
const token=localStorage.getItem('token');


const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
  const formData = new FormData();
  formData.append('avatar', uploadFile.raw!, `${username}.jpg`);
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
  userStore.setAvatar(URL.createObjectURL(uploadFile.raw!))

};


const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('头像图片必须是JPG格式！');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 3) {
    ElMessage.error('头像图片大小不能超过3MB！');
    return false;
  }
  return true;
};

</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.el-card {
  margin: 0;
  min-height: 250px;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
