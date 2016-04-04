package com.eaglesakura.gradle.plugin

import com.eaglesakura.gradle.android.mipmap.ToolsPath
import com.eaglesakura.gradle.task.MipmapGenTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AndroidMipmapPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("mipmap", AndroidMipmapPlugin.DSL);

        // 規定のタスクを追加
        def androidBuildMipmap = project.task('androidBuildMipmap', type: MipmapGenTaskImpl)
        androidBuildMipmap.setDescription("Generate mipmap/drawable resources");
        androidBuildMipmap.setGroup("Android Resource");
    }

    public static class MipmapGenTaskImpl extends MipmapGenTask {
        @Override
        void build() {
            output = project.mipmap.output;
            sources = project.mipmap.sources;
            toolsPath = project.mipmap.toolsPath;
            super.build()
        }
    }

    public static class DSL {
        File sources;
        File output;
        ToolsPath toolsPath = new ToolsPath();
    }
}
