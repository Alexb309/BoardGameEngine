package oogasalad.builder.model.element.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashSet;
import oogasalad.builder.model.element.Action;
import oogasalad.builder.model.element.ElementRecord;
import oogasalad.builder.model.exception.MissingRequiredPropertyException;
import oogasalad.builder.model.property.Property;
import oogasalad.builder.model.property.PropertyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ActionFactory Class
 *
 * @author Shaan Gondalia
 */
public class ActionFactoryTest {

  private ActionFactory actionFactory;
  private static final String ACTION_NAME = "moveTopRight";
  private static final String PROPERTY_NAME_TYPE = "type";
  private static final String ACTION_TYPE = "move";
  private static final String INVALID_ACTION_TYPE = "moveTo";
  private static final String PROPERTY_NAME_ONE = "x";
  private static final int PROPERTY_VALUE_ONE = 1;
  private static final String PROPERTY_NAME_TWO = "y";
  private static final int PROPERTY_VALUE_TWO = 2;

  @BeforeEach
  void setUp(){
    actionFactory = new ActionFactory();
  }

  @Test
  void testActionCreated() throws MissingRequiredPropertyException {
    Collection<Property> properties = new HashSet<>();
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_TYPE, ACTION_TYPE));
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_ONE, PROPERTY_VALUE_ONE));
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_TWO, PROPERTY_VALUE_TWO));
    Action action = actionFactory.createElement(ACTION_NAME, properties);

    ElementRecord record = action.toRecord();
    assertEquals(properties, record.properties());
    assertEquals(ACTION_NAME, record.name());
  }

  @Test
  void testConditionMissingRequired() {
    Collection<Property> properties = new HashSet<>();
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_TYPE, ACTION_TYPE));
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_ONE, PROPERTY_VALUE_ONE));
    assertThrows(MissingRequiredPropertyException.class, () ->
        actionFactory.createElement(ACTION_NAME, properties));
  }

  @Test
  void testConditionInvalidType() {
    Collection<Property> properties = new HashSet<>();
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_TYPE, INVALID_ACTION_TYPE));
    properties.add(PropertyFactory.makeProperty(PROPERTY_NAME_ONE, PROPERTY_VALUE_ONE));
    assertThrows(MissingRequiredPropertyException.class, () ->
        actionFactory.createElement(ACTION_NAME, properties));
  }

}
