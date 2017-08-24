server和connect交互方式：
1、用户登录connect后，登录的connect的IP会存储到redis，通过redis获取connect的IP，发送指定消息到相应的connect，并执行消息发送
redis: playerId_connectIp_index(对局ID)

2、用户分配开局编号后，发送到connect，connect进行存储
Server 内存中构建对局，通过对局的index和playerId绑定保存到redis
（1）对局消息：对局的构建通过index进行自动划分服务器（平均划分），并通过index找到对应的Server
（2）普通消息：发送到任意一台Server上，通过redis存储的index与playerId的对应关系，直接发送消息（例如：发言等）
（3）断线重连：通过redis上存储的playerId对应的对局ID，找到对局对象，并获取相应的内存状态