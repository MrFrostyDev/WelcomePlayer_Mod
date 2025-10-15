package xyz.mrfrostydev.welcomeplayer.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

@OnlyIn(Dist.CLIENT)
public class ModArmPoses {
    public static final EnumProxy<HumanoidModel.ArmPose> ONE_HAND_PISTOL_ENUM_PROXY = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            OneHandedPistolArmPose.INSTANCE
    );

    public static class OneHandedPistolArmPose implements IArmPoseTransformer{
        public static final IArmPoseTransformer INSTANCE = new OneHandedPistolArmPose();

        @Override
        public void applyTransform(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
            boolean isRightHanded = arm == HumanoidArm.RIGHT;

            ModelPart usingHandPart = isRightHanded ? model.rightArm : model.leftArm;
            usingHandPart.yRot = (isRightHanded ? -0.1F : 0.1F) + model.head.yRot;
            usingHandPart.xRot = (float) (-Math.PI / 2) + model.head.xRot + 0.1F;
        }
    }
}
