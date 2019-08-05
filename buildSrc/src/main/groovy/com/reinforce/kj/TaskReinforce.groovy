import com.reinforce.kj.EntityInput
import com.reinforce.kj.PluginReinforce
import com.reinforce.kj.encrypt.DesEncrypt
import com.reinforce.kj.encrypt.IEncrypt
import com.reinforce.kj.utils.DexUtils
import com.reinforce.kj.utils.ZipUtils
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

import java.util.zip.ZipFile

/**
 *
 * @anthor kb_jay
 * create at 2019/7/9 下午4:46
 */
class TaskReinforce extends DefaultTask {

    TaskReinforce() {
        group('custom')
        description('加固apk')
    }

    @TaskAction
    void doAction() {
        EntityInput input = project.extensions.getByName(PluginReinforce.EXT_NAME)
        if (!input.sourceApk.exists()) {
            throw new GradleException("sourceApk == null")
        }

        if (!input.shellAar.exists()) {
            throw new GradleException("shellAar == null")
        }

        println "[+] 开始加固  ${input.sourceApk.getName()}"

        //新建中间过程的文件夹
        File reinforceDir = new File(project.getBuildDir().getAbsolutePath() + "/reinforce")
        if (reinforceDir.exists()) {
            reinforceDir.deleteDir()
        }
        reinforceDir.mkdirs()
        //1：aar转为dex
        File jarFile = new File(reinforceDir, "shell.jar")
        def aarFiles = new ZipFile(input.shellAar)
        aarFiles.entries().each {
            zipEntry ->
                println "[+] ${zipEntry.name}"
                if (zipEntry.getName() == "classes.jar") {
                    jarFile.withOutputStream {
                        outputStream ->
                            def inputStream = aarFiles.getInputStream(zipEntry)
                            byte[] b = new byte[2048]
                            int len = -1
                            while ((len = inputStream.read(b)) > 0) {
                                outputStream.write(b, 0, len)
                            }
                    }
                    println "[+] 壳aar中的${zipEntry.name} 拷贝到 shell.jar"
                }
        }

        //dx --dex --output=target.jar origin.jar
        String command = "${input.sdkPath}/dx --dex --output=${reinforceDir.getAbsolutePath()}/shell.dex ${jarFile.getAbsolutePath()}"
        println "[+] shell.jar -> shell.dex"
        def output = new StringBuilder()
        def error = new StringBuilder()

        def execute = command.execute()
        execute.consumeProcessOutput(output, error)
        execute.waitFor()
        println "[+] 壳aar转dex执行结果：${output}->${error}"

        //2：apk转为dex
        def sourceUnZipDir = new File(reinforceDir, "source")
        if (!sourceUnZipDir.isDirectory()) {
            sourceUnZipDir.mkdirs()
        }
        println "[+] 开始解压缩源apk"

        ZipUtils.unzip(input.sourceApk, sourceUnZipDir)

        println "[+] 解压缩完成"

        println "[+] 开始加密dex"

        IEncrypt encrypt = new DesEncrypt(input.secretKey)

        sourceUnZipDir.listFiles().each {
            File file ->
                if (file.getName().endsWith(".dex")) {
                    println "   ->${file.getName()}"
                    def desFile = new File(file.getParentFile(), "encrypt.dex")
                    encrypt.encrypt(file, desFile)
                    def path = file.getAbsolutePath()
                    file.delete()
                    desFile.renameTo(path)
//                  encrypt.decrypt(desFile,new File(desFile.getParentFile(),"decrypt.dex"))
                }
        }
        println "[+] 加密结束"
        //3：合并dex
        println "[+] 开始合并"
        File sourceDex = new File(sourceUnZipDir, "classes.dex")
        File shellDex = new File(reinforceDir, "shell.dex")
        println "  ->源dex：${sourceDex.bytes.length} 壳dex：${shellDex.bytes.length}"
        int totalLen = sourceDex.bytes.length + shellDex.bytes.length + 4
        byte[] finalByte = new byte[totalLen]

        System.arraycopy(shellDex.bytes, 0, finalByte, 0, shellDex.bytes.length)
        System.arraycopy(sourceDex.bytes, 0, finalByte, shellDex.bytes.length, sourceDex.bytes.length)
        System.arraycopy(DexUtils.intToByte(sourceDex.bytes.length), 0, finalByte, totalLen - 4, 4)
        println "  -> 修改size"
        DexUtils.fixFileSizeHeader(finalByte)
        println "  -> 修改签名"
        DexUtils.fixSHA1Header(finalByte)
        println "  -> 修改校验码"
        DexUtils.fixCheckSumHeader(finalByte)

        sourceDex.withOutputStream {
            it.write(finalByte)
        }
        println "[+] 合并结束"
        //4：重新打包
        println "[+] 开始打包apk（没有签名）"

        ZipUtils.zip(sourceUnZipDir.getAbsolutePath(), new File(reinforceDir, "no-sign.apk").getAbsolutePath())
        println "[+] 打包apk完成（没有签名）"
        println "[+] 开始签名apk"
//       apksigner sign --ks /Users/kb_jay/Documents/kbjay.keystore --ks-key-alias kbjay --ks-pass pass:123456 --out /Users/kb_jay/Documents/myGit/apk_auto_enforce-master/KJ_ReinforceGradlePlugin/app/build/reinforce/sign.apk /Users/kb_jay/Documents/myGit/apk_auto_enforce-master/KJ_ReinforceGradlePlugin/app/build/reinforce/no-sign.apk
        def signerCommand = ["${input.sdkPath}/apksigner",
                             "sign",
                             "--ks",
                             "${input.signKeyStore}",
                             "--ks-key-alias",
                             "${input.signAlias}",
                             "--ks-pass",
                             "pass:123456",
                             "--out",
                             "${reinforceDir.getAbsolutePath()}/sign.apk",
                             "${reinforceDir.getAbsolutePath()}/no-sign.apk"]
        println signerCommand
        def signOut = new StringBuilder()
        def signError = new StringBuilder()
        def signExecute = signerCommand.execute()
        signExecute.waitFor()
        signExecute.consumeProcessOutput(signOut, signError)
        println "[+] 签名结束 ${signOut} -> ${signError}"
    }
}