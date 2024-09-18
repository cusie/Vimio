<template>
  <div>
    <el-form inline>
      <el-select v-model="queryInfoByType.type" placeholder="网站分类" clearable @change="fetchWebSavesByType">
        <el-option label="基础" value="1"></el-option>
        <el-option label="科学上网" value="2"></el-option>
        <el-option label="常用" value="3"></el-option>
        <el-option label="新世界" value="4"></el-option>
      </el-select>
      <el-form-item style="margin-left: 1000px">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">添加网站链</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="websitesList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="图标" width="80">
        <template v-slot="scope">
          <el-avatar shape="square" :size="50" fit="contain" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="昵称" prop="webname"></el-table-column>
      <el-table-column label="描述" prop="description"></el-table-column>
      <el-table-column label="站点" prop="website"></el-table-column>
      <el-table-column label="网站类型" width="100">
        <template v-slot="scope">
          <span>{{ getTypeLabel(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览次数" prop="views" width="100"></el-table-column>
      <el-table-column label="创建时间" width="170">
        <template v-slot="scope">{{ scope.row.createTime | dateFormat }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @onConfirm="deleteWebsiteId(scope.row.id)">
            <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="queryInfo.pageNum" :page-sizes="[10, 20, 30, 50]"
                   :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>

    <el-dialog title="添加友链" width="40%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
        <el-form-item label="昵称" prop="webname">
          <el-input v-model="addForm.webname"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
        <el-form-item label="网站" prop="website">
          <el-input v-model="addForm.website"></el-input>
        </el-form-item>
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="addForm.avatar"></el-input>
        </el-form-item>
        <el-form-item label="网站类型" prop="websitetype">
          <el-radio-group v-model="addForm.type">
            <el-radio label="1">基础</el-radio>
            <el-radio label="2">科学上网</el-radio>
            <el-radio label="3">常用</el-radio>
            <el-radio label="4">新世界</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="addDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="saveWebsite">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑友链" width="40%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="昵称" prop="webname">
          <el-input v-model="editForm.webname"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="网站" prop="website">
          <el-input v-model="editForm.website"></el-input>
        </el-form-item>
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="editForm.avatar"></el-input>
        </el-form-item>
        <el-form-item label="网站类型" prop="websitetype">
          <el-radio-group v-model="editForm.type">
            <el-radio label="1">基础</el-radio>
            <el-radio label="2">科学上网</el-radio>
            <el-radio label="3">常用</el-radio>
            <el-radio label="4">新世界</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="editDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="editWebsite">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb";
import { deleteWebsiteId, getWebDatas, saveWebsite, updateWebsite } from "@/api/websites";

export default {
  name: "WebsitesList",
  components: { Breadcrumb },
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10
      },
      queryInfoByType: {
        type: '' // 用于存储选中的网站分类
      },
      websitesList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        webname: '',
        description: '',
        website: '',
        avatar: '',
        type: ''
      },
      editForm: {
        webname: '',
        description: '',
        website: '',
        avatar: '',
        type: ''
      },
      formRules: {
        webname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述', trigger: 'blur' }],
        website: [{ required: true, message: '请输入网站', trigger: 'blur' }],
        avatar: [{ required: true, message: '请输入头像URL', trigger: 'blur' }],
      }
    }
  },
  created() {
    this.getWebSaveList();
  },
  methods: {
    getWebSaveList() {
      getWebDatas(this.queryInfo).then(res => {
        this.websitesList = res.data.list;
        this.total = res.data.total; // 确保更新总数
      });
    },
    fetchWebSavesByType() {
      const type = this.queryInfoByType.type;
      // 根据选择的类型获取数据
      getWebDatas({ ...this.queryInfo, type }).then(res => {
        this.websitesList = res.data.list;
        this.total = res.data.total; // 更新总数
      });
    },
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getWebSaveList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getWebSaveList();
    },
    deleteWebsiteId(id) {
      deleteWebsiteId(id).then(res => {
        this.getWebSaveList();
        this.msgSuccess(res.msg);
      });
    },
    showEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    editDialogClosed() {
      this.editForm = {};
      this.$refs.editFormRef.resetFields();
    },
    saveWebsite() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          saveWebsite(this.addForm).then(res => {
            this.getWebSaveList();
            this.msgSuccess(res.msg);
            this.addDialogVisible = false;
          });
        }
      });
    },
    editWebsite() {
      this.$refs.editFormRef.validate(valid => {
        if (valid) {
          updateWebsite(this.editForm).then(res => {
            this.getWebSaveList();
            this.msgSuccess(res.msg);
            this.editDialogVisible = false;
          });
        }
      });
    },
    getTypeLabel(type) {
      switch (type) {
        case 1: return '基础';
        case 2: return '科学上网';
        case 3: return '常用';
        case 4: return '新世界';
        default: return '';
      }
    }
  }
}
</script>

<style scoped>
.el-button + span {
  margin-left: 10px;
}

.el-form {
  margin-top: 15px !important;
}

.el-form--inline .el-form-item {
  margin-bottom: 0;
}
.span:focus {
  outline: none;
}
</style>
