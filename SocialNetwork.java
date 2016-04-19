import java.util.*;
/**
 * Heather Bradfield
 * 12-3-15
 */
public class SocialNetwork
{
    private ConnectedGraphInterface<Profile> socialNet;
    private TreeMap<String,Profile> allProfiles;
    private Profile admin;
    private Scanner scan;
    private Random generator = new Random();
    private final int MAX_WEIGHT = 10;

    public SocialNetwork() {

        this.socialNet = new UndirectedGraph<>();
        this.allProfiles = new TreeMap<>();
        this.scan = new Scanner(System.in);
        this.generator = new Random();
        this.admin = new Profile("Admin One","N","The Network","805-NET-WORK"); // main anchor to network
        hardCodedGraph();
        start();
    }

    public void hardCodedGraph()
    {
        // created 10 profiles
        Profile Mary = new Profile("Mary Two", "Y", "Ventura","805-234-4567");
        Profile Joe = new Profile("Joe Three","N","Oxnard","805-345-6789");
        Profile Albert = new Profile("Albert Four", "Y", "Camarillo", "805-111-1122");
        Profile Brett = new Profile("Brett Five", "N", "Thousand Oaks", "818-200-4000");
        Profile Conner = new Profile("Conner Six", "Y", "Moorpark", "805-987-6543");
        Profile Dylan = new Profile("Dylan Seven", "N", "Ventura", "805-654-3210");
        Profile Ethan = new Profile("Ethan Eight", "N", "Newbury Park", "805-529-9084");
        Profile Frank = new Profile("Frank Nine", "N", "Camarillo", "805-818-9023");
        Profile Grace = new Profile("Grace Ten", "Y", "Thousand Oaks", "818-432-9087");

        // added each profile to map
        this.allProfiles.put(this.admin.getName(), this.admin);
        this.allProfiles.put(Mary.getName(), Mary);
        this.allProfiles.put(Joe.getName(), Joe);
        this.allProfiles.put(Albert.getName(),Albert);
        this.allProfiles.put(Brett.getName(),Brett);
        this.allProfiles.put(Conner.getName(),Conner);
        this.allProfiles.put(Dylan.getName(),Dylan);
        this.allProfiles.put(Ethan.getName(),Ethan);
        this.allProfiles.put(Frank.getName(),Frank);
        this.allProfiles.put(Grace.getName(),Grace);

        // created 10 vertexes, one for each profile
        this.socialNet.addVertex(this.admin);
        this.socialNet.addVertex(Joe);
        this.socialNet.addVertex(Mary);
        this.socialNet.addVertex(Albert);
        this.socialNet.addVertex(Brett);
        this.socialNet.addVertex(Conner);
        this.socialNet.addVertex(Dylan);
        this.socialNet.addVertex(Ethan);
        this.socialNet.addVertex(Frank);
        this.socialNet.addVertex(Grace);

        // connect profiles with other profiles
        this.socialNet.addEdge(this.admin,Joe,this.generator.nextInt(MAX_WEIGHT) + 1);
        this.socialNet.addEdge(Mary,Joe,this.generator.nextInt(MAX_WEIGHT) + 1);
        Mary.addFriend(Joe);
        Joe.addFriend(Mary);
        this.socialNet.addEdge(Mary,Albert,this.generator.nextInt(MAX_WEIGHT) + 1);
        Mary.addFriend(Albert);
        Albert.addFriend(Mary);
        this.socialNet.addEdge(Mary,Brett,this.generator.nextInt(MAX_WEIGHT) + 1);
        Mary.addFriend(Brett);
        Brett.addFriend(Mary);
        this.socialNet.addEdge(this.admin,Conner,this.generator.nextInt(MAX_WEIGHT) + 1);
        this.socialNet.addEdge(Albert,Dylan);
        Albert.addFriend(Dylan);
        Dylan.addFriend(Albert);
        this.socialNet.addEdge(this.admin,Ethan,this.generator.nextInt(MAX_WEIGHT) + 1);
        this.socialNet.addEdge(Dylan,Frank);
        Dylan.addFriend(Frank);
        Frank.addFriend(Dylan);
        this.socialNet.addEdge(Frank,Brett,this.generator.nextInt(MAX_WEIGHT) + 1);
        Frank.addFriend(Brett);
        Brett.addFriend(Frank);
        this.socialNet.addEdge(Brett,Grace,this.generator.nextInt(MAX_WEIGHT) + 1);
        Brett.addFriend(Grace);
        Grace.addFriend(Brett);
    }

