<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
	<head>
	    <title>WebSocket聊天室</title>
	    <link rel="stylesheet" href="css/bootstrap.min.css">
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	    <script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<style type="text/css">
		    ::-webkit-scrollbar {
		        background-color: transparent;
		        width: 7px;
		        height: 7px;
		    }

		    ::-webkit-scrollbar-track {
		          background: #c8c9ca;
		          border-radius: 4px;
		    }

			::-webkit-scrollbar-thumb {
		          background:#38c4a9;
		          min-height:6px;
		          width:5px;
		          border-radius:4px;
		    }

		    .chat-content {
		        padding-left:10px;
		        max-height:374px;
		        background-color: #fff;
		        width:100%;  
		        overflow-y:auto;
		        overflow-x:hidden;
		        border: 1px solid #c8e1ff;
		        height:374px;
		    }

		    .chat-content .date {
		    	color: rgb(106, 115, 125);
		    }

		    .chat-content .username {
		    	color: rgb(3, 102, 214);
		    }

		    .chat-content .content {
		    	color: rgb(36, 41, 46);
		    	font-size: 18px;
		    }
		</style>
		<script type="text/javascript">
			var uname = prompt('请输入用户名', 'user' + uuid(8, 16));
		  	var ue = UE.getEditor('editor');
		    var websocket = null;
		    var systemType = 'system_message'
		    var commonType = 'common_message'
		    var reg = new RegExp("<p><br/></p>", "g");
			
			$(function() {
			    if ('WebSocket' in window && uname !== null) {
			        websocket = new WebSocket("ws://localhost:8080/WebsocketChat/websocketServer");
				    
				    websocket.onopen = function(event) {
				        showTipMessage("连接服务器成功");
				        //与服务器建立链接后发送登录名到服务器进行标识
				        sendMsg({
				        	messageType : systemType,
				        	message : uname
				        });
				    };

				    websocket.onclose = function() {
				        showTipMessage("与服务器断开连接");
				    };

				    websocket.onmessage = function(event) {
				    	var data = $.parseJSON(event.data);
				    	
				    	if (data.type == systemType) {
				    		$("#onlineCount").text(data.count);
				    	} else {
				    		var dateHTML = "<span class='date'>" + data.date + "</span>"
				    		var usernameHTML = "<span class='username'>" + data.username + "</span>"
				    		var contentHTML = "<span class='content'>" + data.message.replace(reg, "") + "</span>"
				    		var content = "<p>" + dateHTML + " " + usernameHTML + "</p>" + contentHTML
				    		$("#message").append(content);

				    		//滚动条自动向下移动
				    		$("#message")[0].scrollTop = $("#message")[0].scrollHeight;
				    	}
				    };

				    websocket.onerror = function() {
				        showTipMessage("发生未知错误");
				    };
			    } else {
			        showTipMessage("未输入用户名或浏览器不支持websocket");
			    }
			})

		    window.onbeforeunload = function () {
		        closeWebSocket();
		    };

		    function showTipMessage(tipMessage) {
		        $("#tipContainer p").text(tipMessage);
		        $("#tipContainer").show(500, function() {
		        	setTimeout(function() {
		        		$("#tipContainer").hide();
		        	}, 1000)
		        });
		    };

		    function closeWebSocket() {
		        websocket.close();
		    }

		    function sendMsg(msg) {
		        var data = JSON.stringify(msg);
		        websocket.send(data);
		    }

		    function sendTextMessage() {
		        var message = ue.getContent();
		        if (message != "") {
		        	sendMsg({
		        		messageType : commonType,
		        		message : message
		        	});
		        	ue.setContent('');
		        }
		    }

		    document.onkeydown = function(event) {
		    	if (event.keyCode === 13) {
		    		sendTextMessage();
		    	}
		    }

		    function uuid(len, radix) {
		        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
		        var uuid = [], i;
		        radix = radix || chars.length;
		        if (len) {
		            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
		        } else {
		            var r;
		            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		            uuid[14] = '4';
		            for (i = 0; i < 36; i++) {
		                if (!uuid[i]) {
		                    r = 0 | Math.random() * 16;
		                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
		                }
		            }
		        }
		        return uuid.join('');
		    }
		</script>
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
				                	<label>聊天室</label>
				                </div>
				                <div class="col-md-6" style="text-align:right;">
			               	 		<button class="btn btn-info btn-xs" onclick="closeWebSocket()">退出</button>
			               	 	</div>
			                </div>
		                </div>
		                <div class="panel-footer">
		                    在线人数: <span id="onlineCount">1</span>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<!--chat content-->
		<div class="container-fluid" style="margin-bottom: 15px;">
		    <div class="row">
		        <div class="col-md-12">
					<div id="message" class="chat-content"></div>
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