package net.eveld.currendcy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.eveld.currendcy.block.entity.DisplayEntityRenderer;
import net.eveld.currendcy.gui.CollectorScreen;

@Environment(EnvType.CLIENT)
public class CurrendcyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(Currendcy.DISPLAY_ENTITY, DisplayEntityRenderer::new);

        ScreenRegistry.register(Currendcy.COLLECTOR_SCREEN_HANDLER, CollectorScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(Currendcy.DISPLAY, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Currendcy.DISPENSER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Currendcy.COLLECTOR, RenderLayer.getTranslucent());
    }
}