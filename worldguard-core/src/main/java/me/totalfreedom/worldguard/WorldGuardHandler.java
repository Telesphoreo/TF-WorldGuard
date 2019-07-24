/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package me.totalfreedom.worldguard;

import com.google.common.base.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Logger;

public class WorldGuardHandler {

    public static final boolean DEBUG = true;
    public static final Logger LOGGER = Bukkit.getPluginManager().getPlugin("WorldGuard").getLogger();
    private static Function<Player, Boolean> adminProvider;

    @SuppressWarnings("unchecked")
    public static boolean isAdmin(com.sk89q.worldedit.entity.Player wePlayer) {
        final Player player = getPlayer(wePlayer);
        if (player == null) {
            return false;
        }

        if (adminProvider == null) {
            final Plugin tfm = getTFM();
            if (tfm == null) {
                return false;
            }

            Object provider = null;
            for (RegisteredServiceProvider<?> serv : Bukkit.getServicesManager().getRegistrations(tfm)) {
                if (Function.class.isAssignableFrom(serv.getService())) {
                    provider = serv.getProvider();
                }
            }

            if (provider == null) {
                warning("Could not obtain admin service provider!");
                return false;
            }

            adminProvider = (Function<Player, Boolean>) provider;
        }

        return adminProvider.apply(player);
    }

    @SuppressWarnings("unchecked")
    public static boolean isAdmin(String wePlayer) {
        final Player player = getPlayer(wePlayer);
        if (player == null) {
            return false;
        }

        if (adminProvider == null) {
            final Plugin tfm = getTFM();
            if (tfm == null) {
                return false;
            }

            Object provider = null;
            for (RegisteredServiceProvider<?> serv : Bukkit.getServicesManager().getRegistrations(tfm)) {
                if (Function.class.isAssignableFrom(serv.getService())) {
                    provider = serv.getProvider();
                }
            }

            if (provider == null) {
                warning("Could not obtain admin service provider!");
                return false;
            }

            adminProvider = (Function<Player, Boolean>) provider;
        }

        return adminProvider.apply(player);
    }

    public static Player getPlayer(com.sk89q.worldedit.entity.Player wePlayer) {
        final Player player = Bukkit.getPlayer(wePlayer.getUniqueId());

        if (player == null) {
            debug("Could not resolve Bukkit player: " + wePlayer.getName());
            return null;
        }

        return player;
    }

    public static Player getPlayer(String match) {
        match = match.toLowerCase();

        Player found = null;
        int delta = Integer.MAX_VALUE;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(match)) {
                int curDelta = player.getName().length() - match.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().contains(match)) {
                return player;
            }
        }
        return found;
    }

    public static Plugin getTFM() {
        final Plugin tfm = Bukkit.getPluginManager().getPlugin("TotalFreedomMod");
        if (tfm == null) {
            LOGGER.warning("Could not resolve plugin: TotalFreedomMod");
        }

        return tfm;
    }

    public static void debug(String debug) {
        if (DEBUG) {
            info(debug);
        }
    }

    public static void warning(String warning) {
        LOGGER.warning(warning);
    }

    public static void info(String info) {
        LOGGER.info(info);
    }

}
