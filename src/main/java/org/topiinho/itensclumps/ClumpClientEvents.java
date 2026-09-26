package org.topiinho.itensclumps;

import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = ItensClumps.MODID, value = Dist.CLIENT)
public class ClumpClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CLUMP_ENTITY.get(), ItemEntityRenderer::new);
    }
}
