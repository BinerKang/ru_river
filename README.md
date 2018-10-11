# ru_river
---
### 简介
该项目是为了学习建站的整个流程。目前包含了登录、注册、游戏排行榜以及注册后发送邮件等简单的模块。
欢迎访问[本项目网站](http://www.kangcat.cn)。由于带宽较低，加载失败时，麻烦刷新下访问。

### 技术
该项目为Maven项目。
* 前端框架和路由使用Vue.js，样式使用Bootstrap，聊天室(广播室)、游戏排行榜通过WebSocket实时更新。
* 后台框架SpringMVC\Mybatis，数据库连接池采用Druid，采用Quartz定时任务框架，生产、测试、开发配置分离。
* 地址信息是通过访问者的IP，查询[GeoLite数据库](http://www.maxmind.com)获取的。
* 文章内容大部分采用python爬取。
* 服务器系统CentOS7，搭建了Jekins实现自动部署。[服务器部署.txt](https://github.com/BinerKang/ru_river/blob/master/%E6%9C%8D%E5%8A%A1%E5%99%A8%E9%83%A8%E7%BD%B2.txt)记载了如何安装项目所需要的相关软件。
* 由于服务器内存有限，Jekins与tomcat服务不能同时开启。所以简单的脚本[closeJenkins.sh](https://github.com/BinerKang/ru_river/blob/master/openJenkins.sh)、[closeJenkins.sh](https://github.com/BinerKang/ru_river/blob/master/closeJenkins.sh)实现了切换。

### 感想
闻道有先后，术业有专攻，小小的心愿也算完成了吧。三十而立，这里的立不是成家立业，也非功成名就，而是“做事合礼，言行得当”。还在路上，探索着，努力着。加油，加油，加油！
