# 补丁生成工具使用说明

#### 一、配置

配置文件路径/config/application.yaml

参数说明

```yaml
# 补丁输出目录
patch-path: "/Patch"
# 应用服务器后端文件目录
server-path: "/publish/server/apps/scm"
# 应用服务器前端文件目录
web-path: "/publish/web/apps/scm"
# DBO文件目录
dbo-path: "/publish/server/apps/scm"
# Data文件目录
data-path: "/publish/tools/setup/db/scm"
# iGIX元数据文件目录
metadata-path: "/publish/metadata/apps/scm"
# IDP元数据文件目录
idp-path: "/idp/scm"
# 交付物路径
delivery-path: "D:/projects/Scm/delivery/Scm_All"
# 默认起始日期
default-begin: "2023-02-04 06:00"
# 是否开启提交记录过滤
commit-filter: false
```