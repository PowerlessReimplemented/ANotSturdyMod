package powerlessri.anotsturdymod.blocks.logisticalprocessor;

import net.minecraft.util.math.BlockPos;

public interface ILogisticsCable {
    
    BlockPos getControllerPosition();
    
    ILogisticsController getController();
    
}
