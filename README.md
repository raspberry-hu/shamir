# shamir
本项目采用了shamir秘密共享的方案，将生成的密钥进行加密存储，达到安全可靠的目的。


配置文件为application-development.properties
在使用之前需要修改对应数据库配置信息，包含数据库地址
、数据库用户名、数据库密码，并对数据库进行初始化操作。

数据库初始化SQL文件在根目录下china_mobile.sql，本项目为微服务项目，因此sql文件中包含了其他项目所需的表。

支持docker启动DockerFile文件在根目录下，配置idea直接启动即可