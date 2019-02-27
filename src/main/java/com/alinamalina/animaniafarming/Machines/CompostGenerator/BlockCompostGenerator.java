package com.alinamalina.animaniafarming.Machines.CompostGenerator;

import java.util.Random;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.Blocks.BlockBase;
import com.alinamalina.animaniafarming.Machines.GuiIDs;
import com.alinamalina.animaniafarming.Machines.Sinterer.TileEntitySinteringFurnace;
import com.alinamalina.animaniafarming.init.ModBlocks;

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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCompostGenerator extends BlockBase implements ITileEntityProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");//Defining burning
	
	public BlockCompostGenerator (String name, Material material)
	{
		super(name, material);
		setHardness(5.0F);
		setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));//Normally wont be burning and facing the player
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 	
	{

		return new TileEntityCompostGenerator();
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitY, float hitX, float hitZ) //What happens when you right click the block
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(Main.instance, GuiIDs.GUI_COMPOST_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());//Opens GUI of generator
		}
		return true;	
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.COMPOST_GENERATOR);
	}
	
    @Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		TileEntityCompostGenerator te = (TileEntityCompostGenerator)worldIn.getTileEntity(pos);
		InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), te.handler.getStackInSlot(0));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ModBlocks.COMPOST_GENERATOR);
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
	
	public static void setState(boolean active, World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if (active)
			worldIn.setBlockState(pos,  ModBlocks.COMPOST_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3) ;//If active then burning
		else
			worldIn.setBlockState(pos, ModBlocks.COMPOST_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);//If not active then not burning
		
		if(tileentity !=null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty [] {BURNING,FACING});
	}
	
    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(BURNING) ? 13 : 0;
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
