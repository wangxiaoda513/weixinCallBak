# weixinCallBak
模拟生成微信回调，方便各环境开发

# 场景
微信支付需要配置回调地址。当用户发起微信支付，点击付款成功后，微信会调用应用程序配置好的URL直接，意思是说支付成功了。在这个回调成功的方法里面，应用程序可以处理自己的业务逻辑。如修改订单状态，减少库存等。
# 存在问题
开发环境，IP多为内网IP，不是公网IP，这样微信回调是回调不回来的。当然也可以通过反向代理ngrok结合nginx来搞。

这里主要是通过程序，用redis句柄来模拟各个环境的微信支付回调。

# 说明：
* 项目工程代码已经全部开源，可以下载下来直接运行，保密原则，微信相关的appId和macId已经全部注释掉。只需要替换成自己微信公众号的相应值即可。
* uServiceUrl需要配置成自己应用的回调地址
