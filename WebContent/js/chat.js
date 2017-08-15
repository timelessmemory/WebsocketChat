var uname = prompt('请输入用户名', 'user' + uuid(8, 16));
var chatRoomId = prompt('请输入聊天室ID', 'chatRoomId' + uuid(8, 16));
var ue = UE.getEditor('editor');
var websocket = null;
var systemType = 'system_message'
var commonType = 'common_message'
var historyType = 'history_message'
var reg = new RegExp("<p><br/></p>", "g");

$(function() {
    if ('WebSocket' in window && uname !== null && chatRoomId != null) {
    	$('#chatRoom').text(chatRoomId + '聊天室');
    	
        websocket = new WebSocket("ws://localhost:8080/WebsocketChat/websocketServer/" + chatRoomId);
	    
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
	    	var tmpHTML = ""

	    	if (data.type == systemType) {
	    		if (data.content == "enter") {
	    			showTipMessage(data.username + "进入聊天室");
	    		} else {
	    			showTipMessage(data.username + "离开聊天室");
	    		}

	    		$(".chat-list").empty();
    			$(".chat-list").append("<p>在线人数: <span id='onlineCount'>1</span></p>");
    			data.members.forEach(function(value) {
    				tmpHTML += "<p>" + value + "</p>"
    			})
    			$(".chat-list").append(tmpHTML)
	    		$("#onlineCount").text(data.members.length);
	    	} else if (data.type == commonType) {
	    		$("#message").append(jointHTML(data));

	    		//滚动条自动向下移动
	    		$("#message")[0].scrollTop = $("#message")[0].scrollHeight;
	    	} else {
	    		if (data.content.length != 0) {
		    		$('#history-list').empty();
		    		$('#history-list').append("<span class='close-btn' onclick='hideWindow()'>&times;</span>");
	    			data.content.reverse().forEach(function(record) {
	    				tmpHTML += jointHTML(record)
	    			})
	    			$("#history-list").append(tmpHTML);
	    			$(".history-div").show();
	    		} else {
	    			showTipMessage("暂无历史消息")
	    		}
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

function hideWindow() {
	$(".history-div").hide();
}

function getHistoryMessage() {
	sendMsg({
    	messageType : historyType,
    	message : uname
    });
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