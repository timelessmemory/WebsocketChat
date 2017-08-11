# WebsocketChat
## INTRODUCTION
version1.0 切换至one-to-many-one-chatroom分支
该分支是对version1.0的改版
1. 对于WebsocketServer对象的存储改为对Websocket中的Session对象的存储，并且不再使用Map存储。因为计划加入分布式设计，为了多服务器之间共享Session采取将Session存入Redis的方案。
2. 为了节省内存，聊天成员也存入Redis

## TODO


## result

![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/chat.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/c.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/s.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/screen.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/history.png?raw=true)
