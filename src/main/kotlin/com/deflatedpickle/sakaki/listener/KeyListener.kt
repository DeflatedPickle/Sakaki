package com.deflatedpickle.sakaki.listener

import com.deflatedpickle.sakaki.PanelController
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

object KeyListener : NativeKeyListener {
    override fun nativeKeyTyped(p0: NativeKeyEvent) {
    }

    override fun nativeKeyPressed(p0: NativeKeyEvent) {
        for (panelStage in PanelController.panelList) {
            if (panelStage.toggleModifier == NativeKeyEvent.getModifiersText(p0.modifiers)) {
                if (panelStage.toggleKey == NativeKeyEvent.getKeyText(p0.keyCode)) {
                    if (panelStage.outProperty.value) {
                        panelStage.hideTimeline.play()
                    } else {
                        panelStage.showTimeline.play()
                    }
                }
            }
        }
    }

    override fun nativeKeyReleased(p0: NativeKeyEvent) {
    }
}