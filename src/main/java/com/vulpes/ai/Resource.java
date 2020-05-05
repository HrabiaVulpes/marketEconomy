package com.vulpes.ai;

import java.awt.*;

public enum Resource {
    A(Color.RED),
    B(Color.GREEN),
    C(Color.BLUE),
    D(Color.ORANGE);

    public final Color color;

    Resource(Color color) {
        this.color = color;
    }
}
