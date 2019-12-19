package mcjty.lostcities.varia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.ItemStackDataFlattening;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tools {

    public static BlockState stringToState(String s) {
        // @todo 1.14, support properties
        if (s.contains("@")) {
            // Temporary fix to just remove the meta to get things rolling
            String[] split = s.split("@");
            String converted = ItemStackDataFlattening.updateItem(split[0], Integer.parseInt(split[1]));
            if (converted != null) {
                s = converted;
            } else {
                s = split[0];
            }
        } else {
            String converted = ItemStackDataFlattening.updateItem(s, 0);
            if (converted != null) {
                s = converted;
            }
        }
        Block value = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
        if (value == null) {
            throw new RuntimeException("Cannot find block: '" + s + "'!");
        }
        return value.getDefaultState();
    }

    public static String stateToString(BlockState state) {
        // @todo 1.14
        return state.getBlock().getRegistryName().toString();
    }

    public static String getRandomFromList(Random random, List<Pair<Float, String>> list) {
        if (list.isEmpty()) {
            return null;
        }
        List<Pair<Float, String>> elements = new ArrayList<>();
        float totalweight = 0;
        for (Pair<Float, String> pair : list) {
            elements.add(pair);
            totalweight += pair.getKey();
        }
        float r = random.nextFloat() * totalweight;
        for (Pair<Float, String> pair : elements) {
            r -= pair.getKey();
            if (r <= 0) {
                return pair.getRight();
            }
        }
        return null;
    }
}
