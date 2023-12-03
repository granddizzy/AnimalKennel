public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();
        Kennel kennel = new Kennel(view, log);

        kennel.start();
    }
}
