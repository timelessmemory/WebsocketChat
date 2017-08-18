# WebsocketChat
## INTRODUCTION
### version1.0 多人单聊天室版 切换至one-to-many-one-chatroom分支<br/>
### version2.0 多人多聊天室版 切换至one-to-many-multi-chatroom分支<br/>
### version3.0 一对一聊天版 切换至one-to-one-chatroom分支<br/>
### 该分支是终极版（尚未实现）<br/>
1. 对于WebsocketServer对象的存储改为对Websocket中的Session对象的存储，并且不再使用Map存储。因为计划加入分布式设计，为了多服务器之间共享Session采取将Session存入Redis的方案。
2. 为了节省内存，聊天成员也存入Redis
3. 加入ChatRoomId隔离出多个聊天室

## Problem
在存储session至Radis时出现问题，Session没有实现序列化接口无法序列化，因为无法存入redis。<br/>
目前解决方案思路：<br/>
多个服务器中的Session实例还是存储在Map中，如何实现session共享：采用发布订阅。当某个服务器节点收到用户请求，先查看本服务器有没有目标用户的session，如果没有就发布消息到其它服务器。其它服务器订阅消息查看有没有目标用户的session，有就使用session发送消息。<br/>
但是这种方案比较繁琐，实现可以实现，暂时的方案只有这个。


## result

![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/chat.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/c.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/s.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/screen.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/history.png?raw=true)
