package de.wwiser.itemfilter.shared;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@UtilityClass
public class ItemStackSerDe {

    public byte[] serializeItemStacks(ItemStack[] itemStacks) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(outputStream);

        bukkitOutputStream.writeObject(itemStacks);

        return outputStream.toByteArray();
    }

    public ItemStack[] deserializeItemStacks(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes.length == 0) {
            return null;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream);

        return (ItemStack[]) bukkitInputStream.readObject();
    }

}
