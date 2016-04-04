package com.eaglesakura.gradle.task

import com.eaglesakura.gradle.android.mipmap.ImageItem
import com.eaglesakura.gradle.android.mipmap.ToolsPath
import com.eaglesakura.tool.log.Logger
import com.eaglesakura.util.IOUtil
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;

public class MipmapGenTask extends DefaultTask {

    /**
     * ソースディレクトリへのパス
     * path/to/images/で指定し、更に配下に
     *   * xxhdpi/hoge.png 等の自動生成するリソースを配置する
     */
    File sources;

    /**
     * 出力ディレクトリへのパス
     */
    File output;

    def toolsPath = new ToolsPath();

    boolean isImage(File file) {
        def EXT_TABLE = [".png", ".jpg", ".jpeg"];
        for (def ext : EXT_TABLE) {
            if (file.name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    void buildDpi(File dpiDir, String resType) {
        Logger.out("Check [%s]", dpiDir.absolutePath);
        dpiDir.listFiles().each {
            if (!isImage(it)) {
                Logger.out("Skip [%s]", it.absolutePath);
                return;
            }

            ImageItem item = new ImageItem(it, output);
            item.resType = resType;
            item.build(toolsPath);
        }
    }

    @TaskAction
    void build() {
        IOUtil.cleanDirectory(output);
        new File(sources, "drawable").listFiles().each {
            buildDpi(it, "drawable");
        }
        new File(sources, "mipmap").listFiles().each {
            buildDpi(it, "mipmap");
        }
    }
}
