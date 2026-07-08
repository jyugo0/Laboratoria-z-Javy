package pwr.karmil.laby12;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> findWayChoiceBox;

    @FXML
    private ChoiceBox<Integer> sizeChoiceBox;

    @FXML
    private ChoiceBox<String> mapChoiceBox;

    @FXML
    private Canvas mapCanvas;

    private static int mapSize;
    private int tileSize;
    private int pathWidth;
    private Context jsContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapChoiceBox.setItems(FXCollections.observableArrayList("Wfc", "Random walk"));
        mapChoiceBox.setValue("Wfc");

        findWayChoiceBox.setItems(FXCollections.observableArrayList("dijkstra", "bfs"));
        findWayChoiceBox.setValue("dijkstra");

        sizeChoiceBox.setItems(FXCollections.observableArrayList(3,4,5,6,7,8,9,10));
        sizeChoiceBox.setValue(7);
    }

    @FXML
    public void onMapBtnClicked(ActionEvent event) {

        String selectedAlgo = mapChoiceBox.getValue();
        String fileName = selectedAlgo.contains("Random") ? "/mapAlgo2.js" : "/mapAlgo1.js";
        mapSize = sizeChoiceBox.getValue();
        tileSize = 560/mapSize;
        pathWidth = tileSize/3;

        if (jsContext != null) {
            jsContext.close();
        }

        try {
            jsContext = Context.create("js");
            jsContext.getBindings("js").putMember("JAVA_MAP_SIZE", mapSize);
            URL resource = getClass().getResource(fileName);

            if (resource == null) {
                System.err.println("Nie znaleziono pliku: " + fileName);
                return;
            }

            Source source = Source.newBuilder("js", resource).build();
            Value jsMap = jsContext.eval(source);
            renderMap(jsMap);

        } catch (Exception e) {
            System.err.println("Błąd podczas generowania mapy!");
            e.printStackTrace();
        }
    }

    @FXML
    public void onFindWayBtnClicked(ActionEvent event) {
        if (jsContext == null) {
            System.out.println("Najpierw wygeneruj mapę!");
            return;
        }

        String algo = findWayChoiceBox.getValue();
        String fileName;
        String funcName;
        if (algo == "dijkstra") {
            fileName = "/"+findWayChoiceBox.getValue() + ".js";
            funcName = "findPathDijkstra";
        } else {
            fileName = "/"+findWayChoiceBox.getValue() + ".js";
            funcName = "findPathBFS";
        }

        try {
            URL resource = getClass().getResource(fileName);
            if (resource == null) {
                System.err.println("Nie znaleziono pliku: " + fileName);
                return;
            }
            Source source = Source.newBuilder("js", resource).build();
            jsContext.eval(source);
            String script = funcName + "(myMap, 0, 0, " + (mapSize - 1) + ", " + (mapSize - 1) + ");";
            Value path = jsContext.eval("js", script);

            if (path.hasArrayElements() && path.getArraySize() > 0) {
                GraphicsContext gc = mapCanvas.getGraphicsContext2D();

                gc.setStroke(Color.FORESTGREEN);
                gc.setLineWidth(tileSize/10.0);
                gc.beginPath();

                for (long i = 0; i < path.getArraySize(); i++) {
                    Value point = path.getArrayElement(i);
                    int x = point.getMember("x").asInt();
                    int y = point.getMember("y").asInt();

                    double centerX = (x * tileSize) + (tileSize / 2.0);
                    double centerY = (y * tileSize) + (tileSize / 2.0);

                    if (i == 0) {
                        gc.moveTo(centerX, centerY);
                    } else {
                        gc.lineTo(centerX, centerY);
                    }
                }
                gc.stroke();
            } else {
                System.out.println("Nie znaleziono drogi!");
            }
        } catch (Exception e) {
            System.err.println("Błąd z drogą!");
            e.printStackTrace();
        }
    }

    private void renderMap(Value jsMap) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        gc.setLineWidth(1);
        int size = (int) jsMap.getArraySize();

        for (int y = 0; y < size; y++) {
            Value row = jsMap.getArrayElement(y);
            for (int x = 0; x < size; x++) {
                Value tile = row.getArrayElement(x);
                Value ways = tile.getMember("ways");
                int hasObstacle = tile.getMember("hasObstacle").asInt();

                double px = x * tileSize;
                double py = y * tileSize;

                gc.setFill(Color.LAWNGREEN);
                gc.fillRect(px, py, tileSize, tileSize);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(px, py, tileSize, tileSize);
                gc.setFill(Color.BISQUE);
                double center = tileSize / 2.0;
                double halfPath = pathWidth / 2.0;

                if (ways.getArrayElement(0).asInt() == 1) gc.fillRect(px + center - halfPath, py, pathWidth, center);
                if (ways.getArrayElement(1).asInt() == 1) gc.fillRect(px + center, py + center - halfPath, center, pathWidth);
                if (ways.getArrayElement(2).asInt() == 1) gc.fillRect(px + center - halfPath, py + center, pathWidth, center);
                if (ways.getArrayElement(3).asInt() == 1) gc.fillRect(px, py + center - halfPath, center, pathWidth);

                if (hasObstacle != -1) {
                    gc.setFill(Color.SADDLEBROWN);
                    double obsLength = 16;
                    double obsThickness = pathWidth + 6;

                    switch (hasObstacle) {
                        case 0:
                            gc.fillRect(px + center - obsThickness / 2, py + center / 2 - obsLength / 2, obsThickness, obsLength);
                            break;
                        case 1:
                            gc.fillRect(px + center + center / 2 - obsLength / 2, py + center - obsThickness / 2, obsLength, obsThickness);
                            break;
                        case 2:
                            gc.fillRect(px + center - obsThickness / 2, py + center + center / 2 - obsLength / 2, obsThickness, obsLength);
                            break;
                        case 3:
                            gc.fillRect(px + center / 2 - obsLength / 2, py + center - obsThickness / 2, obsLength, obsThickness);
                            break;
                    }
                }
            }
        }
    }
}