package se.treehou.ng.ohcommunicator.connector.serializers;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import se.treehou.ng.ohcommunicator.connector.GsonHelper;
import se.treehou.ng.ohcommunicator.connector.models.OHStateDescription;

public class JsonStateDescriptionTest {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = GsonHelper.createGsonBuilder();
    }

    @Test
    public void serializeStateDescriptor(){

        final boolean expectedReadOnly = true;
        final String expectedPattern = "%d";

        String serializedState =
            "{\n" +
            "   \"pattern\": \"%d\"," +
            "   \"readOnly\": true,\n" +
            "   \"options\": []\n" +
            "}";

        OHStateDescription stateDescription = gson.fromJson(serializedState, OHStateDescription.class);

        Assert.assertEquals("Expected read only true.", expectedReadOnly, stateDescription.isReadOnly());
        Assert.assertEquals("Expected double pattern.", expectedPattern, stateDescription.getPattern());
    }

    @Test
    public void deserializeStateDescriptor(){

        final boolean expectedReadOnly = true;
        final String expectedPattern = "%d";

        String serializedState =
                "{\n" +
                "   \"pattern\": \"%d\"," +
                "   \"readOnly\": true,\n" +
                "   \"options\": []\n" +
                "}";

        OHStateDescription stateDescription = gson.fromJson(serializedState, OHStateDescription.class);
        serializedState = gson.toJson(stateDescription);
        stateDescription = gson.fromJson(serializedState, OHStateDescription.class);

        Assert.assertEquals("Expected read only true.", expectedReadOnly, stateDescription.isReadOnly());
        Assert.assertEquals("Expected double pattern.", expectedPattern, stateDescription.getPattern());
    }
}