package xyz.voxen.combat.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import xyz.voxen.combat.VoxenCombat;

public record StatsPayload(double strength, double defense, double critChance, double critDamage, double lifesteal, int infernalSlashTicks) implements CustomPayload {
    public static final Id<StatsPayload> ID = new Id<>(VoxenCombat.id("stats"));
    public static final PacketCodec<RegistryByteBuf, StatsPayload> CODEC = PacketCodec.of(
            (payload, buf) -> { buf.writeDouble(payload.strength); buf.writeDouble(payload.defense); buf.writeDouble(payload.critChance);
                buf.writeDouble(payload.critDamage); buf.writeDouble(payload.lifesteal); buf.writeVarInt(payload.infernalSlashTicks); },
            buf -> new StatsPayload(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt()));
    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
