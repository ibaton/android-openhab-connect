package se.treehou.ng.ohcommunicator.util;

public class OpenhabUtil {

    /**
     * Returns true if item type is a group.
     *
     * @param itemType the item type to test.
     * @return true if item is type, else false.
     */
    public static boolean isGroup(String itemType){
        return OpenhabConstants.TYPE_GROUP.equalsIgnoreCase(itemType) ||
                OpenhabConstants.TYPE_GROUP.equalsIgnoreCase(itemType);
    }

    private OpenhabUtil() {}
}
