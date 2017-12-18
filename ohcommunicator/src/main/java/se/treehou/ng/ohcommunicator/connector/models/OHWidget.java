package se.treehou.ng.ohcommunicator.connector.models;

import java.util.List;

public class OHWidget {

    public static final String WIDGET_TYPE_DUMMY           = "Dummy";
    public static final String WIDGET_TYPE_FRAME           = "Frame";
    public static final String WIDGET_TYPE_SWITCH          = "Switch";
    public static final String WIDGET_TYPE_COLORPICKER     = "Colorpicker";
    public static final String WIDGET_TYPE_SELECTION       = "Selection";
    public static final String WIDGET_TYPE_CHART           = "Chart";
    public static final String WIDGET_TYPE_IMAGE           = "Image";
    public static final String WIDGET_TYPE_VIDEO           = "Video";
    public static final String WIDGET_TYPE_WEB             = "Webview";
    public static final String WIDGET_TYPE_TEXT            = "Text";
    public static final String WIDGET_TYPE_SLIDER          = "Slider";
    public static final String WIDGET_TYPE_GROUP           = "Group";
    public static final String WIDGET_TYPE_GROUP_ITEM      = "GroupItem";
    public static final String WIDGET_TYPE_SETPOINT        = "Setpoint";

    public static final String TAG = OHWidget.class.getSimpleName();

    private String widgetId;
    private String type;
    private String icon;
    private String label;

    private String labelColor;
    private String valueColor;

    // Used for charts
    private String period;
    private String service;

    private int minValue=0;
    private int maxValue=100;
    private float step=1;

    private String url;
    private OHItem item;
    private List<OHWidget> widget;
    private List<OHMapping> mapping;

    private OHLinkedPage linkedPage;

    /**
     * Get widget id.
     * @return widget id.
     */
    public String getWidgetId() {
        return widgetId;
    }

    /**
     * Set the id of widget.
     * @param widgetId the id of widget.
     */
    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * Get the type of widget.
     * @return widget type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set type of
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the name of widget icon.
     * @return widget icon name
     */
    public String getIcon() {
        return (icon == null || icon.equals("none") || icon.equals("image") || icon.equals("")) ? null : icon;
    }

    /**
     * Set name of the widget icon.
     * @param icon widget icon.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label != null ? label : "";
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get item powering the widget.
     * @return item for widget.
     */
    public OHItem getItem() {
        return item;
    }

    /**
     * Set the item that is  powering the widget.
     * @param item the item powering the widget.
     */
    public void setItem(OHItem item) {
        this.item = item;
    }

    /**
     * Get the period for when the widget should update.
     * @return period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Set the update period for widget.
     * @param period widget period.
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    /**
     * Get sub widgets of this one.
     * @return sub widgets.
     */
    public List<OHWidget> getWidget() {
        return widget;
    }

    /**
     * Get the url for widget.
     * @return widget url.
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Set the widgets connected to this widget.
     * @param widget connected widgets
     */
    public void setWidget(List<OHWidget> widget) {
        this.widget = widget;
    }

    /**
     * Get page connected to widget.
     * @return page connected to widget, null if no page is linked.
     */
    public OHLinkedPage getLinkedPage() {
        return linkedPage;
    }

    /**
     * Set a page connected to this widget.
     * @param linkedPage connected page.
     */
    public void setLinkedPage(OHLinkedPage linkedPage) {
        this.linkedPage = linkedPage;
    }

    /**
     * Get the mappings for widget.
     * @return widget mappings.
     */
    public List<OHMapping> getMapping() {
        return mapping;
    }

    /**
     * Set mappings connected to widget.
     * @param mapping list of widget mappings.
     */
    public void setMapping(List<OHMapping> mapping) {
        this.mapping = mapping;
    }

    /**
     * Get the length of a step.
     * @return step length.
     */
    public float getStep() {
        return step;
    }

    /**
     * Set the number of steps between values.
     * @param step number of steps between values.
     */
    public void setStep(float step) {
        this.step = step;
    }

    /**
     * Get the maximum value for widget.
     * @return maximum widget value.
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Set the maximum value for widget.
     * @param maxValue the maximum value for widget.
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Get the minimum value for widget.
     * @return minimum widget.
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Set the minimum value for widget.
     * @param minValue min value for widget.
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * Get the path to the widget icon.
     * @return path to widget icon.
     */
    public String getIconPath(){
        String icon = getIcon();
        return (icon != null) ? "/images/"+icon+".png" : null;
    }

    /**
     * Get the label color for widget
     * @return label color
     */
    public String getLabelColor() {
        return labelColor;
    }

    /**
     * Set label color for widget
     * @param labelcolor
     */
    public void setLabelColor(String labelcolor) {
        this.labelColor = labelcolor;
    }

    /**
     * Get the value color for widget
     * @return value color
     */
    public String getValueColor() {
        return valueColor;
    }

    /**
     * Set value color for widget
     * @param valueColor
     */
    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OHWidget widget1 = (OHWidget) o;

        if (minValue != widget1.minValue) return false;
        if (maxValue != widget1.maxValue) return false;
        if (Float.compare(widget1.step, step) != 0) return false;
        if (widgetId != null ? !widgetId.equals(widget1.widgetId) : widget1.widgetId != null)
            return false;
        if (type != null ? !type.equals(widget1.type) : widget1.type != null) return false;
        if (icon != null ? !icon.equals(widget1.icon) : widget1.icon != null) return false;
        if (label != null ? !label.equals(widget1.label) : widget1.label != null) return false;
        if (labelColor != null ? !labelColor.equals(widget1.labelColor) : widget1.labelColor != null) return false;
        if (valueColor != null ? !valueColor.equals(widget1.valueColor) : widget1.valueColor != null) return false;
        if (period != null ? !period.equals(widget1.period) : widget1.period != null) return false;
        if (service != null ? !service.equals(widget1.service) : widget1.service != null)
            return false;
        if (url != null ? !url.equals(widget1.url) : widget1.url != null) return false;
        if (item != null ? !item.equals(widget1.item) : widget1.item != null) return false;
        if (widget != null ? !widget.equals(widget1.widget) : widget1.widget != null) return false;
        if (mapping != null ? !mapping.equals(widget1.mapping) : widget1.mapping != null)
            return false;
        return linkedPage != null ? linkedPage.equals(widget1.linkedPage) : widget1.linkedPage == null;

    }

    @Override
    public int hashCode() {
        int result = widgetId != null ? widgetId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (labelColor != null ? labelColor.hashCode() : 0);
        result = 31 * result + (valueColor != null ? valueColor.hashCode() : 0);
        result = 31 * result + minValue;
        result = 31 * result + maxValue;
        result = 31 * result + (step != +0.0f ? Float.floatToIntBits(step) : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (widget != null ? widget.hashCode() : 0);
        result = 31 * result + (mapping != null ? mapping.hashCode() : 0);
        result = 31 * result + (linkedPage != null ? linkedPage.hashCode() : 0);
        return result;
    }
}
