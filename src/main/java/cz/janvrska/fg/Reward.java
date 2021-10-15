package cz.janvrska.fg;

import org.bukkit.Material;

public class Reward {
    public Material Item;
    public Integer Count;

    public Reward(Material item, Integer count) {
        this.Item = item;
        this.Count = count;
    }
}
