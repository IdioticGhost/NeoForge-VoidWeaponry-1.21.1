package net.idioticghost.voidweaponry.entity.custom;

public class DeathArrowEntityHitInfo {
    public int hitCount;
    public long lastHitTime;

    public DeathArrowEntityHitInfo() {
        this.hitCount = 1;
        this.lastHitTime = System.currentTimeMillis();
    }

    public void refreshHit() {
        this.hitCount++;
        this.lastHitTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - lastHitTime) > 7000; // 7 seconds
    }
}