package dev.tr7zw.waveycapes.render;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.entity.state.*;

public record CapeNode(AvatarRenderState state, CapeInfos capeInfos, PoseStack.Pose pose, int packedLight) {
}