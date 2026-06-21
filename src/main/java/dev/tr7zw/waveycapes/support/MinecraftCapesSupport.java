package dev.tr7zw.waveycapes.support;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.VertexConsumer;

import dev.tr7zw.transition.mc.entitywrapper.PlayerWrapper;
import dev.tr7zw.waveycapes.render.*;
import dev.tr7zw.waveycapes.versionless.ModBase;
//? if >= 1.21.11 {

import net.minecraft.client.renderer.rendertype.*;
//? } else {

/*import net.minecraft.client.renderer.*;
*///? }
import net.minecraftcapes.config.MinecraftCapesConfig;
import net.minecraftcapes.player.PlayerHandler;

public class MinecraftCapesSupport implements ModSupport {

    private MinecraftCapesRenderer render = new MinecraftCapesRenderer();
    private Function<PlayerWrapper, PlayerHandler> getCape = null;

    private void init(PlayerWrapper test) {
        try {
            getCape = player -> {
                //? if >= 1.21.9 {

                var entity = player.getAvatar();
                //? } else {

                /*var entity = player.getEntity();
                *///? }
                PlayerHandler.get(entity.getUUID()).getCapeLocation();
                return PlayerHandler.get(entity.getUUID());
            };
            getCape.apply(test);
            ModBase.LOGGER.info("Using 'get(UUID)' method for MinecraftCapes.");
            return;
        } catch (Throwable ex) {
            // ignore
        }

        //? if < 1.21.2 {

        /*for (java.lang.reflect.Method m : PlayerHandler.class.getMethods()) {
           try {
               if (m.getReturnType() != PlayerHandler.class && m.getParameterCount() == 1
                       && m.getParameterTypes()[0] != java.util.UUID.class) {
                   continue;
               }
               m.invoke(null, test);
               getCape = player -> {
                   try {
                       return (PlayerHandler) m.invoke(null, player);
                   } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                       return null;
                   }
               };
               ModBase.LOGGER.info("Using '" + m.getName() + "' method for MinecraftCapes.");
               return;
           } catch (Throwable ex) {
               // ignore, MinecraftCapes wont work
           }
        }
        *///? }
        getCape = player -> null;
        ModBase.LOGGER.info("Unable to find a method for MinecraftCapes.");
    }

    @Override
    public boolean shouldBeUsed(PlayerWrapper capeRenderInfo) {
        if (!MinecraftCapesConfig.isCapeVisible())
            return false;
        if (getCape == null)
            init(capeRenderInfo);
        PlayerHandler handler = getCape.apply(capeRenderInfo);
        return handler != null && handler.getCapeLocation() != null;
    }

    @Override
    public CapeRenderer getRenderer() {
        return render;
    }

    private class MinecraftCapesRenderer implements CapeRenderer {

        @Override
        public CapeInfos getCapeInfo(PlayerWrapper capeRenderInfo) {
            PlayerHandler playerHandler = getCape.apply(capeRenderInfo);
            if (MinecraftCapesConfig.isCapeVisible() && playerHandler.getCapeLocation() != null) {
                //? if >= 1.21.11 {

                return new CapeInfos(this, RenderTypes.entityTranslucent(playerHandler.getCapeLocation()),
                        playerHandler.getHasCapeGlint());
                //? } else  {

                /*return new CapeInfos(this, RenderTypes.entityTranslucent(playerHandler.getCapeLocation()), playerHandler.getHasCapeGlint());
                *///? }
            } else {
                //? if >= 1.21.11 {

                return new CapeInfos(this, RenderTypes.entityTranslucent(capeRenderInfo.getCapeTexture()),
                        playerHandler.getHasCapeGlint());
                //? } else {

                /*return new CapeInfos(this, RenderTypes.entityTranslucent(capeRenderInfo.getCapeTexture()), playerHandler.getHasCapeGlint());
                *///? }
            }
        }

    }

    @Override
    public boolean blockFeatureRenderer(Object feature) {
        return false;
    }

}
