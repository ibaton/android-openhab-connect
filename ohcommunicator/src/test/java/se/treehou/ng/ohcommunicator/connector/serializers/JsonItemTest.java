package se.treehou.ng.ohcommunicator.connector.serializers;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.treehou.ng.ohcommunicator.connector.GsonHelper;
import se.treehou.ng.ohcommunicator.connector.models.OHItem;
import se.treehou.ng.ohcommunicator.connector.models.OHStateDescription;

public class JsonItemTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = GsonHelper.createGsonBuilder();
    }

    @Test
    public void serializeStateDescriptor(){
        String name = "test_sensor";
        String link = "http://127.0.0.1:8080/rest/items/"+name;
        String state = "26.9";
        String type = "NumberItem";
        String label = "Sensor (temperature)";

        String serializedItem =
                "{\n" +
                        "    \"link\": \"" + link + "\",\n" +
                        "    \"state\": \"" + state + "\",\n" +
                        "    \"stateDescription\": {\n" +
                        "      \"pattern\": \"%.1f\",\n" +
                        "      \"readOnly\": true,\n" +
                        "      \"options\": []\n" +
                        "    },\n" +
                        "    \"type\": \""+type+"\",\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"label\": \""+label+"\",\n" +
                        "    \"tags\": [],\n" +
                        "    \"groupNames\": []\n" +
                        "  }";

        OHItem ohItem = gson.fromJson(serializedItem, OHItem.class);

        Assert.assertEquals(name, ohItem.getName());
        Assert.assertEquals(link, ohItem.getLink());
        Assert.assertEquals(state, ohItem.getState());
        Assert.assertEquals(type, ohItem.getType());
        Assert.assertEquals(label, ohItem.getLabel());

        Assert.assertEquals(true, ohItem.getStateDescription().isReadOnly());
        Assert.assertEquals("%.1f", ohItem.getStateDescription().getPattern());
    }

    @Test
    public void deserializeStateDescriptor(){
        String name = "test_sensor";
        String link = "http://127.0.0.1:8080/rest/items/"+name;
        String state = "26.9";
        String type = "NumberItem";
        String label = "Sensor (temperature)";

        String serializedItem =
                "{\n" +
                        "    \"link\": \"" + link + "\",\n" +
                        "    \"state\": \"" + state + "\",\n" +
                        "    \"stateDescription\": {\n" +
                        "      \"pattern\": \"%.1f\",\n" +
                        "      \"readOnly\": true,\n" +
                        "      \"options\": []\n" +
                        "    },\n" +
                        "    \"type\": \""+type+"\",\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"label\": \""+label+"\",\n" +
                        "    \"tags\": [],\n" +
                        "    \"groupNames\": []\n" +
                        "  }";

        OHItem ohItem = gson.fromJson(serializedItem, OHItem.class);
        serializedItem = gson.toJson(ohItem);
        ohItem = gson.fromJson(serializedItem, OHItem.class);

        Assert.assertEquals(name, ohItem.getName());
        Assert.assertEquals(link, ohItem.getLink());
        Assert.assertEquals(state, ohItem.getState());
        Assert.assertEquals(type, ohItem.getType());
        Assert.assertEquals(label, ohItem.getLabel());

        Assert.assertEquals(true, ohItem.getStateDescription().isReadOnly());
        Assert.assertEquals("%.1f", ohItem.getStateDescription().getPattern());
    }
}