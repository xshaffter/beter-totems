package paraformax.bettertotems.items.totems.boss_totems;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import paraformax.bettertotems.items.totems.PerfectTotem;
import paraformax.bettertotems.util.PlayerEntityBridge;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ThunderTotem extends PerfectTotem {

    public ThunderTotem() {
        super(Arrays.asList(
                //hold-buffs
        ), Arrays.asList(
                //hold-debuffs
        ), ImmutableMultimap.of(
                //hold-attributes
        ));

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip.shift"));
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip.shift2"));
        } else {
            tooltip.add(Text.translatable("item.better-totems.tank_totem.tooltip"));
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void performResurrection(Entity entity) {
        LivingEntity resurrected = (LivingEntity) entity;
        resurrected.setHealth(1.0f);
        resurrected.clearStatusEffects();
        resurrected.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 90));

        if (resurrected instanceof ServerPlayerEntity player) {
            var CUSTOM_TRIDENT = new ItemStack(Items.TRIDENT);

            Objects.requireNonNull(player.getServer()).getOverworld().setWeather(0,Integer.MAX_VALUE, true, true);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20 * 15, 10));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 10, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 5, 5));

            if (player.getScoreboardTeam() != null && player.getScoreboardTeam().getName().equals("MOB_NPC")) {
                var healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                assert healthAttribute != null;
                healthAttribute.clearModifiers();
                healthAttribute.addTemporaryModifier(new EntityAttributeModifier("NEW_PHASE_MODIFIER", 20, EntityAttributeModifier.Operation.ADDITION));
            }

            renderArea(player, 5, 10);

            CUSTOM_TRIDENT.addEnchantment(Enchantments.CHANNELING, 1);
            CUSTOM_TRIDENT.addEnchantment(Enchantments.LOYALTY, 3);
            CUSTOM_TRIDENT.setCustomName(Text.literal("TRUENO"));
            CUSTOM_TRIDENT.setDamage(247);

            player.giveItemStack(CUSTOM_TRIDENT);
        }
        super.performResurrection(entity);
    }

    @SuppressWarnings("SameParameterValue")
    private void renderArea(ServerPlayerEntity player, int radius, int thunders) {

        var center = player.getBlockPos();
        var random = new Random();
        for (int i = 0; i < thunders; i++) {
            int xFactor = random.nextInt(-radius, radius + 1);
            int zFactor = random.nextInt(-radius, radius + 1);
            var thunderPos = new BlockPos(center.getX() + xFactor, center.getY(), center.getZ() + zFactor);
            summonLightning(thunderPos, player.world);
        }

    }

    private void summonLightning(BlockPos blockPos, World world) {
        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
        assert lightningEntity != null;
        lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
        world.spawnEntity(lightningEntity);

    }
}
