package thaumcraft.warp;

public class WarpData {
    private int normal;
    private int permanent;
    private int temporary;
    private int counter;

    public int getNormal() {
        return normal;
    }

    public int getPermanent() {
        return permanent;
    }

    public int getTemporary() {
        return temporary;
    }

    public int getCounter() {
        return counter;
    }

    public void setNormal(int normal) {
        this.normal = clamp(normal);
    }

    public void setPermanent(int permanent) {
        this.permanent = clamp(permanent);
    }

    public void setTemporary(int temporary) {
        this.temporary = clamp(temporary);
    }

    public void setCounter(int counter) {
        this.counter = Math.max(0, counter);
    }

    public int getTotal() {
        return Math.max(0, normal + permanent + temporary);
    }

    public int getActual() {
        return Math.max(0, normal + permanent);
    }

    public void addNormal(int amount) {
        setNormal(normal + amount);
    }

    public void addPermanent(int amount) {
        setPermanent(permanent + amount);
    }

    public void addTemporary(int amount) {
        setTemporary(temporary + amount);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(500, value));
    }
}
