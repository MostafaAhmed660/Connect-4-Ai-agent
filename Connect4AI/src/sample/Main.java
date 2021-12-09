package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main extends Application {
    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private static boolean redMove = true;
    public static int [] counterWins = {0,0};
    public static int [] counterMoves = {0,0};
    private static Disc[][] grid = new Disc[COLUMNS][ROWS];
    private static Pane discRoot = new Pane();
    
    // Building The Outline Borders 
    
    public static Parent createContent() {
        Pane root = new Pane();
        root.getChildren().add(discRoot);
        
        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }

    // Building The Blue Background and the White Circle Holes of the game
    
    public static Shape makeGrid() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);

                shape = Shape.subtract(shape, circle);
            }
        }

        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        shape.setFill(Color.BLUE);
        shape.setEffect(lighting);

        return shape;
    }

    // Making rectangle glows when mouse points on the columns 
    
    public static List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            rect.setOnMouseClicked(e -> {
				try {
				    //human play
					placeDisc(new Disc(redMove), column);

					//then ai turn
                    int[][] initialState = getGridNumbers();
                    Board.setState(initialState);

                    long start = System.currentTimeMillis();
                    Node root = Board.take_decision();
                    long end=System.currentTimeMillis();

                    float time=(end-start)/1000F;
                    System.out.println("Time consumed: "+time+" seconds");
                    System.out.println("number of Nodes Explored : "+Board.getExploredNodes()+"\n\n");

                    Board.print_tree(root);

                    int aiChoice = Board.getColumnNumber(initialState) ;
                    if (aiChoice != -1) {
                        placeDisc(new Disc(redMove), aiChoice);
                    }
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			});

            list.add(rect);
        }
        return list;
    }

    // Placing colored discs in the grid
    
    public static void placeDisc(Disc disc, int column) throws InterruptedException {
        int row = ROWS - 1;
        do {
            if (!getDisc(column, row).isPresent())
                break;
            row--;
        } while (row >= 0);

        if (row < 0)
            return;

        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);

        final int currentRow = row;
        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
        animation.setOnFinished(e -> { });
        animation.play();

        if (turnEnded(column, currentRow)) {
            //gameOver();

            if (redMove) {
                counterMoves[0] = counterMoves[0] + 1;
            }
            else {
                counterMoves[1] = counterMoves[1] + 1;
            }
                /*int total = counterMoves[1] + counterMoves[0];
            	Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Number Of Played Moves");
        		alert.setHeaderText("Total Moves = " + total + " Out Of 42 Moves");
        		alert.setContentText(SampleController.getPlayerRedName()  + " (Red) Score = " + counterWins[0] + "\n" + "Computer" + " (Yellow) Score = " + counterWins[1]);
        		alert.show();
                */

            System.out.println("k :  "+Board.getBranching_depth_k());
            System.out.println("with alpha beta :  "+Board.bruning());
            System.out.println("Player"  + "(Red) Score = " + counterWins[0]);
            System.out.println("Computer" + "(Yellow) Score = " + counterWins[1]);
            int[][] ans  = getGridNumbers();
            for (int i = 0 ; i < ROWS ; i++) {
                for (int j = 0 ; j < COLUMNS ; j++) {
                    System.out.print(ans[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }


        int movesToEnd = (COLUMNS*ROWS)/2;
        boolean falg = ((COLUMNS*ROWS) % 2 == 0 ? true : false) , gameOver = false;
        if (falg  && counterMoves[0] == movesToEnd && counterMoves[1] == movesToEnd)
            gameOver =true ;
        else if (!falg && (counterMoves[0] == movesToEnd+1 || counterMoves[1] == movesToEnd+1))
            gameOver =true ;

        //if the game is over print result
        if (gameOver) {
            if (counterWins[0] > counterWins[1]) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("GameOver");
                alert.setHeaderText("Congratulations!");
                alert.setContentText(SampleController.getPlayerRedName()  + " (Red) Wins = " + counterWins[0]);
                alert.show();
                //System.out.println("Congratulations! " + SampleController.getPlayerRedName()  + "(Red) Wins = " + counterWins[0]);
            }
            else if (counterWins[0] == counterWins[1]) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("GameOver");
                alert.setHeaderText("Draw!");
                alert.setContentText(SampleController.getPlayerRedName()  + " (Red) Score = " + counterWins[0] + " & "  + "Computer" + " (Yellow) Score = " + counterWins[1]);
                alert.show();
                //System.out.println("Draw! " + SampleController.getPlayerRedName()  + " (Red) Score = " + counterWins[0] + ","  + "Computer" + " (Yellow) Score = " + counterWins[1]);
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("GameOver");
                alert.setHeaderText("Lose :(");
                alert.setContentText("Computer" + "(Yellow) Wins = " + counterWins[1]);
                alert.show();
                //System.out.println("Lose! " + "Computer" + "(Yellow) Wins = " + counterWins[1]);
            }
        }
        redMove = !redMove ;
    }
    
    // Placing colored discs in the grid

    public static boolean turnEnded(int column, int row) {
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(column, r))
                .collect(Collectors.toList());

        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());

        Point2D topLeft = new Point2D(column - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> topLeft.add(i, i))
                .collect(Collectors.toList());

        Point2D botLeft = new Point2D(column - 3, row + 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> botLeft.add(i, -i))
                .collect(Collectors.toList());
        
        checkRange(vertical);
        checkRange(horizontal);
        checkRange(diagonal1);
        checkRange(diagonal2);

        return true;
    }

    public static void checkRange(List<Point2D> points) {
        int chain = 0;
        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if (disc.red == redMove) {
                chain++;
                if (chain == 4) {
                	if (redMove) {
                    	counterWins[0] = counterWins[0] + 1;                		
                	}
                	else {
                    	counterWins[1] = counterWins[1] + 1;                		               		
                	}
                    //return true;
                }
            } else {
                chain = 0;
            }
        }

        //return false;
    }

    
    
    // Getting the existing disc in certain cell (column, row)
    
    private static Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= COLUMNS || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[column][row]);
    }
    
    // Identifying The Colored Inserted Disks (Red & Yellow)
    
    public static class Disc extends Circle {
        private final boolean red;
        public Disc(boolean red) {
            super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
            this.red = red;
            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
        public boolean getColor() {
        	return this.red ;
        }
    }

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,750,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Connect 4 Game");
			primaryStage.centerOnScreen();
			primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Converting the grid array to number format 
	
	public static int[][] getGridNumbers(){
       
        int[][] ans = new int[ROWS][COLUMNS];
        int newColumn = 0 , newRow = 0 ;
        for (int oldColumn = ROWS-1 ; oldColumn >= 0;oldColumn--){
            newColumn = 0 ;
            for (int oldrow = 0 ; oldrow < COLUMNS ; oldrow++){
            	if (grid[oldrow][oldColumn] == null)
            		ans[newRow][newColumn] = 0;
            	else if (grid[oldrow][oldColumn].getColor() == true)
            		ans[newRow][newColumn] = 2;
            	else
            		ans[newRow][newColumn] = 1;
                newColumn++;
            }
            newRow++;
        }
        return ans;
    }

	
	public static void main(String[] args) { launch(args); }
}
