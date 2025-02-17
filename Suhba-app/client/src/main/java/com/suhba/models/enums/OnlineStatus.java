package com.suhba.models.enums;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum OnlineStatus {
    ONLINE("Online", "#30a100"),
    OFFLINE("Offline", "#cccccc"),
    BUSY("Busy", "#ff4444"),
    AWAY("Away", "#ffaa00"),
    INVISIBLE("Invisible", "#666666");

    private final String displayName;
    private final String colorCode;

    OnlineStatus(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public Paint getColor() {
        return Color.web(colorCode);
    }
}