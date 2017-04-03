package com.eaglesakura.android.mipmap

import com.eaglesakura.gradle.android.mipmap.ImageItem
import com.eaglesakura.gradle.android.mipmap.ToolsPath
import com.eaglesakura.tool.log.Logger;

class ImageItemTest extends GroovyTestCase {

    void test_リソース名として有効な名前に変換する() throws Throwable {
        assertEquals(ImageItem.toResourceName("Item-00.png"), "item_00.png");
    }

    void test_画像の幅高さが取得できる() throws Exception {
        def src = new File("src/test/assets/mipmap/xxxhdpi/ic_menu_cyclecomputer.png");
        def dst = new File("src/test/gen/res");
        ToolsPath path = new ToolsPath();
        path.identify = System.getenv("IMGMAGICK_IDENTIFY");
        path.convert = System.getenv("IMGMAGICK_CONVERT");

        Logger.out("src= %s", src.absolutePath)
        Logger.out("identify= %s", path.identify)
        Logger.out("convert= %s", path.convert)
        assertTrue(src.file);

        ImageItem item = new ImageItem(src, dst);
        item.build(path);
        assertTrue(item.width > 0)
        assertTrue(item.height > 0)


    }
}
