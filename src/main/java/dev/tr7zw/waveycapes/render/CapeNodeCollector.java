package dev.tr7zw.waveycapes.render;

//? if >= 1.21.9 {

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tr7zw.transition.mc.entitywrapper.*;
import dev.tr7zw.waveycapes.support.*;
import lombok.Getter;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

import java.util.ArrayList;
import java.util.List;

public class CapeNodeCollector {

    private final VanillaCapeRenderer vanillaCape = new VanillaCapeRenderer();

    @Getter
    private final List<CapeNode> capes = new ArrayList<>();

    public void submitCape(AvatarRenderState state, PoseStack stack, int packedLight) {
        var playerWrapper = new PlayerWrapper(state);
        var renderer = getCapeRenderer(playerWrapper);
        if (renderer == null) {
            return;
        }
        var capeInfo = renderer.getCapeInfo(playerWrapper);
        if (capeInfo == null) {
            return;
        }
        capes.add(new CapeNode(state, capeInfo, stack.last().copy(), packedLight));
    }

    public void clear() {
        capes.clear();
    }

    private CapeRenderer getCapeRenderer(PlayerWrapper capeRenderInfo) {
        for (ModSupport support : SupportManager.getSupportedMods()) {
            if (support.shouldBeUsed(capeRenderInfo)) {
                return support.getRenderer();
            }
        }
        if (capeRenderInfo.getCapeTexture() == null || !capeRenderInfo.isCapeVisible()) {
            return null;
        } else {
            return vanillaCape;
        }
    }


}
//? }
