package dev.tr7zw.waveycapes.render;

//? if >= 1.21.11 {

import net.minecraft.client.renderer.rendertype.*;
//? } else {

/*import net.minecraft.client.renderer.*;
*///? }

public record CapeInfos(CapeRenderer capeRenderer, RenderType renderType, boolean isGlint) {
}
