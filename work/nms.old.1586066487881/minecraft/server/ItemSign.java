package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemSign extends ItemBlockWallable {

    public static BlockPosition openSign; // CraftBukkit

    public ItemSign(Item.Info item_info, Block block, Block block1) {
        super(block, block1, item_info);
    }

    @Override
    protected boolean a(BlockPosition blockposition, World world, @Nullable EntityHuman entityhuman, ItemStack itemstack, IBlockData iblockdata) {
        boolean flag = super.a(blockposition, world, entityhuman, itemstack, iblockdata);

        if (!world.isClientSide && !flag && entityhuman != null) {
            // CraftBukkit start - SPIGOT-4678
            // entityhuman.openSign((TileEntitySign) world.getTileEntity(blockposition));
            ItemSign.openSign = blockposition;
            // CraftBukkit end
        }

        return flag;
    }
}
