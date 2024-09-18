/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : mioblog

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 16/09/2024 15:56:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for about
-- ----------------------------
DROP TABLE IF EXISTS `about`;
CREATE TABLE `about`  (
  `id` bigint NOT NULL,
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of about
-- ----------------------------
INSERT INTO `about` VALUES (1, 'title', '标题', '她把思念揉进风');
INSERT INTO `about` VALUES (2, 'musicId', '网易云歌曲ID', '2607303573');
INSERT INTO `about` VALUES (3, 'content', '正文Markdown', '### 目前可以公开的情报\n\n* 我  ：社畜\n* 目标：做最爱自己的人\n* 特征：176cm、男、帅\n* 爱好：听音乐、打篮球、AI绘画、摄影、探索有趣的新事物、冥想\n* 性格：喜欢无拘无束\n* ### 测试一下\n### 我的博客\n\n生性只对感兴趣的事物充满热情');
INSERT INTO `about` VALUES (4, 'commentEnabled', '评论开关', 'true');

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章首图，用于随机文章展示',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章正文',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `is_published` bit(1) NOT NULL COMMENT '公开或私密',
  `is_recommend` bit(1) NOT NULL COMMENT '推荐开关',
  `is_appreciation` bit(1) NOT NULL COMMENT '赞赏开关',
  `is_comment_enabled` bit(1) NOT NULL COMMENT '评论开关',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `views` int NOT NULL COMMENT '浏览次数',
  `words` int NOT NULL COMMENT '文章字数',
  `read_time` int NOT NULL COMMENT '阅读时长(分钟)',
  `category_id` bigint NOT NULL COMMENT '文章分类',
  `is_top` bit(1) NOT NULL COMMENT '是否置顶',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码保护',
  `user_id` bigint NULL DEFAULT NULL COMMENT '文章作者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type_id`(`category_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (12, '项目部署服务器（Docker容器一键部署）', 'https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/1-1.jpg', '## 项目部署服务器（Docker容器一键部署）\n\n## ps：一键部署不了一点，数据库表还是要自己创建的\n\n### 准备工作：\n\n1.一台Liunx系统的云服务器，有ICP备案好的域名/公网IP。\n\n2.需要会在liunx上安装执行依赖（我记得vim命令好像就要安装）。\n\n3.打包好前端项目和后端项目。\n\n注：打包前端项目之前我们先把blog-view它里面的axios.js中的baseURL: \'http://（公网IP/或者域名，删掉括号）:8888/\',然后把blog-cms里面的request.js中的baseURL: \'http://（公网IP/或者域名，删掉括号）:8888/admin\',都改一下，8888是我的暴露端口，你们也可以根据自己的习惯来改。后端的application.properties不用改，到时候我们可以在服务器上改。\n\n\n\n**快速了解docker部署**\n\n<img src=\"https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/1-1.jpg\" style=\"zoom:150%;\" />\n\n我们需要知道宿主机可以和容器进行文件预设，以防后续看不懂容器创建命令。\n\n例如：\n\n/mnt/docker/mysql/data:/var/lib/mysql\n\n表示宿主机的data文件夹和mysql容器的mysql文件夹共享。\n\n\n\n还有就是容器的文件夹是可以通过docker命令进行增删改查操作的，前提是容器需要是运行状态。\n\n##### 补充：\n\n没有什么基础的小伙伴建议下载宝塔面板，具体安装教程网上都有，**需要注意的就是要去云服务器控制器的安全组中把宝塔面板提供的端口打开**。\n\n这是我部署环境创建的所有文件夹，以供大家参考（ps:没展开的包除了admin和view一开始创建是没有东西的，部署之后才有东西，然后jar包是导进去的，也不需要创建）\n\n![file.jpg](https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/1-2.jpg)\n\n\n\n### 1.搭建环境\n\n**安装docker**\n\n1.包更新到最新\n\n```plaintext\nyum update\n```\n\n2.安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的\n\n```plaintext\nyum install -y yum-utils device-mapper-persistent-data lvm2\n```\n\n3.设置yum源,这里用阿里云镜像，你也可以选择其他可以使用的镜像\n\n```plaintext\nsudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo\n```\n\n4.安装docker\n\n```plaintext\nyum install -y docker-ce\n```\n\n5.查看docker版本，验证是否验证成功\n\n```plaintext\ndocker -v\n```\n\n6.启动docker\n\n```plaintext\n/bin/systemctl start docker.service\n```\n\n### 2.拉取并运行\n\n#### 2.1拉取服务前我们还需要对Docker 镜像下载加速拉取一些镜像：\n\n修改配置文件：\n\n```plaintext\nvim /etc/docker/daemon.json\n```\n\n配置内容如下：\n按下i开始修改配置文件\n\n```plaintext\n{\n  \"builder\": {\n    \"gc\": {\n      \"defaultKeepStorage\": \"20GB\",\n      \"enabled\": true\n    }\n  },\n  \"experimental\": true,\n  \"features\": {\n    \"buildkit\": true\n  },\n  \"insecure-registries\": [\n    \"172.24.86.231\"\n  ],\n  \"registry-mirrors\": [\n    \"https://dockerproxy.com\",\n    \"https://mirror.baidubce.com\",\n    \"https://ccr.ccs.tencentyun.com\",\n    \"https://docker.m.daocloud.io\",\n    \"https://docker.nju.edu.cn\",\n    \"https://docker.mirrors.ustc.edu.cn\"\n  ],\n  \"log-driver\":\"json-file\",\n  \"log-opts\": {\n    \"max-size\":\"500m\", \n    \"max-file\":\"3\"\n  }\n}\n```\n\n按下<u>shift + ：</u> \n\n输入<u>:wq</u>  保存并退出\n\n#### 2.2docker重启\n\n```plaintext\nsudo systemctl daemon-reload\nsudo systemctl restart docker\n```\n\n#### 2.3拉取服务\n\n```plaintext\ndocker pull nginx\ndocker pull redis\ndocker pull openjdk:8\ndocker pull mysql:8.0.27\n```\n\n执行docker images检查是否拉取成功\n\n#### 2.4 配置my.cnf\n\n在/mnt/docker/mysql/conf文件路径下vim my.cnf\n\n对其配置\n\n```plaintext\n[mysqld]\npid-file        = /var/run/mysqld/mysqld.pid\nsocket          = /var/run/mysqld/mysqld.sock\ndatadir         = /var/lib/mysql\nsecure-file-priv= NULL\ncharacter-set-server=utf8\nlower_case_table_names=1\nlog_timestamps=SYSTEM\nsql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,\nERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION\n```\n\n#### 2.5 配置redis.conf\n\n在/mnt/docker/redis/conf文件路径下vim redis.conf\n\n对其配置\n\n```plaintext\n# bind 127.0.0.1\ndaemonize no  \nrequirepass 123456 \nappendonly yes  \ntcp-keepalive 300 \n```\n\n#### 2.6 导入jar包，构建后端运行环境,并将jar包镜像到容器当中的命令（dockerfile）\n\n将jar包放入目录：/mnt/docker/app中\n\n**配置Dockerfile**\n\n在/mnt/docker/app下vim dockerfile\n\n```plaintext\nFROM openjdk:8\nVOLUME /tmp\nADD blog-api.jar app.jar\nENTRYPOINT [\"java\",\"-jar\",\"/app.jar\"]\n```\n\n**说明：**Dockerfile是一个文本文件\n包含了一条条的指令\n每一条指令构建一层，基于基础镜像，最终构建出一个新的镜像\n对于开发人员： 可以为开发团队提供一个完全一致的开发环境\n对于测试人员：可以直接拿开发时所构建的镜像或者通过Dockerfile文件构建一个新的镜像开始工作\n对于运维人员：在部署时，可以实现应用的无缝移植\n\n| 关键字      | 作用                     | 备注                                                         |\n| ----------- | ------------------------ | ------------------------------------------------------------ |\n| FROM        | 指定父镜像               | 指定dockerfile基于哪个image构建                              |\n| MAINTAINER  | 作者信息                 | 用来标明这个dockerfile谁写的                                 |\n| LABEL       | 标签                     | 用来标明dockerfile的标签 可以使用Label代替Maintainer 最终都是在docker image基本信息中可以查看 |\n| RUN         | 执行命令                 | 执行一段命令 默认是/bin/sh 格式: RUN command 或者 RUN [“command” , “param1”,“param2”] |\n| CMD         | 容器启动命令             | 提供启动容器时候的默认命令 和ENTRYPOINT配合使用.格式 CMD command param1 param2 或者 CMD [“command” , “param1”,“param2”] |\n| ENTRYPOINT  | 入口                     | 一般在制作一些执行就关闭的容器中会使用                       |\n| COPY        | 复制文件                 | build的时候复制文件到image中                                 |\n| ADD         | 添加文件                 | build的时候添加文件到image中 不仅仅局限于当前build上下文 可以来源于远程服务 |\n| ENV         | 环境变量                 | 指定build时候的环境变量 可以在启动的容器的时候 通过-e覆盖 格式ENV name=value |\n| ARG         | 构建参数                 | 构建参数 只在构建的时候使用的参数 如果有ENV 那么ENV的相同名字的值始终覆盖arg的参数 |\n| VOLUME      | 定义外部可以挂载的数据卷 | 指定build的image那些目录可以启动的时候挂载到文件系统中 启动容器的时候使用 -v 绑定 格式 VOLUME [“目录”] |\n| EXPOSE      | 暴露端口                 | 定义容器运行的时候监听的端口 启动容器的使用-p来绑定暴露端口 格式: EXPOSE 8080 或者 EXPOSE 8080/udp |\n| WORKDIR     | 工作目录                 | 指定容器内部的工作目录 如果没有创建则自动创建 如果指定/ 使用的是绝对地址 如果不是/开头那么是在上一条workdir的路径的相对路径 |\n| USER        | 指定执行用户             | 指定build或者启动的时候 用户 在RUN CMD ENTRYPONT执行的时候的用户 |\n| HEALTHCHECK | 健康检查                 | 指定监测当前容器的健康监测的命令 基本上没用 因为很多时候 应用本身有健康监测机制 |\n| ONBUILD     | 触发器                   | 当存在ONBUILD关键字的镜像作为基础镜像的时候 当执行FROM完成之后 会执行 ONBUILD的命令 但是不影响当前镜像 用处也不怎么大 |\n| STOPSIGNAL  | 发送信号量到宿主机       | 该STOPSIGNAL指令设置将发送到容器的系统调用信号以退出。       |\n| SHELL       | 指定执行脚本的shell      | 指定RUN CMD ENTRYPOINT 执行命令的时候 使用的shell            |\n\n#### 2.7  导入前端文件，配置nginx.conf\n\n分别在导入/mnt/docker/nginx/的view和admin文件目录下导入构建好的dist文件\n\n配置nginx.conf\n\n在/mnt/docker/nginx/下vim  nginx.conf对其进行映射。\n\n```plaintext\n    server {\n        listen 80;  \n        server_name 你的IP地址;  \n    # 解决跨域问题\n    add_header \'Access-Control-Allow-Origin\' \'*\';\n    add_header \'Access-Control-Allow-Methods\' \'GET, POST, OPTIONS\';\n    add_header \'Access-Control-Allow-Headers\' \'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization\';\n    proxy_set_header Cookie $http_cookie;\n    proxy_set_header X-Forwarded-Host $host;  \n    proxy_set_header X-Forwarded-Server $host;  \n    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for; \n    \n    location / {\n        root /view/dist;  \n        index index.html;  \n        try_files $uri $uri/ /index.html; \n    }\n    location /admin/ {\n        alias /admin/dist/;\n        index index.html;\n        try_files $uri $uri/ /admin/index.html;\n    # 允许目录索引\n    autoindex on;\n    }\n    \n    location /admin/lib/ {\n        alias /admin/dist/lib/;\n    }\n    \n    location /admin/static/ {\n        alias /admin/dist/static/;\n    }\n    \n    location /api/ {\n        proxy_pass http://你的ip地址:8888/;\n    }\n    \n        # 静态资源路径配置\n    }\n```\n\n说明，下面图中都是对admin的配置，原因是我在构建的时候出现了两个目录的css和js文件夹，所以我进一步配置说明他们的css和js文件夹路径。\n\n![admin.jpg](https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/1-3.jpg)\n\n#### 2.8  下载docker-compose，准备一键部署工作\n\n```plaintext\ncurl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose\n```\n# 设置文件可执行权限 \n```plaintext\nchmod +x /usr/local/bin/docker-compose\n```\n# 查看版本信息 \n\n```plaintext\ndocker-compose -version\n```\n\n#### 2.9 开始配置服务\n\n在/mnt/docker/app下，vim docker-compose.yml\n\n\n\n```plaintext\nversion: \'3\'\nservices:\n  mysql:\n    image: mysql:8.0.27\n    container_name: mysql\n    ports:\n      - \"3306:3306\"\n    environment:\n      MYSQL_ROOT_PASSWORD: root\n    volumes:\n      - /mnt/docker/mysql/conf:/etc/mysql/conf.d\n      - /mnt/docker/mysql/logs:/logs\n      - /mnt/docker/mysql/data:/var/lib/mysql\n      - /etc/localtime:/etc/localtime\n  redis:\n    image: redis\n    container_name: redis\n    ports:\n      - \"6379:6379\"\n    volumes:\n      - /mnt/docker/redis/data:/data\n      - /mnt/docker/redis/conf/redis.conf:/etc/redis/redis.conf\n  blog-api:\n    build: ./  \n    container_name: blog-api\n    ports:\n      - \"8888:8888\"\n    environment:\n      UPLOAD_FILE_PATH: \"/mnt/docker/appdate\"  # 在这里设置默认值\n      SPRING_DATASOURCE_URL: \"jdbc:mysql://mysql:3306/bmysql?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai\"\n      SPRING_DATASOURCE_USERNAME: \"root\"\n      SPRING_DATASOURCE_PASSWORD: \"root\"\n      \n      SPRING_REDIS_HOST: \"redis\"\n      SPRING_REDIS_PORT: \"6379\"\n      SPRING_REDIS_PASSWORD: \"123456\"\n     \n    depends_on:\n      - mysql\n      - redis\n  nginx1:\n    image: nginx\n    container_name: nginx1\n    ports:\n      - \"80:80\"\n    volumes:\n      - /mnt/docker/nginx/view/dist:/view/dist\n      - /mnt/docker/nginx/admin/dist:/admin/dist\n      - /mnt/docker/nginx:/etc/nginx/conf.d  # 将目录挂载到另一个目录\n    depends_on:\n      - blog-api\n```\n\n说明：\n\nbuild: ./ 当前目录（`./`）下的 Dockerfile 来构建这个服务的镜像。\n\nimage：选择的镜像。\n\ncontainer_name: 构建的容器名。\n\nports:暴露的端口号    -\"80（宿主机暴露的端口）：80（容器暴露的端口）\" 。\n\nvolumes: 宿主机映射到容器的文件地址，就是你在宿主机改变文件配置，重新启动容器，容器里对应的文件也会因为你的宿主机改变而发生改变。\n\ndependes_on:依赖关系，就是你启动当前容器是他会优先启动dependes_on的容器，在对自己进行启动。\n\nenvironment:重新配置环境，这里我们主要用于重新配置我们后端的JDBC和Redis。\n\n#### 2.10开始一键部署\n\n在/mnt/docker/app目录下执行docker-compose up\n\n正常情况下，我们的后端容器是无法启动的，但是其他的容器我们都启动了，这个时候我们把数据库表建一下。\n\n方法一：\n\n直接用Window的数据库图形化工具远程连接数据库，然后对其进行，数据的表的创建\n\n方法二：\n\n这里不是在文件路径出现的mysql是我的容器名字，改成你的容器名字\n\n1.拷贝sql文件到mysql容器里\n\n```plaintext\ndocker cp /mnt/docker/mysql/data/bmysql.sql mysql:/var/lib/mysql\n```\n\n2.进入mysql容器\n\n```plaintext\ndocker exec -it mysql /bin/bash   \n```\n3.登录到 MySQL 服务器\n```plaintext\nmysql -u root -p  #按下回车键后输入密码\n```\n4.创建数据库\n```plaintext\ncreate database bmysql\n```\n创建之后可以看一下是不是真的创建成功了 show database \n\n5.选择要操作的数据库\n```plaintext\nuse bmysql\n```\n6.用.sql文件创建数据库表\n```plaintext\nsource /var/lib/mysql/bmysql.sql;\n```\n7.再次部署\n\n执行成功之后退出容器，再次来到/mnt/docker/app目录下执行```plaintextdocker-compose up```\n\n如果你部署得一切顺利，你只需要在浏览器输入 http://你的ip地址 就可以看到你的项目了。\n\n\n\n#### 3.可能会遇到的问题\n\n我以的遇到的问题来说明总结\n\n1.数据库连接失败问题。\n\n方案：大概率是数据库表没有创建好。\n\n2.后端springboot成功启动，前端页面是空白的。\n\n方案：查看你的nginx.conf配置文件是否正确的指向了index.html，对应的js和css文件是否也指向成功了。\n\n3.后端springboot成功启动，前端接收不到数据的问题。\n\n方案：查看你的baseURL是否对应了正确的IP地址和后端暴露的端口号。\n\n上面3个都方案都试过了还没有用的话就去服务器控制台检查安全组，对应的端口号是否开放，redis和mysql的入站和出站端口都要开放。\n\n\n\n**最后还是非常推荐大家安装宝塔，可以非常直观的看到你的容器情况，安装后不需要在宝塔上进行任何的软件安装，只需要用他起到一个看到容器构建、运行、文件快速增删改查的作用。**\n\n', '**本项目采用docker容器一键部署，利用nginx实现前后端分离部署**\n\n\n![](https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/1-1.jpg)', b'1', b'1', b'1', b'1', '2024-09-16 11:54:33', '2024-09-16 11:54:33', 0, 3000, 15, 1, b'1', '', 1);
INSERT INTO `blog` VALUES (13, '暑假总结和碎碎念', 'https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/2-1.jpg', '<center><span style=\'font-size:25px;font-weight:bold;\'>暑假总结和碎碎念</span></center>\n\n这是我的大三升大四的暑假，也可能是我人生的最后一个暑假了，我其实从暑假之前就在想这个暑假要做一些不一样的事，不能要小学到去年的暑假只打游戏，动漫这些，原本打算考完C1驾驶证就去试试找工作，可是教练这段期间总有事，最后找他协商，我报了7月12日考科目三，这比我预期的要迟不少，后来因为一些变故放弃了找工作的想法，暑假开始之前和暑假开始阶段，我都在探索AIGC相关的东西，但我发现这个东西的便利之处是在你对某项当今传统行业技能很熟练时才能带来便利的，我决定先去重点学习Adobe的热门软件，因为有基础，也确实学了许多，但也发现了一个残酷的现实，如果完成简单的项目，那你只需要用剪映，如果有剪映做不到的特效方面的需求才需要用到AE，不得不说剪映把剪视频的门槛降低到了一个很离谱的程度，如果你想在行业得到比较可观的收入，不仅要会建模，一些绘画功底（或者可以熟练使用SD和MJ），甚至还需要会摄影，总之就是什么的你都要会干，我有点感觉到了这个行业的可怕，可我的专业也很可怕，但我还是决定重新拿起我的专业知识吧，可能秋招也赶不上了，但是要赶在春招之前找到工作，不然基本上是没有什么机会了。\n\n**收获：**\n\n​		确定了将来的就业方向，C1驾驶证，我的个人博客v1.0，见证了表姐幸福的婚礼。\n\n**遗憾：**\n\n​		科目三挂掉了一次，计算机图形学挂科了，奶奶......走了。\n\n**目标：**\n\n​		巩固JAVA，深入学习Redis，Vue2/Vue3，Jwt等，争取在春招之前找到工作。\n\n**最后：**\n\n​		这个暑假开始之后感觉我经历了太多太多，从学校回来的遇到大雨遇到列车停运，依稀记得那天晚上在火车站门口看火车票的感觉，当时又累，又困，又着急，很无力，但还给当时的我点个赞吧，那就是心里没出现过放弃的念想，我有进入职场，”自然“就给我沉痛的打击...\n\n\n\n世界给我带来了痛苦和磨难，我为过去和将来的少年前进。\n\n<p align=\"right\">--她把思念揉进风</p>', '<center>世界给我带来了痛苦和磨难，我为过去和将来的少年前进。\n\n<div style=\'text-align:right;\'><span style=\'font-size:16px;font-weight:bold;\'>-她把思念揉进风</span></div>\n\n![](https://cdn.jsdmirror.com/gh/vi-mio/resources/BlogImage/2-1.jpg)\n', b'1', b'0', b'0', b'1', '2024-09-16 12:18:13', '2024-09-16 12:18:13', 0, 733, 4, 5, b'0', '', 1);

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `blog_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES (12, 3);
INSERT INTO `blog_tag` VALUES (12, 5);
INSERT INTO `blog_tag` VALUES (12, 4);
INSERT INTO `blog_tag` VALUES (13, 8);
INSERT INTO `blog_tag` VALUES (13, 9);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '编程');
INSERT INTO `category` VALUES (2, 'AIGC模块');
INSERT INTO `category` VALUES (3, 'SpringBoot');
INSERT INTO `category` VALUES (4, '测试');
INSERT INTO `category` VALUES (5, '总结');

-- ----------------------------
-- Table structure for city_visitor
-- ----------------------------
DROP TABLE IF EXISTS `city_visitor`;
CREATE TABLE `city_visitor`  (
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '城市名称',
  `uv` int NOT NULL COMMENT '独立访客数量',
  PRIMARY KEY (`city`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of city_visitor
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像(图片路径)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者ip地址',
  `is_published` bit(1) NOT NULL COMMENT '公开或回收站',
  `is_admin_comment` bit(1) NOT NULL COMMENT '博主回复',
  `page` int NOT NULL COMMENT '0普通文章，1关于我页面，2友链页面',
  `is_notice` bit(1) NOT NULL COMMENT '接收邮件提醒',
  `blog_id` bigint NULL DEFAULT NULL COMMENT '所属的文章',
  `parent_comment_id` bigint NOT NULL COMMENT '父评论id，-1为根评论',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人网站',
  `qq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (63, 'vimio', 'he2328806287@126.com', '恭喜自己，网站正式启用！@[tv_可爱]@[tv_可爱]@[tv_可爱]', '/img/avatar.jpg', '2024-09-16 15:51:03', '192.168.17.1', b'1', b'1', 1, b'0', NULL, -1, '/', NULL);

-- ----------------------------
-- Table structure for exception_log
-- ----------------------------
DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exception_log
-- ----------------------------
INSERT INTO `exception_log` VALUES (156, '/siteSettings', 'POST', '{\"map\":{\"settings\":[{\"id\":1,\"nameEn\":\"blogName\",\"nameZh\":\"博客名称\",\"value\":\"Vimio\'s Blog\",\"type\":1},{\"id\":2,\"nameEn\":\"webTitleSuffix\",\"nameZh\":\"网页标题后缀\",\"value\":\" - Vimio\'s Blog\",\"type\":1},{\"id\":3,\"nameEn\":\"footerImgTitle\",\"nameZh\":\"页脚图片标题\",\"value\":\"感谢你对小站的支持~\",\"type\":1},{\"id\":4,\"nameEn\":\"footerImgUrl\",\"nameZh\":\"页脚图片路径\",\"value\":\"/img/qr.png\",\"type\":1},{\"id\":5,\"nameEn\":\"copyright\",\"nameZh\":\"Copyright\",\"value\":\"{\\\"title\\\":\\\"Copyright © 2024 - \\\",\\\"siteName\\\":\\\"Vimio\'s Blog\\\"}\",\"type\":1},{\"id\":6,\"nameEn\":\"beian\",\"nameZh\":\"ICP备案号\",\"value\":\"桂ICP备2024038830号\",\"type\":1},{\"id\":7,\"nameEn\":\"gongan\",\"nameZh\":\"公安备案号\",\"value\":\"桂公网安备45020002000265号\",\"type\":1},{\"id\":8,\"nameEn\":\"commentAdminFlag\",\"nameZh\":\"博主评论标识\",\"value\":\"男神\",\"type\":1},{\"id\":9,\"nameEn\":\"playlistServer\",\"nameZh\":\"播放器平台\",\"value\":\"netease\",\"type\":1},{\"id\":10,\"nameEn\":\"playlistId\",\"nameZh\":\"播放器歌单\",\"value\":\"447284833\",\"type\":1},{\"id\":30,\"nameEn\":\"reward\",\"nameZh\":\"赞赏码\",\"value\":\"/img/reward.jpg\",\"type\":1},{\"id\":11,\"nameEn\":\"avatar\",\"nameZh\":\"头像\",\"value\":\"/img/avatar.png\",\"type\":2},{\"id\":12,\"nameEn\":\"name\",\"nameZh\":\"昵称\",\"value\":\"她把思念揉进风\",\"type\":2},{\"id\":13,\"nameEn\":\"rollText\",\"nameZh\":\"滚动个签\",\"value\":\"\\\"不求繁华三千万，但许人间一两风。\\\"\",\"type\":2},{\"id\":14,\"nameEn\":\"github\",\"nameZh\":\"GitHub\",\"value\":\"https://github.com/vi-mio\",\"type\":2},{\"id\":15,\"nameEn\":\"telegram\",\"nameZh\":\"Telegram\",\"value\":\"\",\"type\":2},{\"id\":16,\"nameEn\":\"qq\",\"nameZh\":\"QQ\",\"value\":\"tencent://Message/?Uin=2328806287&site=qq&menu=yes\",\"type\":2},{\"id\":17,\"nameEn\":\"bilibili\",\"nameZh\":\"bilibili\",\"value\":\"https://space.bilibili.com/694338432?\",\"type\":2},{\"id\":18,\"nameEn\":\"netease\",\"nameZh\":\"网易云音乐\",\"value\":\"https://music.163.com/#/user/home?id=447284833\",\"type\":2},{\"id\":19,\"nameEn\":\"email\",\"nameZh\":\"email\",\"value\":\"mailto:he2328806287@qq.com\",\"type\":2},{\"id\":20,\"nameEn\":\"favorite\",\"nameZh\":\"自定义\",\"value\":\"{\\\"title\\\":\\\"努力活出自己吧，少年🍂\\\",\\\"content\\\":\\\"万恶淫为首，论迹不论心，论心世上无完人。\\\"}\",\"type\":2},{\"id\":21,\"nameEn\":\"badge\",\"nameZh\":\"徽标\",\"value\":\"{\\\"title\\\":\\\"由 Spring Boot 强力驱动\\\",\\\"url\\\":\\\"https:/', '更新站点配置信息', 'java.lang.NullPointerException\r\n	at top.vimio.utils.JwtUtils.getTokenBody(JwtUtils.java:105)\r\n	at top.vimio.aspect.OperationLogAspect.handleLog(OperationLogAspect.java:67)\r\n	at top.vimio.aspect.OperationLogAspect.logAround(OperationLogAspect.java:52)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:634)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:624)\r\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:72)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:750)\r\n	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:64)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:750)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:750)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:692)\r\n	at top.vimio.controller.SiteSettingAdminController$$EnhancerBySpringCGLIB$$34143cc4.updateAll(<generated>)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:197)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:141)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:894)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1063)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:652)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:121)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:115)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:105)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:110)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:80)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211)\r\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:358)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:271)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\r\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\r\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143)\r\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\r\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374)\r\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893)\r\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1707)\r\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\r\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n	at java.lang.Thread.run(Thread.java:748)\r\n', '192.168.17.1', '内网IP|内网IP', 'Windows >=10', 'Edge 128', '2024-09-16 14:20:34', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/128.0.0.0');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '站点',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `is_published` bit(1) NOT NULL COMMENT '公开或隐藏',
  `views` int NOT NULL COMMENT '点击次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of friend
-- ----------------------------

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `status` bit(1) NULL DEFAULT NULL COMMENT '登录状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for moment
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '动态内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `likes` int NULL DEFAULT NULL COMMENT '点赞数量',
  `is_published` bit(1) NOT NULL COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of moment
-- ----------------------------
INSERT INTO `moment` VALUES (12, '花了几个小时美化了我的电脑，已经尽量简洁了，感觉还不错，呵呵，不知道之后会不会会回归默认主题。\n<img src=\"https://cdn.jsdmirror.com/gh/vi-mio/resources/MomentImage/1-1.jpg\">\n\n<img src=\"https://cdn.jsdmirror.com/gh/vi-mio/resources/MomentImage/1-2.jpg\">', '2024-08-12 15:14:14', 0, b'1');
INSERT INTO `moment` VALUES (13, '**就在今天，2024年9月16号，我的博客功能基本完成，正式启用，在此同时在祝贺大家中秋节快乐**\n<div style=\"display:flex;align-items:flex-start;\">\n  <img src=\"https://cdn.jsdmirror.com/gh/vi-mio/resources/MomentImage/2-1.png\" alt=\"2-1.png\" style=\"top:0; left:0; zoom:14%;\" />\n</div>\n<div style=\"display:flex;align-items:flex-start;\">\n  <img src=\"https://cdn.jsdmirror.com/gh/vi-mio/resources/MomentImage/2-2.png\" alt=\"2-2.png\" style=\"zoom:50%;\" />\n</div>\n', '2024-09-16 15:18:52', 0, b'1');

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作者用户名',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `times` int NOT NULL COMMENT '请求耗时（毫秒）',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES (1, 'admin', '/job/run', 'POST', '{\"jobId\":1}', '立即执行定时任务', '192.168.17.1', '内网IP|内网IP', 'Windows >=10', 'Edge 128', 12, '2024-09-16 15:52:29', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/128.0.0.0');

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint NULL DEFAULT NULL COMMENT '任务状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'monthlyLogClearingTask', 'syncMonthlyLogClearingDatabase', '', '0 15 18 15 * ?', 1, '每个月清空一次任务日志、登录日志、操作日志、访问日志、访客统计。', '2020-11-17 23:45:42');
INSERT INTO `schedule_job` VALUES (2, 'visitorSyncScheduleTask', 'syncVisitInfoToDatebase', '', '0 0 0 * * ?', 1, '清空当天Redis访客标识，记录当天的PV和UV，更新当天所有访客的PV和最后访问时间，更新城市新增访客UV数', '2021-02-05 08:14:28');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `bean_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint NOT NULL COMMENT '任务执行结果',
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `times` int NOT NULL COMMENT '耗时（单位：毫秒）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES (1, 1, 'monthlyLogClearingTask', 'syncMonthlyLogClearingDatabase', '', 1, NULL, 288, '2024-09-16 15:52:29');

-- ----------------------------
-- Table structure for site_setting
-- ----------------------------
DROP TABLE IF EXISTS `site_setting`;
CREATE TABLE `site_setting`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `type` int NULL DEFAULT NULL COMMENT '1基础设置，2页脚徽标，3资料卡，4友链信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of site_setting
-- ----------------------------
INSERT INTO `site_setting` VALUES (1, 'blogName', '博客名称', 'Vimio\'s Blog', 1);
INSERT INTO `site_setting` VALUES (2, 'webTitleSuffix', '网页标题后缀', ' - Vimio\'s Blog', 1);
INSERT INTO `site_setting` VALUES (3, 'footerImgTitle', '页脚图片标题', '感谢你对小站的支持~', 1);
INSERT INTO `site_setting` VALUES (4, 'footerImgUrl', '页脚图片路径', '/img/qr.png', 1);
INSERT INTO `site_setting` VALUES (5, 'copyright', 'Copyright', '{\"title\":\"Copyright © 2024 - \",\"siteName\":\"Vimio\'s Blog\"}', 1);
INSERT INTO `site_setting` VALUES (6, 'beian', 'ICP备案号', '桂ICP备2024038830号', 1);
INSERT INTO `site_setting` VALUES (7, 'gongan', '公安备案号', '桂公网安备45020002000265号', 1);
INSERT INTO `site_setting` VALUES (8, 'commentAdminFlag', '博主评论标识', '男神', 1);
INSERT INTO `site_setting` VALUES (9, 'playlistServer', '播放器平台', 'netease', 1);
INSERT INTO `site_setting` VALUES (10, 'playlistId', '播放器歌单', '447284833', 1);
INSERT INTO `site_setting` VALUES (11, 'avatar', '头像', '/img/avatar.png', 2);
INSERT INTO `site_setting` VALUES (12, 'name', '昵称', '她把思念揉进风', 2);
INSERT INTO `site_setting` VALUES (13, 'rollText', '滚动个签', '\"不求繁华三千万，但许人间一两风。\"', 2);
INSERT INTO `site_setting` VALUES (14, 'github', 'GitHub', 'https://github.com/vi-mio', 2);
INSERT INTO `site_setting` VALUES (15, 'telegram', 'Telegram', '', 2);
INSERT INTO `site_setting` VALUES (16, 'qq', 'QQ', 'tencent://Message/?Uin=2328806287&site=qq&menu=yes', 2);
INSERT INTO `site_setting` VALUES (17, 'bilibili', 'bilibili', 'https://space.bilibili.com/694338432?', 2);
INSERT INTO `site_setting` VALUES (18, 'netease', '网易云音乐', 'https://music.163.com/#/user/home?id=447284833', 2);
INSERT INTO `site_setting` VALUES (19, 'email', 'email', 'mailto:he2328806287@qq.com', 2);
INSERT INTO `site_setting` VALUES (20, 'favorite', '自定义', '{\"title\":\"努力活出自己吧，少年🍂\",\"content\":\"万恶淫为首，论迹不论心，论心世上无完人。\"}', 2);
INSERT INTO `site_setting` VALUES (21, 'badge', '徽标', '{\"title\":\"由 Spring Boot 强力驱动\",\"url\":\"https://spring.io/projects/spring-boot/\",\"subject\":\"Powered\",\"value\":\"Spring Boot\",\"color\":\"brightgreen\"}', 3);
INSERT INTO `site_setting` VALUES (22, 'badge', '徽标', '{\"title\":\"Vue.js 客户端渲染\",\"url\":\"https://cn.vuejs.org/\",\"subject\":\"SPA\",\"value\":\"Vue.js\",\"color\":\"brightgreen\"}', 3);
INSERT INTO `site_setting` VALUES (23, 'badge', '徽标', '{\"title\":\"UI 框架 Semantic-UI\",\"url\":\"https://semantic-ui.com/\",\"subject\":\"UI\",\"value\":\"Semantic-UI\",\"color\":\"semantic-ui\"}', 3);
INSERT INTO `site_setting` VALUES (24, 'badge', '徽标', '{\"title\":\"阿里云提供服务器及域名相关服务\",\"url\":\"https://www.aliyun.com/\",\"subject\":\"VPS & DNS\",\"value\":\"Aliyun\",\"color\":\"orange\"}', 3);
INSERT INTO `site_setting` VALUES (25, 'badge', '徽标', '{\"title\":\"泽瑶网络网站加速\",\"url\":\"https://www.jsdmirror.com/\",\"subject\":\"CDN\",\"value\":\"JSDMirror\",\"color\":\"blue\"}', 3);
INSERT INTO `site_setting` VALUES (26, 'badge', '徽标', '{\"title\":\"静态资源托管于 GitHub\",\"url\":\"https://github.com/\",\"subject\":\"OSS\",\"value\":\"GitHub\",\"color\":\"github\"}', 3);
INSERT INTO `site_setting` VALUES (27, 'badge', '徽标', '{\"color\":\"pink\",\"subject\":\"CC\",\"title\":\"本站点采用 CC BY 4.0 国际许可协议进行许可\",\"url\":\"https://creativecommons.org/licenses/by/4.0/\",\"value\":\"BY 4.0\"}', 3);
INSERT INTO `site_setting` VALUES (28, 'friendContent', '友链页面信息', '欢迎交换友链~(￣▽￣)~*\n\n* 昵称：Vimio\n* 个签：不求繁华三千万，但许人间一两风。\n* 网址：[https://vimio.top](https://vimio.top)\n* 头像URL：[Mio酱](https://vimio.top/resources/image/avatar.png)\n\n博客还在完善中，我会努力学习加入我想加入的东西。测试\n', 4);
INSERT INTO `site_setting` VALUES (29, 'friendCommentEnabled', '友链页面评论开关', '1', 4);
INSERT INTO `site_setting` VALUES (30, 'reward', '赞赏码', '/img/reward.jpg', 1);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签颜色(可选)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Node.js', 'pink');
INSERT INTO `tag` VALUES (2, 'AI绘图', 'orange');
INSERT INTO `tag` VALUES (3, '项目部署', 'red');
INSERT INTO `tag` VALUES (4, '笔记', 'olive');
INSERT INTO `tag` VALUES (5, 'SpringBoot', 'green');
INSERT INTO `tag` VALUES (6, '游戏', 'blue');
INSERT INTO `tag` VALUES (8, '2024', 'grey');
INSERT INTO `tag` VALUES (9, '总结', 'purple');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像地址',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色访问权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'vi-mio@qq.com', '$2a$10$4wnwMW8Z4Bn6wR4K1YlbquQunlHM/4rvudVBX8oyfx16xeVtI6i7C', 'vimio', '/img/avatar.jpg', 'he2328806287@126.com', '2024-08-05 18:15:36', '2024-09-16 16:47:22', 'ROLE_admin');
INSERT INTO `user` VALUES (2, 'Visitor', '$2a$10$4wnwMW8Z4Bn6wR4K1YlbquQunlHM/4rvudVBX8oyfx16xeVtI6i7C', 'Visitor', '/img/visitor.jpg', 'he2328806287@126.com', '2024-09-15 10:13:20', '2024-09-16 10:13:20', 'ROLE_visitor');

-- ----------------------------
-- Table structure for visit_log
-- ----------------------------
DROP TABLE IF EXISTS `visit_log`;
CREATE TABLE `visit_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访客标识码',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数',
  `behavior` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问行为',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问内容',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `times` int NOT NULL COMMENT '请求耗时（毫秒）',
  `create_time` datetime NOT NULL COMMENT '访问时间',
  `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2921 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visit_log
-- ----------------------------

-- ----------------------------
-- Table structure for visit_record
-- ----------------------------
DROP TABLE IF EXISTS `visit_record`;
CREATE TABLE `visit_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pv` int NOT NULL COMMENT '访问量',
  `uv` int NOT NULL COMMENT '独立用户',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日期\"02-23\"',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visit_record
-- ----------------------------
INSERT INTO `visit_record` VALUES (12, 82, 12, '08-11');
INSERT INTO `visit_record` VALUES (13, 34, 9, '08-12');
INSERT INTO `visit_record` VALUES (14, 3, 1, '08-19');
INSERT INTO `visit_record` VALUES (15, 8, 5, '08-27');
INSERT INTO `visit_record` VALUES (16, 8, 5, '08-27');
INSERT INTO `visit_record` VALUES (17, 66, 5, '08-28');

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客标识码',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '首次访问时间',
  `last_time` datetime NOT NULL COMMENT '最后访问时间',
  `pv` int NULL DEFAULT NULL COMMENT '访问页数统计',
  `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_uuid`(`uuid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visitor
-- ----------------------------

-- ----------------------------
-- Table structure for websites
-- ----------------------------
DROP TABLE IF EXISTS `websites`;
CREATE TABLE `websites`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `webname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网站昵称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '站点',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '站点图标',
  `type` int NULL DEFAULT NULL COMMENT '1基础，2科学上网，3常用，4新世界',
  `views` int NOT NULL COMMENT '浏览次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of websites
-- ----------------------------
INSERT INTO `websites` VALUES (9, 'freecompress', '免费压缩图片、静态资源大小的工具', 'https://freecompress.com/zh-cn/compress-image', 'https://cdn.jsdmirror.com/gh/vi-mio/resources/Images/compress.jpg', 3, 0, '2024-09-16 14:41:00');

SET FOREIGN_KEY_CHECKS = 1;
