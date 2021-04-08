package com.huimi.core.po.equipment;

import lombok.Data;

import java.util.List;

/**
 * create by lja on 2020/8/15 20:58
 */
@Data
public class EquipmentAndGroup {

  private   List<Equipment> equipmentList;
  private   List<EquipmentGroup> equipmentGroupList;
}
