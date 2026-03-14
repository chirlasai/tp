package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cat.Cat;

/**
 * Panel containing the list of cats.
 */
public class CatListPanel extends UiPart<Region> {
    private static final String FXML = "CatListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CatListPanel.class);

    @FXML
    private ListView<Cat> catListView;

    /**
     * Creates a {@code CatListPanel} with the given {@code ObservableList}.
     */
    public CatListPanel(ObservableList<Cat> catList) {
        super(FXML);
        catListView.setItems(catList);
        catListView.setCellFactory(listView -> new CatListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cat} using a {@code CatCard}.
     */
    class CatListViewCell extends ListCell<Cat> {
        @Override
        protected void updateItem(Cat cat, boolean empty) {
            super.updateItem(cat, empty);

            if (empty || cat == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CatCard(cat, getIndex() + 1).getRoot());
            }
        }
    }

}
