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

package com.sk89q.worldguard.internal.permission;

import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.totalfreedom.worldguard.WorldGuardHandler;

import javax.annotation.Nullable;

/**
 * Used for querying region-related permissions.
 */
public class RegionPermissionModel extends AbstractPermissionModel {

    public RegionPermissionModel(Actor sender) {
        super(sender);
    }

    public boolean mayIgnoreRegionProtection(World world) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayIgnoreRegionProtection(ProtectedRegion region) {
        return region.isOwner((LocalPlayer) getSender()) || region.isMember((LocalPlayer) getSender()) || WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayForceLoadRegions() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayForceSaveRegions() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayMigrateRegionStore() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayMigrateRegionNames() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayDefine() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayRedefine(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayClaim() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayClaimRegionsUnbounded() {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayDelete(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean maySetPriority(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean maySetParent(ProtectedRegion child, ProtectedRegion parent) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean maySelect(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayLookup(ProtectedRegion region) {
        return true;
    }

    public boolean mayTeleportTo(ProtectedRegion region) {
        return true;
    }

    public boolean mayOverrideLocationFlagBounds(ProtectedRegion region) {
        return region.isOwner((LocalPlayer) getSender()) || region.isMember((LocalPlayer) getSender()) || WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayList() {
        return true;
    }

    public boolean mayList(String targetPlayer) {
        if (targetPlayer == null) {
            return mayList();
        }

        if (targetPlayer.equalsIgnoreCase(getSender().getName())) {
            return true;
        } else {
            return mayList();
        }
    }

    public boolean maySetFlag(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean maySetFlag(ProtectedRegion region, Flag<?> flag) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean maySetFlag(ProtectedRegion region, Flag<?> flag, @Nullable String value) {
        String sanitizedValue;

        if (value != null) {
            sanitizedValue = value.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
            if (sanitizedValue.length() > 20) {
                sanitizedValue = sanitizedValue.substring(0, 20);
            }
        } else {
            sanitizedValue = "unset";
        }

        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayAddMembers(ProtectedRegion region) {
        return region.isOwner((LocalPlayer) getSender()) || WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayAddOwners(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayRemoveMembers(ProtectedRegion region) {
        return region.isOwner((LocalPlayer) getSender()) || WorldGuardHandler.isAdmin((Player) getSender());
    }

    public boolean mayRemoveOwners(ProtectedRegion region) {
        return WorldGuardHandler.isAdmin((Player) getSender());
    }

    /**
     * Checks to see if the given sender has permission to modify the given region
     * using the region permission pattern.
     *
     * @param perm the name of the node
     * @param region the region
     */
    private boolean hasPatternPermission(String perm, ProtectedRegion region) {
        if (!(getSender() instanceof Player)) {
            return true; // Non-players (i.e. console, command blocks, etc.) have full power
        }

        String idLower = region.getId().toLowerCase();
        String effectivePerm;

        return region.isOwner((LocalPlayer) getSender()) || region.isMember((LocalPlayer) getSender()) || WorldGuardHandler.isAdmin((Player) getSender());
    }

}