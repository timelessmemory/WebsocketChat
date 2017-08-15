# WebsocketChat
## FEATURE
1. 多人聊天，多聊天室， 互相独立, 界面简洁美观， 使用ueditor支持发送文字，图片信息
2. 群聊成员列表， 登入登出公告
3. 存储聊天记录， 查看历史消息
## 技术点
1. 使用CopyOnWriteMap存储Session对象，线程安全
2. redis存储消息记录
3. redis存储聊天成员

## result
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/1.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/2.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/3.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/4.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/history.png?raw=true)
