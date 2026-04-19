/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 24th, 2-26							  	*/
/* Course: CS211-01											*/ 
/* VERSION 2												*/
/* Filename: MenuUI.java 									*/
/* Purpose: This program creates all UIs for the player		*/
/* 			to interact with. The title screen, pause menu, */
/* 			shop menu, and sell menu. The shop				*/
/* 			and sell menus both interact with the shop 		*/
/* 			class and the inventory class, adding seeds or  */
/* 			removing seeds whenever a player buys, sells	*/
/* 			or harvests a crop								*/
/************************************************************/

package application;

import javafx.scene.image.Image;  
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import javafx.application.Platform;

public class MenuUI 
{

	private Prototype prototype;
	private Inventory playerInventory;
	private Shop shop;
	
	private StackPane root;
	private Pane uiLayer;
	private Pane overlay;
	private Pane menu;
	private Pane pause;
	private Pane shopUI;
	private Pane buy;
	private Pane sell;
	private Pane purchase;
	
	private Image bg;
	private ImageView bgView;
	
	//buttons
	private Image playButtonNormal;
	private Image playButtonHover;
	private Image playButtonPressed;
	private ImageView playButton;
	
	private Image menuNormal;
	private Image menuHover;
	private Image menuPressed;
	private ImageView menuButton;
	
	private Image quitNormal;
	private Image quitHover;
	private Image quitPressed;
	private ImageView quitButton;
	
	private Image xNormal;
	private Image xHover;
	private Image xPressed;

	private Image resumeNormal;
	private Image resumeHover;
	private Image resumePressed;
	private ImageView resumeButton;
	
	private Image exitNormal;
	private Image exitHover;
	private Image exitPressed;
	private ImageView exitButton;
	
	private Image buyNormal;
	private Image buyHover;
	private Image buyPressed;
	private ImageView buyButton;
	
	private Image sellNormal;
	private Image sellHover;
	private Image sellPressed;
	private ImageView sellButton;
	
	private Image yesNormal;
	private Image yesHover;
	private Image yesPressed;
	private ImageView yesButton;
	
	private Image noNormal;
	private Image noHover;
	private Image noPressed;
	private ImageView noButton;
	
	private Image okNormal;
	private Image okHover;
	private Image okPressed;
	private ImageView okButton;
	
	private Image cancelNormal;
	private Image cancelHover;
	private Image cancelPressed;
	private ImageView cancelButton;
	
	private Image[] carrot;
	private ImageView carrotIcon;
	
	private Image[] corn;
	private ImageView cornIcon;
	
	private Image[] strawberry;
	private ImageView strawberryIcon;
	
	private Image[] tomato;
	private ImageView tomatoIcon;
	
	//title (F A R M E R S H A V E N)
	private Image f;
	private Image a;
	private Image r;
	private Image m;
	private Image e;
	private Image s;
	private Image h;
	private Image v;
	private Image n;
	
	//UI menu for shop/help/settings
	private Image topLeftUI;
	private Image topMidUI;
	private Image topRightUI;
	private Image midLeftUI;
	private Image midUI;
	private Image midRightUI;
	private Image botLeftUI;
	private Image botMidUI;
	private Image botRightUI;
	private Image butTopLeft;
	private Image butTopMid;
	private Image butTopRight;
	private Image butBotLeft;
	private Image butBotMid;
	private Image butBotRight;
	
	
	//fonts
	private Font pixelFont;
	
	Text selected_item;
	
	//flags
	private boolean settingsOpenedFromPause = false;
	
	public MenuUI(Prototype prototype, Inventory inventory, Shop shop){
		this.prototype = prototype;
		this.playerInventory = inventory;
		this.shop = shop;
		
		root = new StackPane();
		uiLayer = new Pane();
		overlay = new Pane();
		menu = new Pane();
		pause = new Pane();
		shopUI = new Pane();
		buy = new Pane();
		sell = new Pane();
		purchase = new Pane();
		
		pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Minecraft.ttf"), 24);

		root.setPrefSize(900, 700);
		overlay.setStyle("-fx-background-color: rgba(0,0,0,0.2);");
		
		loadImages();
		buildBackground();
		
		buildTitle();
		hitPlay();
		buildMenu();
		buildMenuButton();
		buildQuitButton();
		buildPauseMenu();
		buildBuy();
		buildSell();
		buildShop();
		buildBuyMenu();

	    menu.setVisible(false);
	    menu.setMouseTransparent(true);
	    pause.setVisible(false);
	    pause.setMouseTransparent(true);
	    buy.setVisible(false);
	    buy.setMouseTransparent(true);
	    shopUI.setVisible(false);
	    shopUI.setMouseTransparent(false);
	    sell.setVisible(false);
	    sell.setMouseTransparent(true);
	    purchase.setVisible(false);
	    purchase.setMouseTransparent(true);
	    
	    root.getChildren().addAll(bgView, overlay, uiLayer, menu, pause, shopUI, buy, sell, purchase);

		
	}
	
