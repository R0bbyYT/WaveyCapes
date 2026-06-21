package dev.tr7zw.waveycapes.render;

import dev.tr7zw.transition.mc.entitywrapper.PlayerWrapper;
//? if >= 1.21.11 {

import net.minecraft.client.renderer.rendertype.*;
//? } else {

/*import net.minecraft.client.renderer.*;
*///? }

public class VanillaCapeRenderer implements CapeRenderer {

    @Override
    public CapeInfos getCapeInfo(PlayerWrapper capeRenderInfo) {
        var cape = capeRenderInfo.getCapeTexture();
        if (cape != null) {
            return new CapeInfos(this, RenderTypes.entityTranslucent(cape), false);
        }
        return null;
    }

    @Override
    public boolean vanillaUvValues() {
        return true;
    }

}
