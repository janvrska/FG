package cz.janvrska.fg.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.janvrska.fg.Button;
import cz.janvrska.fg.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FgCommands implements CommandExecutor {

    List<Button> buttons;
    private Plugin plugin;

    public FgCommands(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
                if (p.hasPermission("fg.create")) {
                    if (args[1].length() >= 3) {
                        Block b = p.getTargetBlock(null, 5);
                        if (b.getType() == Material.STONE_BUTTON) {
                            GetButtons();
                            Button button = new Button(args[1], b.getX(), b.getY(), b.getZ());
                            if (buttons == null) {
                                buttons = new ArrayList<>();
                            }

                            String exist_button = ButtonOnCordinates(button);

                            if (exist_button != null) {
                                p.sendMessage("Na této pozici je již registrováno tlačítko: " + ChatColor.YELLOW + exist_button);
                                return true;
                            }

                            if (existButton(args)) {
                                p.sendMessage("Tlačítko s tímto jménem již existuje");
                                return true;
                            }

                            buttons.add(button);
                            Gson gson = new Gson();
                            String json = gson.toJson(buttons);
                            WriteFile(json);

                            p.sendMessage("Event tlačítko " + ChatColor.YELLOW + args[1] + ChatColor.WHITE + " bylo vytvořeno");
                        } else {
                            p.sendMessage("Pro vytvoření musíš mířit na stone tlačítko");
                        }
                    } else {
                        p.sendMessage("Název musí obsahovat alespoň 3 znaky");
                    }
                } else {
                    p.sendMessage("Tohoto příkazu nejsi hoden");
                }

            } else if (args.length == 3 && args[0].equalsIgnoreCase("reward")) {
                if (p.hasPermission("fg.reward")) {
                    Block b = p.getTargetBlock(null, 5);
                    if (b.getType() == Material.STONE_BUTTON) {
                        GetButtons();
                        Button button = new Button("random", b.getX(), b.getY(), b.getZ());
                        if (buttons == null) {
                            buttons = new ArrayList<>();
                        }
                        String button_name = ButtonOnCordinates(button);
                        if (button_name != null) {
                            Material item = Material.matchMaterial(args[1]);

                            if (item != null) {
                                int count = Integer.parseInt(args[2]);
                                if (count > 0 && count <= 64) {
                                    AddButtonReward(button_name, item, count);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(buttons);
                                    WriteFile(json);
                                    p.sendMessage("Odměna na tlačítko byla nastavena");
                                } else {
                                    p.sendMessage("Počet itemů musí být v rozmezí 1-64");
                                }
                            } else {
                                p.sendMessage("Název itemu byl chybný");
                            }
                        } else {
                            p.sendMessage("Toto tlačítko nebylo nalezeno");
                        }
                    } else {
                        p.sendMessage("Pro přidání odměny za kliknutí musíš mířit na vytvořené tlačítko");
                    }
                } else {
                    p.sendMessage("Tohoto příkazu nejsi hoden");
                }

            } else {
                Help(p);
            }
        } else {
            System.out.println("fg: Tento příkaz může vyvolat pouze hráč");
        }
        return true;
    }

    public void Help(Player p) {
        p.sendMessage(ChatColor.YELLOW + "----------------fg - Příkazy-----------------");
        p.sendMessage(ChatColor.AQUA + "/fg create name - vytvoření Event tlačítka");
        p.sendMessage(ChatColor.AQUA + "/fg reward item_name pocet - přidání odměny na tlačítko");
        /*p.sendMessage(ChatColor.AQUA + "/fg rewclear - smazání odměn tlačítka");
        p.sendMessage(ChatColor.AQUA + "/fg eclear - smazání event tlačítka");*/
    }

    public void WriteFile(String json) {
        try {
            FileWriter myWriter = new FileWriter("plugins/fg/buttons.json");
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("FG: An error occurred.");
            e.printStackTrace();
        }
    }

    public void GetButtons() {
        File file = new File("plugins/fg/buttons.json");
        boolean empty = !file.exists() || file.length() == 0;
        if (!empty) {
            Gson gson = new Gson();

            try {
                buttons = gson.fromJson(new FileReader("plugins/fg/buttons.json"), new TypeToken<ArrayList<Button>>() {
                }.getType());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existButton(String[] args) {
        int counter = 0;
        for (Button button : buttons) {
            if (button.Name.equals(args[1])) {
                counter++;
            }
        }

        return counter != 0;
    }

    public String ButtonOnCordinates(Button button) {

        int x = button.X;
        int y = button.Y;
        int z = button.Z;

        for (Button value : buttons) {
            if (value.X.equals(x) && value.Y.equals(y) && value.Z.equals(z)) {
                return value.Name;
            }
        }
        return null;
    }

    public void AddButtonReward(String name, Material item, Integer count) {
        for (Button button : buttons) {
            if (button.Name.equals(name)) {
                button.AddItem(item, count);
            }
        }
    }

}