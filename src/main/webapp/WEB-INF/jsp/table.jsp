<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单</title>
<link rel="stylesheet" href="static/element/css/element-index.css">
<script src="static/jquery-2.1.3.js"></script>
<script type="text/javascript" src="static/vue/axios.min.js" ></script>
<script type="text/javascript" src="static/vue/vue.js" ></script>
<script type="text/javascript" src="static/element/js/element-index.js" ></script>
<script src="static/excelexport/tableExport.js"></script>
<script src="static/excelexport/base64.js"></script>
<script src="static/sweetalert.min.js"></script>
<style lang="scss" scoped>
.index {
  width: 100%;
  height: 100%;
}
</style>

</head>
<body style="background-color: #545c64">
	<div id="root">
	<el-container style="border: 1px solid #eee;height:100%">
  <el-aside  width="250px" style="background-color: #545c64">
   <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleSelect">
      <el-menu-item index="1">
          <i class="el-icon-location"></i>
          <span slot="title">导航一</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="4" >
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
	</div>

</body>

<script>

	window.onload = function(){
			var app = new Vue({
				el:"#root",
				
	
			methods:{
				handleSelect(key, keyPath) {
			        console.log(key, keyPath);
			      }
					
				}
			});
		}
	</script>
</html>