	public void loadImages() {
		
		bg = new Image(getClass().getResource("/Title/Background.png").toExternalForm());
	
		//buttons
		playButtonNormal = new Image(getClass().getResource("/assets/tiles/buttons/play_normal.png").toExternalForm());
		playButtonHover = new Image(getClass().getResource("/assets/tiles/buttons/play_hover.png").toExternalForm());
		playButtonPressed = new Image(getClass().getResource("/assets/tiles/buttons/play_pressed.png").toExternalForm());
		
		menuNormal = new Image(getClass().getResource("/assets/tiles/buttons/menu_normal.png").toExternalForm());
		menuHover = new Image(getClass().getResource("/assets/tiles/buttons/menu_hover.png").toExternalForm());
		menuPressed = new Image(getClass().getResource("/assets/tiles/buttons/menu_pressed.png").toExternalForm());
		
		quitNormal = new Image(getClass().getResource("/assets/tiles/buttons/quit_normal.png").toExternalForm());
		quitHover = new Image(getClass().getResource("/assets/tiles/buttons/quit_hover.png").toExternalForm());
		quitPressed = new Image(getClass().getResource("/assets/tiles/buttons/quit_pressed.png").toExternalForm());
		
		xNormal = new Image(getClass().getResource("/assets/tiles/buttons/x_normal.png").toExternalForm());
		xHover = new Image(getClass().getResource("/assets/tiles/buttons/x_hover.png").toExternalForm());
		xPressed = new Image(getClass().getResource("/assets/tiles/buttons/x_pressed.png").toExternalForm());
		
		resumeNormal = new Image(getClass().getResource("/assets/tiles/buttons/resume_normal.png").toExternalForm());
		resumeHover = new Image(getClass().getResource("/assets/tiles/buttons/resume_hover.png").toExternalForm());
		resumePressed = new Image(getClass().getResource("/assets/tiles/buttons/resume_pressed.png").toExternalForm());
		
		exitNormal = new Image(getClass().getResource("/assets/tiles/buttons/exit_normal.png").toExternalForm());
		exitHover = new Image(getClass().getResource("/assets/tiles/buttons/exit_hover.png").toExternalForm());
		exitPressed = new Image(getClass().getResource("/assets/tiles/buttons/exit_pressed.png").toExternalForm());
		
		buyNormal = new Image(getClass().getResource("/assets/tiles/buttons/buy_normal.png").toExternalForm());
		buyHover = new Image(getClass().getResource("/assets/tiles/buttons/buy_hover.png").toExternalForm());
		buyPressed= new Image(getClass().getResource("/assets/tiles/buttons/buy_pressed.png").toExternalForm());
		
		sellNormal = new Image(getClass().getResource("/assets/tiles/buttons/sell_normal.png").toExternalForm());
		sellHover = new Image(getClass().getResource("/assets/tiles/buttons/sell_hover.png").toExternalForm());
		sellPressed = new Image(getClass().getResource("/assets/tiles/buttons/sell_pressed.png").toExternalForm());
		
		yesNormal = new Image(getClass().getResource("/assets/tiles/buttons/yes_normal.png").toExternalForm());
		yesHover = new Image(getClass().getResource("/assets/tiles/buttons/yes_hover.png").toExternalForm());
		yesPressed = new Image(getClass().getResource("/assets/tiles/buttons/yes_pressed.png").toExternalForm());
		
		noNormal = new Image(getClass().getResource("/assets/tiles/buttons/no_normal.png").toExternalForm());
		noHover = new Image(getClass().getResource("/assets/tiles/buttons/no_hover.png").toExternalForm());
		noPressed = new Image(getClass().getResource("/assets/tiles/buttons/no_pressed.png").toExternalForm());
		
		okNormal = new Image(getClass().getResource("/assets/tiles/buttons/ok_normal.png").toExternalForm());
		okHover = new Image(getClass().getResource("/assets/tiles/buttons/ok_hover.png").toExternalForm());
		okPressed = new Image(getClass().getResource("/assets/tiles/buttons/ok_pressed.png").toExternalForm());
		
		cancelNormal = new Image(getClass().getResource("/assets/tiles/buttons/cancel_normal.png").toExternalForm());
		cancelHover = new Image(getClass().getResource("/assets/tiles/buttons/cancel_hover.png").toExternalForm());
		cancelPressed = new Image(getClass().getResource("/assets/tiles/buttons/cancel_pressed.png").toExternalForm());
		
		//UI
		topLeftUI = new Image(getClass().getResource("/assets/tiles/UI/top_left_ui.png").toExternalForm());
		topMidUI = new Image(getClass().getResource("/assets/tiles/UI/top_mid_ui.png").toExternalForm()); 
		topRightUI = new Image(getClass().getResource("/assets/tiles/UI/top_right_ui.png").toExternalForm());
		midLeftUI = new Image(getClass().getResource("/assets/tiles/UI/mid_left_ui.png").toExternalForm());
		midUI = new Image(getClass().getResource("/assets/tiles/UI/mid_ui.png").toExternalForm());
		midRightUI = new Image(getClass().getResource("/assets/tiles/UI/mid_right_ui.png").toExternalForm());
		botLeftUI = new Image(getClass().getResource("/assets/tiles/UI/bot_left_ui.png").toExternalForm());
		botMidUI = new Image(getClass().getResource("/assets/tiles/UI/bot_mid_ui.png").toExternalForm());
		botRightUI = new Image(getClass().getResource("/assets/tiles/UI/bot_right_ui.png").toExternalForm());
		
		butTopLeft = new Image(getClass().getResource("/assets/tiles/UI/but_top_left_ui.png").toExternalForm()); 
		butTopMid = new Image(getClass().getResource("/assets/tiles/UI/but_top_mid_ui.png").toExternalForm());
		butTopRight = new Image(getClass().getResource("/assets/tiles/UI/but_top_right_ui.png").toExternalForm());
		butBotLeft = new Image(getClass().getResource("/assets/tiles/UI/but_bot_left_ui.png").toExternalForm()); 
		butBotMid = new Image(getClass().getResource("/assets/tiles/UI/but_bot_mid_ui.png").toExternalForm());
		butBotRight = new Image(getClass().getResource("/assets/tiles/UI/but_bot_right_ui.png").toExternalForm());
		
		carrot = new Image[] {
				new Image(getClass().getResource("/assets/tiles/UI/carrot_deselect.png").toExternalForm()),
				new Image(getClass().getResource("/assets/tiles/UI/carrot_select.png").toExternalForm())
		};
		corn = new Image[] {
				new Image(getClass().getResource("/assets/tiles/UI/corn_deselect.png").toExternalForm()),
				new Image(getClass().getResource("/assets/tiles/UI/corn_select.png").toExternalForm())
		};
		strawberry = new Image[] {
				new Image(getClass().getResource("/assets/tiles/UI/strawberry_deselect.png").toExternalForm()),
				new Image(getClass().getResource("/assets/tiles/UI/strawberry_select.png").toExternalForm())
		};
		tomato = new Image[] {
				new Image(getClass().getResource("/assets/tiles/UI/tomato_deselect.png").toExternalForm()),
				new Image(getClass().getResource("/assets/tiles/UI/tomato_select.png").toExternalForm())
		};
		
		//title
		f = new Image(getClass().getResource("/Title/F.png").toExternalForm());
		a = new Image(getClass().getResource("/Title/A.png").toExternalForm());
		r = new Image(getClass().getResource("/Title/R.png").toExternalForm());
		m = new Image(getClass().getResource("/Title/M.png").toExternalForm());
		e = new Image(getClass().getResource("/Title/E.png").toExternalForm());
		s = new Image(getClass().getResource("/Title/S.png").toExternalForm());
		h = new Image(getClass().getResource("/Title/H.png").toExternalForm());
		v = new Image(getClass().getResource("/Title/V.png").toExternalForm());
		n = new Image(getClass().getResource("/Title/N.png").toExternalForm());
		
	}
	
	
	public void buildBackground() {
		bgView = new ImageView(bg);
		bgView.setPreserveRatio(false);
		bgView.fitWidthProperty().bind(root.widthProperty());
        bgView.fitHeightProperty().bind(root.heightProperty());
	}
	
