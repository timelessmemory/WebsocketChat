var ue = UE.getEditor('editor');
var websocket = null;
var systemType = 'system_message'
var commonType = 'common_message'
var historyType = 'history_message'
var reg = new RegExp("<p><br/></p>", "g");
var from = getQueryString("from");
var to = getQueryString("to");

$(function() {
	$("title")[0].innerText = "与" + to + "交谈中"

    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/WebsocketChat/chatServer");
	    
	    websocket.onopen = function(event) {
	        console.log("连接服务器成功");
	        sendMsg({
	        	messageType : systemType,
	        	from : from,
	        	to : to
	        });
	    };

	    websocket.onclose = function() {
	        console.log("与服务器断开连接");
	    };

	    websocket.onmessage = function(event) {
	    	var data = $.parseJSON(event.data);
	    	var tmpHTML = ""

	    	if (data.type == commonType) {
	    		$("#message").append(jointHTML(data));

	    		//滚动条自动向下移动
	    		$("#message")[0].scrollTop = $("#message")[0].scrollHeight;
	    	} else if (data.type == historyType) {
	    		if (data.content.length != 0) {
		    		$('#message').empty();
	    			data.content.reverse().forEach(function(record) {
	    				tmpHTML += jointHTML(record)
	    			})
	    			$("#message").append(tmpHTML);
		    		$('#message').append("<p></p>");
		    		$('#message').append("<p class='history-tip'><------以上为历史消息------></p>");
		    		$('#message').append("<p></p>");
	    		}
	    	} else {
	    		showTipMessage(data.username + "不在线");
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

function jointHTML(record) {
	var dateHTML = "<span class='date'>" + record.date + "</span>"
	var usernameHTML = "<span class='username'>" + record.username + "</span>"
	var contentHTML = "<span class='content'>" + record.message.replace(reg, "") + "</span>"
	return "<p>" + dateHTML + " " + usernameHTML + "</p>" + contentHTML
}

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
    		message : message,
    		from : from,
    		to : to
    	});
    	ue.setContent('');
    }
}

document.onkeydown = function(event) {
	if (event.keyCode === 13) {
		sendTextMessage();
	}
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    return r != null ? unescape(r[2]) : null;
}