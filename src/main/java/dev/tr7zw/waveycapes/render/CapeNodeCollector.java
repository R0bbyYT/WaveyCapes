package dev.tr7zw.waveycapes.render;

//? if >= 1.21.9 {

import com.mojang.blaze3d.vertex.*;
import dev.tr7zw.transition.mc.entitywrapper.*;
import dev.tr7zw.waveycapes.*;
import dev.tr7zw.waveycapes.support.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.state.*;
import net.minecraft.client.renderer.rendertype.*;

public class CapeNodeCollector {

    private final VanillaCapeRenderer vanillaCape = new VanillaCapeRenderer();

    public void submitCape(SubmitNodeCollector submitNodeCollector, AvatarRenderState state, PoseStack stack, int packedLight, float delta) {
        var playerWrapper = new PlayerWrapper(state);
        var renderer = getCapeRenderer(playerWrapper);
        if (renderer == null) {
            return;
        }
        var capeInfo = renderer.getCapeInfo(playerWrapper);
        if (capeInfo == null) {
            return;
        }
        if (capeInfo.isGlint()) {
            submitNodeCollector.submitCustomGeometry(stack, RenderTypes.entityGlint(), (pose, vertexConsumer) -> {
                PoseStack sharedStack = new PoseStack();
                sharedStack.last().set(pose);
                WaveyCapesBase.getINSTANCE().getRenderer().render(playerWrapper, renderer, vertexConsumer, sharedStack, packedLight, delta);
            });
        }

        submitNodeCollector.submitCustomGeometry(stack, capeInfo.renderType(), (pose, vertexConsumer) -> {
            PoseStack sharedStack = new PoseStack();
            sharedStack.last().set(pose);
            WaveyCapesBase.getINSTANCE().getRenderer().render(playerWrapper, renderer, vertexConsumer, sharedStack, packedLight, delta);
        });
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