	public void buildTitle() {
		
		Image[] titleRow1 = { f, a, r, m, e, r, s };

		// exact positions you already figured out
		double[] xPositions1 = { 
		    98,   // F
		    287,  // A
		    479,  // R
		    670,  // M (bigger)
		    890,  // E
		    1080, // R
		    1270  // S
		};

		double y1 = 0;

		for (int i = 0; i < titleRow1.length; i++) {
		    ImageView letter = new ImageView(titleRow1[i]);

		    letter.setSmooth(false);
		    letter.setPreserveRatio(false);

		    letter.setX(xPositions1[i]);
		    letter.setY(y1);

		    uiLayer.getChildren().add(letter);
		}

		Image[] titleRow2 = { h, a, v, e, n };

		double[] xPositions2 = {
		    287,
		    479,
		    670,
		    890,
		    1080
		};

		double y2 = 192;

		for (int i = 0; i < titleRow2.length; i++) {
		    ImageView letter = new ImageView(titleRow2[i]);

		    letter.setSmooth(false);
		    letter.setPreserveRatio(false);

		    letter.setX(xPositions2[i]);
		    letter.setY(y2);

		    uiLayer.getChildren().add(letter);
		}
	}
	
	public void buildMenu() {
		buildBox(menu, 484, 142);
		
		Text settingsTitle = new Text("SETTINGS");
		settingsTitle.setFont(Font.font(pixelFont.getFamily(), 68));
		settingsTitle.setFill(Color.WHITE);
		settingsTitle.setX(545);
		settingsTitle.setY(235);
		
		Text musicText = new Text("MUSIC:");
		musicText.setFont(Font.font(pixelFont.getFamily(), 48));
		musicText.setFill(Color.WHITE);
		musicText.setX(545);
		musicText.setY(400);
		
		ImageView settingsCloseButton = new ImageView(xNormal);

		settingsCloseButton.setSmooth(false);
		settingsCloseButton.setPreserveRatio(false);
		settingsCloseButton.setX(950);
		settingsCloseButton.setY(180);

		settingsCloseButton.setOnMouseEntered(e -> settingsCloseButton.setImage(xHover));
		settingsCloseButton.setOnMouseExited(e -> settingsCloseButton.setImage(xNormal));
		settingsCloseButton.setOnMousePressed(e -> settingsCloseButton.setImage(xPressed));

		settingsCloseButton.setOnMouseReleased(e -> {
		    if (settingsCloseButton.contains(settingsCloseButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		        settingsCloseButton.setImage(xHover);
		        closeSettings();
		        if (settingsOpenedFromPause) {
		            openPauseMenu();
		            settingsOpenedFromPause = false;
		        }
		    } else {
		        settingsCloseButton.setImage(xNormal);
		    }
		});

		menu.getChildren().addAll(settingsTitle, musicText, settingsCloseButton);

	}
	
	public void buildBuyMenu() {
		buildButtonBox(purchase, 484, 600);
		selected_item = new Text();
		selected_item.setFont(Font.font(pixelFont.getFamily(), 35));
		selected_item.setFill(Color.WHITE);
		selected_item.setX(510);
		selected_item.setY(690);
		
		cancelButton = new ImageView(cancelNormal);
		cancelButton.setSmooth(false);
		cancelButton.setPreserveRatio(false);
		cancelButton.setX(845);
		cancelButton.setY(750);

		cancelButton.setOnMouseEntered(e -> cancelButton.setImage(cancelHover));
		cancelButton.setOnMouseExited(e -> cancelButton.setImage(cancelNormal));
		cancelButton.setOnMousePressed(e -> cancelButton.setImage(cancelPressed));

		cancelButton.setOnMouseReleased(e -> {
		    if (cancelButton.contains(cancelButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	cancelButton.setImage(cancelHover);
		        closePurchase();
		    } else {
		    	cancelButton.setImage(cancelNormal);
		    }
		});
		
		okButton = new ImageView(okNormal);
		okButton.setSmooth(false);
		okButton.setPreserveRatio(false);
		okButton.setX(510);
		okButton.setY(750);

		okButton.setOnMouseEntered(e -> okButton.setImage(okHover));
		okButton.setOnMouseExited(e -> okButton.setImage(okNormal));
		okButton.setOnMousePressed(e -> okButton.setImage(okPressed));

		okButton.setOnMouseReleased(e -> {
		    if (okButton.contains(okButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	okButton.setImage(okHover);
		    	
		    	boolean success = shop.buySelected(playerInventory);
		    	System.out.println(playerInventory.getCoins());
		    	System.out.println(shop.getSelectedPrice());
		   		System.out.println(shop.getSelectedCrop());
		        if (success) {
		    		System.out.println("Purchased " + shop.getSelectedCrop());
		    		closePurchase();
		        }
		        else {
		        	selected_item.setText("Insufficient funds");
		        	System.out.println("Purchasing failed");
		        }
		        System.out.println("Coins after: " + playerInventory.getCoins());
		    } else {
		    	okButton.setImage(okNormal);
		    }
		});
		
		
		purchase.getChildren().addAll(selected_item, okButton, cancelButton);
		
	}
	
	public void buildShop() {
		buyButton = new ImageView(buyNormal);

		buyButton.setSmooth(false);
		buyButton.setPreserveRatio(false);
		buyButton.setX(400);
		buyButton.setY(600);

		buyButton.setOnMouseEntered(e -> buyButton.setImage(buyHover));
		buyButton.setOnMouseExited(e -> buyButton.setImage(buyNormal));
		buyButton.setOnMousePressed(e -> buyButton.setImage(buyPressed));

		buyButton.setOnMouseReleased(e -> {
		    if (buyButton.contains(buyButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	buyButton.setImage(buyHover);
		        openBuy();
		    } else {
		    	buyButton.setImage(buyNormal);
		    }
		});
		
		sellButton = new ImageView(sellNormal);

		sellButton.setSmooth(false);
		sellButton.setPreserveRatio(false);
		sellButton.setX(960);
		sellButton.setY(600);

		sellButton.setOnMouseEntered(e -> sellButton.setImage(sellHover));
		sellButton.setOnMouseExited(e -> sellButton.setImage(sellNormal));
		sellButton.setOnMousePressed(e -> sellButton.setImage(sellPressed));

		sellButton.setOnMouseReleased(e -> {
		    if (sellButton.contains(sellButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	sellButton.setImage(sellHover);
		        openSell();
		    } else {
		    	sellButton.setImage(sellNormal);
		    }
		});
		
		ImageView shopCloseButton = new ImageView(exitNormal);

		shopCloseButton.setSmooth(false);
		shopCloseButton.setPreserveRatio(false);
		shopCloseButton.setX(680);
		shopCloseButton.setY(700);

		shopCloseButton.setOnMouseEntered(e -> shopCloseButton.setImage(exitHover));
		shopCloseButton.setOnMouseExited(e -> shopCloseButton.setImage(exitNormal));
		shopCloseButton.setOnMousePressed(e -> shopCloseButton.setImage(exitPressed));

		shopCloseButton.setOnMouseReleased(e -> {
		    if (shopCloseButton.contains(shopCloseButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		        closeShop();
		        prototype.resumeGame(this);
		    } else {
		    	shopCloseButton.setImage(exitNormal);
		    }
		});

		shopUI.getChildren().addAll(buyButton, sellButton, shopCloseButton);
	}
	
	public void buildBuy() {
		buildBox(buy, 484, 142);
			
		Text shopTitle = new Text("SHOP");
		shopTitle.setFont(Font.font(pixelFont.getFamily(), 68));
		shopTitle.setFill(Color.WHITE);
		shopTitle.setX(545);
		shopTitle.setY(235);
		
		carrotIcon = new ImageView(carrot[0]);
		cornIcon = new ImageView(corn[0]);
		strawberryIcon = new ImageView(strawberry[0]);
		tomatoIcon = new ImageView(tomato[0]);
		
		carrotIcon.setSmooth(false);
		carrotIcon.setPreserveRatio(false);
		carrotIcon.setX(525);
		carrotIcon.setY(260);
		carrotIcon.setOnMousePressed(e -> carrotIcon.setImage(carrot[1]));
		carrotIcon.setOnMouseReleased(e -> {
		    if (carrotIcon.contains(carrotIcon.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	deselectAll();
				carrotIcon.setImage(carrot[1]);
				shop.setSelection("carrot", 5);
				selected_item.setText("Purchase " + shop.getSelectedCrop() + " for $" + shop.getSelectedPrice() + "?");
				openPurchase();
		    }
		});
		
		
		cornIcon.setSmooth(false);
		cornIcon.setPreserveRatio(false);
		cornIcon.setX(695);
		cornIcon.setY(260);
		cornIcon.setOnMousePressed(e -> cornIcon.setImage(corn[1]));
		cornIcon.setOnMouseReleased(e -> {
		if (cornIcon.contains(cornIcon.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
			deselectAll();
			cornIcon.setImage(corn[1]);
			shop.setSelection("corn", 7);
			selected_item.setText("Purchase " + shop.getSelectedCrop() + " for $" + shop.getSelectedPrice() + "?");
			openPurchase();
		}
		});
		
		
		strawberryIcon.setSmooth(false);
		strawberryIcon.setPreserveRatio(false);
		strawberryIcon.setX(865);
		strawberryIcon.setY(260);
		strawberryIcon.setOnMousePressed(e -> strawberryIcon.setImage(strawberry[1]));
		strawberryIcon.setOnMouseReleased(e -> {
		if (strawberryIcon.contains(strawberryIcon.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
			deselectAll();
			strawberryIcon.setImage(strawberry[1]);
			shop.setSelection("strawberry", 2);
			selected_item.setText("Purchase " + shop.getSelectedCrop() + " for $" + shop.getSelectedPrice() + "?");
			openPurchase();
		}
		});
		
		tomatoIcon.setSmooth(false);
		tomatoIcon.setPreserveRatio(false);
		tomatoIcon.setX(525);
		tomatoIcon.setY(430);
		tomatoIcon.setOnMousePressed(e -> tomatoIcon.setImage(tomato[1]));
		tomatoIcon.setOnMouseReleased(e -> {
		if (tomatoIcon.contains(tomatoIcon.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
			deselectAll();
			tomatoIcon.setImage(tomato[1]);
			shop.setSelection("tomato", 5);
			selected_item.setText("Purchase " + shop.getSelectedCrop() + " for $" + shop.getSelectedPrice() + "?");
			openPurchase();
		}
		});
		
		ImageView buyCloseButton = new ImageView(xNormal);

		buyCloseButton.setSmooth(false);
		buyCloseButton.setPreserveRatio(false);
		buyCloseButton.setX(950);
		buyCloseButton.setY(180);

		buyCloseButton.setOnMouseEntered(e -> buyCloseButton.setImage(xHover));
		buyCloseButton.setOnMouseExited(e -> buyCloseButton.setImage(xNormal));
		buyCloseButton.setOnMousePressed(e -> buyCloseButton.setImage(xPressed));

		buyCloseButton.setOnMouseReleased(e -> {
		    if (buyCloseButton.contains(buyCloseButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		        deselectAll();
		    	closeBuy();
		    } else {
		    	buyCloseButton.setImage(xNormal);	
		    }
		});

		buy.getChildren().addAll(shopTitle, buyCloseButton, carrotIcon, cornIcon, strawberryIcon, tomatoIcon);
	}
	
	public void buildSell() {
		
		Text sellText = new Text("Sell Inventory?");
		sellText.setFont(Font.font(pixelFont.getFamily(), 68));
		sellText.setFill(Color.WHITE);
		sellText.setX(540);
		sellText.setY(550);
		
		yesButton = new ImageView(yesNormal);
		yesButton.setSmooth(false);
		yesButton.setPreserveRatio(false);
		yesButton.setX(400);
		yesButton.setY(600);

		yesButton.setOnMouseEntered(e -> yesButton.setImage(yesHover));
		yesButton.setOnMouseExited(e -> yesButton.setImage(yesNormal));
		yesButton.setOnMousePressed(e -> yesButton.setImage(yesPressed));

		yesButton.setOnMouseReleased(e -> {
		    if (yesButton.contains(yesButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	yesButton.setImage(yesHover);
		        System.out.println("Selling inventory");
		        closeSell();
		    } else {
		    	yesButton.setImage(yesNormal);
		    }
		});
		
		noButton = new ImageView(noNormal);

		noButton.setSmooth(false);
		noButton.setPreserveRatio(false);
		noButton.setX(960);
		noButton.setY(600);

		noButton.setOnMouseEntered(e -> noButton.setImage(noHover));
		noButton.setOnMouseExited(e -> noButton.setImage(noNormal));
		noButton.setOnMousePressed(e -> noButton.setImage(noPressed));

		noButton.setOnMouseReleased(e -> {
		    if (noButton.contains(noButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
		    	noButton.setImage(noHover);
		    	closeSell();
		    } else {
		    	noButton.setImage(noNormal);
		    }
		});

		sell.getChildren().addAll(yesButton, noButton, sellText);
	}
	
	public void hitPlay() {
		playButton = new ImageView(playButtonNormal);
		
		playButton.setSmooth(false);
		playButton.setPreserveRatio(false);
		
		
		//position
		playButton.setX(680);
		playButton.setY(450);
		
		//hover
		playButton.setOnMouseEntered(e -> {playButton.setImage(playButtonHover);});
	
		//normal when mouse leaves
		playButton.setOnMouseExited(e -> {playButton.setImage(playButtonNormal);});
		
		playButton.setOnMousePressed(e -> {playButton.setImage(playButtonPressed);});
		
		//click release
		playButton.setOnMouseReleased(e -> {
			if (playButton.contains(playButton.sceneToLocal(e.getSceneX(), e.getSceneY())))
			{
				playButton.setImage(playButtonHover);
				prototype.startGame(this);

//				
//				//hide whole menu
//				root.setVisible(false);
//				root.setMouseTransparent(true);
			}
			else {
				playButton.setImage(playButtonNormal);
			}
		});
		
		uiLayer.getChildren().add(playButton);
	}

	public void buildMenuButton() {
		menuButton = new ImageView(menuNormal);
		
		menuButton.setSmooth(false);
		menuButton.setPreserveRatio(false);
		
		
		//position
		menuButton.setX(680);
		menuButton.setY(550);
		
		//hover
		menuButton.setOnMouseEntered(e -> {menuButton.setImage(menuHover);});
	
		//normal when mouse leaves
		menuButton.setOnMouseExited(e -> {menuButton.setImage(menuNormal);});
		
		menuButton.setOnMousePressed(e -> {menuButton.setImage(menuPressed);});
		
		//click release
		menuButton.setOnMouseReleased(e -> {
			if (menuButton.contains(menuButton.sceneToLocal(e.getSceneX(), e.getSceneY())))
			{		
				settingsOpenedFromPause = false;
				openSettings();
			}
			else {
				menuButton.setImage(menuNormal);
			}
		});
		
		uiLayer.getChildren().add(menuButton);
	}
	
	public void buildQuitButton() {
		quitButton = new ImageView(quitNormal);
		
		quitButton.setSmooth(false);
		quitButton.setPreserveRatio(false);
		
		
		//position
		quitButton.setX(680);
		quitButton.setY(650);
		
		//hover
		quitButton.setOnMouseEntered(e -> {quitButton.setImage(quitHover);});
	
		//normal when mouse leaves
		quitButton.setOnMouseExited(e -> {quitButton.setImage(quitNormal);});
		
		quitButton.setOnMousePressed(e -> {quitButton.setImage(quitPressed);});
		
		//click release
		quitButton.setOnMouseReleased(e -> {
			if (quitButton.contains(quitButton.sceneToLocal(e.getSceneX(), e.getSceneY())))
			{
				quitGame();
			}
			else {
				quitButton.setImage(quitNormal);
			}
		});
		
		uiLayer.getChildren().add(quitButton);
	}
	
	public void buildPauseMenu() {
		Text pauseTitle = new Text("PAUSED");
        pauseTitle.setFont(Font.font(pixelFont.getFamily(), 48));
        pauseTitle.setFill(Color.WHITE);
        pauseTitle.setX(680);
        pauseTitle.setY(235);

        resumeButton = new ImageView(resumeNormal);
        resumeButton.setSmooth(false);
        resumeButton.setPreserveRatio(false);
        resumeButton.setX(680);
        resumeButton.setY(330);

        resumeButton.setOnMouseEntered(e -> resumeButton.setImage(resumeHover));
        resumeButton.setOnMouseExited(e -> resumeButton.setImage(resumeNormal));
        resumeButton.setOnMousePressed(e -> resumeButton.setImage(resumePressed));
        resumeButton.setOnMouseReleased(e -> {
            if (resumeButton.contains(resumeButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
                resumeButton.setImage(resumeHover);
                prototype.resumeGame(this);
            } else {
                resumeButton.setImage(resumeNormal);
            }
        });

        ImageView pauseSettingsButton = new ImageView(menuNormal);
        pauseSettingsButton.setSmooth(false);
        pauseSettingsButton.setPreserveRatio(false);
        pauseSettingsButton.setX(680);
        pauseSettingsButton.setY(450);

        pauseSettingsButton.setOnMouseEntered(e -> pauseSettingsButton.setImage(menuHover));
        pauseSettingsButton.setOnMouseExited(e -> pauseSettingsButton.setImage(menuNormal));
        pauseSettingsButton.setOnMousePressed(e -> pauseSettingsButton.setImage(menuPressed));
        pauseSettingsButton.setOnMouseReleased(e -> {
            if (pauseSettingsButton.contains(pauseSettingsButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
                pauseSettingsButton.setImage(menuHover);
                settingsOpenedFromPause = true;
                closePauseMenu();
                openSettings();
                overlay.setVisible(true);
                
            } else {
                pauseSettingsButton.setImage(menuNormal);
            }
        });

        exitButton = new ImageView(exitNormal);
        exitButton.setSmooth(false);
        exitButton.setPreserveRatio(false);
        exitButton.setX(680);
        exitButton.setY(570);

        exitButton.setOnMouseEntered(e -> exitButton.setImage(exitHover));
        exitButton.setOnMouseExited(e -> exitButton.setImage(exitNormal));
        exitButton.setOnMousePressed(e -> exitButton.setImage(exitPressed));
        exitButton.setOnMouseReleased(e -> {
            if (exitButton.contains(exitButton.sceneToLocal(e.getSceneX(), e.getSceneY()))) {
                exitButton.setImage(exitHover);
                prototype.returnToTitle(this);
            } else {
                exitButton.setImage(exitNormal);
            }
        });

        pause.getChildren().addAll(pauseTitle, resumeButton, pauseSettingsButton, exitButton);
    }
 
	
	public void buildBox(Pane targetPane, double startX, double startY) {
		Image[] topRow = {topLeftUI, topMidUI, topRightUI};
		Image[] middleRow = {midLeftUI, midUI, midRightUI};
		Image[] bottomRow = {botLeftUI, botMidUI, botRightUI};
		
		for (int i = 0; i < 3; i++) {
			ImageView top = new ImageView(topRow[i]);
			top.setX(startX + (i * 192));
			top.setY(startY);
			targetPane.getChildren().add(top);
			
			ImageView mid = new ImageView(middleRow[i]);
			mid.setX(startX + (i * 192));
			mid.setY(startY + 192);
			targetPane.getChildren().add(mid);
			
			ImageView bot = new ImageView(bottomRow[i]);
			bot.setX(startX + (i * 192));
			bot.setY(startY + 384);
			targetPane.getChildren().add(bot);
		}
	}
	
	public void buildButtonBox(Pane targetPane, double startX, double startY) {
		Image[] topRow = {butTopLeft, butTopMid, butTopRight};
		Image[] bottomRow = {butBotLeft, butBotMid, butBotRight};
		for (int i = 0; i < 3; i++) {
			ImageView top = new ImageView(topRow[i]);
			top.setX(startX + (i * 192));
			top.setY(startY);
			targetPane.getChildren().add(top);
			
			ImageView bot = new ImageView(bottomRow[i]);
			bot.setX(startX + (i * 192));
			bot.setY(startY+50);
			targetPane.getChildren().add(bot);
		}
		
	}
	
	public void closePauseMenu() {
		pause.setVisible(false);
		overlay.setVisible(false);
		pause.setMouseTransparent(true);	
	}

	public void openPauseMenu() {
		pause.setVisible(true);
		overlay.setVisible(true);
		pause.setMouseTransparent(false);
		pause.toFront();
	}

	
	public void closeSettings() {
		menu.setVisible(false);
		menu.setMouseTransparent(true);
	}
	public void openSettings() {
		menu.setVisible(true);
		menu.setMouseTransparent(false);
	}
	
	public void openBuy() {
		buy.setVisible(true);
		buy.setMouseTransparent(false);
		shopUI.setVisible(false);
		shopUI.setMouseTransparent(true);
	}
	
	public void openShop() {
		shopUI.setVisible(true);
		shopUI.setMouseTransparent(false);
	}
	public void closeBuy() {
			buy.setVisible(false);
			buy.setMouseTransparent(true);
			shopUI.setVisible(true);
			shopUI.setMouseTransparent(false);
	}
	
	public void closeShop() {
		shopUI.setVisible(false);
		shopUI.setMouseTransparent(true);
		buy.setVisible(false);
		buy.setMouseTransparent(true);
		sell.setVisible(false);
		sell.setMouseTransparent(true);
	}
	
	public void openSell() {
		sell.setVisible(true);
		sell.setMouseTransparent(false);
		shopUI.setVisible(false);
		shopUI.setMouseTransparent(true);
		
	}
	
	public void closeSell() {
		sell.setVisible(false);
		sell.setMouseTransparent(true);
		shopUI.setVisible(true);
		shopUI.setMouseTransparent(false);
	}
	
	public void quitGame() {
		Platform.exit();
	}
	
	public void hideTitleScreen() {
		bgView.setVisible(false);
		overlay.setVisible(false);
		uiLayer.setVisible(false);
		uiLayer.setMouseTransparent(true);
		
		menu.setVisible(false);
		menu.setMouseTransparent(true);
	}
	
	public void showTitleScreen() {
		bgView.setVisible(true);
		overlay.setVisible(true);
		uiLayer.setVisible(true);
		uiLayer.setMouseTransparent(false);
	}
	
	public void deselectAll() {
		carrotIcon.setImage(carrot[0]);
		cornIcon.setImage(corn[0]);
		strawberryIcon.setImage(strawberry[0]);
		tomatoIcon.setImage(tomato[0]);
	}
	
	public void openPurchase() {
		purchase.setVisible(true);
		purchase.setMouseTransparent(false);
	}
	public void closePurchase() {
		purchase.setVisible(false);
		purchase.setMouseTransparent(true);
	}
	
	public StackPane getRoot() {
		return root;
	}
}
