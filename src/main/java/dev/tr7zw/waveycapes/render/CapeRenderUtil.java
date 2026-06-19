package dev.tr7zw.waveycapes.render;

import com.mojang.blaze3d.vertex.*;
import dev.tr7zw.transition.mc.entitywrapper.*;
import dev.tr7zw.waveycapes.support.*;
import net.minecraft.client.renderer.feature.*;
import net.minecraft.client.renderer.rendertype.*;

public final class CapeRenderUtil {
    public static final CapeRenderUtil INSTANCE = new CapeRenderUtil();

    private final CustomCapeRenderer capeRenderer = new CustomCapeRenderer();

    private CapeRenderUtil() {
    }

    public void renderCape(PlayerWrapper capeRenderInfo, CapeNode node, BufferHolder bufferHolder, float delta) {
            PoseStack sharedStack = new PoseStack();
            sharedStack.last().set(node.pose());
            capeRenderer.render(capeRenderInfo, node.capeInfos().capeRenderer(), createConsumer(node, bufferHolder), sharedStack, node.packedLight(), delta);
    }

    private VertexConsumer createConsumer(CapeNode node, BufferHolder holder) {
        if (node.capeInfos().isGlint()) {
            return ItemFeatureRenderer.getFoilBuffer(holder.multiBufferSource(),
                    node.capeInfos().renderType(), false,
                    true);
        } else {
            return holder.createVertexConsumer().apply(node.capeInfos().renderType());
        }
    }

}
