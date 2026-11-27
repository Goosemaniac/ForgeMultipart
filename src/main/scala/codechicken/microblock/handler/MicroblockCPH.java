package codechicken.microblock.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class MicroblockCPH {

    private MicroblockCPH() {}

    public static String registryChannel() {
        return MicroblockCPH$.MODULE$.registryChannel();
    }

    @SideOnly(Side.CLIENT)
    public static void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient netHandler) {
        MicroblockCPH$.MODULE$.handlePacket(packet, minecraft, netHandler);
    }

    public static void handleMaterialRegistration(PacketCustom packet, INetHandlerPlayClient netHandler) {
        MicroblockCPH$.MODULE$.handleMaterialRegistration(packet, netHandler);
    }
}
