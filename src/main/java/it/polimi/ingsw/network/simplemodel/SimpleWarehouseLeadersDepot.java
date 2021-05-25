package it.polimi.ingsw.network.simplemodel;

import it.polimi.ingsw.network.assets.resources.ResourceAsset;

import java.util.List;
import java.util.Map;

public class SimpleWarehouseLeadersDepot extends SimpleModelElement{

    Map<Integer, List<ResourceAsset>> simpleWarehouseLeadersDepot;

    public SimpleWarehouseLeadersDepot(){}

    public SimpleWarehouseLeadersDepot(Map<Integer, List<ResourceAsset>> simpleWarehouseLeadersDepot){
        this.simpleWarehouseLeadersDepot = simpleWarehouseLeadersDepot;
    }

    @Override
    public  Map<Integer, List<ResourceAsset>> getElement(){
        return simpleWarehouseLeadersDepot;
    }

    @Override
    public void update(SimpleModelElement element) {
        this.simpleWarehouseLeadersDepot = (Map<Integer, List<ResourceAsset>>) element.getElement();
    }


}
