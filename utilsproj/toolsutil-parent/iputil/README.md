# IPUtil

1、通过IP获取地理位置

2、通过手机号码查询归属地

## IP数据库选型
1、MaxMind公司（https://www.maxmind.com/en/geoip2-cit）提供的IP解析数据库有：GeoIP2 City Database（收费）、GeoLite2 City（免费，但2019年1月2日后其不再更新）。

其解析正确率见：https://blog.csdn.net/CZ_yjsy_data/article/details/87897140

2、纯真（ http://www.cz88.net/）IP数据库
纯真IP数据库是开源的IP库，支持多语言。数据库大小：9M，收集了包括中国电信、中国移动、中国联通、长城宽带、聚友宽带等 ISP 的 IP 地址数据，包括网吧数据。IP数据库每5天更新一次。纯真IP库的获取率是99%