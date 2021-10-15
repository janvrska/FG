package cz.janvrska.fg.events;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.janvrska.fg.Button;
import cz.janvrska.fg.PlayerReward;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OnButtonPress implements Listener {
    List<Button> buttons;
    private static final String file = "plugins/fg/buttons.json";


    @EventHandler
    public void OnButtonPress(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clicked = event.getClickedBlock();
            if (clicked.getType() == Material.STONE_BUTTON) {

                GetButtons();

                if (buttons != null) {
                    Button reward = ButtonOnCordinates(clicked.getX(), clicked.getY(), clicked.getZ());
                    if (reward != null) {
                        if (reward.Players.size() == 0) {
                            reward.Players.add(new PlayerReward(p.getName(), System.currentTimeMillis() / 1000));
                            p.sendMessage(ChatColor.RED + "Gratulujeme ke splnění eventu");
                            p.sendMessage(ChatColor.YELLOW + "Dostáváš odměnu, kterou si můžeš vybrat jednou za 24 hodin :-)");

                            giveReward(reward, p);

                            SaveFile();
                            p.performCommand("spawn");
                            return;
                        }

                        for (int i = 0; i < reward.Players.size(); i++) {
                            if (reward.Players.get(i).PlayerName.equals(p.getName())) {
                                long time_now = System.currentTimeMillis() / 1000;
                                long day_before = time_now - 86400;
                                p.sendMessage(ChatColor.RED + "Gratulujeme ke splnění eventu");
                                if (reward.Players.get(i).Time <= day_before) {
                                    p.sendMessage(ChatColor.YELLOW + "Dostáváš odměnu, kterou si můžeš vybrat jednou za 24 hodin :-)");
                                    reward.Players.get(i).Time = System.currentTimeMillis() / 1000;

                                    giveReward(reward, p);

                                    SaveFile();
                                } else {
                                    p.sendMessage(ChatColor.RED + "Odměnu lze obdržet pouze jednou za 24 hodin");
                                }
                                p.performCommand("spawn");
                                return;
                            }
                        }

                        reward.Players.add(new PlayerReward(p.getName(), System.currentTimeMillis() / 1000));
                        p.sendMessage(ChatColor.RED + "Gratulujeme ke splnění eventu");
                        p.sendMessage(ChatColor.YELLOW + "Dostáváš odměnu, kterou si můžeš vybrat jednou za 24 hodin :-)");
                        giveReward(reward, p);

                        SaveFile();

                        p.performCommand("spawn");
                    }
                }
            }
        }
    }

    public void giveReward(Button reward, Player p) {
        for (int i = 0; i < reward.Items.size(); i++) {
            ItemStack item = new ItemStack(reward.Items.get(i).Item, reward.Items.get(i).Count);
            p.getInventory().addItem(item);
        }
    }

    public Button ButtonOnCordinates(int x, int y, int z) {

        for (Button button : buttons) {
            if (button.X.equals(x) && button.Y.equals(y) && button.Z.equals(z)) {
                return button;
            }
        }
        return null;
    }

    public void GetButtons() {
        File file = new File(OnButtonPress.file);
        boolean empty = !file.exists() || file.length() == 0;
        if (!empty) {
            Gson gson = new Gson();

            try {
                buttons = gson.fromJson(new FileReader(OnButtonPress.file), new TypeToken<ArrayList<Button>>() {
                }.getType());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void WriteFile(String json) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("FG: An error occurred.");
            e.printStackTrace();
        }
    }

    public void SaveFile() {
        Gson gson = new Gson();
        String json = gson.toJson(buttons);
        WriteFile(json);
    }
}
