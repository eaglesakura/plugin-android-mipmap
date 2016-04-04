package com.eaglesakura.gradle.plugin

import org.gradle.testfixtures.ProjectBuilder

public class AndroidMipmapPluginTest extends GroovyTestCase {

    public void testPluginApply() {
        def project = ProjectBuilder.builder().build();
        project.apply plugin: AndroidMipmapPlugin

        project.mipmap.sources = new File("src/test/assets");
        project.mipmap.output = new File("src/test/gen");
        project.mipmap.toolsPath.convert = "C:\\dev-home\\tools\\imagemagick\\convert.exe"
        project.mipmap.toolsPath.identify = "C:\\dev-home\\tools\\imagemagick\\identify.exe"

        project.tasks.androidBuildMipmap.execute();
    }
}