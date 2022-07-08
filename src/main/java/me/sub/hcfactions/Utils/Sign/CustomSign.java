package me.sub.hcfactions.Utils.Sign;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CustomSign implements Sign {

    Sign sign;

    public CustomSign(Sign s) {
        this.sign = s;
    }

    @Override
    public String[] getLines() {
        return sign.getLines();
    }

    @Override
    public String getLine(int i) throws IndexOutOfBoundsException {
        return sign.getLine(i);
    }

    @Override
    public void setLine(int i, String s) throws IndexOutOfBoundsException {
        sign.setLine(i, s);
    }

    @Override
    public Block getBlock() {
        return sign.getBlock();
    }

    @Override
    public MaterialData getData() {
        return sign.getData();
    }

    @Override
    public Material getType() {
        return sign.getType();
    }

    @Override
    public int getTypeId() {
        return sign.getTypeId();
    }

    @Override
    public byte getLightLevel() {
        return sign.getLightLevel();
    }

    @Override
    public World getWorld() {
        return sign.getWorld();
    }

    @Override
    public int getX() {
        return sign.getX();
    }

    @Override
    public int getY() {
        return sign.getY();
    }

    @Override
    public int getZ() {
        return sign.getZ();
    }

    @Override
    public Location getLocation() {
        return sign.getLocation();
    }

    @Override
    public Location getLocation(Location location) {
        return null;
    }

    @Override
    public Chunk getChunk() {
        return sign.getChunk();
    }

    @Override
    public void setData(MaterialData materialData) {
        sign.setData(materialData);
    }

    @Override
    public void setType(Material material) {
        sign.setType(material);
    }

    @Override
    public boolean setTypeId(int i) {
        return sign.setTypeId(i);
    }

    @Override
    public boolean update() {
        return sign.update();
    }

    @Override
    public boolean update(boolean b) {
        return sign.update(b);
    }

    @Override
    public boolean update(boolean b, boolean b1) {
        return sign.update(b, b1);
    }

    @Override
    public byte getRawData() {
        return sign.getRawData();
    }

    @Override
    public void setRawData(byte b) {
        sign.setRawData(b);
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {
        sign.setMetadata(s, metadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return sign.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(String s) {
        return sign.hasMetadata(s);
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        sign.removeMetadata(s, plugin);
    }
}
