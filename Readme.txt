分为三个模块处理

connect 负责长连接的建立保持和重新认证，以及数据的读写

server 负责后台处理全部的业务逻辑
相关jar包部分
    codec 负责数据编码和解码
    rule 负责规则
    process 负责业务流程
    context 负责业务运行上下文环境，并依次执行配置的流程
        configuration 负责业务配置
server逻辑部分
    dao 负责静态数据的查询
    connectService 负责连接服务调用

log 负责过程数据的记录以及最终结果统计，以及日志记录查询，建议使用hbase做大数据

common 为通用工具集合

connect 与server之间使用dubbo作为中间层

server和log使用kafka作为中间层



