<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
	<head>
	    <title>畅聊</title>
	    <link rel="stylesheet" href="css/bootstrap.min.css">
	    <link rel="stylesheet" href="css/index.css">
	    <script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/index.js"></script>
	</head>
	<body>
	  	<!--alert-->
		<div id="tipContainer" style="display:none;position: fixed;top: 0px;width: 100%;" class="alert alert-info alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		  <p style="text-align:center;" id="tip"></p>
		</div>

		<div class="container-fluid" style="margin-top: 5%;">
		    <div class="row">
		        <div class="col-md-12">
		            <div class="panel panel-primary">
		                <div class="panel-heading">
		                	<div class="row">
		                		<div class="col-md-6">
				                	<label id="username">主页</label>
				                </div>
				                <div class="col-md-6" style="text-align:right;">
			               	 		<button class="btn btn-info btn-xs" onclick="closeWebSocket()">退出</button>
			               	 	</div>
			                </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="container-fluid" style="margin-bottom: 15px;">
		    <div class="row">
				<div class="col-md-4">
					<div class="chat-list">
	                </div>
				</div>
		        <div class="col-md-8">
					<div id="notice" class="notice-content">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>