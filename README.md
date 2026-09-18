# Voxen Combat

Minecraft **1.21 exactly** Fabric RPG combat expansion. It requires **Java 21** and targets Fabric Loader 0.15.11 with Fabric API 0.100.8+1.21.

## Included foundation

- Persistent, validated player RPG stats: strength, defense, crit chance, crit damage, and lifesteal.
- `voxencombat:demon_blade`, with centralized weapon values.
- Server-authoritative normal attacks and Infernal Slash area skill (press `R` while holding the blade).
- Centralized damage calculation with defense, critical hits, and lifesteal.
- Per-player, tick-time cooldown tracking and server-to-client stats/cooldown synchronization.
- A small client HUD. Common initialization has no client-only imports, so dedicated servers do not load rendering code.

## Layout

- `combat/` — damage context, calculator, and attack event integration.
- `stats/` and `mixin/` — validated stat model and player NBT persistence.
- `item/weapons/` — Demon Blade and immutable weapon values.
- `skill/` — reusable skill and cooldown abstractions.
- `network/` — validated action request and stats synchronization payloads.
- `src/client/` — HUD and input only.

## Build

```bash
gradle clean build
```

This repository intentionally does not include the Gradle wrapper JAR. Install Gradle locally and use the system `gradle` command above.
