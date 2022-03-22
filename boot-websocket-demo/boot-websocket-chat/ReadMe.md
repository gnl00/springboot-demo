#boot-websocket-chat

##相关技术
SpringBoot | WebSocket

##前言
<br/> 1、关于单工、半双工、全双工
<br/> 单工：数据传输只允许在一个方向上的传输，只能一方来发送数据，另一方来接收数据并发送。例如：对讲机
<br/> 半双工：数据传输允许两个方向上的传输，但是同一时间内，只可以有一方发送或接受消息。例如：打电话
<br/> 全双工：同时可进行双向传输。例如：websocket

<br/>
<br/> 2、为什么需要 websocket，而不是单纯使用 http

> http 协议是一种单向的网络协议，在建立连接后，它只允许 Browser/UA（UserAgent）向 Server 发出请求资源后，
Server 才能返回相应的数据，而 Server 不能主动的推送数据给 Browser/UA

[Websocket 教程-为什么需要 WebSocket？](http://www.ruanyifeng.com/blog/2017/05/websocket.html)

[为什么引入WebSocket协议](https://blog.csdn.net/yl02520/article/details/7298309)

<br/> http 1.0：单工。因为是短连接，客户端发起请求之后，服务端处理完请求并收到客户端的响应后即断开连接
<br/> http 1.1：半双工。默认开启长连接keep-alive，开启一个连接可发送多个请求。
<br/> http 2.0：全双工，允许服务端主动向客户端发送数据

[HTTP是单工的还是双工的还是半双工](https://www.jianshu.com/p/3ca180af7dca)



##问题与解决

<br/> 1、消息体的内容如何设计
<br/> 2、消息（发送方与接收方）该如何进行存储
<br/> 3、当接收方不在线，投递失败的消息如何处理
<br/> 4、首次进入页面消息该如何加载
<br/>

###消息体内容
>消息实体最少需要包含**发送者、接收者、消息内容、发送时间**四部分，可以在此基础上扩展

```java
public class Message<T> {
    private String from;
    private String to;
    private Date date = new Date();
    // 消息类型
    private String type;
    // 消息体内容
    private T body;
    // 已读未读
    private Boolean read = false;
}
```

<br/>

###消息存储
>思考以下在数据库中该如何存储消息？数据表该如何设计？

简单设计，根据消息实体类设计一张表
```sql
create table `message`(
    `id` bigint(20) not null auto_increment,
    `from` bigint(20) not null,
    `to` bigint(20) not null,
    `date` datetime not null DEFAULT CURRENT_TIMESTAMP,
    `type` varchar(20) not null,
    `body` text not null,
    --1 stand for true/已读 0 stand for false/未读
    `read` int not null,
    PRIMARY KEY (`id`),
    key `uid` (`from`),
    key `uid` (`to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

如果后续消息量增多可以将消息表**拆分**成多个表，或者消息的形式不止文本类型，可**增加**消息内容表。此是后话不再展开
```
新增消息类型表

消息表 message (
id
from
to
contentId
key `content.id` (`contentId`)
)

内容表 content (
id
type 可以是文本、表情、照片、视频
data
)
```
查询消息的时候联查消息表和内容表即可

<br/>

###消息投递失败
>当消息接收方不在线时如何处理投递失败的消息？