package se.treehou.ng.ohcommunicator.util;

public class OpenhabUtil {

    private static final String TYPE_GROUP           = "Group";
    private static final String TYPE_GROUP_ITEM      = "GroupItem";

    /**
     * Returns true if item type is a group.
     *
     * @param itemType the item type to test.
     * @return true if item is type, else false.
     */
    public static boolean isGroup(String itemType){
        return TYPE_GROUP.equalsIgnoreCase(itemType) || TYPE_GROUP_ITEM.equalsIgnoreCase(itemType);
    }

    private OpenhabUtil() {}
}
