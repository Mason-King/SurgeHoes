package net.skysurge.Gui;

import net.skysurge.Main;
import net.skysurge.Utils.ChatUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaGui {

    private Main main;

    public CaptchaGui(Main main) {
        this.main = main;

    }

    private boolean canClose;
    private int untilKick = 3;

    public void makeGui(Player p) {
        Gui gui = new Gui(main);
        Gui.NoobPage mainPage = gui.create(ChatUtils.color("&lClick the Green Glass Pane"), 27);

        mainPage.noClick();
        mainPage.noShift();

        Material[] materials = Material.values();
        this.canClose = false;

        int randSlot = ThreadLocalRandom.current().nextInt(27);

        ItemStack correct = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        mainPage.i(randSlot, correct);

        mainPage.f(Material.GRAY_STAINED_GLASS_PANE);

        gui.show(p, 0);

        mainPage.onClose(e ->{
            if(!canClose) {
                p.kickPlayer(ChatUtils.color("&f&lSkySurged &7| Kicked for failing to complete captcha"));
            }
        });

        mainPage.onClick(e -> {
            int slot = e.getSlot();
            if(!e.getCurrentItem().getType().equals(null) && e.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                canClose = true;
                p.closeInventory();
            } else {
                untilKick--;
                if(untilKick == 0) {
                    p.kickPlayer(ChatUtils.color("&f&lSkySurged &7| Kicked for failing captcha too many times"));
                }
            }
        });

    }

    public ItemStack makeItemStack(Material material, String name, NamespacedKey upgradeKey, String... lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(ChatUtils.color(name));

        //add lore here
        List<String> loreList = new ArrayList<>();
        for(String s : lore) {
            loreList.add(ChatUtils.color(s));
        }

        itemMeta.setLore(loreList);

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(new NamespacedKey(main, "upgrade"), PersistentDataType.STRING, upgradeKey.getKey().toString());


        stack.setItemMeta(itemMeta);

        return stack;
    }


}
