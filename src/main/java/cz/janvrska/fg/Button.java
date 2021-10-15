package cz.janvrska.fg;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Button {

    public String Name;
    public Integer X;
    public Integer Y;
    public Integer Z;
    public List<Reward> Items = new ArrayList<>();
    public List<PlayerReward> Players = new ArrayList<>();

    public Button(String Name, Integer X, Integer Y, Integer Z) {
        this.Name = Name;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public void AddItem(Material item, Integer count) {
        Reward reward = new Reward(item, count);
        Items.add(reward);
    }
}
