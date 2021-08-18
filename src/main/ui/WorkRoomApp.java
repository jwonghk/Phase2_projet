package ui;

import model.Category;
import model.Thingy;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents the workroom application
public class WorkRoomApp {
    private String JSON_STORE = "./data/workroom.json";
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String customername;

    // EFFECTS: constructs workroom and runs application
    public WorkRoomApp() throws FileNotFoundException {
        new_customer();
        //input = new Scanner(System.in);
        workRoom = new WorkRoom(customername);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWorkRoom();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWorkRoom() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: check if it is new customer, it it is, then create a new jsondata
    private void new_customer(){
        System.out.println("Are you first time here?");
        System.out.println("Press 1 for yes, and 0 for no");
        Scanner input = new Scanner(System.in);
        String yes_no;
        yes_no = input.next();

        System.out.println("What is your name?");
        Scanner nameinput = new Scanner(System.in);
        customername = input.next();

        if (yes_no.equals("1")){
            create_new_json();
        } else {
            load_old_json();
        }

    }

    //EFFECTS: create new .json file if new user
    public void create_new_json(){
        System.out.println("first time");
        JSON_STORE = "./data/" + customername + "workroom.json";

    }

    //EFFECTS: load old .json file
    public void load_old_json(){
        System.out.println("have been here");
        JSON_STORE = "./data/" + customername + "workroom.json";
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add thingy");
        System.out.println("\tp -> print thingies");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addThingy();
        } else if (command.equals("p")) {
            printThingies();
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt user for name and category of thingy and adds to workroom
    private void addThingy() {
        Category category = readCategory();
        System.out.println("Please enter name of thingy: ");
        String name = input.next();
        workRoom.addThingy(new Thingy(name, category));
    }

    // EFFECTS: prompts user to select category and returns it
    private Category readCategory() {
        System.out.println("Please select a category for your thingy");

        int menuLabel = 1;
        for (Category c : Category.values()) {
            System.out.println(menuLabel + ": " + c);
            menuLabel++;
        }

        int menuSelection = input.nextInt();
        return Category.values()[menuSelection - 1];
    }

    // EFFECTS: prints all the thingies in workroom to the console
    private void printThingies() {
        List<Thingy> thingies = workRoom.getThingies();

        for (Thingy t : thingies) {
            System.out.println(t);
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + workRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
