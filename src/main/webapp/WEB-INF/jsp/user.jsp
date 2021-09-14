<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户信息</title>
<!-- <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css"> -->
<link rel="stylesheet" href="static/element/css/element-index.css">
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<!--<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>-->
<script src="static/jquery-2.1.3.js"></script>
<script type="text/javascript" src="static/vue/axios.min.js" ></script>
<script type="text/javascript" src="static/vue/vue.js" ></script>
<!-- 引入组件库 -->
<!--<script src="https://unpkg.com/element-ui/lib/index.js"></script>-->
<script type="text/javascript" src="static/element/js/element-index.js" ></script>
<script src="static/excelexport/tableExport.js"></script>
<script src="static/excelexport/base64.js"></script>
<script src="static/sweetalert.min.js"></script>
<style>
		.el-table .warning-row {
	      background: oldlace;
	  }
	
	   .el-table .success-row {
	      background: #f0f9eb;
	  }
	</style>
</head>
<body>
	<div id="root">
	<el-container>
	  <el-header>
	  		<!-- <el-input placeholder="请输入中文名" v-model="inputname" style="width: 200px;" clearable></el-input> -->
	  		<el-input placeholder="请输英文名" v-model="inputusername" style="width: 200px;" clearable></el-input>
	  		<!-- <el-input placeholder="请输入中文名" v-model="input" style="width: 200px;" clearable></el-input> -->
	  		<el-button type= "success" size= "medium" icon="el-icon-search " @click="getUserbyPage(0)">查询</el-button>
	  		<el-button type="primary" size="medium" icon="el-icon-edit" @click="insertUser(user)">新增</el-button>
	  		<el-button type="primary" size="medium" icon="el-icon-download" @click="exportExcel()">导出</el-button>
	  </el-header>
	  <el-main> 
	  		<template> 
				<!-- <el-table :data="userdata.filter(data => !inputusername || data.username.toLowerCase().includes(inputusername.toLowerCase()))" border style="width:100%"> --> 
				<el-table class="userTab" :data="userdata" border style="width:100%">
					<el-table-column fixed prop="username"
					label="英文名" align="center"></el-table-column> <el-table-column
					prop="name" label="中文名" align="center"></el-table-column> 
					<el-table-column prop="createTime" label="创建时间" align="center"></el-table-column> 
					<el-table-column fixed="right" label="操作" align="center"> 
					<template slot-scope="scope">
						<!--<el-button @click="showProject(scope.row)" type="success" size="mini">课程</el-button>-->
						<el-button @click="showMsg(scope.row)" type="info" size="mini">查看</el-button> 
						<el-button @click="modifyUser(scope.row)" type="primary" size="mini">编辑</el-button> 
						<el-button @click="deleteUser(scope.row)" type="danger" size="mini">删除</el-button>
					</template> 
					</el-table-column> 
				</el-table> 
			</template>
	  </el-main>
	  <el-footer align="left">
	  <!--分页显示-->
			<el-pagination
			  background
			  layout="prev, pager, next"
			  @current-change="current_change"
			  :total= "total" 
			  :page-size = "6">
			</el-pagination>
	  </el-footer>
	  <el-button @click="test">测试</el-button>
	</el-container>
		<!--按钮点击查询下一页与上一页
		<el-row type="flex" justify="end">
			<el-col :span="4">
				<el-button-group >
					<el-button type="primary" icon="el-icon-left" @click="getUserbyPage(-1)" :disabled="disabled">上一页</el-button>
					<el-button type="primary" @click="getUserbyPage(1)" :disabled="disabledAfter">下一页</el-button>
				</el-button-group>
			</el-col>
		</el-row>-->
		<el-dialog title='查看个人信息' :visible.sync='dialogFormVisible'>
			<el-form :model='user'> 
				<el-form-item label='英文名' > 
					<el-input v-model='user.username' autocomplate='off' :disabled='true'></el-input> 
				</el-form-item> 
				<el-form-item label='中文名' > 
					<el-input v-model='user.name' autocomplate='off' :disabled='true'></el-input> 
				</el-form-item>
				<el-form-item label='创建时间' > 
					<el-input v-model='user.createTime' type='date' autocomplate='off' :disabled='true'></el-input>
				</el-form-item>
				<template>
					<el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
						<el-table-column prop="teacher" label="授课老师"></el-table-column>
						<el-table-column prop="projectName" label="课程名称"></el-table-column>
					</el-table>
				</template>
				<div class="demo-image__placeholder">
					  <div class="block">
					    <span class="demonstration">图片</span>
					    <el-image :src="src">
					      <div slot="placeholder" class="image-slot">
					       		 加载中<span class="dot">...</span>
					      </div>
					    </el-image>
					  </div>
				</div>
			</el-form> 
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取消</el-button> 
			</div>
		</el-dialog>
		
		<el-dialog title='新增个人信息' :visible.sync='dialogFormVisible1'>
			<el-form :model='user'> 
				<el-form-item label='英文名' > 
					<el-input v-model='user.username' autocomplate='off'></el-input> 
				</el-form-item> 
				<el-form-item label='中文名' > 
					<el-input v-model='user.name' autocomplate='off'></el-input> 
				</el-form-item>
				<el-form-item label='创建时间' > 
					<el-input v-model='user.createTime' type='date' autocomplate='off'></el-input>
				</el-form-item>
				<el-button type= "success" size= "medium" icon="el-icon-search" @click="insertProject()">新增</el-button>
				<template>
					<el-table :data="user.projects" style="width: 100%" :row-class-name="tableRowClassName">
						<el-table-column prop="teacher" label="授课老师"></el-table-column>
						<el-table-column prop="projectName" label="课程名称"></el-table-column>
					</el-table>
				</template>
			</el-form> 
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible1 = false">取消</el-button> 
				<el-button @click="onSubmit()">保存</el-button> 
			</div>
		</el-dialog>
		
		<el-dialog title='修改个人信息' :visible.sync='dialogModifyUser'>
			<el-form :model='user'> 
				<el-form-item label='英文名' > 
					<el-input v-model='user.username' autocomplate='off'></el-input> 
				</el-form-item> 
				<el-form-item label='中文名' > 
					<el-input v-model='user.name' autocomplate='off'></el-input> 
				</el-form-item>
				<el-form-item label='创建时间' > 
					<el-input v-model='user.createTime' type='date' autocomplate='off'></el-input>
				</el-form-item>
			</el-form> 
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogModifyUser = false">取消</el-button> 
				<el-button @click="saveModifyUser()">保存</el-button> 
			</div>
		</el-dialog>
		
		<el-dialog title="新增课程" :visible.sync='dialogInsertProject'>
			<el-form :model='projects'>
				<el-form-item label='课程名称'>
					<el-input v-model='projects.projectName' autocomplate='off'></el-input>
				</el-form-item>
				<el-form-item label='授课老师'>
					<el-input v-model='projects.teacher' autocomplate='off'></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogInsertProject = false">取消</el-button>
				<el-button @click="saveProject">保存</el-button>
			</div>
		</el-dialog>
	</div>

