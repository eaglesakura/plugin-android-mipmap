package com.eaglesakura.gradle.android.mipmap

import com.eaglesakura.tool.log.Logger
import com.eaglesakura.util.Util

public class ImageItem {
    final File src;

    final File dstDirectory;

    String resType = "mipmap"

    private int width;

    private int height;

    private String dpi;

    private static final Map<String, Double> scaleTable = new HashMap<>();

    static {
        // 倍率テーブル
        scaleTable.put("ldpi", 0.75);
        scaleTable.put("mdpi", 1.0);
        scaleTable.put("hdpi", 1.5);
        scaleTable.put("xhdpi", 2.0);
        scaleTable.put("xxhdpi", 3.0);
        scaleTable.put("xxxhdpi", 4.0);
    }

    ImageItem(File src, File dstDirectory) {
        this.src = src.absoluteFile;
        this.dpi = src.parentFile.name;
        this.dstDirectory = dstDirectory.absoluteFile;
    }

    int etWidth() {
        return width
    }

    int getHeight() {
        return height
    }

    String getDpi() {
        return dpi
    }

    boolean build(ToolsPath path) {
        if (!src.file) {
            Logger.out("NotFound : %s", src.absolutePath)
            return false;
        }

        width = [path.identify, "-format", "%w", src.absolutePath].execute().text as int;
        height = [path.identify, "-format", "%h", src.absolutePath].execute().text as int;

        Logger.out("ImageSize[%d x %d]", width, height);

        // 本来あるべきサイズを計算する
        final double ORIGINAL_WIDTH = (float) width / (float) scaleTable.get(dpi)
        final double ORIGINAL_HEIGHT = (float) width / (float) scaleTable.get(dpi)
        Logger.out("1.0x size = %.1f x %.1f", ORIGINAL_WIDTH, ORIGINAL_HEIGHT)

        scaleTable.each {
            def genDir = new File(dstDirectory, resType + "-" + it.key);
            // 出力先を確保する
            genDir.mkdirs();
            def genFile = new File(genDir, src.name);
            if (dpi == it.key) {
                // コピーする
                Logger.out("  - Copy File[%s] -> [%s]", src.absolutePath, genFile.absolutePath);
                genFile << src.readBytes();
            } else {
                // genする
                final int OUTPUT_WIDTH = (int) (ORIGINAL_WIDTH * it.value);
                final int OUTPUT_HEIGHT = (int) (ORIGINAL_HEIGHT * it.value);

                // 元サイズを超えたらもう生成の必要はない
                if (OUTPUT_WIDTH > width) {
                    return;
                }

                genFile.delete();
                Logger.out("  - Convert %s %dx%d -> [%s]", it.key, OUTPUT_WIDTH, OUTPUT_HEIGHT, genFile.absolutePath);
                def output = [path.convert, "-resize", String.format("%dx%d", OUTPUT_WIDTH, OUTPUT_HEIGHT), src.absolutePath, genFile.absolutePath].execute().waitFor();
                Logger.out("    - exit(" + output + ")");
            }
        }

        return true;
    }
}
