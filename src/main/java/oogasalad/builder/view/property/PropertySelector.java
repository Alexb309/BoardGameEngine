package oogasalad.builder.view.property;

import javafx.scene.Node;
import oogasalad.builder.model.property.Property;

/**
 * API that defines a view element that chooses a property.
 *
 * @author Shaan Gondalia
 */
public interface PropertySelector {

  /**
   * Returns a JavaFX Node that will be displayed to the user next to the property label
   *
   * @return a Node that will be shown to the user containing UI for entering a property value
   */
  Node display();

  /**
   * Returns a populated property with the correct value, name, and form
   *
   * @return a populated property with the correct value, name, and form
   */
  Property getProperty();
}