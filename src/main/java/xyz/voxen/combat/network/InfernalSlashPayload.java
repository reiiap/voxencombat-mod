package xyz.voxen.combat.network;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import xyz.voxen.combat.VoxenCombat;

/** Empty client request; the server independently validates weapon, cooldown, and targets. */
public record InfernalSlashPayload() implements CustomPayload {
    public static final Id<InfernalSlashPayload> ID = new Id<>(VoxenCombat.id("infernal_slash"));
    public static final PacketCodec<net.minecraft.network.RegistryByteBuf, InfernalSlashPayload> CODEC = PacketCodec.unit(new InfernalSlashPayload());
    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
