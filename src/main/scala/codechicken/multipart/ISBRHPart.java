package codechicken.multipart;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Optional client render hook for parts that draw through an {@code ISimpleBlockRenderingHandler} instead of
 * {@link TMultiPart#renderStatic}. The method is side-only so a dedicated server never resolves {@code RenderBlocks};
 * implementations keep the method on both sides as an ordinary extra method.
 */
public interface ISBRHPart {

    @SideOnly(Side.CLIENT)
    boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, RenderBlocks renderer);
}
