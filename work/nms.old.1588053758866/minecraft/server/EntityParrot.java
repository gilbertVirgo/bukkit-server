package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class EntityParrot extends EntityPerchable implements EntityBird {

    private static final DataWatcherObject<Integer> bD = DataWatcher.a(EntityParrot.class, DataWatcherRegistry.b);
    private static final Predicate<EntityInsentient> bE = new Predicate<EntityInsentient>() {
        public boolean test(@Nullable EntityInsentient entityinsentient) {
            return entityinsentient != null && EntityParrot.bH.containsKey(entityinsentient.getEntityType());
        }
    };
    private static final Item bF = Items.COOKIE;
    private static final Set<Item> bG = Sets.newHashSet(new Item[]{Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS});
    private static final Map<EntityTypes<?>, SoundEffect> bH = (Map) SystemUtils.a(Maps.newHashMap(), (hashmap) -> { // CraftBukkit - decompile error
        hashmap.put(EntityTypes.BLAZE, SoundEffects.ENTITY_PARROT_IMITATE_BLAZE);
        hashmap.put(EntityTypes.CAVE_SPIDER, SoundEffects.ENTITY_PARROT_IMITATE_SPIDER);
        hashmap.put(EntityTypes.CREEPER, SoundEffects.ENTITY_PARROT_IMITATE_CREEPER);
        hashmap.put(EntityTypes.DROWNED, SoundEffects.ENTITY_PARROT_IMITATE_DROWNED);
        hashmap.put(EntityTypes.ELDER_GUARDIAN, SoundEffects.ENTITY_PARROT_IMITATE_ELDER_GUARDIAN);
        hashmap.put(EntityTypes.ENDER_DRAGON, SoundEffects.ENTITY_PARROT_IMITATE_ENDER_DRAGON);
        hashmap.put(EntityTypes.ENDERMITE, SoundEffects.ENTITY_PARROT_IMITATE_ENDERMITE);
        hashmap.put(EntityTypes.EVOKER, SoundEffects.ENTITY_PARROT_IMITATE_EVOKER);
        hashmap.put(EntityTypes.GHAST, SoundEffects.ENTITY_PARROT_IMITATE_GHAST);
        hashmap.put(EntityTypes.GUARDIAN, SoundEffects.ENTITY_PARROT_IMITATE_GUARDIAN);
        hashmap.put(EntityTypes.HUSK, SoundEffects.ENTITY_PARROT_IMITATE_HUSK);
        hashmap.put(EntityTypes.ILLUSIONER, SoundEffects.ENTITY_PARROT_IMITATE_ILLUSIONER);
        hashmap.put(EntityTypes.MAGMA_CUBE, SoundEffects.ENTITY_PARROT_IMITATE_MAGMA_CUBE);
        hashmap.put(EntityTypes.PHANTOM, SoundEffects.ENTITY_PARROT_IMITATE_PHANTOM);
        hashmap.put(EntityTypes.PILLAGER, SoundEffects.ENTITY_PARROT_IMITATE_PILLAGER);
        hashmap.put(EntityTypes.RAVAGER, SoundEffects.ENTITY_PARROT_IMITATE_RAVAGER);
        hashmap.put(EntityTypes.SHULKER, SoundEffects.ENTITY_PARROT_IMITATE_SHULKER);
        hashmap.put(EntityTypes.SILVERFISH, SoundEffects.ENTITY_PARROT_IMITATE_SILVERFISH);
        hashmap.put(EntityTypes.SKELETON, SoundEffects.ENTITY_PARROT_IMITATE_SKELETON);
        hashmap.put(EntityTypes.SLIME, SoundEffects.ENTITY_PARROT_IMITATE_SLIME);
        hashmap.put(EntityTypes.SPIDER, SoundEffects.ENTITY_PARROT_IMITATE_SPIDER);
        hashmap.put(EntityTypes.STRAY, SoundEffects.ENTITY_PARROT_IMITATE_STRAY);
        hashmap.put(EntityTypes.VEX, SoundEffects.ENTITY_PARROT_IMITATE_VEX);
        hashmap.put(EntityTypes.VINDICATOR, SoundEffects.ENTITY_PARROT_IMITATE_VINDICATOR);
        hashmap.put(EntityTypes.WITCH, SoundEffects.ENTITY_PARROT_IMITATE_WITCH);
        hashmap.put(EntityTypes.WITHER, SoundEffects.ENTITY_PARROT_IMITATE_WITHER);
        hashmap.put(EntityTypes.WITHER_SKELETON, SoundEffects.ENTITY_PARROT_IMITATE_WITHER_SKELETON);
        hashmap.put(EntityTypes.ZOMBIE, SoundEffects.ENTITY_PARROT_IMITATE_ZOMBIE);
        hashmap.put(EntityTypes.ZOMBIE_VILLAGER, SoundEffects.ENTITY_PARROT_IMITATE_ZOMBIE_VILLAGER);
    });
    public float bz;
    public float bA;
    public float bB;
    public float bC;
    private float bI = 1.0F;
    private boolean bJ;
    private BlockPosition bK;

    public EntityParrot(EntityTypes<? extends EntityParrot> entitytypes, World world) {
        super(entitytypes, world);
        this.moveController = new ControllerMoveFlying(this, 10, false);
        this.a(PathType.DANGER_FIRE, -1.0F);
        this.a(PathType.DAMAGE_FIRE, -1.0F);
        this.a(PathType.COCOA, -1.0F);
    }

    @Nullable
    @Override
    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        this.setVariant(this.random.nextInt(5));
        if (groupdataentity == null) {
            groupdataentity = new EntityAgeable.a();
            ((EntityAgeable.a) groupdataentity).a(false);
        }

        return super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, (GroupDataEntity) groupdataentity, nbttagcompound);
    }

    @Override
    protected void initPathfinder() {
        this.goalSit = new PathfinderGoalSit(this);
        this.goalSelector.a(0, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(2, this.goalSit);
        this.goalSelector.a(2, new PathfinderGoalFollowOwner(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.a(2, new PathfinderGoalRandomFly(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalPerch(this));
        this.goalSelector.a(3, new PathfinderGoalFollowEntity(this, 1.0D, 3.0F, 7.0F));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeMap().b(GenericAttributes.FLYING_SPEED);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(6.0D);
        this.getAttributeInstance(GenericAttributes.FLYING_SPEED).setValue(0.4000000059604645D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    @Override
    protected NavigationAbstract b(World world) {
        NavigationFlying navigationflying = new NavigationFlying(this, world);

        navigationflying.a(false);
        navigationflying.d(true);
        navigationflying.b(true);
        return navigationflying;
    }

    @Override
    protected float b(EntityPose entitypose, EntitySize entitysize) {
        return entitysize.height * 0.6F;
    }

    @Override
    public void movementTick() {
        b(this.world, (Entity) this);
        if (this.bK == null || !this.bK.a((IPosition) this.getPositionVector(), 3.46D) || this.world.getType(this.bK).getBlock() != Blocks.JUKEBOX) {
            this.bJ = false;
            this.bK = null;
        }

        super.movementTick();
        this.eD();
    }

    private void eD() {
        this.bC = this.bz;
        this.bB = this.bA;
        this.bA = (float) ((double) this.bA + (double) (!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.bA = MathHelper.a(this.bA, 0.0F, 1.0F);
        if (!this.onGround && this.bI < 1.0F) {
            this.bI = 1.0F;
        }

        this.bI = (float) ((double) this.bI * 0.9D);
        Vec3D vec3d = this.getMot();

        if (!this.onGround && vec3d.y < 0.0D) {
            this.setMot(vec3d.d(1.0D, 0.6D, 1.0D));
        }

        this.bz += this.bI * 2.0F;
    }

    private static boolean b(World world, Entity entity) {
        if (entity.isAlive() && !entity.isSilent() && world.random.nextInt(50) == 0) {
            List<EntityInsentient> list = world.a(EntityInsentient.class, entity.getBoundingBox().g(20.0D), EntityParrot.bE);

            if (!list.isEmpty()) {
                EntityInsentient entityinsentient = (EntityInsentient) list.get(world.random.nextInt(list.size()));

                if (!entityinsentient.isSilent()) {
                    SoundEffect soundeffect = b(entityinsentient.getEntityType());

                    world.playSound((EntityHuman) null, entity.locX(), entity.locY(), entity.locZ(), soundeffect, entity.getSoundCategory(), 0.7F, b(world.random));
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean a(EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);

        if (itemstack.getItem() instanceof ItemMonsterEgg) {
            return super.a(entityhuman, enumhand);
        } else if (!this.isTamed() && EntityParrot.bG.contains(itemstack.getItem())) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                itemstack.subtract(1);
            }

            if (!this.isSilent()) {
                this.world.playSound((EntityHuman) null, this.locX(), this.locY(), this.locZ(), SoundEffects.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.world.isClientSide) {
                if (this.random.nextInt(10) == 0 && !org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) { // CraftBukkit
                    this.tame(entityhuman);
                    this.world.broadcastEntityEffect(this, (byte) 7);
                } else {
                    this.world.broadcastEntityEffect(this, (byte) 6);
                }
            }

            return true;
        } else if (itemstack.getItem() == EntityParrot.bF) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                itemstack.subtract(1);
            }

            this.addEffect(new MobEffect(MobEffects.POISON, 900), org.bukkit.event.entity.EntityPotionEffectEvent.Cause.FOOD); // CraftBukkit
            if (entityhuman.isCreative() || !this.isInvulnerable()) {
                this.damageEntity(DamageSource.playerAttack(entityhuman), Float.MAX_VALUE);
            }

            return true;
        } else if (!this.eF() && this.isTamed() && this.i((EntityLiving) entityhuman)) {
            if (!this.world.isClientSide) {
                this.goalSit.setSitting(!this.isSitting());
            }

            return true;
        } else {
            return super.a(entityhuman, enumhand);
        }
    }

    @Override
    public boolean i(ItemStack itemstack) {
        return false;
    }

    public static boolean c(EntityTypes<EntityParrot> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, Random random) {
        Block block = generatoraccess.getType(blockposition.down()).getBlock();

        return (block.a(TagsBlock.LEAVES) || block == Blocks.GRASS_BLOCK || block instanceof BlockLogAbstract || block == Blocks.AIR) && generatoraccess.getLightLevel(blockposition, 0) > 8;
    }

    @Override
    public boolean b(float f, float f1) {
        return false;
    }

    @Override
    protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {}

    @Override
    public boolean mate(EntityAnimal entityanimal) {
        return false;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    public static void a(World world, Entity entity) {
        if (!entity.isSilent() && !b(world, entity) && world.random.nextInt(200) == 0) {
            world.playSound((EntityHuman) null, entity.locX(), entity.locY(), entity.locZ(), a(world.random), entity.getSoundCategory(), 1.0F, b(world.random));
        }

    }

    @Override
    public boolean B(Entity entity) {
        return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
    }

    @Nullable
    @Override
    public SoundEffect getSoundAmbient() {
        return a(this.random);
    }

    private static SoundEffect a(Random random) {
        if (random.nextInt(1000) == 0) {
            List<EntityTypes<?>> list = Lists.newArrayList(EntityParrot.bH.keySet());

            return b((EntityTypes) list.get(random.nextInt(list.size())));
        } else {
            return SoundEffects.ENTITY_PARROT_AMBIENT;
        }
    }

    private static SoundEffect b(EntityTypes<?> entitytypes) {
        return (SoundEffect) EntityParrot.bH.getOrDefault(entitytypes, SoundEffects.ENTITY_PARROT_AMBIENT);
    }

    @Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return SoundEffects.ENTITY_PARROT_HURT;
    }

    @Override
    protected SoundEffect getSoundDeath() {
        return SoundEffects.ENTITY_PARROT_DEATH;
    }

    @Override
    protected void a(BlockPosition blockposition, IBlockData iblockdata) {
        this.a(SoundEffects.ENTITY_PARROT_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float e(float f) {
        this.a(SoundEffects.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        return f + this.bA / 2.0F;
    }

    @Override
    protected boolean aq() {
        return true;
    }

    @Override
    protected float dn() {
        return b(this.random);
    }

    private static float b(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    protected void C(Entity entity) {
        if (!(entity instanceof EntityHuman)) {
            super.C(entity);
        }
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            if (this.goalSit != null) {
                // CraftBukkit - moved into EntityLiving.d(DamageSource, float)
                // this.goalSit.setSitting(false);
            }

            return super.damageEntity(damagesource, f);
        }
    }

    public int getVariant() {
        return MathHelper.clamp((Integer) this.datawatcher.get(EntityParrot.bD), 0, 4);
    }

    public void setVariant(int i) {
        this.datawatcher.set(EntityParrot.bD, i);
    }

    @Override
    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.register(EntityParrot.bD, 0);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Variant", this.getVariant());
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setVariant(nbttagcompound.getInt("Variant"));
    }

    public boolean eF() {
        return !this.onGround;
    }
}
