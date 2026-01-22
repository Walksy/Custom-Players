package walksy.customplayers.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.customplayers.config.Config;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public abstract class PlayerHeldItemFeatureRendererMixin<S extends PlayerEntityRenderState, M extends EntityModel<S> & ModelWithArms & ModelWithHead> extends HeldItemFeatureRenderer<S, M> {

    public PlayerHeldItemFeatureRendererMixin(FeatureRendererContext<S, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Inject(method = "renderItem(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V", at = @At("HEAD"), cancellable = true)
    public void renderItem(PlayerEntityRenderState state, ItemRenderState itemState, ItemStack stack, Arm arm, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CallbackInfo ci) {
        Config.ModelEntry entry = Config.getEntry(state.entityType);
        if (Config.modEnabled && ((arm == Arm.LEFT && !entry.showLeftHand) || (arm == Arm.RIGHT && !entry.showRightHand))) {
            ci.cancel();
        }
    }
}
