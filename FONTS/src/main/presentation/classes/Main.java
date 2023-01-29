package src.main.presentation.classes;

import src.main.presentation.controllers.PresentationController;

/**
 * Main class, it is called to start the graphical interface and the program
 * itself.
 * 
 * Class called in order to start the program.
 */
public class Main {

    public static void main(String[] args) {
        PresentationController.get_instance().openAndLoad();
        new MainView();
    }

}
