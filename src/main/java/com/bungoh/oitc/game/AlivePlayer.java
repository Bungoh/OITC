package com.bungoh.oitc.game;

import com.bungoh.oitc.OITC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class AlivePlayer extends GameParticipant {

    private AlivePlayerListener listener;

    public AlivePlayer(Game game, Player player) {
        super(game, player);
        init();
    }

    public void init() {
        //Register Listener
        listener = new AlivePlayerListener();
        Bukkit.getPluginManager().registerEvents(listener, OITC.getPlugin());

        //Give Player Items
        ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack crossBow = new ItemStack(Material.CROSSBOW);
        ItemStack arrow = new ItemStack(Material.ARROW);

        //Add Items to Inventory
        player.getInventory().addItem(sword, crossBow, arrow);
    }

    @Override
    public void cleanup() {
        //Cleanup Listener
        HandlerList.unregisterAll(listener);
    }

    class AlivePlayerListener implements Listener {

        @EventHandler
        public void onPlayerItemDrop(PlayerDropItemEvent e) {
            e.setCancelled(true);
        }

        @EventHandler
        public void onPlayerKill(EntityDeathEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }

            Player victim = (Player) e.getEntity();

            if (!victim.equals(player)) {
                return;
            }

            Player killer = victim.getKiller();

            game.addKill(game.getAlivePlayer(killer));

            game.getArena().sendMessage(killer.getName() + ChatColor.RED + " killed " + ChatColor.WHITE + victim.getName());
        }

        @EventHandler
        public void onArrowHitPlayer(EntityDamageByEntityEvent e) {
            if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Projectile)) {
                return;
            }

            Player hit = (Player) e.getEntity();
            Projectile hitter = (Projectile) e.getDamager();

            if (hitter.getType() != EntityType.ARROW) {
                return;
            }

            e.setDamage(hit.getHealth());
        }

        @EventHandler
        public void onPlayerDeathEvent(PlayerDeathEvent e) {
            e.setKeepInventory(true);
            e.setDeathMessage("");
            e.getDrops().clear();
        }

        @EventHandler
        public void onRespawn(PlayerRespawnEvent e) {
            if (!e.getPlayer().equals(player)) {
                return;
            }

            e.setRespawnLocation(game
                    .getArena()
                    .getSpawnLocations()
                    .get((int) (Math.random() * game.getArena().getSpawnLocations().size())));
        }

    }
}
