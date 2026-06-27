class Logger{

    private static Logger instance;

    // this private constructor prevent the external object creation
    private Logger(){
        System.out.println("Logger instance created");
    }

    // this provide static method to get an instance.
    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }
}

public class SingletonPatternExample {
    public static void main(String[] args) {

        // i created multiple instances og logger using getInstance().
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println(logger1 == logger2); // verified that all refernces point to the same object.
    }
}