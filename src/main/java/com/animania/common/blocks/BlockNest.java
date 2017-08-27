package com.animania.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.animania.Animania;
import com.animania.common.ModSoundEvents;
import com.animania.common.entities.chickens.EntityChickLeghorn;
import com.animania.common.entities.chickens.EntityChickOrpington;
import com.animania.common.entities.chickens.EntityChickPlymouthRock;
import com.animania.common.entities.chickens.EntityChickRhodeIslandRed;
import com.animania.common.entities.chickens.EntityChickWyandotte;
import com.animania.common.entities.chickens.EntityRoosterLeghorn;
import com.animania.common.entities.chickens.EntityRoosterOrpington;
import com.animania.common.entities.chickens.EntityRoosterPlymouthRock;
import com.animania.common.entities.chickens.EntityRoosterRhodeIslandRed;
import com.animania.common.entities.chickens.EntityRoosterWyandotte;
import com.animania.common.entities.peacocks.EntityPeachickBlue;
import com.animania.common.entities.peacocks.EntityPeachickWhite;
import com.animania.common.entities.peacocks.EntityPeacockBlue;
import com.animania.common.entities.peacocks.EntityPeacockWhite;
import com.animania.common.handler.BlockHandler;
import com.animania.common.handler.ItemHandler;
import com.animania.common.helper.ItemHelper;
import com.animania.common.tileentities.TileEntityNest;
import com.animania.config.AnimaniaConfig;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockNest extends BlockContainer
{
	private String name = "block_nest";

	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.00D, 1.0D, 0.3D, 1.0D);
	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.00D, 0.3D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.00D, 0.3D, 1.0D);

	public BlockNest()
	{
		super(Material.WOOD);
		this.setRegistryName(new ResourceLocation(Animania.MODID, this.name));
		this.setDefaultState(this.blockState.getBaseState());
		GameRegistry.register(this);
		this.setUnlocalizedName(Animania.MODID + "_" + this.name);
		this.setCreativeTab(Animania.TabAnimaniaResources);
		this.setTickRandomly(true);

	}

	public int tickRate(World worldIn)
	{
		return 5;
	}

	@Override
	public String getLocalizedName()
	{
		return I18n.translateToLocal("tile.animania_block_nest.name");
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{

		return BlockNest.NORTH_AABB;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		TileEntityNest te = (TileEntityNest) worldIn.getTileEntity(pos);
		if (te != null && te.getNestType() != 0)
		{
			int nestType = te.getNestType();

			int esize = worldIn.loadedEntityList.size();
			for (int k = 0; k <= esize - 1; k++)
			{
				Entity entity = worldIn.loadedEntityList.get(k);

				if (entity != null && entity instanceof EntityRoosterLeghorn)
				{

					boolean hatchFlag = false;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int) x2) < 6 && MathHelper.abs((int) z2) < 3 && MathHelper.abs((int) y2) < 6)
						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
						{
							if (nestType == 1 || nestType == 2 || nestType == 3)
							{

								if (rand.nextInt(2) < 1)
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 4 || nestType == 5 | nestType == 6)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 7 || nestType == 8 || nestType == 9)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 10 || nestType == 11 || nestType == 12)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 13 || nestType == 14 || nestType == 15)
								if (rand.nextInt(2) < 1)
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}

							if (hatchFlag)
								this.updateNest(worldIn, pos, state, te);
						}

				} else if (entity != null && entity instanceof EntityRoosterOrpington)
				{
					boolean hatchFlag = false;

					k = esize;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int) x2) < 6 && MathHelper.abs((int) z2) < 3 && MathHelper.abs((int) y2) < 6)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
							if (nestType == 1 || nestType == 2 || nestType == 3)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 4 || nestType == 5 | nestType == 6)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									k = esize;
								} else
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 7 || nestType == 8 || nestType == 9)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 10 || nestType == 11 || nestType == 12)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 13 || nestType == 14 || nestType == 15)
								if (rand.nextInt(2) < 1)
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}

						if (hatchFlag)
							this.updateNest(worldIn, pos, state, te);

					}

				} else if (entity != null && entity instanceof EntityRoosterPlymouthRock)
				{
					boolean hatchFlag = false;
					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int) x2) < 6 && MathHelper.abs((int) z2) < 3 && MathHelper.abs((int) y2) < 6)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
							if (nestType == 1 || nestType == 2 || nestType == 3)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 4 || nestType == 5 | nestType == 6)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 7 || nestType == 8 || nestType == 9)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 10 || nestType == 11 || nestType == 12)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 13 || nestType == 14 || nestType == 15)
								if (rand.nextInt(2) < 1)
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}

						if (hatchFlag)
							this.updateNest(worldIn, pos, state, te);

					}

				} else if (entity != null && entity instanceof EntityRoosterRhodeIslandRed)
				{
					boolean hatchFlag = false;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int) x2) < 6 && MathHelper.abs((int) z2) < 3 && MathHelper.abs((int) y2) < 6)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
							if (nestType == 1 || nestType == 2 || nestType == 3)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 4 || nestType == 5 | nestType == 6)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 7 || nestType == 8 || nestType == 9)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 10 || nestType == 11 || nestType == 12)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 13 || nestType == 14 || nestType == 15)
								if (rand.nextInt(2) < 1)
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}

						if (hatchFlag)
							this.updateNest(worldIn, pos, state, te);
					}

				} else if (entity != null && entity instanceof EntityRoosterWyandotte)
				{

					boolean hatchFlag = false;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int) x2) < 6 && MathHelper.abs((int) z2) < 3 && MathHelper.abs((int) y2) < 6)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
							if (nestType == 1 || nestType == 2 || nestType == 3)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickLeghorn entityChick = new EntityChickLeghorn(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 4 || nestType == 5 | nestType == 6)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickOrpington entityChick = new EntityChickOrpington(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 7 || nestType == 8 || nestType == 9)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickPlymouthRock entityChick = new EntityChickPlymouthRock(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 10 || nestType == 11 || nestType == 12)
							{
								if (rand.nextInt(2) < 1)
								{
									EntityChickRhodeIslandRed entityChick = new EntityChickRhodeIslandRed(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}
							} else if (nestType == 13 || nestType == 14 || nestType == 15)
								if (rand.nextInt(2) < 1)
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								} else
								{
									EntityChickWyandotte entityChick = new EntityChickWyandotte(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.chickenCluck1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;
								}

					}
					if (hatchFlag) {
						updateNest(worldIn, pos, state, te);
					}

				} else if (entity !=null && (entity instanceof EntityPeacockBlue))
				{

					boolean hatchFlag = false;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int)x2) < 8 && MathHelper.abs((int)y2) < 4 && MathHelper.abs((int)z2) < 8)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1) {
							if (nestType == 16 || nestType == 17 || nestType == 18) {
								EntityPeachickBlue entityChick = new EntityPeachickBlue(worldIn);
								entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
								worldIn.spawnEntity(entityChick);
								entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
								hatchFlag = true;
								k = esize;	
							} else if (nestType == 19 || nestType == 20 | nestType == 21) {
								if (rand.nextInt(2) < 1) {
									EntityPeachickWhite entityChick = new EntityPeachickWhite(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;	
								} else {
									EntityPeachickBlue entityChick = new EntityPeachickBlue(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;	
								}
							}
						}

						if (hatchFlag) {
							updateNest(worldIn, pos, state, te);
						}

					}

				} else if (entity !=null && (entity instanceof EntityPeacockWhite))
				{

					boolean hatchFlag = false;

					double xt = entity.posX;
					double yt = entity.posY;
					double zt = entity.posZ;
					int x1 = pos.getX();
					int y1 = pos.getY();
					int z1 = pos.getZ();
					double x2 = xt - x1;
					double y2 = yt - y1;
					double z2 = zt - z1;

					if (MathHelper.abs((int)x2) < 8 && MathHelper.abs((int)y2) < 4 && MathHelper.abs((int)z2) < 8)
					{

						if (rand.nextInt(AnimaniaConfig.careAndFeeding.eggHatchChance) < 1)
						{
							if (nestType == 16 || nestType == 17 || nestType == 18) {

								if (rand.nextInt(2) < 1) {
									EntityPeachickBlue entityChick = new EntityPeachickBlue(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;	
								} else {
									EntityPeachickWhite entityChick = new EntityPeachickWhite(worldIn);
									entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
									worldIn.spawnEntity(entityChick);
									entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
									hatchFlag = true;
									k = esize;	
								}
							} else if (nestType == 19 || nestType == 20 | nestType == 21) {

								EntityPeachickWhite entityChick = new EntityPeachickWhite(worldIn);
								entityChick.setPosition(pos.getX() + .5, pos.getY() + .2, pos.getZ() + .5);
								worldIn.spawnEntity(entityChick);
								entityChick.playSound(ModSoundEvents.peacock1, 0.50F, 1.4F);
								k = esize;	

							}
						}
					}

					if (hatchFlag) {
						updateNest(worldIn, pos, state, te);

					}
				}
			}
		}

	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{

		return this.getDefaultState();
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityNest();
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(BlockHandler.blockNest, 1);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{

		TileEntityNest te = (TileEntityNest) worldIn.getTileEntity(pos);

		if (te != null && te.getNestType() == 0) {
			worldIn.removeTileEntity(te.getPos());
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlockHandler.blockNest);
	}

	public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack)
	{
		return stack.getMetadata() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		ItemStack heldItem = playerIn.getHeldItem(hand);

		TileEntityNest te = (TileEntityNest) worldIn.getTileEntity(pos);

		if (worldIn.isRemote || te.getNestType() == 0)
			return true;
		else if (te.getNestType() == 1)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 2)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(1);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 3)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(2);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 4)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 5)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(4);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 6)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(5);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 7)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 8)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(7);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 9)
		{
			ItemStack bob = new ItemStack(Items.EGG, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(8);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 10)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 11)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(10);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 12)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(11);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 13)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 14)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(13);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 15)
		{
			ItemStack bob = new ItemStack(ItemHandler.brownEgg, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(14);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 16) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggBlue, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 17) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggBlue, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(16);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 18) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggBlue, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(17);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 19) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggWhite, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 20) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggWhite, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(19);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else if (te.getNestType() == 21) 
		{
			ItemStack bob = new ItemStack(ItemHandler.peacockEggWhite, 1);
			playerIn.inventory.addItemStackToInventory(bob);
			te.setType(20);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
			return true;
		} else 
			return false;
	}

	public String getName()
	{
		return this.name;
	}

	public void updateNest(World worldIn, BlockPos pos, IBlockState state, TileEntityNest te)
	{

		int nestType = te.getNestType();

		System.out.println(nestType);

		if (nestType == 15)
		{
			te.setType(14);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 14)
		{
			te.setType(13);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 13)
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 12)
		{
			te.setType(11);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 11)
		{
			te.setType(10);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 10)
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 9)
		{
			te.setType(8);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 8)
		{
			te.setType(7);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 7)			
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 6)
		{
			te.setType(5);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 5)
		{
			te.setType(4);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 4)			
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 3)
		{
			te.setType(2);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 2)
		{
			te.setType(1);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 1)
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);

			//Peacocks
		} else if (nestType == 21)
		{
			te.setType(20);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 20)
		{
			te.setType(19);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 19)
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 18)
		{
			te.setType(17);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 17)
		{
			te.setType(16);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		} else if (nestType == 16)
		{
			te.setType(0);
			te.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 1);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

	}

}