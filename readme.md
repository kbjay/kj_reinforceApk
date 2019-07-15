> * 输入：源apk，加密算法，壳aar，签名文件
> * 输出：加固apk 

插件功能：
1. apktool反编译源apk得到dex文件，使用dx工具从aar获取dex文件
2. 对源apk的dex文件进行加密
3. 将源dex文件拼接到壳dex文件后面
4. 将合并的dex文件放入壳apk中
5. 重新打包，签名输出apk