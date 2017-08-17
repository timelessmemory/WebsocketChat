<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
	<head>
	    <title>newChat</title>
	    <link rel="stylesheet" href="css/bootstrap.min.css">
	    <link rel="stylesheet" href="css/common.css">
	    <link rel="stylesheet" href="css/newChat.css">
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	    <script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/newChat.js"></script>
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
				                	<label id="chatRoom">畅聊</label>
				                </div>
				                <div class="col-md-6" style="text-align:right;">
			               	 		<button class="btn btn-info btn-xs" onclick="closeWebSocket()">结束聊天</button>
			               	 	</div>
			                </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<!--chat content-->
		<div class="container-fluid" style="margin-bottom: 15px;">
		    <div class="row">
		        <div class="col-md-8">
					<div id="message" class="chat-content">
						
					</div>
				</div>
				<div class="col-md-4">
					<div class="chat-list">
	                </div>
				</div>
			</div>
		</div>

		<!--Ueditor-->
		<div class="container-fluid">
		    <div class="row">
		        <div class="col-md-12">
		            <script id="editor" type="text/plain" style="width:100%;height:200px;"></script>
		        </div>
		    </div>
		</div>

		<!--send message button-->
		<div class="container-fluid" style="margin-top: 8px;">
		    <div class="row">
		        <div class="col-md-12">
		            <p class="text-right">
		            	<button onclick="sendTextMessage()" class="btn btn-info">发送</button>
		            </p>
		        </div>
		    </div>
		</div>
	</body>
</html>