</body>

<script>

	window.onload = function(){
			var app = new Vue({
				el:"#root",
				data : {
					userdata : [],
					user:{
						username : "",
						password:"",
					},
					total:10,
					currentPage : 0,
					disabled : true,
					disabledAfter: false,
					username: null,
					dialogFormVisible : false,
					dialogFormVisible1: false,
					dialogModifyUser: false,
					dialogInsertProject: false,
					inputname:'',
					inputusername:null,
					src: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
					tableData: [],
					projects: {},
					projectData:[]
				},
				mounted : function(){
						this.getUserbyPage(0,'');
			 	},
			methods:{
				getAll: function() {
					axios
				       .get('get.do')
				       .then(e => (this.total = e.data.length ))
				       .catch(function (error) { // 请求失败处理
				          console.log(error);
				       });
				},
				showMsg : function(row) {
				 	this.user = row;
					this.dialogFormVisible  = true;
					axios
						.get('getUserProject.do?guid=' + row.guid)
						.then(e => (this.tableData = e.data.result))
						.catch(function (error) {
							this.$message({
				                 type: 'error',
				                 message: '请求失败！'
				               })
						});
						console.log(this.tableData)
				},
				insertUser: function(row) {
					this.user = {};
					this.dialogFormVisible1 = true;
				},
				modifyUser: function(row) {
					this.user = row;
					this.dialogModifyUser = true;
				},
				insertProject: function(row) {
					this.projects = {};
					this.dialogInsertProject = true;
				},
				saveModifyUser: function(row) {
					var data = {
							 guid: this.user.guid,
			 			username : this.user.username,
			 			name : this.user.name,
				 	};
					axios.post('update.do', data)
					.then(e => (
						this.dialogModifyUser = false,
						this.getAll()
					))
					.catch(function (error) {
						console.log(error);
					})
				},
				deleteUser: function(row) {
					this.$confirm('此操作将删除该用户, 是否继续?', '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {
				          axios
				          	.get('delete.do?guid='+row.guid)
				          	.then(e=>(
				        		  this.$message({
						                 type: 'info',
						                 message: '删除成功！'
						               }),
						          this.getAll(),
				        	      this.getUserbyPage('0')
					          )).catch(function(error){
					    			 this.$message({
						                 type: 'error',
						                 message: '删除失败！'
						               })
					    		});
				        }).catch(() => {
				        	this.$message({
				                 type: 'info',
				                 message: action === 'cancel'
				                   ? '放弃删除'
				                   : '停留在当前页面'
				               }) 
				        });
				},
				current_change: function(currentPage) {
					console.log(currentPage)
			        this.currentPage = currentPage-1;
			        this.getUserbyPage( this.currentPage)
			    },
			    exportExcel : function(){
      		    	$(".userTab .el-table__body-wrapper .el-table__body").tableExport({type:"excel",escape:"false",fileName:"用户信息表"});
      		    },
				getUserbyPage : function(offset) {
			    	this.currentPage = offset;
			    	if(this.currentPage > 0){
			    		this.disabled = false;
			    	}else{
			    		this.disabled = true;
			    	}
			    	var requestUrl = ""; 
			    	if(this.inputusername != null){
			    		requestUrl = 'getUserByPage.do?currentPage='+this.currentPage+'&username='+this.inputusername;
			    	}else{
			    		requestUrl = 'getUserByPage.do?currentPage='+this.currentPage;
			    	}
			    	//查询后台
			    	axios
				       .get(requestUrl)
				       .then(e => (
			    		   this.userdata = e.data,
			    		   e.data.length < 5?(this.disabledAfter = true):(this.disabledAfter = false)
			    		   )
			    		 )
				       .catch(() => { // 请求失败处理
				    	   this.$message({
				                 type: 'error',
				                 message: '删除失败!'
				               })
				       });
			    },
			    /* handlePictureCardPreview : function() {
			    	alert("11111");
			    },
			    handleDownload:function(){
			    	alert("22222");
			    },
			    handleRemove:function(){
			     	alert("33333");
			    }, */
			    tableRowClassName: function({row, rowIndex}) {
			        if (rowIndex === 1) {
			          return 'warning-row';
			        } else if (rowIndex === 3) {
			          return 'success-row';
			        }
			        return '';
			    },
				onSubmit : function(){
				 	var data = {
				 			username : this.user.username,
				 			name : this.user.name,
				 			createTime : this.user.createTime,
				 			projects : this.user.projects
				 	};
			       axios
				       .post('add.do',data)
				       .then(e => (
					        this.$confirm('此操作将保存该文件, 是否继续?', '提示', {
					          confirmButtonText: '确定',
					          cancelButtonText: '取消',
					          type: 'warning'
					        }).then(() => {
					          this.$message({
						          type: 'success',
						          message: '保存成功!'
					          }),
					          this.dialogFormVisible1 = false,
					          this.dialogModifyUser = false,
							  this.getAll()
					        }).catch(() => {
					          this.$message({
					            type: 'info',
					            message: '已取消保存'
					          })        
					        })
				       	  )
				       )
				       .catch(function (error) {
				          console.log(error);
				       });
			    	},
			    	saveProject : function(){
			    		this.projectData.push(this.projects);
						Vue.set(this.user,'projects',this.projectData);
						this.dialogInsertProject = false;
					},
					test : function(){
						swal({
							  title: "Are you sure?",
							  text: "Once deleted, you will not be able to recover this imaginary file!",
							  icon: "warning",
							  buttons: true,
							  dangerMode: true,
							})
							.then((willDelete) => {
							  if (willDelete) {
							    swal("Poof! Your imaginary file has been deleted!", {
							      icon: "success",
							    });
							  } 
							});
					}
				}
			});
		}
	</script>
</html>
