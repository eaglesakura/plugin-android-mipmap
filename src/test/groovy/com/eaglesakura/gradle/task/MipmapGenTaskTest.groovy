package com.eaglesakura.gradle.task

import org.gradle.testfixtures.ProjectBuilder

public class MipmapGenTaskTest extends GroovyTestCase {

    public void test_複数ファイルが正常に変換出力される() throws Exception {
        def project = ProjectBuilder.builder().build();
        def task = (MipmapGenTask) project.task("testingTask", type: MipmapGenTask);

        task.sources = new File("src/test/assets");
        task.output = new File("src/test/gen");
        task.toolsPath.convert = "C:\\dev-home\\tools\\imagemagick\\convert.exe"
        task.toolsPath.identify = "C:\\dev-home\\tools\\imagemagick\\identify.exe"
        task.build()
    }
}