    public void start()
    {
        String firstName, lastName, status, home, phone;
        System.out.println("Welcome to the VCNetwork!");
        System.out.println("Let's create your very own profile!");
        System.out.println();
        do {
            System.out.println("Please enter your first and last name: ");
            firstName = this.scan.next();
            lastName = this.scan.next();
        }while (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+"));

        do {
            System.out.println("Are you in a relationship?(Y/N): ");
            status = this.scan.next();
        } while(!status.equalsIgnoreCase("y") && !status.equalsIgnoreCase("n"));

        System.out.println("What city do you live in?: ");
        {
            home = this.scan.next();
        }
        System.out.println("Enter your phone number(###-###-####): ");
        {
            phone = this.scan.next();
        }
        Profile user = new Profile((firstName + " " + lastName),status,home,phone);
        this.allProfiles.put(user.getName(), user);
        boolean added = this.socialNet.addVertex(user);
        if (added) {
            System.out.println("Your profile has been added to the network!\n");
            this.socialNet.addEdge(this.admin,user); // insure graph is connected
        }

        else
            System.out.println("An error occurred\n");
        String command;
        do{
            this.scan = new Scanner(System.in);
            System.out.println("\nEnter a command (Modify / Search / FindFriends / ListFriends / ShortPath / Emergency / LogOff): ");
            command = this.scan.next().toLowerCase();
            switch (command)
            {
                case "modify":
                    modify(user);
                    break;
                case "search":
                    System.out.println("Would you like to search for people by city or relationship status? (C/R): ");
                    String criteria = this.scan.next();
                    search(criteria);
                    break;
                case "findfriends":
                    System.out.println("Search for a friend on the network (First + \"  \" + Last): ");
                    firstName = this.scan.next();
                    lastName = this.scan.next();
                    String friendName = firstName + " " + lastName;
                    find(user,friendName);
                    break;
                case "listfriends":
                    System.out.println("These are the friends of your friends");
                    friendsList(user);
                    break;
                case "shortpath":
                    Profile profA, profB;
                    do {
                        this.scan = new Scanner(System.in);
                        System.out.println("Enter two people on the network: ");
                        String personA = scan.nextLine();
                        String personB = scan.nextLine();
                        profA = this.allProfiles.get(personA.toUpperCase());
                        profB = this.allProfiles.get(personB.toUpperCase());
                    }while(profA == null || profB == null);
                    findShortestPath(profA,profB,user);
                    break;
                case "emergency":
                    System.out.println("Here's the emergency phone chain: ");
                    System.out.println();
                    Queue<Profile> everyone = this.socialNet.getDepthFirstTraversal(user);
                    everyone.poll(); //remove yourself
                    System.out.println("You should first call: ");
                    while (everyone.size() > 0)
                    {
                        Profile person = everyone.poll();
                        System.out.println(person.getName() + " --> " + person.getPhone());
                    }
                    break;
                case "logoff":
                    System.out.println("\nLogging Off");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }while(!command.equalsIgnoreCase("LogOff"));

    }
    public void modify(Profile user)
    {
        System.out.println("What would you like to modify? (Name / Relationship / City / Phone): ");
        String modify = this.scan.next().toLowerCase();
        switch (modify)
        {
            case "name":
                System.out.println("Enter new name (First + \" \" + Last): ");
                String newFirst = this.scan.next();
                String newLast = this.scan.next();
                user.setName(newFirst + " " + newLast);
                break;
            case "relationship":
                System.out.println("Are you in a relationship?(Y/N): ");
                String newStatus = this.scan.next();
                user.setStatus(newStatus);
                break;
            case "city":
                System.out.println("Where do you live now?: ");
                String newCity = this.scan.next();
                user.setResidence(newCity);
                break;
            case "phone":
                System.out.println("What is your new phone number?: ");
                String newPhone = this.scan.next();
                user.setPhone(newPhone);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }

    }

    public void search(String criteria)
    {
        this.scan = new Scanner(System.in);
        boolean found = false;
        if (criteria.equalsIgnoreCase("c"))
        {
            System.out.println("Enter a city to search for: ");
            String city = this.scan.nextLine();
            for(Map.Entry<String,Profile> user : this.allProfiles.entrySet())
            {
                String key = user.getKey();
                Profile prof = user.getValue();
                if (prof.getResidence().equalsIgnoreCase(city))
                {
                    System.out.println(key);
                    found = true;
                }
            }
        }
        else if (criteria.equalsIgnoreCase("r"))
        {
            System.out.println("People who are not in a relationship:");

            for(Map.Entry<String,Profile> user : this.allProfiles.entrySet())
            {
                String key = user.getKey();
                Profile prof = user.getValue();
                if(prof.getStatus().equalsIgnoreCase("n"))
                {
                    System.out.println(key);
                    found = true;
                }
            }
        }

        if(!found)
        {
            System.out.println("No results");
        }
    }

    public void find(Profile user, String name)
    {
        int weight;
        name = name.toUpperCase();
        Profile prof = this.allProfiles.get(name);
        if (prof != null)
        {
            System.out.println("Would you like to add " + name + " to your friend list? (Y/N): ");
            String answer = this.scan.next();
            if (answer.equalsIgnoreCase("y"))
            {
                user.addFriend(prof);
                prof.addFriend(user);
                System.out.println("You friended " + name);
                weight = this.generator.nextInt(MAX_WEIGHT) + 1;
                // random distance between their places of residence (didn't have time to make county graph)
                socialNet.addEdge(user,prof,weight);
            }
        }
        else
        {
            System.out.println(name + " is not part of the network");
        }
    }

    public void friendsList(Profile user)
    {
        ArrayList<Profile> myFriends = user.getFriends();
        for(Profile friends : myFriends)
        {
            System.out.println("*** " + friends.getName() + "'s Friends ***");
            ArrayList<Profile> ff = friends.getFriends();
            for(Profile theirFriends : ff)
            {
                System.out.println(theirFriends.getName());
            }
            System.out.println();
        }
    }

    public void findShortestPath(Profile person1, Profile person2, Profile user)
    {
        this.scan = new Scanner(System.in); // my scanners were acting up
        Stack<Profile> shortPath = new Stack<>();
        double shortestPath = this.socialNet.getShortestPath(person1,person2,shortPath);
        System.out.println("The shortest path is " + shortestPath + " miles.");
        System.out.println("The path: " + shortPath.toString());
        System.out.println("Would you like to add one of these friends? (Y/N): ");
        String answer = scan.next();
        this.scan = new Scanner(System.in);
        if (answer.equalsIgnoreCase("y"))
        {
            System.out.println("Enter a name (First + Last): ");
            answer = scan.nextLine();
            Profile newFriend = this.allProfiles.get(answer.toUpperCase());
            if (newFriend != null) {
                user.addFriend(newFriend);
                newFriend.addFriend(user);
                System.out.println("You friended " + newFriend.getName());
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetwork();
    }
}
