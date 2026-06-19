package dev.tr7zw.waveycapes.render;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.rendertype.*;

import java.util.function.*;

public record BufferHolder(
        Function<RenderType, VertexConsumer> createVertexConsumer
        //? if < 26.2 {
        /*
        ,net.minecraft.client.renderer.MultiBufferSource multiBufferSource
        *///? }

) {
}
