package com.evacipated.cardcrawl.mod.hiddeninfo

import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils
import com.google.gson.GsonBuilder
import imgui.ImGui
import imgui.type.ImBoolean
import java.nio.file.Paths
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties

data class HiddenConfig(
) {
    companion object {
        @Transient private var dirty: Boolean = false

        private lateinit var INSTANCE: HiddenConfig

        fun load() {
            val configPath = Paths.get(ConfigUtils.CONFIG_DIR, "Hidden Information", "config.json")
            val configFile = Gdx.files.absolute(configPath.toString())

            if (configFile.exists()) {
                val gson = GsonBuilder()
                    .create()
                INSTANCE = gson.fromJson(configFile.reader(), HiddenConfig::class.java)
            }
        }

        fun save() {
            if (!dirty) {
                return
            }

            val configPath = Paths.get(ConfigUtils.CONFIG_DIR, "Hidden Information", "config.json")
            val configFile = Gdx.files.absolute(configPath.toString())
            configFile.parent().mkdirs()

            val gson = GsonBuilder()
                .setPrettyPrinting()
                .create()
            val json = gson.toJson(INSTANCE)
            configFile.writeString(json, false)

            dirty = false
        }

        internal fun imgui() {
            if (ImGui.begin("Hidden Information")) {
                HiddenConfig::class.declaredMemberProperties.forEach { kprop ->
                    if (kprop is KMutableProperty1 && kprop.returnType == Boolean::class.createType()) {
                        if (ImGui.checkbox(kprop.name, kprop.get(INSTANCE) as Boolean)) {
                            (kprop as KMutableProperty1<HiddenConfig, Boolean>).set(INSTANCE, !kprop.get(INSTANCE))
                        }
                    }
                }
            }
            ImGui.end()
        }
    }
}
