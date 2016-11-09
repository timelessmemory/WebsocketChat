<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
	<head>
	    <title>WebSocket Chat Room</title>
	    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
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
		    } /* 滑块颜色 */

		    #message {
		        padding-left:10px;
		        max-height:374px;
		        width:100%;  
		        overflow-y:auto;
		        overflow-x:hidden;
		        border:1px solid #C3C4C5;
		        height:374px;
		    }
		</style>
		<script type="text/javascript">
		  	var ue = UE.getEditor('editor');
		    var websocket = null;

		    if ('WebSocket' in window) {
		        websocket = new WebSocket("ws://localhost:8080/Soc/websocket");
		    } else {
		        alert("该浏览器不支持webSocket");
		    }

		    websocket.onopen = function(event) {
		        showTipMessage("connect into server");
		    };

		    websocket.onmessage = function(event) {
		    	var data = $.parseJSON(event.data);

		        if (data.message != "") {
		       		$("#message").append(data.message);
		        }
		        $("#onlineCount").text(data.count);
		    };

		    websocket.onclose = function() {
		        showTipMessage("close connect");
		    };

		    websocket.onerror = function() {
		        showTipMessage("unknown error occurred");
		    };

		    window.onbeforeunload = function () {
		        closeWebSocket();
		    };

		    function showTipMessage(tipMessage) {
		        $("#tip").text(tipMessage);
		        $("#tipContainer").show(500, function() {
		        	setTimeout(function() {
		        		$("#tipContainer").hide();
		        	}, 1000)
		        });
		    };

		    function closeWebSocket() {
		        websocket.close();
		    }

		    function sendMessage() {
		        var message = ue.getContent();
		        if (message != "") {
		        	websocket.send(message);
		        	ue.setContent('');
		        }
		    }
		</script>
	</head>
	<body>
	  	<!--alert-->
		<div id="tipContainer" style="display:none;margin: 0 15px;" class="alert alert-info alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		  <p style="text-align:center;" id="tip"></p>
		</div>

		<p style="text-align:right;margin-right:17px;margin-top: 3px;">
			<button class="btn btn-info btn-xs" onclick="closeWebSocket()">Exit</button>
		</p>

		<div class="container-fluid">
		    <div class="row">
		        <div class="col-md-12">
		            <div class="panel panel-primary">
		                <div class="panel-heading">Chat Room</div>
		                <div class="panel-footer">
		                    Online User Count: <span id="onlineCount">1</span>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<!--chat content-->
		<div class="container-fluid">
		    <div class="row">
		        <div class="col-md-12">
					<div id="message"></div>
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
		            	<button onclick="sendMessage()" class="btn btn-info">Send</button>
		            </p>
		        </div>
		    </div>
		</div>
	</body>
</html>