package codechicken.multipart.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/**
 * Reproduces the dedicated server crash that WarpDrive triggered on 1.7.10: it called {@code getDeclaredMethod} on
 * ForgeMultipart's tile, which makes the JVM resolve every declared descriptor of that class, including the parameter
 * types of methods it is not calling. A single client-only type in such a descriptor (for example
 * {@code net.minecraft.client.renderer.RenderBlocks}) drags in a class that FML's side transformer refuses to load on a
 * server and the whole class fails with {@code NoClassDefFoundError}.
 * <p>
 * The list below is the API surface other mods are known to reflect on. It must never contain a client-only signature
 * on the server, so this test fails if a client render or particle hook loses its {@code @SideOnly(Side.CLIENT)}.
 */
class ServerSignatureResolutionFunctionalTest {

    private static final String[] SERVER_LOADED_TYPES = {
            "codechicken.multipart.TileMultipart",
            "codechicken.multipart.TileMultipart$",
            "codechicken.multipart.TileMultipartClient",
            "codechicken.multipart.BlockMultipart",
            "codechicken.multipart.BlockMultipart$",
            "codechicken.multipart.TMultiPart",
            "codechicken.multipart.IconHitEffects",
            "codechicken.multipart.ISBRHPart",
            "codechicken.microblock.Microblock",
            "codechicken.microblock.ItemMicroPart",
            "codechicken.microblock.MicroMaterialRegistry",
            "codechicken.microblock.handler.MicroblockProxy",
            "codechicken.microblock.handler.MicroblockProxy$",
            "codechicken.microblock.handler.MicroblockProxy_clientImpl",
            "codechicken.multipart.minecraft.LeverPart",
            "codechicken.multipart.minecraft.McBlockPart" };

    @Test
    void everyServerLoadedApiClassResolvesItsDeclaredSignatures() throws Exception {
        for (String name : SERVER_LOADED_TYPES) {
            Class<?> type = Class.forName(name);
            assertDoesNotThrow(type::getDeclaredMethods, name + " declares a client-only method signature");
            assertDoesNotThrow(type::getDeclaredFields, name + " declares a client-only field type");
            for (Method method : type.getDeclaredMethods()) {
                assertDoesNotThrow(
                        method::getParameterTypes,
                        name + "." + method.getName() + " leaves a client-only parameter type unresolved");
                assertDoesNotThrow(
                        method::getReturnType,
                        name + "." + method.getName() + " leaves a client-only return type unresolved");
            }
            for (Field field : type.getDeclaredFields()) {
                assertDoesNotThrow(
                        field::getType,
                        name + "." + field.getName() + " leaves a client-only field type unresolved");
            }
        }
    }
}
