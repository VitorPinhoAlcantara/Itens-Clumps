package org.topiinho.itensclumps;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ItensClumps.MODID)
public class ItensClumps {
    public static final String MODID = "itens_clumps";

    public ItensClumps(IEventBus modEventBus) {
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
    }
}
