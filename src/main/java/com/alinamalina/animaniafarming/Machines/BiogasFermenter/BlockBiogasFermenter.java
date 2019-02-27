package com.alinamalina.animaniafarming.Machines.BiogasFermenter;

import java.util.Random;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.Blocks.BlockBase;
import com.alinamalina.animaniafarming.Machines.GuiIDs;
import com.alinamalina.animaniafarming.init.ModBlocks;
import com.alinamalina.animaniafarming.init.ModFluids;
import com.alinamalina.animaniafarming.util.Helper;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class BlockBiogasFermenter extends BlockBase implements ITileEntityProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");//Defining burning

	public BlockBiogasFermenter (String name, Material material)
	{
		
		super(name, material);
		setHardness(5.0F);
		setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitY, float hitX, float hitZ) //What happens when you right click the block
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		TileEntityBiogasFermenter te = (TileEntityBiogasFermenter) worldIn.getTileEntity(pos);

		if(!worldIn.isRemote)
//			if(Helper.hasFluid(heldItem, FluidRegistry.WATER) && te.waterTank.getFluidAmount() < 1000)
//			{
//				te.waterTank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
//				Helper.sendTileEntityUpdate(te);
//				worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 0.6F, 0.8F);
//				if (!playerIn.isCreative())
//				{
//					IFluidHandlerItem handler = FluidUtil.getFluidHandler(heldItem);
//					handler.drain(1000, true);
//					ItemStack newStack = handler.getContainer();
//					playerIn.setHeldItem(hand, newStack);
//				}
//				return true;
//
//			}

			 playerIn.openGui(Main.instance, GuiIDs.GUI_BIOGAS_FERMENTER, worldIn, pos.getX(), pos.getY(), pos.getZ());//Opens GUI of generator
		return true;
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if (active)
			worldIn.setBlockState(pos, ModBlocks.BIOGAS_FERMENTER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3) ;//If active then burning
		else
			worldIn.setBlockState(pos, ModBlocks.BIOGAS_FERMENTER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);//If not active then not burning
		
		if(tileentity !=null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(BURNING) ? 13 : 0;
    }

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.BIOGAS_FERMENTER);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ModBlocks.BIOGAS_FERMENTER);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 	
	{

		return new TileEntityBiogasFermenter();
	} 
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);

			if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;//Calculating how to face the block so when the player faces some direction the block will be placed facing the player
			else if(face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
			else if(face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
			else if(face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}	
	}   


	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		TileEntityBiogasFermenter tileentity = (TileEntityBiogasFermenter)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.handler.getStackInSlot(0)));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
		world.setBlockState(pos, state.withProperty(BlockHorizontal.FACING, player.getHorizontalFacing().getOpposite()), 2);

		super.onBlockPlacedBy(world, pos, state, player, stack);
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty [] {BURNING,FACING});
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror){
		return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
	}

}
