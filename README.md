# WebsocketChat
## FEATURE
1. 多人聊天， 界面简洁美观， 使用ueditor支持发送文字，图片信息
2. 群聊成员列表， 登入登出公告
3. 存储聊天记录， 查看历史消息
## 技术点
1. 使用CopyOnWriteMap存储websocketServer对象，线程安全
2. redis存储消息记录
3. ConcurrentLinkedQueue存储聊天成员

TODO
没有处理高并发，高并发情况对服务器和内存都会产生极大压力
解决方案 采取实现分布式

## result
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/screen.png?raw=true)
![](https://github.com/timelessmemory/WebsocketChat/blob/master/screenshot/history.png?raw=true)
