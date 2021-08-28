package com.iridium.iridiumenchants.configs.inventories;

import com.iridium.iridiumenchants.AnimatedBackground;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AnimatedBackgroundGUI {

    /**
     * The size of the GUI.
     */
    public int size;

    /**
     * The title of the GUI.
     */
    public String title;

    /**
     * The background of the GUI.
     */
    public AnimatedBackground background;

    public int nextFrameInterval;

}
