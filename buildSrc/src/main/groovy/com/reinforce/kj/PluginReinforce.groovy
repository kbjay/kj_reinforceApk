package com.reinforce.kj

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 * @anthor kb_jay
 * create at 2019/7/9 下午3:00
 */
class PluginReinforce implements Plugin<Project> {

    public static final String EXT_NAME = "reinforce"
    public static final String TASK_NAME = "reinforce"

    @Override
    void apply(Project project) {
        //初始化扩展属相
        project.extensions.create(EXT_NAME, EntityInput)
        //初始化task
        project.tasks.create(TASK_NAME, TaskReinforce)
    }
}