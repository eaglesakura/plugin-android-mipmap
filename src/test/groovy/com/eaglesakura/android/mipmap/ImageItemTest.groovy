package com.eaglesakura.android.mipmap

import com.eaglesakura.gradle.android.mipmap.ImageItem
import com.eaglesakura.gradle.android.mipmap.ToolsPath
import com.eaglesakura.tool.log.Logger;

public class ImageItemTest extends GroovyTestCase {

    public void test_画像の幅高さが取得できる() throws Exception {
        def src = new File("src/test/assets/mipmap/xxxhdpi/ic_menu_cyclecomputer.png");
        def dst = new File("src/test/gen/res");
        ToolsPath path = new ToolsPath();

        Logger.out("Path = %s", src.absolutePath)
        assertTrue(src.file);

        ImageItem item = new ImageItem(src, dst);
        item.build(path);
        assertTrue(item.width > 0)
        assertTrue(item.height > 0)


    }
}
