var uname = prompt('请输入用户名', 'user' + uuid(8, 16));
var websocket = null;
var systemType = 'system_message'
var receiveMessage = []

$(function() {
    if ('WebSocket' in window && uname !== null) {
    	$('#username').text("欢迎， " + uname);
        websocket = new WebSocket("ws://localhost:8080/WebsocketChat/websocketServer");
	    
	    websocket.onopen = function(event) {
	        showTipMessage("连接服务器成功");
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
	    			showTipMessage(data.username + "上线");
	    		} else {
	    			showTipMessage(data.username + "下线");
	    		}

	    		$(".chat-list").empty();
    			$(".chat-list").append("<p>在线好友列表 : <span id='onlineCount'>1</span>(单击以建立聊天)</p>");
    			data.members.forEach(function(value) {
    				tmpHTML += "<div class='member-item' tabindex='-1' onclick='newChat(\"" + uname + "\", \"" + value + "\")'>" + value + "</div>"
    			})
    			$(".chat-list").append(tmpHTML)
	    		$("#onlineCount").text(data.members.length);
	    	} else {
                addReceiveMessage(data)
                $(".notice-content").empty();
                var head = receiveMessage.length == 0 ? "<p>暂无未读好友消息</p>" : "<p>未读的好友消息 : <span id='receiveCount'>1</span>(单击进行回复)</p>";
                $(".notice-content").append(head);

                receiveMessage.forEach(function(value) {
                    tmpHTML += "<div class='member-item' tabindex='-1' onclick='receiveChat(\"" + uname + "\", \"" + value.username + "\")'>" + value.username + "在" + value.date + "给你发了：</div><div class='receive-message'>" + value.message + "</div>"
                })
                $(".notice-content").append(tmpHTML)
                if (receiveMessage.length > 0) {
                    $("#receiveCount").text(receiveMessage.length);
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

var addReceiveMessage = function(data) {
    var flag = false
    receiveMessage.forEach(function(item, idx) {
        if (item.username == data.username) {
            flag = true
            receiveMessage[idx] = data
        }
    })

    if (!flag) {
       receiveMessage.push(data) 
    }
}

var removeReceiveMessage = function(to) {
    var flag = -1
    receiveMessage.forEach(function(item, idx) {
        if (item.username == to) {
            flag = idx
        }
    })

    if (flag != -1) {
        receiveMessage.splice(flag, 1)
    }
}

window.onbeforeunload = function () {
    closeWebSocket();
};

var newChat = function(from, to) {
	if (from != to) {
		window.open("newChat.jsp?from=" + escape(from) + "&to=" + escape(to), "_blank");
	}
}

var receiveChat = function(from, to) {
    newChat(from, to);
    removeReceiveMessage(to);
    var tmpHTML = "";
    $(".notice-content").empty();
    var head = receiveMessage.length == 0 ? "<p>暂无未读好友消息</p>" : "<p>未读的好友消息 : <span id='receiveCount'>1</span>(点击查看)</p>";
    $(".notice-content").append(head);

    receiveMessage.forEach(function(value) {
        tmpHTML += "<div class='member-item' tabindex='-1' onclick='receiveChat(\"" + uname + "\", \"" + value.username + "\")'>" + value.username + "在" + value.date + "给你发了：</div><div class='receive-message'>" + value.message + "</div>"
    })
    $(".notice-content").append(tmpHTML)

    if (receiveMessage.length > 0) {
        $("#receiveCount").text(receiveMessage.length);
    }

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