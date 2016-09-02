package se.treehou.ng.ohcommunicator.util;

import se.treehou.ng.ohcommunicator.connector.models.OHWidget;

public class OpenhabUtil {

    /**
     * Returns true if item type is a group.
     *
     * @param itemType the item type to test.
     * @return true if item is type, else false.
     */
    public static boolean isGroup(String itemType){
        return OHWidget.WIDGET_TYPE_GROUP.equalsIgnoreCase(itemType) ||
                OHWidget.WIDGET_TYPE_GROUP_ITEM.equalsIgnoreCase(itemType);
    }

    private OpenhabUtil() {}
}
