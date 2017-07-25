## spiderdt 短链接

####框架组件
spring boot + redis+postgresql

服务端口：

*8906*

思路：

使用redis的原子递增功能，对每个URL进行发号。对号码转换成对应的62进制字符串->code。

redis中存放code 和对应的原始url。有效期10天。

异步线程，保存到数据库中。

如果相同的url，在一天之内重复请求，则会返回相同的code。

每次转换短链的个数小于**50**个。

需要携带accesstoken。目前是 2.005IvWNGoAN9dD586707c2a90CCEPa

请求短链时，如：127.0.0.1:8906/s/Q2o

首先查询redis。如果有对应的原始url，则直接返回，进行302跳转。同时更新redis失效时间。
如果redis中不存在，则查询数据库，并更新到redis中。该值，有效期10天。

