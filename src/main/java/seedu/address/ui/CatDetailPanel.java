package seedu.address.ui;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatImageUtil;
import seedu.address.model.cat.Trait;

/**
 * Panel that displays the full details of a selected {@code Cat}.
 */
public class CatDetailPanel extends UiPart<Region> {

    private static final String FXML = "CatDetailPanel.fxml";

    @FXML private Label noSelectionLabel;
    @FXML private ScrollPane detailScrollPane;
    @FXML private StackPane imageContainer;
    @FXML private ImageView catDetailImage;
    @FXML private Label noImageLabel;
    @FXML private Label catDetailName;
    @FXML private Label catDetailLocation;
    @FXML private Label catDetailHealth;
    @FXML private FlowPane catDetailTraits;

    private ChangeListener<Number> layoutTrigger;

    public CatDetailPanel() {
        super(FXML);
    }

    /**
     * Populates the panel with the given {@code Cat}'s details and makes it visible.
     */
    public void displayCat(Cat cat) {
        noSelectionLabel.setVisible(false);
        noSelectionLabel.setManaged(false);
        detailScrollPane.setVisible(true);
        detailScrollPane.setManaged(true);

        catDetailName.setText(cat.getName().fullName);
        catDetailLocation.setText(cat.getLocation().value);
        catDetailHealth.setText(cat.getHealth().value);

        catDetailTraits.getChildren().clear();
        for (Trait trait : cat.getTraits()) {
            Label traitLabel = new Label(trait.traitName);
            traitLabel.setStyle(
                    "-fx-background-color: #00afb9;"
                    + "-fx-background-radius: 4;"
                    + "-fx-background-insets: 0;"
                    + "-fx-border-color: transparent;"
                    + "-fx-border-width: 0;"
                    + "-fx-text-fill: #fdfcdc;"
                    + "-fx-font-family: 'Georgia';"
                    + "-fx-font-size: 10px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-padding: 3 10 3 10;"
                    + "-fx-opacity: 1;"
            );
            catDetailTraits.getChildren().add(traitLabel);
        }

        File imageFile = CatImageUtil.resolveImageFile(cat);
        if (imageFile != null) {
            catDetailImage.setImage(new Image(imageFile.toURI().toString()));
            catDetailImage.setVisible(true);
            noImageLabel.setVisible(false);
        } else if (cat.getImage().hasImage()) {
            // Explicit path was set but the file no longer exists
            catDetailImage.setImage(null);
            catDetailImage.setVisible(false);
            noImageLabel.setText("⚠ Image not found: " + cat.getImage().value
                    + "\nUse 'attach --reset' to clear it.");
            noImageLabel.setStyle("-fx-text-fill: #FFD700;");
            noImageLabel.setVisible(true);
        } else {
            catDetailImage.setImage(null);
            catDetailImage.setVisible(false);
            noImageLabel.setText("No image");
            noImageLabel.setStyle("");
            noImageLabel.setVisible(true);
        }

        forceLayout();
    }

    /**
     * Forces a full CSS + layout pass. If the panel has no width yet (not laid out),
     * waits until the scroll pane gets its first real width, then fires.
     */
    private void forceLayout() {
        if (layoutTrigger != null) {
            detailScrollPane.widthProperty().removeListener(layoutTrigger);
            layoutTrigger = null;
        }

        if (detailScrollPane.getWidth() > 0) {
            applySceneLayout();
        } else {
            layoutTrigger = (obs, oldVal, newVal) -> {
                if (newVal.doubleValue() > 0) {
                    detailScrollPane.widthProperty().removeListener(layoutTrigger);
                    layoutTrigger = null;
                    Platform.runLater(this::applySceneLayout);
                }
            };
            detailScrollPane.widthProperty().addListener(layoutTrigger);
        }
    }

    private void applySceneLayout() {
        javafx.scene.Scene scene = getRoot().getScene();
        if (scene != null) {
            scene.getRoot().applyCss();
            scene.getRoot().layout();
        }
    }

    /**
     * Reverts to the "no selection" placeholder state.
     */
    public void clearDisplay() {
        detailScrollPane.setVisible(false);
        detailScrollPane.setManaged(false);
        noSelectionLabel.setVisible(true);
        noSelectionLabel.setManaged(true);
    }
}
