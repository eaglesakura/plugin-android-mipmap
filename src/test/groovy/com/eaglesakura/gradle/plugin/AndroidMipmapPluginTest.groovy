package com.eaglesakura.gradle.plugin

import org.gradle.testfixtures.ProjectBuilder

public class AndroidMipmapPluginTest extends GroovyTestCase {

    public void testPluginApply() {
        def project = ProjectBuilder.builder().build();
        project.apply plugin: AndroidMipmapPlugin

        project.mipmap.sources = new File("src/test/assets");
        project.mipmap.output = new File("src/test/gen");
        project.mipmap.toolsPath.convert = System.getenv("IMGMAGICK_CONVERT");
        project.mipmap.toolsPath.identify = System.getenv("IMGMAGICK_IDENTIFY");
        project.mipmap.cleanOldFiles = false;

        project.tasks.androidBuildMipmap.execute();
    }
}