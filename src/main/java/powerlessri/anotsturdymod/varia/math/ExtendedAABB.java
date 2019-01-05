package powerlessri.anotsturdymod.varia.math;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class ExtendedAABB extends AxisAlignedBB {

    public static ExtendedAABB from(AxisAlignedBB source) {
        return new ExtendedAABB(source.minX, source.minY, source.minZ, source.maxX, source.maxY, source.maxZ);
    }


    public ExtendedAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
    }

    public ExtendedAABB(BlockPos pos) {
        super(pos);
    }

    public ExtendedAABB(BlockPos pos1, BlockPos pos2) {
        super(pos1, pos2);
    }

    public ExtendedAABB(Vec3d min, Vec3d max) {
        super(min, max);
    }

    public ExtendedAABB(Vector3d min, Vector3d max) {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }


    /**
     * Rotate the current ExtendedAABB on three different axis and return a new one if any of the parameters are nonzero.
     *
     * <p>
     * When called, it will rotate the current ExtendedAABB on all three axis by a given degree one at a time, as <b><a
     * href="https://en.wikipedia.org/wiki/Right-hand_rule#Electromagnetics">Right Hand Law in electromagnetism</a></b>:
     * <p><q>
     * The direction of a positive rotation is the direction of your fingers when the thumb is pointing towards the positive direction of
     * the axis.
     * </q></p>
     * <p>Equivalent to apply the method {@link #rotate(EnumFacing.Axis, int)} three times on all X, Y, and Z axis.</p>
     * </p>
     *
     * <h3>Example:</h3>
     * <p>
     * Given the current AABB describes an half box where the empty side is pointing {@link EnumFacing#UP up}<sup>1</sup>, a rotation of
     * x=90 will make the empty side pointing to the {@link EnumFacing#SOUTH south}<sup>2</sup>.
     * </p>
     *
     *
     * <p>
     * <hr/>
     * <p><sup>1. {@link net.minecraft.util.EnumFacing.AxisDirection#POSITIVE positive} direction of {@link Axis#Y y axis}</sup></p>
     * <p><sup>2. {@link net.minecraft.util.EnumFacing.AxisDirection#POSITIVE positive} direction of {@link Axis#Z z axis}</sup></p>
     * </p>
     *
     * @param x The angle to rotate on x-axis, in degrees
     * @param y The angle to rotate on y-axis, in degrees
     * @param z The angle to rotate on z-axis, in degrees
     * @return A new ExtendedAABB
     */
    public ExtendedAABB rotate(int x, int y, int z) {
        Matrix4d rotationMat = TransformationUtils.combineTransformations(
                TransformationUtils.getRotationMatrix(Axis.X, x),
                TransformationUtils.getRotationMatrix(Axis.Y, y),
                TransformationUtils.getRotationMatrix(Axis.Z, z));

        return this.transformSelf(rotationMat, true);
    }


    /**
     * Rotate the ExtendedAABB on the given axis at a given angle.
     * <p>
     * See {@link #rotate(int, int, int)} for more information and examples.
     * </p>
     *
     * @param axis  The axis to rotate around
     * @param angle The angle to rotate, in degrees.
     * @return A new, rotated ExtendedAABB
     * @see #rotate(int, int, int)
     */
    public ExtendedAABB rotate(Axis axis, int angle) {
        if (angle == 0) {
            return this;
        }
        return this.transformSelf(TransformationUtils.getRotationMatrix(axis, angle), true);
    }


    private ExtendedAABB transformSelf(Matrix4d transformation, boolean centralize) {
        ExtendedAABB source = this;
        if (centralize) {
            source = this.offset(-0.5d, -0.5d, -0.5d);
        }

        Vector3d min = new Vector3d(source.minX, source.minY, source.minZ);
        Vector3d max = new Vector3d(source.maxX, source.maxY, source.maxZ);
        transformation.transform(min);
        transformation.transform(max);

        if (centralize) {
            min.setX(min.x + 0.5d);
            min.setY(min.y + 0.5d);
            min.setZ(min.z + 0.5d);
            max.setX(max.x + 0.5d);
            max.setY(max.y + 0.5d);
            max.setZ(max.z + 0.5d);
        }
        return new ExtendedAABB(min, max);
    }


    // =========================================================================//
    // Override the return types of parent methods to make chain calling easier //
    // =========================================================================//


    @Override
    public ExtendedAABB setMaxY(double y2) {
        return from(super.setMaxY(y2));
    }

    @Override
    public ExtendedAABB contract(double x, double y, double z) {
        return from(super.contract(x, y, z));
    }

    @Override
    public ExtendedAABB expand(double x, double y, double z) {
        return from(super.expand(x, y, z));
    }

    @Override
    public ExtendedAABB grow(double x, double y, double z) {
        return from(super.grow(x, y, z));
    }

    @Override
    public ExtendedAABB grow(double value) {
        return from(super.grow(value));
    }

    @Override
    public ExtendedAABB intersect(AxisAlignedBB other) {
        return from(super.intersect(other));
    }

    @Override
    public ExtendedAABB union(AxisAlignedBB other) {
        return from(super.union(other));
    }

    @Override
    public ExtendedAABB offset(double x, double y, double z) {
        return from(super.offset(x, y, z));
    }

    @Override
    public ExtendedAABB offset(BlockPos pos) {
        return from(super.offset(pos));
    }

    @Override
    public ExtendedAABB offset(Vec3d vec) {
        return from(super.offset(vec));
    }

    @Override
    public ExtendedAABB shrink(double value) {
        return from(super.shrink(value));
    }

}
