package com.halalcraft.mod.component;

import java.util.UUID;

public class PlayerState {
    public boolean hasAcceptedShahada = false;
    public long lastPrayerTime = 0;
    public int prayerCycleIndex = 0;
    public long lastBismillahTime = 0;
    public UUID lastBismillahTarget = null;
    public boolean isCombatActive = false;
    public UUID combatTarget = null;
    public long combatGraceStartTime = 0;

    public PlayerState() {}
}
